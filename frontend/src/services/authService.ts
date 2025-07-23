import ApiInterceptor from './apiInterceptor.ts';

export const login = async (data: { username: string; password: string }) => {
    const response = await ApiInterceptor.post('/auth/login', data);
    return response.data;
};

export const register = async (data: { username: string; password: string; role: string }) => {
    const response = await ApiInterceptor.post('/auth/register', data);
    return response.data;
};
