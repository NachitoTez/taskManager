import './Navbar.scss';

export default function Navbar() {

    return (
        <header className="navbar">
            <div className={"nav-left-container"}>
                <div className="nav-title">
                    Task Manager
                </div>
                <div className="nav-filters">
                    <button className="active">All</button>
                    <button>Active</button>
                    <button>Backlog</button>
                </div>
            </div>
            <div className="create-container">
                <button>
                    Create
                </button>
            </div>

        </header>
    );
}
