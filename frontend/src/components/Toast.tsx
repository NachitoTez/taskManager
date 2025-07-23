import './Toast.scss';

interface Props {
    message: string;
    onClose: () => void;
}

export default function Toast({ message, onClose }: Props) {
    return (
        <div className={"toast"} onClick={onClose}>
            {message}
        </div>
    );
}
