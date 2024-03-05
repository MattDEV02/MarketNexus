SELECT VERSION();

--CONNECT LambertEcommerce;

--ALTER DATABASE LambertEcommerce SET TIMEZONE TO 'Europe/Rome';

DROP SCHEMA IF EXISTS LambertEcommerce CASCADE;

CREATE SCHEMA IF NOT EXISTS LambertEcommerce;

COMMENT ON SCHEMA LambertEcommerce IS 'LambertEcommerce SQL DataBase.';

SELECT CURRENT_DATABASE();

SET search_path TO LambertEcommerce;

SELECT CURRENT_SCHEMA();

SELECT NOW();

DROP TYPE IF EXISTS ROLES;

CREATE TYPE ROLES AS ENUM (
    'DEFAULT',
    'ADMIN'
    );

COMMENT ON TYPE ROLES IS 'LambertEcommerce Credentials Roles.';

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


CREATE TABLE IF NOT EXISTS LambertEcommerce.ProductCategories
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    description TEXT        NOT NULL,
    CONSTRAINT productcategories_name_unique UNIQUE (name),
    CONSTRAINT productcategories_id_min_value_check CHECK (LambertEcommerce.ProductCategories.id >= 1),
    CONSTRAINT productcategories_name_min_length_check CHECK (LENGTH(LambertEcommerce.ProductCategories.name) >= 3)
);

ALTER TABLE LamberteCommerce.ProductCategories
    OWNER TO postgres;

COMMENT ON TABLE LambertEcommerce.ProductCategories IS 'LambertEcommerce Product Categories.';

INSERT INTO LambertEcommerce.ProductCategories (name, description)
VALUES ('Electronics', 'Electronics products'),
       ('Clothing', 'Clothing products'),
       ('Books', 'Books products'),
       ('Home Appliances', 'Home Appliances products'),
       ('Footwear', 'Footwear products'),
       ('Sports and Outdoors', 'Sports and Outdoors products'),
       ('Beauty and Personal Care', 'Beauty and Personal Care products'),
       ('Toys and Games', 'Toys and Games products'),
       ('Food and Grocery', 'Food and Grocery products');


CREATE TABLE IF NOT EXISTS lambertecommerce.Products
(
    id          SERIAL       NOT NULL PRIMARY KEY,
    name        VARCHAR(30)  NOT NULL,
    price       FLOAT        NOT NULL,
    description TEXT         NOT NULL,
    image_path  VARCHAR(255) NOT NULL,
    category    INTEGER      NOT NULL,
    CONSTRAINT products_productcategories_fk FOREIGN KEY (category) REFERENCES LamberteCommerce.ProductCategories (id) ON DELETE CASCADE,
    CONSTRAINT products_id_min_value_check CHECK (LambertEcommerce.Products.id >= 1),
    CONSTRAINT products_name_min_length_check CHECK (LENGTH(LambertEcommerce.Products.name) >= 3),
    CONSTRAINT products_price_min_value_check CHECK (LambertEcommerce.Products.price > 0),
    CONSTRAINT products_description_min_length_check CHECK (LENGTH(LambertEcommerce.Products.description) >= 3),
    CONSTRAINT products_category_min_value_check CHECK (LambertEcommerce.Products.category >= 1),
    CONSTRAINT products_imagepath_min_length_check CHECK (LENGTH(LambertEcommerce.Products.image_path) >= 3),
    CONSTRAINT products_imagepath_min_valid_check CHECK (POSITION('/' IN LambertEcommerce.Products.image_path) > 0)
);

ALTER TABLE LambertEcommerce.Products
    OWNER TO postgres;

COMMENT ON TABLE LambertEcommerce.Products IS 'LambertEcommerce Products.';

INSERT INTO LambertEcommerce.Products (name, price, description, image_path, category)
VALUES ('Smartphone', 599.99, 'High-end smartphone', '/images/smartphone.jpg', 1),
       ('T-shirt', 29.99, 'Cotton T-shirt', '/images/tshirt.jpg', 2),
       ('Java Programming Book', 49.99, 'Learn Java programming', '/images/javabook.jpg', 3),
       ('Laptop', 1299.99, 'Powerful laptop', '/images/laptop.jpg', 1),
       ('Running Shoes', 79.99, 'Lightweight running shoes', '/images/runningshoes.jpg', 2),
       ('Python Programming Book', 39.99, 'Master Python programming', '/images/pythonbook.jpg', 3),
       ('Coffee Maker', 89.99, 'Automatic coffee maker', '/images/coffeemaker.jpg', 4),
       ('Denim Jeans', 49.99, 'Classic denim jeans', '/images/jeans.jpg', 2),
       ('Fishing Rod', 39.99, 'Professional fishing rod', '/images/fishingrod.jpg', 6),
       ('Hair Dryer', 29.99, 'Ionic hair dryer', '/images/hairdryer.jpg', 7),
       ('Board Game', 19.99, 'Family board game', '/images/boardgame.jpg', 8),
       ('Rice', 2.99, 'Long-grain white rice', '/images/rice.jpg', 9);


CREATE TABLE IF NOT EXISTS LambertEcommerce.Users
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    name        VARCHAR(30)                                                   NOT NULL,
    surname     VARCHAR(30)                                                   NOT NULL,
    birthdate   DATE,
    email       VARCHAR(50)                                                   NOT NULL,
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
                                              '^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'::TEXT),
    CONSTRAINT users_balance_min_value_check CHECK (LambertEcommerce.Users.balance >= 0),
    CONSTRAINT users_nation_min_value_check CHECK (LambertEcommerce.Users.nation >= 1),
    CONSTRAINT users_insertedat_min_value_check CHECK (True), -- LambertEcommerce.Users.inserted_at <= NOW() - INTERVAL '1 minutes'
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


CREATE TABLE IF NOT EXISTS LambertEcommerce.Credentials
(
    id       SERIAL      NOT NULL PRIMARY KEY,
    role     VARCHAR(10) NOT NULL,
    password VARCHAR(72) NOT NULL,
    username VARCHAR(10) NOT NULL,
    _user    INTEGER     NOT NULL,
    CONSTRAINT credentials_users_fk FOREIGN KEY (_user) REFERENCES LamberteCommerce.Users (id) ON DELETE CASCADE,
    CONSTRAINT credentials_role_valid_check CHECK (LambertEcommerce.Credentials.role = 'DEFAULT' OR
                                                   LambertEcommerce.Credentials.role = 'ADMIN'),
    CONSTRAINT credentials_username_min_length_check CHECK (LENGTH(LambertEcommerce.Credentials.username) >= 3),
    CONSTRAINT credentials_id_min_value_check CHECK (LambertEcommerce.Credentials.id >= 1),
    CONSTRAINT credentials_user_min_value_check CHECK (LambertEcommerce.Credentials._user >= 1)
);

COMMENT ON TABLE LambertEcommerce.Credentials IS 'LambertEcommerce Users Credentials.';

ALTER TABLE LambertEcommerce.Credentials
    OWNER TO postgres;


CREATE TABLE LambertEcommerce.Selling
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    quantity    INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    _user       INTEGER                                                       NOT NULL,
    product     INTEGER                                                       NOT NULL,
    CONSTRAINT user_product_insertedat_unique UNIQUE (_user, product, inserted_at),
    CONSTRAINT selling_users_fk FOREIGN KEY (_user) REFERENCES LamberteCommerce.Users (id) ON DELETE CASCADE,
    CONSTRAINT selling_products_fk FOREIGN KEY (product) REFERENCES LamberteCommerce.Products (id) ON DELETE CASCADE,
    CONSTRAINT selling_id_min_value_check CHECK (LambertEcommerce.Selling.id >= 1),
    CONSTRAINT selling_user_min_value_check CHECK (LambertEcommerce.Selling._user >= 1),
    CONSTRAINT selling_product_min_value_check CHECK (LambertEcommerce.Selling.product >= 1),
    CONSTRAINT selling_quantity_min_value_check CHECK (LambertEcommerce.Selling.quantity >= 0),
    CONSTRAINT selling_insertedat_min_value_check CHECK (LambertEcommerce.Selling.inserted_at >= NOW()),
    CONSTRAINT selling_insertedat_updatedat_value_check CHECK (LambertEcommerce.Selling.inserted_at <=
                                                               LambertEcommerce.Selling.updated_at)
);

CREATE
    OR REPLACE TRIGGER selling_updatedat_trigger
    BEFORE
        UPDATE
    ON LambertEcommerce.Selling
    FOR EACH ROW
EXECUTE
    FUNCTION updatedat_set_timestamp_function();

COMMENT ON TABLE LambertEcommerce.Selling IS 'Publication of a Selling by the LambertEcommerce Users.';

ALTER TABLE Lambertecommerce.Selling
    OWNER TO postgres;


CREATE TABLE LambertEcommerce.Carts
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    quantity    INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    _user       INTEGER                                                       NOT NULL,
    selling     INTEGER                                                       NOT NULL,
    CONSTRAINT carts_user_selling_insertedat_unique UNIQUE (_user, selling, inserted_at),
    CONSTRAINT carts_users_fk FOREIGN KEY (_user) REFERENCES LambertEcommerce.Users (id) ON DELETE CASCADE,
    CONSTRAINT carts_selling_fk FOREIGN KEY (selling) REFERENCES LambertEcommerce.Selling (id) ON DELETE CASCADE,
    CONSTRAINT carts_id_min_value_check CHECK (LambertEcommerce.Carts.id >= 1),
    CONSTRAINT carts_user_min_value_check CHECK (LambertEcommerce.Carts._user >= 1),
    CONSTRAINT carts_selling_min_value_check CHECK (LambertEcommerce.Carts.selling >= 1),
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

COMMENT ON TABLE LambertEcommerce.Carts IS 'User who puts a selling product in his cart.';

ALTER TABLE Lambertecommerce.Carts
    OWNER TO postgres;


CREATE TABLE LambertEcommerce.Orders
(
    id          SERIAL                                                        NOT NULL PRIMARY KEY,
    quantity    INTEGER                                                       NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('UTC'::TEXT, NOW()) NOT NULL,
    _user       INTEGER                                                       NOT NULL,
    selling     INTEGER                                                       NOT NULL,
    CONSTRAINT orders_user_selling_insertedat_unique UNIQUE (_user, selling, inserted_at),
    CONSTRAINT orders_users_fk FOREIGN KEY (_user) REFERENCES LambertEcommerce.Users (id) ON DELETE CASCADE,
    CONSTRAINT orders_selling_fk FOREIGN KEY (selling) REFERENCES LambertEcommerce.Selling (id) ON DELETE CASCADE,
    CONSTRAINT orders_id_min_value_check CHECK (LambertEcommerce.Orders.id >= 1),
    CONSTRAINT orders_user_min_value_check CHECK (LambertEcommerce.Orders._user >= 1),
    CONSTRAINT orders_selling_min_value_check CHECK (LambertEcommerce.Orders.selling >= 1),
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

COMMENT ON TABLE LambertEcommerce.Orders IS 'User who buys a selling product.';

ALTER TABLE Lambertecommerce.Orders
    OWNER TO postgres;
