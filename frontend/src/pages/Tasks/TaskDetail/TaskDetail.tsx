import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './TaskDetail.scss';
import NotFound from "../../../components/NotFound/NotFound.tsx";

type User = {
    id: string;
    username: string;
};

type Project = {
    id: string;
    name: string;
};

type Component = {
    id: string;
    name: string;
    project: Project;
};

type Task = {
    id: string;
    title: string;
    description: string;
    status: string;
    createdBy: User;
    assignedTo: User | null;
    component: Component;
};

export default function TaskDetail() {
    const { taskId } = useParams();
    const [task, setTask] = useState<Task | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetch(`/api/tasks/${taskId}`)
            .then(res => res.json())
            .then(setTask)
            .finally(() => setLoading(false));
    }, [taskId]);

    if (loading) return <p>Cargando tarea...</p>;
    if (!task) return <NotFound />;

    return (
        <div className="task-detail">
            <h1>{task.title}</h1>
            <p><strong>Descripción:</strong> {task.description}</p>
            <p><strong>Estado:</strong> {task.status}</p>
            <p><strong>Proyecto:</strong> {task.component.project.name}</p>
            <p><strong>Componente:</strong> {task.component.name}</p>
            <p><strong>Creado por:</strong> {task.createdBy.username}</p>
            <p><strong>Asignado a:</strong> {task.assignedTo ? task.assignedTo.username : 'No asignado'}</p>
        </div>
    );
}
