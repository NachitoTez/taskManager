import {  useNavigate } from 'react-router-dom';
import logo from "../../assets/lemon.svg";
import {useAuth} from "../../context/useAuth.ts";

function Sidebar() {
    const navigate = useNavigate();
    const { logout } = useAuth();

    return (
        <div className="sidebar">

            <nav className="sidebar-nav">

                <div className={"logo"}>
                    <img src={logo} alt="Lemon logo" className="logo"/>
                </div>
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

                <div className="userArea">
                    <button onClick={logout}>Cerrar sesión</button>
                </div>

            </nav>
        </div>
    );
}

export default Sidebar;
