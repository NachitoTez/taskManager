import {useForm} from 'react-hook-form';
import {z} from 'zod';
import {zodResolver} from '@hookform/resolvers/zod';
import {Link, useNavigate} from 'react-router-dom';
import {login, register as registerService} from '../../services/authService';
import './Auth.scss';
import logo from '../../assets/lemon.svg';
import {useState} from 'react';
import {useAuth} from "../../context/useAuth.ts";
import {LOGGER} from "../../services/logger.ts";

const baseSchema = z.object({
    email: z.email('Email inválido'),
    password: z.string().min(6, 'La contraseña debe tener al menos 6 caracteres'),
});

const registerSchema = baseSchema.extend({
    confirmPassword: z.string(),
    role: z.enum(['MEMBER', 'MANAGER']),
}).refine((data) => data.password === data.confirmPassword, {
    path: ['confirmPassword'],
    message: 'Las contraseñas no coinciden',
});

type LoginForm = z.infer<typeof baseSchema>;
type RegisterForm = z.infer<typeof registerSchema>;
type FormData = LoginForm | RegisterForm;

type AuthProps = {
    isLogin: boolean;
};

export default function Auth({ isLogin }: AuthProps) {
    const { login: authLogin } = useAuth();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<FormData>({
        resolver: zodResolver(isLogin ? baseSchema : registerSchema),
    });

    const onSubmit = async (data: FormData) => {
        setLoading(true);
        try {
            const payload = {
                username: data.email,
                password: data.password,
                role: 'role' in data ? data.role : "MEMBER",
            };
            const response = isLogin ? await login(payload) : await registerService(payload);
            authLogin(response.token);
            if (isLogin) {
                navigate('/tasks');
            } else {
                navigate('/tasks', {
                    state: {
                        welcome: '¡Bienvenido/a! 🎉',
                    },
                });
            }
        } catch (err) {
            console.error('Error en autenticación:', err);
            LOGGER.error(`Error en login/register: ${JSON.stringify(err)}`);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-wrapper">
            <img src={logo} alt="Lemon logo" className="logo"/>
            <div className="login-box">
                <div className="login-box-title">
                    <h1>{isLogin ? 'Bienvenido/a' : 'Crea tu cuenta'}</h1>
                    <h2>Lemon Task Manager</h2>
                </div>
                <div className="login-box-body">
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <div>
                            <input type="email" placeholder="Email" {...register('email')} />
                            {errors.email && <span>{errors.email.message}</span>}
                        </div>
                        <div>
                            <input type="password" placeholder="Contraseña" {...register('password')} />
                            {errors.password && <span>{errors.password.message}</span>}
                        </div>
                        {!isLogin && (
                            <>
                                <div>
                                    <input
                                        type="password"
                                        placeholder="Repetir contraseña"
                                        {...register('confirmPassword')}
                                    />
                                    {'confirmPassword' in errors && errors.confirmPassword && (
                                        <span>{errors.confirmPassword.message}</span>
                                    )}
                                </div>
                                <div className="role-selector">
                                    <label>
                                        <input
                                            type="radio"
                                            value="MEMBER"
                                            defaultChecked
                                            {...register('role')}
                                        /> Member
                                    </label>
                                    <label>
                                        <input
                                            type="radio"
                                            value="MANAGER"
                                            {...register('role')}
                                        /> Manager
                                    </label>
                                </div>
                            </>
                        )}

                        {!loading ? (
                            <button type="submit">{isLogin ? 'Iniciar sesión' : 'Registrarse'}</button>
                        ) : (
                            <div className="loader-wrapper">
                                <span className="spinner"/>
                            </div>
                        )}
                    </form>
                </div>
                <div className="login-box-footer">
                    <p>
                        {isLogin ? '¿No tenés cuenta? ' : '¿Ya tenés cuenta? '}
                        <Link to={isLogin ? '/register' : '/login'}>
                            {isLogin ? 'Registrate acá' : 'Iniciá sesión'}
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    );
}
