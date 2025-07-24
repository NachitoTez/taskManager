import ApiInterceptor from './apiInterceptor';
import type {Task} from "../pages/Tasks/interfaces/interfaces.ts";

export async function getTasks(): Promise<Task[]> {
    const response = await ApiInterceptor.get('/tasks');
    return response.data;
}

export type CreateTaskRequest = {
    title: string;
    description: string;
    assign: string;
};

export async function createTask(request: CreateTaskRequest): Promise<Task> {
    const response = await ApiInterceptor.post('/tasks', request);
    return response.data;
}

export async function createProject(request: { name: string; members: null }) {
    return ApiInterceptor.post('/tasks/projects', request);
}

export async function createComponent(request: { name: string; projectId: string }) {
    return ApiInterceptor.post('/tasks/components', request);
}