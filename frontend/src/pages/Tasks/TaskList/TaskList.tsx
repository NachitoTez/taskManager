import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getTasks } from '../../../services/taskService';
import type { Task } from '../interfaces/interfaces.ts';
import './TasksList.scss';

export default function TaskList() {
    const [tasks, setTasks] = useState<Task[]>([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        getTasks()
            .then(setTasks)
            .catch(err => console.error('Error cargando tareas:', err))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <p>Cargando tareas...</p>;

    const tasksByStatus = tasks.reduce<Record<string, Task[]>>((acc, task) => {
        acc[task.status] = acc[task.status] || [];
        acc[task.status].push(task);
        return acc;
    }, {});

    return (
        <div className="task-wrapper">
            {Object.entries(tasksByStatus).map(([status, group]) => (
                <div className="task-group" key={status}>
                    <div className="task-group-header">
                        <span>{status}</span>
                        <span>{group.length}</span>
                    </div>
                    <ul className="task-list">
                        {group.map((task) => (
                            <li
                                key={task.id}
                                className="task-item"
                                onClick={() => navigate(`/tasks/${task.id}`)}
                            >
                                <span className="task-id">{task.id}</span>
                                <span className="task-title">{task.title}</span>
                                <span className="task-date">
                  {task.component?.project?.name || '-'}
                </span>
                            </li>
                        ))}
                    </ul>
                </div>
            ))}
        </div>
    );
}
