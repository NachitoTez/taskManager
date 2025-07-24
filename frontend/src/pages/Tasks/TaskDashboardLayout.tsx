import { Outlet, useLocation } from 'react-router-dom';
import { useEffect, useState } from 'react';
import './TaskDashboardLayout.scss';
import Toast from '../../components/Toast/Toast.tsx';
import Sidebar from "./Sidebar.tsx";
import Navbar from "../../components/Navbar/Navbar.tsx";

export default function TaskDashboardLayout() {
    const location = useLocation();
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


            <div className="container">
                <Sidebar />
                <main className="content">
                    <Navbar></Navbar>
                    <Outlet />
                </main>
            </div>
        </div>
    );
}
