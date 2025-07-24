export type Task = {
    id: string;
    title: string;
    description: string;
    status: string;
    createdBy: { id: string; username: string };
    assignedTo?: { id: string; username: string } | null;
    component: {
        id: string;
        name: string;
        project: {
            id: string;
            name: string;
        };
    };
};
