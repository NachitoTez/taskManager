import ApiInterceptor from './apiInterceptor.ts';
import type { Environment } from '../context/Environment.ts';


export const login = async (data: { username: string; password: string }) => {
    const response = await ApiInterceptor.post('/auth/login', data);
    return response.data;
};

export const register = async (data: { username: string; password: string; role: string }) => {
    const response = await ApiInterceptor.post('/auth/register', data);
    return response.data;
};

export async function getEnvironment(): Promise<Environment> {
    const response = await ApiInterceptor.get('/auth/environment');
    return response.data;
}