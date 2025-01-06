DROP TABLE IF EXISTS transazione CASCADE;
DROP TABLE IF EXISTS categoria CASCADE;
DROP TABLE IF EXISTS utente CASCADE;

CREATE TABLE utente (
                        email VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        username VARCHAR(255) NOT NULL PRIMARY KEY,
                        saldo DOUBLE NOT NULL
);

CREATE TABLE categoria (
                           id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                           nome VARCHAR(255) NOT NULL,
                           fk_utente VARCHAR(255),
                           FOREIGN KEY (fk_utente) REFERENCES utente (username)
);

CREATE TABLE transazione (
                             id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                             causale VARCHAR(255) NOT NULL,
                             data DATE NOT NULL,
                             importo DOUBLE NOT NULL,
                             nome_categoria VARCHAR(255) NOT NULL,
                             fk_categoria BIGINT,
                             fk_utente VARCHAR(255) NOT NULL,
                             FOREIGN KEY (fk_categoria) REFERENCES categoria (id),
                             FOREIGN KEY (fk_utente) REFERENCES utente (username)
);

-- Inserimento dati
INSERT INTO utente (email, password, username, saldo)
VALUES ('testuser@example.com', 'hashedpassword', 'Test1', 1000.0);

INSERT INTO categoria (nome, fk_utente)
VALUES ('categoria', 'Test1');

INSERT INTO transazione (causale, data, importo, nome_categoria, fk_categoria, fk_utente)
VALUES ('Test causale', '2025-01-06', 200.0, 'categoria', 1, 'Test1');
