import './Navbar.scss';
import { useState } from 'react';
import CreateModal from '../Modal/CreateModal.tsx';

export default function Navbar() {
    const [showModal, setShowModal] = useState(false);

    return (
        <>
            <header className="navbar">
                <div className="nav-left-container">
                    <div className="nav-title">Task Manager</div>
                    <div className="nav-filters">
                        <button className="active">All</button>
                        <button>Active</button>
                        <button>Backlog</button>
                    </div>
                </div>
                <div className="create-container">
                    <button onClick={() => setShowModal(true)}>Create task</button>
                </div>
            </header>

            {showModal && (
                <CreateModal
                    type="task"
                    onClose={() => setShowModal(false)}
                    onSuccess={() => window.location.reload()}
                />
            )}
        </>
    );
}
