CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE assets (
                        id SERIAL PRIMARY KEY,
                        ticker VARCHAR(10) NOT NULL,
                        market VARCHAR(50) NOT NULL,
                        currency VARCHAR(3) NOT NULL,
                        user_id INT NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE portfolio (
                           id SERIAL PRIMARY KEY,
                           asset_id INT NOT NULL,
                           user_id INT NOT NULL,
                           quantity NUMERIC(15, 2) NOT NULL,
                           average_price NUMERIC(15, 2) NOT NULL,
                           total_invested NUMERIC(15, 2) NOT NULL,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE dividends (
                           id SERIAL PRIMARY KEY,
                           asset_id INT NOT NULL,
                           user_id INT NOT NULL,
                           payment_date DATE NOT NULL,
                           amount NUMERIC(15, 2) NOT NULL,
                           currency VARCHAR(3) NOT NULL,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              asset_id INT NOT NULL,
                              user_id INT NOT NULL,
                              type VARCHAR(10) NOT NULL, -- Ex: BUY, SELL, SPLIT
                              quantity NUMERIC(15, 2) NOT NULL,
                              price NUMERIC(15, 2) NOT NULL,
                              total NUMERIC(15, 2) NOT NULL,
                              transaction_date DATE NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
                              FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE exchange_rates (
                                id SERIAL PRIMARY KEY,
                                currency_from VARCHAR(3) NOT NULL,
                                currency_to VARCHAR(3) NOT NULL,
                                rate NUMERIC(15, 6) NOT NULL,
                                date DATE NOT NULL,
                                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE price_alerts (
                              id SERIAL PRIMARY KEY,
                              asset_id INT NOT NULL,
                              user_id INT NOT NULL,
                              target_price NUMERIC(15, 2) NOT NULL,
                              direction VARCHAR(10) NOT NULL, -- Ex: ABOVE, BELOW
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (asset_id) REFERENCES assets(id) ON DELETE CASCADE,
                              FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE reports (
                         id SERIAL PRIMARY KEY,
                         user_id INT NOT NULL,
                         report_type VARCHAR(50) NOT NULL, -- Ex: PERFORMANCE, ALLOCATION
                         generated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         file_path VARCHAR(255) NOT NULL, -- Caminho para o arquivo gerado
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE fees (
                      id SERIAL PRIMARY KEY,
                      transaction_id INT NOT NULL,
                      fee_type VARCHAR(50) NOT NULL, -- Ex: BROKERAGE, TAX
                      amount NUMERIC(15, 2) NOT NULL,
                      currency VARCHAR(3) NOT NULL,
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE
);
