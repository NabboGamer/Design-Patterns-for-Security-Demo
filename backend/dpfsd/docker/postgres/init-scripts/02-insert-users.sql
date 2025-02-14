INSERT INTO users (username, password) VALUES ('user1','password') ON CONFLICT (username) DO NOTHING;

INSERT INTO users (username, password) VALUES ('user2','1234') ON CONFLICT (username) DO NOTHING;