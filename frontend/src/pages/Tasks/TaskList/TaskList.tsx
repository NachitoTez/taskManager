import { useNavigate } from 'react-router-dom';
import './TasksList.scss'
const mockTasks = [
    { id: 'PEP-1', title: 'Set up your teams', count: 2, date: 'Jul 21' },
    { id: 'PEP-2', title: 'Connect your tools', count: 3, date: 'Jul 21' },
    { id: 'PEP-3', title: 'Import your data', count: 4, date: 'Jul 21' },
];

export default function TaskList() {
    const navigate = useNavigate();

    return (
        <div className="task-wrapper">

            <div className="task-group">
                <div className="task-group-header">
                    <span>Todo</span>
                    <span>{mockTasks.length}</span>
                </div>

                <ul className="task-list">
                    {mockTasks.map((task) => (
                        <li
                            key={task.id}
                            className="task-item"
                            onClick={() => navigate(`/tasks/${task.id}`)}
                        >
                            <span className="task-id">{task.id}</span>
                            <span className="task-title">
                {task.title} ({task.count})
              </span>
                            <span className="task-date">{task.date}</span>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}
