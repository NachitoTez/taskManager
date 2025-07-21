import {useForm} from 'react-hook-form';
import {z} from 'zod';
import {zodResolver} from '@hookform/resolvers/zod';
import {useNavigate, Link} from 'react-router-dom';
import {login, register as registerService} from '../../services/authService';
import './Auth.scss';
import logo from '../../assets/lemon.svg';
import {useEffect, useState} from "react";

type AuthProps = {
    isLogin: boolean;
};

const schema = z.object({
    email: z.email({message: 'Email inválido'}),
    password: z.string().min(6, 'La contraseña debe tener al menos 6 caracteres'),
});

type FormData = z.infer<typeof schema>;

export default function Auth({isLogin}: AuthProps) {
    const navigate = useNavigate();

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        setError(null);
    }, [navigate]);

    const {
        register,
        handleSubmit,
        formState: {errors},
    } = useForm<FormData>({
        resolver: zodResolver(schema),
    });

    const onSubmit = async (data: FormData) => {
        setLoading(true);
        try {
            console.log("enviandooo")
            const payload = {
                username: data.email,
                password: data.password,
            };
            const response = isLogin ? await login(payload) : await registerService(payload);
            localStorage.setItem('token', response.token);
            console.log("yendo a tasks")
            navigate('/tasks');
        } catch (err) {
            console.error('Error en autenticación:', err);
            setError('Hubo un error al autenticar. Verificá tus credenciales o intenta más tarde.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-container">
            <img src={logo} alt="Lemon logo" className="logo"/>

            {error && (
                <div className="toast-error">
                    <span>{error}</span>
                    <button onClick={() => setError(null)}>×</button>
                </div>
            )}

            <div className="login-box">
                <div className="login-box-title">
                    <h1>{isLogin ? 'Accedé a tu cuenta' : 'Crea tu cuenta'}</h1>
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
                        {loading ? (
                            <div className="loader-wrapper">
                                <span className="spinner"/>
                            </div>
                        ) : (
                            <button type="submit">
                                {isLogin ? 'Iniciar sesión' : 'Registrarse'}
                            </button>
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

//TODO cosas por agregar:
//Confirmar contraseña, condiciones que debe cumplir y validacion en tiempo real
//Un context
//Toast para los errores (listo, ahora manejar especificamente cada error)
//El register en lugar de redirigir a login debería ir a tasks, total ya tiene el token
