import { useState } from 'react';
import { createTask, createComponent, createProject } from '../../services/taskService';
import './CreateModal.scss';

type ModalType = 'project' | 'component' | 'task';

type Props = {
    type: ModalType;
    onClose: () => void;
    onSuccess?: () => void;
};

export default function CreateModal({ type, onClose, onSuccess }: Props) {
    const [form, setForm] = useState({
        name: '',
        title: '',
        description: '',
        projectName: '',
        assign: ''
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            if (type === 'project') {
                await createProject({ name: form.name, members: null }); // TODO
            } else if (type === 'component') {
                await createComponent({ name: form.name, projectId: form.projectName });
            } else {
                await createTask({
                    title: form.title,
                    description: form.description,
                    assign: "",
                });
            }

            onClose();
            onSuccess?.();
        } catch (err) {
            console.error('Error creando', type, err);
        }
    };

    const renderFields = () => {
        switch (type) {
            case 'project':
                return (
                    <input
                        placeholder="Nombre del proyecto"
                        name="name"
                        value={form.name}
                        onChange={handleChange}
                        required
                    />
                );
            case 'component':
                return (
                    <>
                        <input
                            placeholder="Nombre del componente"
                            name="name"
                            value={form.name}
                            onChange={handleChange}
                            required
                        />
                        <input
                            placeholder="ID del proyecto asociado"
                            name="projectId"
                            value={form.projectName}
                            onChange={handleChange}
                            required
                        />
                    </>
                );
            case 'task':
                return (
                    <>
                        <input
                            placeholder="Title"
                            name="title"
                            value={form.title}
                            onChange={handleChange}
                            required
                        />
                        <textarea
                            placeholder="Description"
                            name="description"
                            value={form.description}
                            onChange={handleChange}
                        />
                    </>
                );
        }
    };

    return (
        <div className="modal-backdrop" onClick={onClose}>
            <div className="modal" onClick={(e) => e.stopPropagation()}>
                <h2>Crear {type}</h2>
                <form onSubmit={handleSubmit}>
                    {renderFields()}
                    <div className="modal-buttons">
                        {/*TODO podría tener un estilo global para los botones primarios y secundarios*/}
                        <button type="button" onClick={onClose}>Cancelar</button>
                        <button type="submit">Crear</button>
                    </div>
                </form>
            </div>
        </div>
    );
}
