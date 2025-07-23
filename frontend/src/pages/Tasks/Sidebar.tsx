import { useAuth } from '../../context/useAuth.ts';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Sidebar() {
    const { logout } = useAuth();
    const [open, setOpen] = useState(false);
    const navigate = useNavigate();

    return (
        <div className="sidebar">
            <div className="sidebar-top">
                <button onClick={() => setOpen(!open)} className="dropdown-toggle">
                    ☰
                </button>
                {open && (
                    <div className="dropdown-menu">
                        <button onClick={logout}>Cerrar sesión</button>
                    </div>
                )}
            </div>
            <nav className="sidebar-nav">
                <button onClick={() => navigate('/projects')}>Proyectos</button>
                <button onClick={() => navigate('/components')}>Componentes</button>
                <button onClick={() => navigate('/tasks')}>Tareas</button>
            </nav>
        </div>
    );
}

export default Sidebar;
