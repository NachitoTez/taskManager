import { Outlet, Navigate } from 'react-router-dom';

// TODO: Reemplazar con validación real del auth context
const isAuthenticated = false;

function ProtectedLayout() {
    return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
}

export default ProtectedLayout;
