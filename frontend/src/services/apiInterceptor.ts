import axios from 'axios';
import { LOGGER } from './logger';

const ApiInterceptor = axios.create({
    baseURL: 'http://localhost:8081',
});

ApiInterceptor.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

ApiInterceptor.interceptors.response.use(
    response => response,
    error => {
        const status = error.response?.status;
        const url = error.config?.url;

        if (status === 401) {
            LOGGER.warn(`401 no autorizado al acceder a ${url}`);
            localStorage.removeItem('token');
            window.location.href = '/login';
        }

        return Promise.reject(error);
    }
);

export default ApiInterceptor;
