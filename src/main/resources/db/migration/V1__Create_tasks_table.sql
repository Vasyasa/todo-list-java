CREATE TABLE tasks (
                id SERIAL PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                description TEXT,
                completed BOOLEAN NOT NULL DEFAULT FALSE
);