INSERT INTO users (username, password, "name", "surname", "level", "companyIdentificationCode", "image")
VALUES ('mario.rossi','password', 'Mario', 'Rossi', 'Operaio', 'sqi9gP3zN6YXt3g', 'mario.rossi.jpeg')
    ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, password, "name", "surname", "level", "companyIdentificationCode", "image")
VALUES ('luigi.bianchi','1234', 'Luigi', 'Bianchi', 'Capo Reparto', 'kJ4hrSBYSXLRV4s', 'luigi.bianchi.jpeg')
    ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, password, "name", "surname", "level", "companyIdentificationCode", "image")
VALUES ('paolo.neri','5678', 'Paolo', 'Neri', 'Capo Sezione', 'LypezC6bVkAVn6h', 'paolo.neri.jpeg')
    ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, password, "name", "surname", "level", "companyIdentificationCode", "image")
VALUES ('vincenzo.gialli','4DcehensNh6hFEs', 'Vincenzo', 'Gialli', 'Amministratore delegato', 'see92z8cBx5fk8a', 'vincenzo.gialli.jpeg')
    ON CONFLICT (username) DO NOTHING;