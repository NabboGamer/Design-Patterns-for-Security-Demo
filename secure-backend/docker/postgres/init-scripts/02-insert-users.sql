INSERT INTO users (username, password, "role", "name", "surname", "level", "companyIdentificationCode", "image", "email", "phone", "address")
VALUES ('mario.rossi','password', 'USER','Mario', 'Rossi', 'OPERAIO', 'sqi9gP3zN6YXt3g', 'mario.rossi.jpeg', 'mario.rossi@gmail.com', '333666777', 'Via Mazzini n.36, Potenza')
    ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, password, "role", "name", "surname", "level", "companyIdentificationCode", "image", "email", "phone", "address")
VALUES ('luigi.bianchi','1234', 'USER','Luigi', 'Bianchi', 'CAPO_REPARTO', 'kJ4hrSBYSXLRV4s', 'luigi.bianchi.jpeg', 'luigi.bianchi@gmail.com', '333777888', 'Piazza 18 Agosto n.18, Potenza')
    ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, password, "role", "name", "surname", "level", "companyIdentificationCode", "image", "email", "phone", "address")
VALUES ('paolo.neri','5678', 'USER','Paolo', 'Neri', 'CAPO_SEZIONE', 'LypezC6bVkAVn6h', 'paolo.neri.jpeg', 'paolo.neri@gmail.com', '333888999', 'Discesa San Gerardo n.31, Potenza')
    ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, password, "role", "name", "surname", "level", "companyIdentificationCode", "image", "email", "phone", "address")
VALUES ('vincenzo.gialli','4DcehensNh6hFEs', 'ADMIN','Vincenzo', 'Gialli', 'AMMINISTRATORE_DELEGATO', 'see92z8cBx5fk8a', 'vincenzo.gialli.jpeg', 'vincenzo.gialli@gmail.com', '333999000', 'Via Cavour n.53, Potenza')
    ON CONFLICT (username) DO NOTHING;