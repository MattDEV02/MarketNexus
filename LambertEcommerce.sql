SELECT VERSION();

--CONNECT LambertEcommerce;

--ALTER DATABASE LambertEcommerce SET TIMEZONE TO 'Europe/Rome';

DROP SCHEMA IF EXISTS LambertEcommerce CASCADE;

CREATE SCHEMA IF NOT EXISTS LambertEcommerce;

COMMENT ON SCHEMA LambertEcommerce IS 'LambertEcommerce SQL DataBase.';

SELECT CURRENT_DATABASE();

SET SEARCH_PATH TO LambertEcommerce;

SELECT CURRENT_SCHEMA();

SELECT NOW();

DROP TYPE IF EXISTS ROLES;

CREATE TYPE ROLES AS ENUM (
    'SELLER',
    'BOUGHTER',
    'ALL'
    );

COMMENT ON TYPE ROLES IS 'LambertEcommerce Users Credentials Roles.';

CREATE
    OR REPLACE FUNCTION updatedat_set_timestamp_function() RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION updatedat_set_timestamp_function IS 'Function that allows to update the updated_at TIMESTAMP fields.';

CREATE TABLE IF NOT EXISTS LambertEcommerce.Nations
(
    id   SERIAL      NOT NULL PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    CONSTRAINT nations_name_unique UNIQUE (name),
    CONSTRAINT nations_id_min_value_check CHECK (LambertEcommerce.Nations.id >= 1),
    CONSTRAINT nations_name_min_length_check CHECK (LENGTH(LambertEcommerce.Nations.name) >= 3)
);

ALTER TABLE LambertEcommerce.Nations
    OWNER TO postgres;

COMMENT ON TABLE LambertEcommerce.Nations IS 'LambertEcommerce Users Nation origin.';

INSERT INTO LambertEcommerce.Nations (name)
VALUES ('Italy'),
       ('France'),
       ('Germany'),
       ('United States'),
       ('Spain'),
       ('United Kingdom'),
       ('Japan'),
       ('China'),
       ('India');


CREATE TABLE IF NOT EXISTS LambertEcommerce.product_categories
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    description VARCHAR(60) NOT NULL,
    CONSTRAINT productcategories_name_unique UNIQUE (name),
    CONSTRAINT productcategories_id_min_value_check CHECK (LambertEcommerce.product_categories.id >= 1),
    CONSTRAINT productcategories_name_min_length_check CHECK (LENGTH(LambertEcommerce.product_categories.name) >= 3)
);

ALTER TABLE LamberteCommerce.product_categories
    OWNER TO postgres;

COMMENT ON TABLE LambertEcommerce.product_categories IS 'LambertEcommerce Product Categories.';

INSERT INTO LambertEcommerce.product_categories (name, description)
VALUES ('Electronics', 'Electronics products'),
       ('Clothing', 'Clothing products'),
       ('Books', 'Books products'),
       ('Home Appliances', 'Home Appliances products'),
       ('Footwear', 'Footwear products'),
       ('Sports and Outdoors', 'Sports and Outdoors products'),
       ('Beauty and Personal Care', 'Beauty and Personal Care products'),
       ('Toys and Games', 'Toys and Games products'),
       ('Food and Grocery', 'Food and Grocery products');


CREATE TABLE IF NOT EXISTS LambertEcommerce.Products
(
    id                  SERIAL      NOT NULL PRIMARY KEY,
    name                VARCHAR(30) NOT NULL,
    description         VARCHAR(60) NOT NULL,
    price               FLOAT       NOT NULL,
    image_relative_path TEXT        NOT NULL,
    category            INTEGER     NOT NULL,
    CONSTRAINT products_productcategories_fk FOREIGN KEY (category) REFERENCES LamberteCommerce.product_categories (id) ON DELETE CASCADE,
    CONSTRAINT products_id_min_value_check CHECK (LambertEcommerce.Products.id >= 1),
    CONSTRAINT products_name_min_length_check CHECK (LENGTH(LambertEcommerce.Products.name) >= 3),
    CONSTRAINT products_name_valid_check CHECK (LambertEcommerce.Products.name ~ '^[^\\\\/:*?"<>|]*$'::TEXT),
    CONSTRAINT products_price_min_value_check CHECK (LambertEcommerce.Products.price > 0),
    CONSTRAINT products_description_min_length_check CHECK (LENGTH(LambertEcommerce.Products.description) >= 3),
    CONSTRAINT products_imagerelativepath_min_length_check CHECK (LENGTH(LambertEcommerce.Products.image_relative_path) >= 3),
    CONSTRAINT products_imagerelativepath_min_valid_check CHECK (POSITION('/' IN LambertEcommerce.Products.image_relative_path) > 0),
    CONSTRAINT products_category_min_value_check CHECK (LambertEcommerce.Products.category >= 1)
);

ALTER TABLE LambertEcommerce.Products
    OWNER TO postgres;

COMMENT ON TABLE LambertEcommerce.Products IS 'LambertEcommerce Products.';

CREATE OR REPLACE FUNCTION LambertEcommerce.GENERATE_PRODUCT_RELATIVEIMAGEPATH_FUNCTION(product_id INT, product_name TEXT)
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
    OR REPLACE FUNCTION LambertEcommerce.INSERT_PRODUCT_RELATIVEIMAGEPATH_FUNCTION()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.image_relative_path = LambertEcommerce.GENERATE_PRODUCT_RELATIVEIMAGEPATH_FUNCTION(NEW.ID, NEW.name);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER product_imagerelativepath_trigger
    BEFORE INSERT
    ON LambertEcommerce.Products
    FOR EACH ROW
EXECUTE FUNCTION LambertEcommerce.INSERT_PRODUCT_RELATIVEIMAGEPATH_FUNCTION();


INSERT INTO LambertEcommerce.Products (name, price, description, category)
VALUES ('Smartphone', 599.99, 'High-end smartphone', 1),
       ('T-shirt', 29.99, 'Cotton T-shirt', 2),
       ('Java Programming Book', 49.99, 'Learn Java programming', 3),
       ('Laptop', 1299.99, 'Powerful laptop', 1),
       ('Running Shoes', 79.99, 'Lightweight running shoes', 2),
       ('Python Programming Book', 39.99, 'Master Python programming', 3),
       ('Coffee Maker', 89.99, 'Automatic coffee maker', 4),
       ('Denim Jeans', 49.99, 'Classic denim jeans', 2),
       ('Fishing Rod', 39.99, 'Professional fishing rod', 6),
       ('Hair Dryer', 29.99, 'Ionic hair dryer', 7),
       ('Board Game', 19.99, 'Family board game', 8),
       ('Rice', 2.99, 'Long-grain white rice', 9);


CREATE TABLE IF NOT EXISTS LambertEcommerce.Users
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    name        VARCHAR(30)                                                   NOT NULL,
    surname     VARCHAR(30)                                                   NOT NULL,
    email       VARCHAR(50)                                                   NOT NULL,
    birthdate   DATE,
    balance     FLOAT                                                         NOT NULL,
    nation      INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    CONSTRAINT users_email_unique UNIQUE (email),
    CONSTRAINT users_nations_fk FOREIGN KEY (nation) REFERENCES LamberteCommerce.Nations (id) ON DELETE CASCADE,
    CONSTRAINT users_name_min_length_check CHECK (LENGTH(LambertEcommerce.Users.name) >= 3),
    CONSTRAINT users_id_min_value_check CHECK (LambertEcommerce.Users.id >= 1),
    CONSTRAINT users_surname_min_length_check CHECK (LENGTH(LambertEcommerce.Users.surname) >= 3),
    CONSTRAINT users_birthdate_min_value_check CHECK (LambertEcommerce.Users.birthdate >= (NOW() - INTERVAL '100 years')),
    CONSTRAINT users_birthdate_max_value_check CHECK (LambertEcommerce.Users.birthdate <= NOW()),
    CONSTRAINT users_email_min_length_check CHECK (LENGTH(LambertEcommerce.Users.email) >= 3),
    CONSTRAINT users_email_valid_check CHECK (LambertEcommerce.Users.email ~
                                              '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'::TEXT),
    CONSTRAINT users_balance_min_value_check CHECK (LambertEcommerce.Users.balance >= 0),
    CONSTRAINT users_balance_max_value_check CHECK (LambertEcommerce.Users.balance <= 10000),
    CONSTRAINT users_nation_min_value_check CHECK (LambertEcommerce.Users.nation >= 1),
    CONSTRAINT users_insertedat_min_value_check CHECK (LambertEcommerce.Users.inserted_at >= NOW() - INTERVAL '1 minutes'),
    CONSTRAINT users_insertedat_updatedat_value_check CHECK (LambertEcommerce.Users.inserted_at <=
                                                             LambertEcommerce.Users.updated_at)
);

CREATE
    OR REPLACE TRIGGER users_updatedat_trigger
    BEFORE
        UPDATE
    ON LambertEcommerce.Users
    FOR EACH ROW
EXECUTE
    FUNCTION updatedat_set_timestamp_function();

COMMENT ON TABLE LambertEcommerce.Users IS 'LambertEcommerce Users.';

ALTER TABLE LambertEcommerce.Users
    OWNER TO postgres;

CREATE OR REPLACE FUNCTION CHECK_ROLE_ROLES_ENUM(role TEXT) RETURNS BOOLEAN AS
$$
BEGIN
    RETURN role IN (SELECT TEXT_ROLE::text
                    FROM (SELECT unnest(enum_range(NULL::ROLES)) AS TEXT_ROLE) as SubQuery);
END;
$$ LANGUAGE plpgsql;

INSERT INTO LambertEcommerce.Users (name, surname, email, birthdate, balance, nation)
VALUES ('Matteo', 'Lambertucci', 'matteolambertucci3@gmail.com', '2024-03-14', 22, 1),
       ('Test', 'Test', 'test@test.it', '2024-03-17', 2, 2),
       ('Gabriel', 'Muscedere', 'gabrielmuscedere@gmail.com', '2002-03-27', 0.1, 6);


CREATE TABLE IF NOT EXISTS LambertEcommerce.Credentials
(
    id       SERIAL      NOT NULL PRIMARY KEY,
    password VARCHAR(72) NOT NULL,
    username VARCHAR(10) NOT NULL,
    role     VARCHAR(10) NOT NULL,
    _user    INTEGER     NOT NULL,
    CONSTRAINT credentials_users_fk FOREIGN KEY (_user) REFERENCES LamberteCommerce.Users (id) ON DELETE CASCADE,
    CONSTRAINT credentials_role_min_length_check CHECK (LENGTH(LambertEcommerce.Credentials.role) >= 3),
    CONSTRAINT credentials_role_valid_check CHECK (LambertEcommerce.CHECK_ROLE_ROLES_ENUM(role)),
    CONSTRAINT credentials_username_min_length_check CHECK (LENGTH(LambertEcommerce.Credentials.username) >= 3),
    --CONSTRAINT credentials_username_valid__check CHECK (LambertEcommerce.Credentials.username ~ ''::TEXT),
    CONSTRAINT credentials_id_min_value_check CHECK (LambertEcommerce.Credentials.id >= 1),
    CONSTRAINT credentials_user_min_value_check CHECK (LambertEcommerce.Credentials._user >= 1)
);

COMMENT ON TABLE LambertEcommerce.Credentials IS 'LambertEcommerce Users Credentials.';

ALTER TABLE LambertEcommerce.Credentials
    OWNER TO postgres;

INSERT INTO LambertEcommerce.Credentials (username, password, role, _user)
VALUES ('Lamb', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'ALL', 1),
       ('Test1', '$2a$10$WprxEwx6mj231RuhiUZrxO2Hdnw1acKE/INs0B5Y9.5A1jMjainve', 'ALL', 2),
       ('Musc', '$2a$10$eL/ln3CGVOdYbPJ4Faao.OeN46ZkP91e.h5pKOAGe08a1ICNGIzBW', 'ALL', 3);

-- N.B. = La password è criptata da spring boot e arriva a 60 caratteri.


CREATE TABLE LambertEcommerce.Sales
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    quantity    INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    _user       INTEGER                                                       NOT NULL,
    product     INTEGER                                                       NOT NULL,
    CONSTRAINT user_product_insertedat_unique UNIQUE (_user, product, inserted_at),
    CONSTRAINT sale_users_fk FOREIGN KEY (_user) REFERENCES LamberteCommerce.Users (id) ON DELETE CASCADE,
    CONSTRAINT sale_products_fk FOREIGN KEY (product) REFERENCES LamberteCommerce.Products (id) ON DELETE CASCADE,
    CONSTRAINT sale_id_min_value_check CHECK (LambertEcommerce.Sales.id >= 1),
    CONSTRAINT sale_user_min_value_check CHECK (LambertEcommerce.Sales._user >= 1),
    CONSTRAINT sale_product_min_value_check CHECK (LambertEcommerce.Sales.product >= 1),
    CONSTRAINT sale_quantity_min_value_check CHECK (LambertEcommerce.Sales.quantity >= 0),
    -- CONSTRAINT sale_insertedat_min_value_check CHECK (LambertEcommerce.Sales.inserted_at >= NOW()),
    CONSTRAINT sale_insertedat_updatedat_value_check CHECK (LambertEcommerce.Sales.inserted_at <=
                                                            LambertEcommerce.Sales.updated_at)
);

CREATE
    OR REPLACE TRIGGER sale_updatedat_trigger
    BEFORE
        UPDATE
    ON LambertEcommerce.Sales
    FOR EACH ROW
EXECUTE
    FUNCTION updatedat_set_timestamp_function();

COMMENT ON TABLE LambertEcommerce.Sales IS 'Publication of a Sales by the LambertEcommerce Users.';

ALTER TABLE Lambertecommerce.Sales
    OWNER TO postgres;

INSERT INTO LambertEcommerce.Sales(quantity, _user, product)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 3),
       (2, 2, 4),
       (2, 2, 5),
       (2, 2, 6),
       (2, 2, 7);


CREATE TABLE LambertEcommerce.Carts
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    quantity    INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    _user       INTEGER                                                       NOT NULL,
    sale        INTEGER                                                       NOT NULL,
    CONSTRAINT carts_user_sale_insertedat_unique UNIQUE (_user, sale, inserted_at),
    CONSTRAINT carts_users_fk FOREIGN KEY (_user) REFERENCES LambertEcommerce.Users (id) ON DELETE CASCADE,
    CONSTRAINT carts_sale_fk FOREIGN KEY (sale) REFERENCES LambertEcommerce.Sales (id) ON DELETE CASCADE,
    CONSTRAINT carts_id_min_value_check CHECK (LambertEcommerce.Carts.id >= 1),
    CONSTRAINT carts_user_min_value_check CHECK (LambertEcommerce.Carts._user >= 1),
    CONSTRAINT carts_sale_min_value_check CHECK (LambertEcommerce.Carts.sale >= 1),
    CONSTRAINT carts_quantity_min_value_check CHECK (LambertEcommerce.Carts.quantity >= 0),
    CONSTRAINT carts_insertedat_min_value_check CHECK (LambertEcommerce.Carts.inserted_at >= NOW()),
    CONSTRAINT carts_insertedat_updatedat_value_check CHECK (LambertEcommerce.Carts.inserted_at <=
                                                             LambertEcommerce.Carts.updated_at)
);

CREATE
    OR REPLACE TRIGGER carts_updatedat_trigger
    BEFORE
        UPDATE
    ON LambertEcommerce.Carts
    FOR EACH ROW
EXECUTE
    FUNCTION updatedat_set_timestamp_function();

COMMENT ON TABLE LambertEcommerce.Carts IS 'User who puts a sale product in his cart.';

ALTER TABLE Lambertecommerce.Carts
    OWNER TO postgres;

INSERT INTO LambertEcommerce.Carts(quantity, _user, sale)
VALUES (1, 1, 3),
       (1, 1, 4),
       (1, 2, 5);


CREATE TABLE LambertEcommerce.Orders
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    quantity    INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    _user       INTEGER                                                       NOT NULL,
    sale        INTEGER                                                       NOT NULL,
    CONSTRAINT orders_user_sale_insertedat_unique UNIQUE (_user, sale, inserted_at),
    CONSTRAINT orders_users_fk FOREIGN KEY (_user) REFERENCES LambertEcommerce.Users (id) ON DELETE CASCADE,
    CONSTRAINT orders_sale_fk FOREIGN KEY (sale) REFERENCES LambertEcommerce.Sales (id) ON DELETE CASCADE,
    CONSTRAINT orders_id_min_value_check CHECK (LambertEcommerce.Orders.id >= 1),
    CONSTRAINT orders_user_min_value_check CHECK (LambertEcommerce.Orders._user >= 1),
    CONSTRAINT orders_sale_min_value_check CHECK (LambertEcommerce.Orders.sale >= 1),
    CONSTRAINT orders_quantity_min_value_check CHECK (LambertEcommerce.Orders.quantity >= 0),
    CONSTRAINT orders_insertedat_min_value_check CHECK (LambertEcommerce.Orders.inserted_at >= NOW()),
    CONSTRAINT orders_insertedat_updatedat_value_check CHECK (LambertEcommerce.Orders.inserted_at <=
                                                              LambertEcommerce.Orders.updated_at)
);

CREATE
    OR REPLACE TRIGGER orders_updatedat_trigger
    BEFORE
        UPDATE
    ON LambertEcommerce.Orders
    FOR EACH ROW
EXECUTE
    FUNCTION updatedat_set_timestamp_function();

COMMENT ON TABLE LambertEcommerce.Orders IS 'User who buys a sale product.';

ALTER TABLE Lambertecommerce.Orders
    OWNER TO postgres;

INSERT INTO LambertEcommerce.Orders(quantity, _user, sale)
VALUES (1, 1, 6),
       (1, 1, 7);


/*

TODO:
    Vincoli da mettere:
        Un Utente non può mettere nel carrello e ordinare i suoi stessi prodotti (messi in vendita).
        Occhio alle quantità.

*/
