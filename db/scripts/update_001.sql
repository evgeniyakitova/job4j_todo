CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email VARCHAR UNIQUE,
    password TEXT
);

CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    done BOOLEAN,
    user_id INT REFERENCES users(id)
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE
);

CREATE TABLE items_categories (
    id SERIAL PRIMARY KEY,
    item_id INT REFERENCES items(id),
    category_id INT REFERENCES categories(id)
);

INSERT INTO categories (name) VALUES ('Работа'), ('Дом'), ('Учёба');