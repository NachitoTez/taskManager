import { Outlet, useLocation, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useAuth } from '../../context/useAuth';
import './TaskDashboardLayout.scss';
import Toast from '../../components/Toast';

export default function TaskDashboardLayout() {
    const location = useLocation();
    const navigate = useNavigate();
    const { logout } = useAuth();
    const [welcomeMsg, setWelcomeMsg] = useState<string | null>(null);

    useEffect(() => {
        if (location.state?.welcome) {
            setWelcomeMsg(location.state.welcome);
            const timer = setTimeout(() => setWelcomeMsg(null), 3000);
            return () => clearTimeout(timer);
        }
    }, [location.state]);

    return (
        <div className="layout">
            {welcomeMsg && <Toast message={welcomeMsg} onClose={() => setWelcomeMsg(null)} />}

            <header className="navbar">
                <div className="userArea">
                    <button onClick={logout}>Cerrar sesión</button>
                </div>
                <div className="filtersArea">
                    Filtros
                </div>
            </header>

            <div className="container">
                <aside className="sidebar">
                    <nav className="nav">
                        <ul>
                            <li><button onClick={() => navigate('/tasks/projects')}>Projects</button></li>
                            <li><button onClick={() => navigate('/tasks/components')}>Components</button></li>
                            <li><button onClick={() => navigate('/tasks')}>Tasks</button></li>
                        </ul>
                    </nav>
                </aside>

                <main className="content">
                    <Outlet />
                </main>
            </div>
        </div>
    );
}
