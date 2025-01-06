-- Dump per H2
-- Database: `testdb`

-- --------------------------------------------------------

-- Struttura della tabella `categoria`
CREATE TABLE categoria (
                           id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                           nome VARCHAR(255) NOT NULL,
                           fk_utente VARCHAR(255)
);

-- --------------------------------------------------------

-- Struttura della tabella `categoria_seq`
CREATE TABLE categoria_seq (
                               next_not_cached_value BIGINT NOT NULL,
                               minimum_value BIGINT NOT NULL,
                               maximum_value BIGINT NOT NULL,
                               start_value BIGINT NOT NULL,
                               increment BIGINT NOT NULL,
                               cache_size BIGINT NOT NULL,
                               cycle_option BOOLEAN NOT NULL,
                               cycle_count BIGINT NOT NULL
);

-- --------------------------------------------------------

-- Struttura della tabella `transazione`
CREATE TABLE transazione (
                             id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                             causale VARCHAR(255) NOT NULL,
                             data DATE NOT NULL,
                             importo DOUBLE NOT NULL,
                             nome_categoria VARCHAR(255) NOT NULL,
                             fk_categoria BIGINT,
                             fk_utente VARCHAR(255) NOT NULL
);

-- --------------------------------------------------------

-- Struttura della tabella `transazione_seq`
CREATE TABLE transazione_seq (
                                 next_not_cached_value BIGINT NOT NULL,
                                 minimum_value BIGINT NOT NULL,
                                 maximum_value BIGINT NOT NULL,
                                 start_value BIGINT NOT NULL,
                                 increment BIGINT NOT NULL,
                                 cache_size BIGINT NOT NULL,
                                 cycle_option BOOLEAN NOT NULL,
                                 cycle_count BIGINT NOT NULL
);

-- --------------------------------------------------------

-- Struttura della tabella `utente`
CREATE TABLE utente (
                        email VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        username VARCHAR(255) NOT NULL PRIMARY KEY,
                        saldo DOUBLE NOT NULL
);

-- --------------------------------------------------------

-- Inserimento dati nella tabella `utente`
INSERT INTO utente (email, password, username, saldo)
VALUES ('testuser@example.com', 'hashedpassword', 'Test1', 1000.0);

-- Inserimento dati nella tabella `categoria`
INSERT INTO categoria (id, nome, fk_utente)
VALUES (1, 'categoria', 'Test1');

-- Inserimento dati nella tabella `transazione`
INSERT INTO transazione (id, causale, data, importo, nome_categoria, fk_categoria, fk_utente)
VALUES (1, 'Test causale', '2025-01-06', 200.0, 'categoria', 1, 'Test1');

-- --------------------------------------------------------

-- Aggiunta delle chiavi esterne per la tabella `categoria`
ALTER TABLE categoria
    ADD CONSTRAINT FK_categoria_utente FOREIGN KEY (fk_utente) REFERENCES utente (username);

-- Aggiunta delle chiavi esterne per la tabella `transazione`
ALTER TABLE transazione
    ADD CONSTRAINT FK_transazione_categoria FOREIGN KEY (fk_categoria) REFERENCES categoria (id);

ALTER TABLE transazione
    ADD CONSTRAINT FK_transazione_utente FOREIGN KEY (fk_utente) REFERENCES utente (username);
