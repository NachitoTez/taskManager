export type Environment = {
    userId: string;
    username: string;
    role: 'MANAGER' | 'MEMBER';
    users: string[];
};
