import { createContext, useContext } from 'react';
import type { Environment } from './Environment';

export type EnvironmentContextType = {
    environment: Environment | null;
    loading: boolean;
};

export const EnvironmentContext = createContext<EnvironmentContextType>({
    environment: null,
    loading: true,
});

export const useEnvironment = () => useContext(EnvironmentContext);
