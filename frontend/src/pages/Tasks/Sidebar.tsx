import {  useNavigate } from 'react-router-dom';

function Sidebar() {
    const navigate = useNavigate();

    return (
        <div className="sidebar">

            <nav className="sidebar-nav">

                <div className={"nav-item"}
                     onClick={() => navigate('/projects')}>
                    <h3>Projects</h3>
                </div>

                <div className={"nav-item"}
                    onClick={() => navigate('/components')}>
                    <h3>Components</h3>
                </div>

                <div className={"nav-item"}
                    onClick={() => navigate('/tasks')}>
                    <h3>Tasks</h3>
                </div>
            </nav>
        </div>
    );
}

export default Sidebar;
