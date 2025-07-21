import axios from 'axios';

const API = 'http://localhost:8081';

export const login = async (data: { username: string; password: string }) => {
    const response = await axios.post(`${API}/auth/login`, data);
    return response.data;
};

export const register = async (data: { username: string; password: string }) => {
    const response = await axios.post(`${API}/auth/register`, data);
    return response.data;
};
