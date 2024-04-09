SELECT VERSION();

-- CREATE DATABASE IF NOT EXISTS MarketNexus;

--CONNECT MarketNexus;

--ALTER DATABASE MarketNexus SET TIMEZONE TO 'Europe/Rome';

DROP SCHEMA IF EXISTS MarketNexus CASCADE;

CREATE SCHEMA IF NOT EXISTS MarketNexus;

COMMENT ON SCHEMA MarketNexus IS 'MarketNexus SQL DataBase Schema.';

SELECT CURRENT_DATABASE();

SET SEARCH_PATH TO MarketNexus;

SELECT CURRENT_SCHEMA();

SELECT NOW();

DROP TYPE IF EXISTS ROLES;

CREATE TYPE ROLES AS ENUM (
    'SELLER',
    'BUYER',
    'SELLER_AND_BUYER'
    );

COMMENT ON TYPE ROLES IS 'MarketNexus Users Credentials Roles.';

CREATE
    OR REPLACE FUNCTION UPDATEDAT_SET_TIMESTAMP_FUNCTION() RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION UPDATEDAT_SET_TIMESTAMP_FUNCTION IS 'Function that allows to update the updated_at TIMESTAMP fields.';

CREATE TABLE IF NOT EXISTS MarketNexus.Nations
(
    id   SERIAL      NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    CONSTRAINT nations_name_unique UNIQUE (name),
    CONSTRAINT nations_id_min_value_check CHECK (MarketNexus.Nations.id >= 1),
    CONSTRAINT nations_name_min_length_check CHECK (LENGTH(MarketNexus.Nations.name) >= 3)
);

ALTER TABLE MarketNexus.Nations
    OWNER TO postgres;

COMMENT ON TABLE MarketNexus.Nations IS 'MarketNexus Users Nation origin.';

INSERT INTO MarketNexus.Nations (name)
VALUES ('Italy'),
       ('France'),
       ('Germany'),
       ('United States'),
       ('Spain'),
       ('United Kingdom'),
       ('Japan'),
       ('China'),
       ('India');


CREATE TABLE IF NOT EXISTS MarketNexus.product_categories
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    description VARCHAR(60) NOT NULL,
    CONSTRAINT productcategories_name_unique UNIQUE (name),
    CONSTRAINT productcategories_id_min_value_check CHECK (MarketNexus.product_categories.id >= 1),
    CONSTRAINT productcategories_name_min_length_check CHECK (LENGTH(MarketNexus.product_categories.name) >= 3)
);

ALTER TABLE MarketNexus.product_categories
    OWNER TO postgres;

COMMENT ON TABLE MarketNexus.product_categories IS 'MarketNexus Product Categories.';

INSERT INTO MarketNexus.product_categories (name, description)
VALUES ('Electronics', 'Electronics products'),
       ('Clothing', 'Clothing products'),
       ('Books', 'Books products'),
       ('Home Appliances', 'Home Appliances products'),
       ('Footwear', 'Footwear products'),
       ('Sports and Outdoors', 'Sports and Outdoors products'),
       ('Beauty and Personal Care', 'Beauty and Personal Care products'),
       ('Toys and Games', 'Toys and Games products'),
       ('Food and Grocery', 'Food and Grocery products');


CREATE TABLE IF NOT EXISTS MarketNexus.Products
(
    id                  SERIAL      NOT NULL PRIMARY KEY,
    name                VARCHAR(30) NOT NULL,
    description         VARCHAR(60) NOT NULL,
    price               FLOAT       NOT NULL,
    image_relative_path TEXT,
    category            INTEGER     NOT NULL,
    CONSTRAINT products_productcategories_fk FOREIGN KEY (category) REFERENCES MarketNexus.product_categories (id) ON DELETE CASCADE,
    CONSTRAINT products_id_min_value_check CHECK (MarketNexus.Products.id >= 1),
    CONSTRAINT products_name_min_length_check CHECK (LENGTH(MarketNexus.Products.name) >= 3),
    CONSTRAINT products_name_valid_check CHECK (MarketNexus.Products.name ~ '^[^\\\\/:*?"<>|]*$'::TEXT),
    CONSTRAINT products_price_min_value_check CHECK (MarketNexus.Products.price > 0),
    CONSTRAINT products_description_min_length_check CHECK (LENGTH(MarketNexus.Products.description) >= 3),
    CONSTRAINT products_imagerelativepath_min_length_check CHECK (LENGTH(MarketNexus.Products.image_relative_path) >= 3),
    CONSTRAINT products_imagerelativepath_min_valid_check CHECK (POSITION('/' IN MarketNexus.Products.image_relative_path) > 0),
    CONSTRAINT products_category_min_value_check CHECK (MarketNexus.Products.category >= 1)
);

ALTER TABLE MarketNexus.Products
    OWNER TO postgres;

COMMENT ON TABLE MarketNexus.Products IS 'MarketNexus Products.';

CREATE OR REPLACE FUNCTION MarketNexus.GENERATE_PRODUCT_RELATIVEIMAGEPATH_FUNCTION(product_id INT, product_name TEXT)
    RETURNS TEXT AS
$$
DECLARE
    base_path CONSTANT TEXT := '/images/products/';
    extension CONSTANT TEXT := '.jpeg';
BEGIN
    RETURN base_path || product_id || '/' || LOWER(product_name) || extension;
END;
$$
    LANGUAGE plpgsql;

CREATE
    OR REPLACE FUNCTION MarketNexus.INSERT_PRODUCT_RELATIVEIMAGEPATH_FUNCTION()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.image_relative_path = MarketNexus.GENERATE_PRODUCT_RELATIVEIMAGEPATH_FUNCTION(NEW.ID, NEW.name);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER product_imagerelativepath_trigger
    BEFORE INSERT
    ON MarketNexus.Products
    FOR EACH ROW
EXECUTE FUNCTION MarketNexus.INSERT_PRODUCT_RELATIVEIMAGEPATH_FUNCTION();


INSERT INTO MarketNexus.Products (name, price, description, category)
VALUES ('Smartphone', 599.99, 'High-end smartphone', 1),
       ('T-shirt', 29.99, 'Cotton T-shirt', 2),
       ('Java Programming Book', 49.99, 'Learn Java programming', 3),
       ('Laptop', 1299.99, 'Powerful laptop', 1),
       ('Running Shoes', 79.99, 'Lightweight running shoes', 2),
       ('Python Book', 39.99, 'Master Python programming', 3),
       ('Coffee Maker', 89.99, 'Automatic coffee maker', 4),
       ('Denim Jeans', 49.99, 'Classic denim jeans', 2),
       ('Fishing Rod', 39.99, 'Professional fishing rod', 6),
       ('Hair Dryer', 29.99, 'Ionic hair dryer', 7),
       ('Board Game', 19.99, 'Family board game', 8),
       ('Rice', 2.99, 'Long-grain white rice', 9);


CREATE OR REPLACE FUNCTION CHECK_ROLE_ROLES_ENUM_FUNCTION(role TEXT) RETURNS BOOLEAN AS
$$
BEGIN
    RETURN role IN (SELECT TEXT_ROLE::text
                    FROM (SELECT unnest(enum_range(NULL::ROLES)) AS TEXT_ROLE) as SubQuery);
END;
$$ LANGUAGE plpgsql;

CREATE TABLE IF NOT EXISTS MarketNexus.Credentials
(
    id       SERIAL      NOT NULL PRIMARY KEY,
    password VARCHAR(72) NOT NULL,
    username VARCHAR(10) NOT NULL,
    role     VARCHAR(20) DEFAULT 'SELLER_AND_BUYER',
    CONSTRAINT credentials_role_min_length_check CHECK (LENGTH(MarketNexus.Credentials.role) >= 3),
    CONSTRAINT credentials_role_valid_check CHECK (MarketNexus.CHECK_ROLE_ROLES_ENUM_FUNCTION(role)),
    CONSTRAINT credentials_username_min_length_check CHECK (LENGTH(MarketNexus.Credentials.username) >= 3),
    --CONSTRAINT credentials_username_valid__check CHECK (MarketNexus.Credentials.username ~ ''::TEXT),
    --CONSTRAINT credentials_password_valid__check CHECK (MarketNexus.Credentials.password),
    CONSTRAINT credentials_id_min_value_check CHECK (MarketNexus.Credentials.id >= 1)
);

COMMENT ON TABLE MarketNexus.Credentials IS 'MarketNexus Users Credentials.';

ALTER TABLE MarketNexus.Credentials
    OWNER TO postgres;

INSERT INTO MarketNexus.Credentials (username, password, role)
VALUES ('Lamb', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'SELLER_AND_BUYER'),
       ('Test1', '$2a$10$WprxEwx6mj231RuhiUZrxO2Hdnw1acKE/INs0B5Y9.5A1jMjainve', 'SELLER_AND_BUYER'),
       ('Musc', '$2a$10$eL/ln3CGVOdYbPJ4Faao.OeN46ZkP91e.h5pKOAGe08a1ICNGIzBW', 'SELLER_AND_BUYER');
-- Gabriel1

-- N.B. = La password è criptata da spring boot e arriva a 60 caratteri.

CREATE TABLE IF NOT EXISTS MarketNexus.Users
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    name        VARCHAR(30)                                                   NOT NULL,
    surname     VARCHAR(30)                                                   NOT NULL,
    email       VARCHAR(50)                                                   NOT NULL,
    birthdate   DATE,
    balance     FLOAT                                                         NOT NULL,
    credentials INTEGER                                                       NOT NULL,
    nation      INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    CONSTRAINT users_email_unique UNIQUE (email),
    CONSTRAINT users_credentials_fk FOREIGN KEY (credentials) REFERENCES MarketNexus.Credentials (id) ON DELETE CASCADE,
    CONSTRAINT users_nations_fk FOREIGN KEY (nation) REFERENCES MarketNexus.Nations (id) ON DELETE CASCADE,
    CONSTRAINT users_name_min_length_check CHECK (LENGTH(MarketNexus.Users.name) >= 3),
    CONSTRAINT users_id_min_value_check CHECK (MarketNexus.Users.id >= 1),
    CONSTRAINT users_surname_min_length_check CHECK (LENGTH(MarketNexus.Users.surname) >= 3),
    CONSTRAINT users_birthdate_min_value_check CHECK (MarketNexus.Users.birthdate >= (NOW() - INTERVAL '100 years')),
    CONSTRAINT users_birthdate_max_value_check CHECK (MarketNexus.Users.birthdate <= NOW()),
    CONSTRAINT users_email_min_length_check CHECK (LENGTH(MarketNexus.Users.email) >= 3),
    CONSTRAINT users_email_valid_check CHECK (MarketNexus.Users.email ~
                                              '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'::TEXT),
    CONSTRAINT users_balance_min_value_check CHECK (MarketNexus.Users.balance >= 0),
    CONSTRAINT users_balance_max_value_check CHECK (MarketNexus.Users.balance <= 10000),
    CONSTRAINT users_credentials_min_value_check CHECK (MarketNexus.Users.credentials >= 1),
    CONSTRAINT users_nation_min_value_check CHECK (MarketNexus.Users.nation >= 1),
    --CONSTRAINT users_insertedat_min_value_check CHECK (MarketNexus.Users.inserted_at >= NOW() - INTERVAL '1 minutes'),
    CONSTRAINT users_insertedat_updatedat_value_check CHECK (MarketNexus.Users.inserted_at <=
                                                             MarketNexus.Users.updated_at)
);

CREATE
    OR REPLACE TRIGGER users_updatedat_trigger
    BEFORE
        UPDATE
    ON MarketNexus.Users
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.UPDATEDAT_SET_TIMESTAMP_FUNCTION();

COMMENT ON TABLE MarketNexus.Users IS 'MarketNexus Users.';

ALTER TABLE MarketNexus.Users
    OWNER TO postgres;

INSERT INTO MarketNexus.Users (name, surname, email, birthdate, balance, credentials, nation)
VALUES ('Matteo', 'Lambertucci', 'matteolambertucci3@gmail.com', '2024-03-14', 22, 1, 1),
       ('Test', 'Test', 'test@test.it', '2024-03-17', 2, 2, 2),
       ('Gabriel', 'Muscedere', 'gabrielmuscedere@gmail.com', '2002-03-27', 0.1, 3, 6);


CREATE TABLE MarketNexus.Sales
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    quantity    INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    _user       INTEGER                                                       NOT NULL,
    product     INTEGER                                                       NOT NULL,
    CONSTRAINT user_product_insertedat_unique UNIQUE (_user, product, inserted_at),
    CONSTRAINT sale_users_fk FOREIGN KEY (_user) REFERENCES MarketNexus.Users (id) ON DELETE CASCADE,
    CONSTRAINT sale_products_fk FOREIGN KEY (product) REFERENCES MarketNexus.Products (id) ON DELETE CASCADE,
    CONSTRAINT sale_id_min_value_check CHECK (MarketNexus.Sales.id >= 1),
    CONSTRAINT sale_user_min_value_check CHECK (MarketNexus.Sales._user >= 1),
    CONSTRAINT sale_product_min_value_check CHECK (MarketNexus.Sales.product >= 1),
    CONSTRAINT sale_quantity_min_value_check CHECK (MarketNexus.Sales.quantity >= 0),
    -- CONSTRAINT sale_insertedat_min_value_check CHECK (MarketNexus.Sales.inserted_at >= NOW()),
    CONSTRAINT sale_insertedat_updatedat_value_check CHECK (MarketNexus.Sales.inserted_at <=
                                                            MarketNexus.Sales.updated_at)
);

CREATE
    OR REPLACE TRIGGER sale_updatedat_trigger
    BEFORE
        UPDATE
    ON MarketNexus.Sales
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.UPDATEDAT_SET_TIMESTAMP_FUNCTION();

COMMENT ON TABLE MarketNexus.Sales IS 'Publication of a Sales by the MarketNexus Users.';

ALTER TABLE MarketNexus.Sales
    OWNER TO postgres;

INSERT INTO MarketNexus.Sales(quantity, _user, product)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 3),
       (2, 2, 4),
       (2, 2, 5),
       (2, 2, 6),
       (2, 2, 7);


CREATE TABLE MarketNexus.Carts
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    quantity    INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    _user       INTEGER                                                       NOT NULL,
    sale        INTEGER                                                       NOT NULL,
    CONSTRAINT carts_user_sale_insertedat_unique UNIQUE (_user, sale, inserted_at),
    CONSTRAINT carts_users_fk FOREIGN KEY (_user) REFERENCES MarketNexus.Users (id) ON DELETE CASCADE,
    CONSTRAINT carts_sale_fk FOREIGN KEY (sale) REFERENCES MarketNexus.Sales (id) ON DELETE CASCADE,
    CONSTRAINT carts_id_min_value_check CHECK (MarketNexus.Carts.id >= 1),
    CONSTRAINT carts_user_min_value_check CHECK (MarketNexus.Carts._user >= 1),
    CONSTRAINT carts_sale_min_value_check CHECK (MarketNexus.Carts.sale >= 1),
    CONSTRAINT carts_quantity_min_value_check CHECK (MarketNexus.Carts.quantity >= 0),
    -- CONSTRAINT carts_insertedat_min_value_check CHECK (MarketNexus.Carts.inserted_at >= NOW()),
    CONSTRAINT carts_insertedat_updatedat_value_check CHECK (MarketNexus.Carts.inserted_at <=
                                                             MarketNexus.Carts.updated_at)
);

CREATE
    OR REPLACE TRIGGER carts_updatedat_trigger
    BEFORE
        UPDATE
    ON MarketNexus.Carts
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.UPDATEDAT_SET_TIMESTAMP_FUNCTION();

COMMENT ON TABLE MarketNexus.Carts IS 'User who puts a sale product in his cart.';

ALTER TABLE MarketNexus.Carts
    OWNER TO postgres;

INSERT INTO MarketNexus.Carts(quantity, _user, sale)
VALUES (1, 1, 6);


CREATE TABLE MarketNexus.Orders
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    quantity    INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    _user       INTEGER                                                       NOT NULL,
    cart        INTEGER                                                       NOT NULL,
    CONSTRAINT orders_user_cart_insertedat_unique UNIQUE (_user, cart, inserted_at),
    CONSTRAINT orders_users_fk FOREIGN KEY (_user) REFERENCES MarketNexus.Users (id) ON DELETE CASCADE,
    CONSTRAINT orders_carts_fk FOREIGN KEY (cart) REFERENCES MarketNexus.Sales (id) ON DELETE CASCADE,
    CONSTRAINT orders_id_min_value_check CHECK (MarketNexus.Orders.id >= 1),
    CONSTRAINT orders_user_min_value_check CHECK (MarketNexus.Orders._user >= 1),
    CONSTRAINT orders_sale_min_value_check CHECK (MarketNexus.Orders.cart >= 1),
    CONSTRAINT orders_quantity_min_value_check CHECK (MarketNexus.Orders.quantity >= 0),
    --CONSTRAINT orders_insertedat_min_value_check CHECK (MarketNexus.Orders.inserted_at >= NOW()),
    CONSTRAINT orders_insertedat_updatedat_value_check CHECK (MarketNexus.Orders.inserted_at <=
                                                              MarketNexus.Orders.updated_at)
);

CREATE
    OR REPLACE TRIGGER orders_updatedat_trigger
    BEFORE
        UPDATE
    ON MarketNexus.Orders
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.UPDATEDAT_SET_TIMESTAMP_FUNCTION();

COMMENT ON TABLE MarketNexus.Orders IS 'User who buys sale products that are his cart.';

ALTER TABLE MarketNexus.Orders
    OWNER TO postgres;

INSERT INTO MarketNexus.Orders(quantity, _user, cart)
VALUES (1, 1, 1);

select *
from MarketNexus.Users
where id = 1;

select *
from MarketNexus.Credentials
where id = 1;

/*

TODO:
    Vincoli da mettere:
        Un Utente non può mettere nel carrello e ordinare i suoi stessi prodotti (messi in vendita).
        Occhio alle quantità.

*/
