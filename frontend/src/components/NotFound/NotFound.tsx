import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { LOGGER } from '../../services/logger.ts';
import notFound from '../../assets/notFound.avif';
import './NotFound.scss'

export default function NotFound() {
    const navigate = useNavigate();

    useEffect(() => {
        LOGGER.warn(`Ruta inexistente accedida: ${window.location.pathname}`);
    }, []);

    return (
        <div className="not-found-container">
            <img src={notFound} alt="404 Not Found" className="not-found-img" />
            <button className="not-found-button" onClick={() => navigate('/tasks')}>
                Ir a la Home
            </button>
            <h1 className="not-found-title">Ups acá no es...</h1>
        </div>
    );
}
