import { Outlet, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { useAuth } from '../../context/useAuth';
import './TaskDashboardLayout.scss';
import Toast from '../../components/Toast';
import Sidebar from "./Sidebar.tsx";

export default function TaskDashboardLayout() {
    const location = useLocation();
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

            {/*TODO podría ser otro componente*/}
            <header className="navbar">
                <div className="userArea">
                    <button onClick={logout}>Cerrar sesión</button>
                </div>
                <div className="filtersArea">
                    Filtros
                </div>
            </header>

            <div className="container">
                <Sidebar />


                <main className="content">
                    <Outlet />
                </main>
            </div>
        </div>
    );
}
