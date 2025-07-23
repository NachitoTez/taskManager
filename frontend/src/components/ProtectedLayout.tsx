import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../context/useAuth';
import { LOGGER } from '../services/logger';

export default function ProtectedLayout() {
    const { isAuthenticated } = useAuth();

    if (!isAuthenticated) {
        LOGGER.warn('Intento de acceso a ruta protegida sin login');
        return <Navigate to="/login" replace />;
    }

    return <Outlet />;
}
