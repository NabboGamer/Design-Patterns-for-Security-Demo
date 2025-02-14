-- Connessione al database dpfsd
\connect dpfsd

-- Creazione del ruolo appuser solo se non esiste già
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'appuser') THEN
        CREATE ROLE appuser WITH LOGIN PASSWORD 'appPassword';
    END IF;
END;
$$;

-- Concessione del privilegio di CONNECT sul database
GRANT CONNECT ON DATABASE dpfsd TO appuser;

-- Concessione dell'utilizzo dello schema public
GRANT USAGE ON SCHEMA public TO appuser;

-- Concessione dei privilegi di SELECT, INSERT, UPDATE e DELETE su tutte le tabelle esistenti
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO appuser;

-- Impostazione dei privilegi di default per le tabelle future create nello schema public
ALTER DEFAULT PRIVILEGES IN SCHEMA public
  GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO appuser;

-- Concessione dei privilegi SELECT, INSERT, UPDATE, DELETE ON TABLE sulla tabella users
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE users TO appuser;
GRANT USAGE, SELECT ON SEQUENCE users_id_seq TO appuser;
