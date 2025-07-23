import { useEffect } from 'react';
import { LOGGER } from '../services/logger';

export default function NotFound() {
    useEffect(() => {
        LOGGER.warn(`Ruta inexistente accedida: ${window.location.pathname}`);
    }, []);

    return <h1 style={{ color: 'white' }}>404 - Página no encontrada</h1>;
}
