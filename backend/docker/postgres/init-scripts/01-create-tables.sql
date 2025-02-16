CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    "name" VARCHAR(50) NOT NULL,
    "surname" VARCHAR(50) NOT NULL,
    "level" VARCHAR(50) NOT NULL,
    "companyIdentificationCode" VARCHAR(50) NOT NULL,
    "image" TEXT NOT NULL
    );

-- Indice per migliorare le query per username
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);