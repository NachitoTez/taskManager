import { Route, Routes, Navigate } from 'react-router-dom';
import Login from './pages/Auth/Login.tsx';
import Register from './pages/Auth/Register.tsx';
import ProtectedLayout from './components/ProtectedLayout';
import TaskDashboardLayout from './pages/Tasks/TaskDashboardLayout';
// import ProjectList from './pages/Projects/ProjectList';
// import ComponentList from './pages/Components/ComponentList';
import TaskList from './pages/Tasks/TaskList/TaskList.tsx';
import TaskDetail from './pages/Tasks/TaskDetail/TaskDetail.tsx';
import NotFound from "./components/NotFound/NotFound.tsx";

function App() {
    return (
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            <Route index element={<Navigate to="/login" replace />} />

            <Route path="/" element={<ProtectedLayout />}>
                <Route path="/tasks" element={<TaskDashboardLayout />}>
                    <Route index element={<TaskList />} />
                    <Route path=":taskId" element={<TaskDetail />} />
                </Route>
            </Route>

            <Route path="*" element={<NotFound />} />
        </Routes>

    );
}

export default App;
