const LOGGING_URL = 'http://localhost:8081/log';

type LogLevel = 'INFO' | 'WARN' | 'ERROR';

class BackendLogger {
    info(message: string) {
        this.postLogging('INFO', message);
    }

    warn(message: string) {
        this.postLogging('WARN', message);
    }

    error(message: string) {
        this.postLogging('ERROR', message);
    }

    private postLogging(logLevel: LogLevel, message: string) {

        fetch(LOGGING_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ logLevel, message }),
        })
            .then(({ ok, status }) =>
                console.log(`${ok ? 'Log enviado' : 'Error al loguear'}`, status)
            )
            .catch(error => console.log('Error en logger:', error));
    }
}

export const LOGGER = new BackendLogger();
