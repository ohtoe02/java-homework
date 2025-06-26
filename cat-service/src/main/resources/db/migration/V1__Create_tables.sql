-- Создание таблицы пользователей
CREATE TABLE users (
    telegram_id BIGINT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    username VARCHAR(255)
);

-- Создание таблицы котиков
CREATE TABLE cats (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    photo_url VARCHAR(500) NOT NULL,
    description TEXT,
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    likes INTEGER DEFAULT 0,
    dislikes INTEGER DEFAULT 0,
    FOREIGN KEY (owner_id) REFERENCES users(telegram_id)
);

-- Индексы для быстрого поиска
CREATE INDEX idx_cats_owner_id ON cats(owner_id);
CREATE INDEX idx_cats_created_at ON cats(created_at); 