import { Route, Routes, Navigate } from 'react-router-dom';
import Login from './pages/Auth/Login.tsx';
import Register from './pages/Auth/Register.tsx';
import TasksList from './pages/TasksList/TasksList.tsx';
import ProtectedLayout from './components/ProtectedLayout';

function App() {
    return (
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/tasks" element={<ProtectedLayout />}>
                <Route index element={<TasksList />} />
            </Route>
            <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
    );
}

export default App;
