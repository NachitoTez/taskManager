import ApiInterceptor from './apiInterceptor';
import type {Task} from "../pages/Tasks/interfaces/interfaces.ts";

export async function getTasks(): Promise<Task[]> {
    const response = await ApiInterceptor.get('/tasks');
    return response.data;
}
