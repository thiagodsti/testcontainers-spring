CREATE TABLE IF NOT EXISTS todo (
    id SERIAL PRIMARY KEY,
    title varchar(255),
    description text,
    done boolean,
    created_at timestamp with time zone,
    completed_at timestamp with time zone
 );