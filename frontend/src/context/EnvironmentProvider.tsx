import { useEffect, useState } from 'react';
import type { Environment } from './Environment';
import { EnvironmentContext } from './EnvironmentContext';
import { getEnvironment } from '../services/authService';

export function EnvironmentProvider({ children }: { children: React.ReactNode }) {
    const [environment, setEnvironment] = useState<Environment | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getEnvironment()
            .then(setEnvironment)
            .catch(() => setEnvironment(null))
            .finally(() => setLoading(false));
    }, []);

    return (
        <EnvironmentContext.Provider value={{ environment, loading }}>
            {children}
        </EnvironmentContext.Provider>
    );
}
