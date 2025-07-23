import './TasksList.scss'
const mockTasks = [
    { id: 'PEP-1', title: 'Set up your teams', count: 2, date: 'Jul 21' },
    { id: 'PEP-2', title: 'Connect your tools', count: 3, date: 'Jul 21' },
    { id: 'PEP-3', title: 'Import your data', count: 4, date: 'Jul 21' },
];

export default function TaskList() {
    return (
        <div className="task-wrapper">
            {/* Filtros */}
            <div className="task-filters">
                <button className="active">All</button>
                <button>Active</button>
                <button>Backlog</button>
            </div>

            {/* Lista de tareas */}
            <div className="task-group">
                <div className="task-group-header">
                    <span>Todo</span>
                    <span>{mockTasks.length}</span>
                </div>

                <ul className="task-list">
                    {mockTasks.map((task) => (
                        <li key={task.id} className="task-item">
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
