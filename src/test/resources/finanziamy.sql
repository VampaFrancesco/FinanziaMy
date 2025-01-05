-- Dump per H2
-- Database: `testdb`

-- --------------------------------------------------------

-- Struttura della tabella `categoria`
CREATE TABLE categoria (
                           id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                           nome VARCHAR(255) NOT NULL,
                           fk_utente VARCHAR(255)
);

-- Dump dei dati per la tabella `categoria`
INSERT INTO categoria (id, nome, fk_utente) VALUES
                                                (5, 'motori', 'franvam'),
                                                (6, 'casa', 'franvam'),
                                                (7, 'spesa', 'franvam');

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

-- Dump dei dati per la tabella `categoria_seq`
INSERT INTO categoria_seq (next_not_cached_value, minimum_value, maximum_value, start_value, increment, cache_size, cycle_option, cycle_count) VALUES
    (1, 1, 9223372036854775806, 1, 50, 0, FALSE, 0);

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

-- Dump dei dati per la tabella `transazione`
INSERT INTO transazione (id, causale, data, importo, nome_categoria, fk_categoria, fk_utente) VALUES
    (83, 'v', '2025-01-08', 5, 'spesa', 7, 'franvam');

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

-- Dump dei dati per la tabella `transazione_seq`
INSERT INTO transazione_seq (next_not_cached_value, minimum_value, maximum_value, start_value, increment, cache_size, cycle_option, cycle_count) VALUES
    (101, 1, 9223372036854775806, 1, 50, 0, FALSE, 0);

-- --------------------------------------------------------

-- Struttura della tabella `utente`
CREATE TABLE utente (
                        email VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        username VARCHAR(255) NOT NULL PRIMARY KEY,
                        saldo DOUBLE NOT NULL
);

-- Dump dei dati per la tabella `utente`
INSERT INTO utente (email, password, username, saldo) VALUES
                                                          ('f@gmail.com', '$2a$10$ybqyoOOl89650E7P44Q9jO1qALMPbRd5ctgWFy2tgnZBNhpRQNpHa', 'franvam', 4290),
                                                          ('ss', '$2a$10$aJDlkQagzeCZcus3nVth2OcKKxQJuxU.S5AoPubHa.eB1RSgdwSny', 's', 499);

-- --------------------------------------------------------

-- Aggiunta delle chiavi esterne per la tabella `categoria`
ALTER TABLE categoria
    ADD CONSTRAINT FK_categoria_utente FOREIGN KEY (fk_utente) REFERENCES utente (username);

-- Aggiunta delle chiavi esterne per la tabella `transazione`
ALTER TABLE transazione
    ADD CONSTRAINT FK_transazione_categoria FOREIGN KEY (fk_categoria) REFERENCES categoria (id);

ALTER TABLE transazione
    ADD CONSTRAINT FK_transazione_utente FOREIGN KEY (fk_utente) REFERENCES utente (username);
