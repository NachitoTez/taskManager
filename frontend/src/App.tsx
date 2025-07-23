import { Route, Routes, Navigate } from 'react-router-dom';
import Login from './pages/Auth/Login.tsx';
import Register from './pages/Auth/Register.tsx';
import ProtectedLayout from './components/ProtectedLayout';
import TaskDashboardLayout from './pages/Tasks/TaskDashboardLayout';
// import ProjectList from './pages/Projects/ProjectList';
// import ComponentList from './pages/Components/ComponentList';
// import TaskList from './pages/Tasks/TaskList';
// import TaskDetail from './pages/Tasks/TaskDetail';

function App() {
    return (
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            <Route path="/" element={<ProtectedLayout />}>
                <Route path="tasks" element={<TaskDashboardLayout />}>
                    {/*<Route index element={<TaskList />} />*/}
                    {/*<Route path=":taskId" element={<TaskDetail />} />*/}
                    {/*<Route path="projects" element={<ProjectList />} />*/}
                    {/*<Route path="components" element={<ComponentList />} />*/}
                </Route>
            </Route>

            <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
    );
}

export default App;
