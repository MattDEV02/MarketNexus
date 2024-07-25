--SET TIME ZONE 'Europe/Rome';

SHOW TIMEZONE;

SELECT VERSION();

-- CREATE DATABASE IF NOT EXISTS MarketNexus;

--CONNECT MarketNexus;

DROP SCHEMA IF EXISTS MarketNexus CASCADE;

CREATE SCHEMA IF NOT EXISTS MarketNexus;

COMMENT ON SCHEMA MarketNexus IS 'MarketNexus SQL DataBase Schema.';

SELECT CURRENT_DATABASE();

SET SEARCH_PATH TO MarketNexus;

SELECT CURRENT_SCHEMA();

SELECT CURRENT_CATALOG;

SELECT CURRENT_TIMESTAMP;

DROP TYPE IF EXISTS ROLES;

CREATE TYPE ROLES AS ENUM (
    'SELLER',
    'SELLER_AND_BUYER',
    'BUYER'
    );

COMMENT ON TYPE ROLES IS 'MarketNexus Users Credentials Roles.';

CREATE
    OR REPLACE FUNCTION UPDATEDAT_SET_TIMESTAMP_FUNCTION() RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION UPDATEDAT_SET_TIMESTAMP_FUNCTION IS 'Function that allows to update the updated_at TIMESTAMP fields.';

--CREATE SEQUENCE nome_sequenza START 1;

--COMMENT ON SEQUENCE nome_sequenza IS '';

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
    CONSTRAINT productcategories_name_min_length_check CHECK (LENGTH(MarketNexus.product_categories.name) >= 3),
    CONSTRAINT productcategories_description_min_length_check CHECK (LENGTH(MarketNexus.product_categories.description) >= 3)
);

ALTER TABLE MarketNexus.product_categories
    OWNER TO postgres;

COMMENT ON TABLE MarketNexus.product_categories IS 'MarketNexus Product Categories.';

INSERT INTO MarketNexus.product_categories (name, description)
VALUES ('Electronics', 'Electronics products.'),
       ('Clothing', 'Clothing products.'),
       ('Books', 'Books products.'),
       ('Appliances', 'Sales Appliances products.'),
       ('Footwear', 'Footwear products.'),
       ('Sports', 'Sports products.'),
       ('Beauty', 'Beauty products.'),
       ('Games', 'Games products.'),
       ('Food', 'Food products.');


CREATE TABLE IF NOT EXISTS MarketNexus.Products
(
    id                   SERIAL      NOT NULL PRIMARY KEY,
    name                 VARCHAR(30) NOT NULL,
    description          VARCHAR(60) NOT NULL,
    price                FLOAT       NOT NULL,
    image_relative_paths TEXT[]      NOT NULL,
    category             INTEGER     NOT NULL,
    CONSTRAINT products_productcategories_fk FOREIGN KEY (category) REFERENCES MarketNexus.product_categories (id) ON DELETE CASCADE,
    CONSTRAINT products_id_min_value_check CHECK (MarketNexus.Products.id >= 1),
    CONSTRAINT products_name_min_length_check CHECK (LENGTH(MarketNexus.Products.name) >= 3),
    CONSTRAINT products_name_valid_check CHECK (MarketNexus.Products.name ~ '^[^\\\\/:*?"<>|]*$'::TEXT),
    CONSTRAINT products_price_min_value_check CHECK (MarketNexus.Products.price > 0),
    CONSTRAINT products_price_max_value_check CHECK (MarketNexus.Products.price <= 1000),
    CONSTRAINT products_description_min_length_check CHECK (LENGTH(MarketNexus.Products.description) >= 3),
    CONSTRAINT products_category_min_value_check CHECK (MarketNexus.Products.category >= 1)
);

CREATE OR REPLACE FUNCTION MarketNexus.VALIDATE_PRODUCTS_IMAGE_PATHS_FUNCTION() RETURNS TRIGGER AS
$$
DECLARE
    image_path TEXT;
BEGIN
    FOREACH image_path IN ARRAY NEW.image_relative_paths
        LOOP
            IF image_path !~ '^/images/products/[1-9][0-9]*/-?\d+\.jpeg$' THEN
                RAISE EXCEPTION 'Invalid image path: %', image_path;
            END IF;
        END LOOP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER VALIDATE_PRODUCTS_IMAGE_PATHS_TRIGGER
    BEFORE INSERT OR UPDATE
    ON MarketNexus.Products
    FOR EACH ROW
EXECUTE FUNCTION VALIDATE_PRODUCTS_IMAGE_PATHS_FUNCTION();

ALTER TABLE MarketNexus.Products
    OWNER TO postgres;

COMMENT ON TABLE MarketNexus.Products IS 'MarketNexus Users Products.';

INSERT INTO MarketNexus.Products (name, price, description, category, image_relative_paths)
VALUES ('Smartphone', 599.99, 'High-end smartphone.', 1,
        '{/images/products/1/1.jpeg, /images/products/1/2.jpeg, /images/products/1/3.jpeg}'),
       ('T-shirt', 29.99, 'Cotton T-shirt.', 2, '{/images/products/2/1.jpeg, /images/products/2/2.jpeg}'),
       ('Java Programming Book', 49.99, 'Learn Java programming.', 3, '{/images/products/3/1.jpeg}'),
       ('Laptop', 999.99, 'Powerful laptop.', 1, '{/images/products/4/1.jpeg, /images/products/4/2.jpeg}'),
       ('Running Shoes', 79.99, 'Lightweight running shoes.', 2,
        '{/images/products/5/1.jpeg, /images/products/5/2.jpeg}'),
       ('Python Book', 39.99, 'Master Python programming.', 3,
        '{/images/products/6/1.jpeg, /images/products/6/2.jpeg}'),
       ('Coffee Maker', 89.99, 'Automatic coffee maker.', 4, '{/images/products/7/1.jpeg, /images/products/7/2.jpeg}');


CREATE OR REPLACE FUNCTION CHECK_ROLE_ROLES_ENUM_FUNCTION(role TEXT) RETURNS BOOLEAN AS
$$
BEGIN
    RETURN role IN (SELECT TEXT_ROLE::TEXT
                    FROM (SELECT UNNEST(ENUM_RANGE(NULL::ROLES)) AS TEXT_ROLE) AS TEXT_ROLES);
END;
$$ LANGUAGE PLPGSQL;

CREATE TABLE IF NOT EXISTS MarketNexus.Credentials
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    password    VARCHAR(72) NOT NULL,
    username    VARCHAR(10) NOT NULL,
    role        VARCHAR(30) NOT NULL     DEFAULT 'SELLER_AND_BUYER',
    is_online   BOOLEAN     NOT NULL     DEFAULT FALSE,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT pg_catalog.TIMEZONE('UTC'::TEXT, CURRENT_TIMESTAMP) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT pg_catalog.TIMEZONE('UTC'::TEXT, CURRENT_TIMESTAMP) NOT NULL,
    CONSTRAINT credentials_username_unique UNIQUE (username),
    CONSTRAINT credentials_role_min_length_check CHECK (LENGTH(MarketNexus.Credentials.role) >= 3),
    CONSTRAINT credentials_role_valid_check CHECK (MarketNexus.CHECK_ROLE_ROLES_ENUM_FUNCTION(role)),
    CONSTRAINT credentials_username_min_length_check CHECK (LENGTH(MarketNexus.Credentials.username) >= 3),
    CONSTRAINT credentials_password_min_check CHECK (LENGTH(MarketNexus.Credentials.password) >= 60),
    CONSTRAINT credentials_id_min_value_check CHECK (MarketNexus.Credentials.id >= 1),
    --CONSTRAINT credentials_insertedat_min_value_check CHECK (MarketNexus.Credentials.inserted_at <= CURRENT_TIMESTAMP),
    CONSTRAINT credentials_insertedat_updatedat_value_check CHECK (MarketNexus.Credentials.inserted_at <=
                                                                   MarketNexus.Credentials.updated_at)
);

CREATE
    OR REPLACE TRIGGER CREDENTIALS_UPDATEDAT_TRIGGER
    BEFORE
        UPDATE
    ON MarketNexus.Credentials
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.UPDATEDAT_SET_TIMESTAMP_FUNCTION();

COMMENT ON TABLE MarketNexus.Credentials IS 'MarketNexus Users Credentials.';

ALTER TABLE MarketNexus.Credentials
    OWNER TO postgres;

INSERT INTO MarketNexus.Credentials (username, password, role)
VALUES ('Lamb', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'SELLER_AND_BUYER'),
       ('JohnDoe', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'SELLER_AND_BUYER'),
       ('Giuseppe', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'SELLER_AND_BUYER'),
       ('KatyPerry', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'SELLER'),
       ('SanJay', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'BUYER'),
       ('Queen', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'BUYER'),
       ('King', '$2a$10$1xyrTM4fzIZINm3GBh7H6.IyMc0RFFzplC/emdv3aXctk3k7U55oG', 'SELLER');


-- N.B. = La password è criptata da spring boot con l'algoritmo bscrypt e va da 60 caratteri a 72.

CREATE TABLE IF NOT EXISTS MarketNexus.Users
(
    id             SERIAL      NOT NULL PRIMARY KEY,
    name           VARCHAR(30) NOT NULL,
    surname        VARCHAR(30) NOT NULL,
    email          VARCHAR(50) NOT NULL,
    birthdate      DATE,
    balance        FLOAT       NOT NULL,
    show_is_online BOOLEAN DEFAULT TRUE,
    credentials    INTEGER     NOT NULL,
    nation         INTEGER     NOT NULL,
    CONSTRAINT users_email_unique UNIQUE (email),
    CONSTRAINT users_credentials_unique UNIQUE (credentials),
    CONSTRAINT users_credentials_fk FOREIGN KEY (credentials) REFERENCES MarketNexus.Credentials (id) ON DELETE CASCADE,
    CONSTRAINT users_nations_fk FOREIGN KEY (nation) REFERENCES MarketNexus.Nations (id) ON DELETE CASCADE,
    CONSTRAINT users_name_min_length_check CHECK (LENGTH(MarketNexus.Users.name) >= 3),
    CONSTRAINT users_id_min_value_check CHECK (MarketNexus.Users.id >= 1),
    CONSTRAINT users_surname_min_length_check CHECK (LENGTH(MarketNexus.Users.surname) >= 3),
    CONSTRAINT users_birthdate_min_value_check CHECK (MarketNexus.Users.birthdate >=
                                                      (CURRENT_TIMESTAMP - INTERVAL '100 years')),
    CONSTRAINT users_birthdate_max_value_check CHECK (MarketNexus.Users.birthdate <= CURRENT_TIMESTAMP),
    CONSTRAINT users_email_min_length_check CHECK (LENGTH(MarketNexus.Users.email) >= 3),
    CONSTRAINT users_email_valid_check CHECK (MarketNexus.Users.email ~
                                              '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'::TEXT),
    CONSTRAINT users_balance_min_value_check CHECK (MarketNexus.Users.balance >= 0),
    CONSTRAINT users_balance_max_value_check CHECK (MarketNexus.Users.balance <= 10000),
    CONSTRAINT users_credentials_min_value_check CHECK (MarketNexus.Users.credentials >= 1),
    CONSTRAINT users_nation_min_value_check CHECK (MarketNexus.Users.nation >= 1)
);

COMMENT ON TABLE MarketNexus.Users IS 'MarketNexus Users.';

ALTER TABLE MarketNexus.Users
    OWNER TO postgres;

INSERT INTO MarketNexus.Users (name, surname, email, birthdate, balance, credentials, nation)
VALUES ('Matteo', 'Lambertucci', 'matteolambertucci3@gmail.com', '2002-04-02', 220, 1, 1),
       ('John', 'Doe', 'johndoe@test.it', '2020-03-17', 2000, 2, 6),
       ('Giuseppe', 'Rossi', 'giusepperossi@test.it', '2020-11-02', 2000.05, 3, 1),
       ('Katy', 'Perry', 'katyperry@test.it', '2000-01-12', 100.16, 4, 4),
       ('San', 'Jay', 'sanjay@test.it', '2000-01-12', 0, 5, 9),
       ('Queen', 'Elizabeth', 'queen@test.it', '1950-02-10', 0, 6, 6),
       ('King', 'Carlo', 'king@test.it', '1970-12-10', 0, 7, 6);


CREATE TABLE IF NOT EXISTS MarketNexus.Sales
(
    id          SERIAL                                                                               NOT NULL PRIMARY KEY,
    _user       INTEGER                                                                              NOT NULL,
    product     INTEGER                                                                              NOT NULL,
    quantity    INTEGER                                                                              NOT NULL,
    is_sold     BOOLEAN                                                                              NOT NULL DEFAULT FALSE,
    sale_price  FLOAT                                                                                NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT pg_catalog.TIMEZONE('UTC'::TEXT, CURRENT_TIMESTAMP) NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE DEFAULT pg_catalog.TIMEZONE('UTC'::TEXT, CURRENT_TIMESTAMP) NOT NULL,
    CONSTRAINT sales_user_product_unique UNIQUE (_user, product),
    CONSTRAINT sale_users_fk FOREIGN KEY (_user) REFERENCES MarketNexus.Users (id) ON DELETE CASCADE,
    CONSTRAINT sale_products_fk FOREIGN KEY (product) REFERENCES MarketNexus.Products (id) ON DELETE CASCADE,
    CONSTRAINT sale_id_min_value_check CHECK (MarketNexus.Sales.id >= 1),
    CONSTRAINT sale_user_min_value_check CHECK (MarketNexus.Sales._user >= 1),
    CONSTRAINT sale_product_min_value_check CHECK (MarketNexus.Sales.product >= 1),
    CONSTRAINT sale_quantity_min_value_check CHECK (MarketNexus.Sales.quantity >= 0),
    CONSTRAINT sale_quantity_max_value_check CHECK (MarketNexus.Sales.quantity <= 10),
    CONSTRAINT sale_saleprice_min_value_check CHECK (MarketNexus.Sales.sale_price > 0),
    CONSTRAINT sale_saleprice_max_value_check CHECK (MarketNexus.Sales.sale_price <= 10000),
    --CONSTRAINT sale_insertedat_min_value_check CHECK (MarketNexus.Sales.inserted_at <= CURRENT_TIMESTAMP),
    CONSTRAINT sale_insertedat_updatedat_value_check CHECK (MarketNexus.Sales.inserted_at <=
                                                            MarketNexus.Sales.updated_at)
);

CREATE
    OR REPLACE TRIGGER SALE_UPDATEDAT_TRIGGER
    BEFORE
        UPDATE
    ON MarketNexus.Sales
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.UPDATEDAT_SET_TIMESTAMP_FUNCTION();

CREATE
    OR REPLACE FUNCTION CHECK_SALE_SALEPRICE_VALUE_FUNCTION()
    RETURNS TRIGGER AS
$$
BEGIN
    IF ((SELECT CASE WHEN (s.sale_price = (p.price * s.quantity)) THEN TRUE ELSE FALSE END AS are_equals
         FROM MarketNexus.Products p
                  JOIN MarketNexus.Sales s ON p.id = s.product
         WHERE s.id = NEW.ID) = TRUE) THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Sale sale_price error with this Sale ID: % and this sale_price: %.' , NEW.ID, NEW.sale_price;
    END IF;
END;
$$ LANGUAGE PLPGSQL;


CREATE
    OR REPLACE TRIGGER SALE_SALEPRICE_VALUE_TRIGGER
    AFTER
        INSERT OR UPDATE
    ON MarketNexus.Sales
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.CHECK_SALE_SALEPRICE_VALUE_FUNCTION();

CREATE
    OR REPLACE FUNCTION CHECK_SALE_USER_CREDENTIALS_ROLE_FUNCTION()
    RETURNS TRIGGER AS
$$
BEGIN
    IF ((SELECT CASE
                    WHEN (c.role = 'SELLER_AND_BUYER' OR c.role = 'SELLER') THEN TRUE
                    ELSE FALSE END AS are_equals
         FROM MarketNexus.Sales s
                  JOIN MarketNexus.Users u ON s._user = u.id
                  JOIN MarketNexus.Credentials c ON u.credentials = c.id
         WHERE s.id = NEW.ID) = TRUE) THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'User Sale Credentials role error, with this Sale ID: % .' , NEW.ID;
    END IF;
END;
$$ LANGUAGE PLPGSQL;

CREATE
    OR REPLACE TRIGGER SALE_USER_CREDENTIALS_ROLE_TRIGGER
    AFTER
        INSERT OR UPDATE
    ON MarketNexus.Sales
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.CHECK_SALE_USER_CREDENTIALS_ROLE_FUNCTION();

COMMENT ON TABLE MarketNexus.Sales IS 'Publication of a Sale (Product in Sale) by the MarketNexus Users.';

ALTER TABLE MarketNexus.Sales
    OWNER TO postgres;

INSERT INTO MarketNexus.Sales(_user, product, quantity, sale_price)
VALUES (1, 2, 2, 59.98),
       (2, 3, 3, 149.97),
       (2, 4, 1, 999.99),
       (2, 5, 2, 159.98),
       (2, 6, 2, 79.98),
       (2, 7, 2, 179.98);

INSERT INTO MarketNexus.Sales(_user, product, quantity, sale_price, inserted_at)
VALUES (1, 1, 1, 599.99, CURRENT_TIMESTAMP - INTERVAL '1 days');

CREATE TABLE IF NOT EXISTS MarketNexus.Carts
(
    id          SERIAL                                                                               NOT NULL PRIMARY KEY,
    cart_price  FLOAT                                                                                NOT NULL,
    _user       INTEGER                                                                              NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT pg_catalog.TIMEZONE('UTC'::TEXT, CURRENT_TIMESTAMP) NOT NULL,
    CONSTRAINT carts_user_insertedat_unique UNIQUE (_user, inserted_at),
    CONSTRAINT carts_users_fk FOREIGN KEY (_user) REFERENCES MarketNexus.Users (id) ON DELETE CASCADE,
    CONSTRAINT carts_id_min_value_check CHECK (MarketNexus.Carts.id >= 1),
    CONSTRAINT carts_cartprice_min_value_check CHECK (MarketNexus.Carts.cart_price >= 0),
    CONSTRAINT carts_user_min_value_check CHECK (MarketNexus.Carts._user >= 1)
    --CONSTRAINT carts_insertedat_min_value_check CHECK (MarketNexus.Carts.inserted_at <= CURRENT_TIMESTAMP),
);

COMMENT ON TABLE MarketNexus.Carts IS 'MarketNexus User Cart.';

ALTER TABLE MarketNexus.Carts
    OWNER TO postgres;

INSERT INTO MarketNexus.Carts(cart_price, _user)
VALUES (0, 1),
       (0, 2),
       (0, 3),
       (0, 4),
       (0, 5),
       (0, 6),
       (0, 7);

CREATE TABLE IF NOT EXISTS MarketNexus.cart_line_items
(
    id                 SERIAL                                                                               NOT NULL PRIMARY KEY,
    quantity           INTEGER                                                                              NOT NULL,
    cartlineitem_price FLOAT                                                                                NOT NULL,
    cart               INTEGER                                                                              NOT NULL,
    sale               INTEGER                                                                              NOT NULL,
    inserted_at        TIMESTAMP WITH TIME ZONE DEFAULT pg_catalog.TIMEZONE('UTC'::TEXT, CURRENT_TIMESTAMP) NOT NULL,
    updated_at         TIMESTAMP WITH TIME ZONE DEFAULT pg_catalog.TIMEZONE('UTC'::TEXT, CURRENT_TIMESTAMP) NOT NULL,
    CONSTRAINT cartlineitems_cart_sale_insertedat_unique UNIQUE (cart, sale, inserted_at),
    CONSTRAINT cartlineitems_carts_fk FOREIGN KEY (cart) REFERENCES MarketNexus.Carts (id) ON DELETE CASCADE,
    CONSTRAINT cartlineitems_sale_fk FOREIGN KEY (sale) REFERENCES MarketNexus.Sales (id) ON DELETE CASCADE,
    CONSTRAINT cartlineitems_id_min_value_check CHECK (MarketNexus.cart_line_items.id >= 1),
    CONSTRAINT cartlineitems_cartlineitemprice_min_value_check CHECK (MarketNexus.cart_line_items.cartlineitem_price > 0),
    CONSTRAINT cartlineitems_cartlineitemprice_max_value_check CHECK (MarketNexus.cart_line_items.cartlineitem_price <= 10000),
    CONSTRAINT cartlineitems_cart_min_value_check CHECK (MarketNexus.cart_line_items.cart >= 1),
    CONSTRAINT cartlineitems_sale_min_value_check CHECK (MarketNexus.cart_line_items.sale >= 1),
    CONSTRAINT cartlineitems_quantity_min_value_check CHECK (MarketNexus.cart_line_items.quantity >= 1),
    CONSTRAINT cartlineitems_insertedat_updatedat_value_check CHECK (MarketNexus.cart_line_items.inserted_at <=
                                                                     MarketNexus.cart_line_items.updated_at)
    --CONSTRAINT cartlineitems_insertedat_min_value_check CHECK (MarketNexus.cart_line_items.inserted_at <= CURRENT_TIMESTAMP),
);

CREATE
    OR REPLACE TRIGGER CARTLINEITEMS_UPDATEDAT_TRIGGER
    BEFORE
        UPDATE
    ON MarketNexus.cart_line_items
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.UPDATEDAT_SET_TIMESTAMP_FUNCTION();

CREATE
    OR REPLACE FUNCTION CHECK_CART_USER_CREDENTIALS_ROLE_FUNCTION()
    RETURNS TRIGGER AS
$$
BEGIN
    IF ((SELECT CASE
                    WHEN (c.role = 'SELLER_AND_BUYER' OR c.role = 'BUYER')
                        THEN TRUE
                    ELSE FALSE END AS are_equals
         FROM MarketNexus.cart_line_items cli
                  JOIN MarketNexus.Sales s ON cli.sale = s.id
                  JOIN MarketNexus.Users u ON s._user = u.id
                  JOIN MarketNexus.Credentials c ON u.credentials = c.id
         WHERE cli.id = NEW.ID) = TRUE) THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'User Cart Credentials role error, with this Cart_Line_Item ID: % .' , NEW.ID;
    END IF;
END;
$$ LANGUAGE PLPGSQL;

CREATE
    OR REPLACE TRIGGER CART_USER_CREDENTIALS_ROLE_TRIGGER
    AFTER
        INSERT OR UPDATE
    ON MarketNexus.cart_line_items
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.CHECK_CART_USER_CREDENTIALS_ROLE_FUNCTION();

CREATE
    OR REPLACE FUNCTION CHECK_CARTLINEITEM_CARTLINEITEMPRICE_VALUE_FUNCTION()
    RETURNS TRIGGER AS
$$
BEGIN
    IF ((SELECT CASE
                    WHEN (ROUND(cli.cartlineitem_price::NUMERIC, 2) = ROUND((p.price * cli.quantity)::NUMERIC, 2))
                        THEN TRUE
                    ELSE FALSE END AS are_equals
         FROM MarketNexus.Products p
                  JOIN MarketNexus.Sales s ON p.id = s.product
                  JOIN MarketNexus.cart_line_items cli ON s.id = cli.sale
         WHERE cli.id = NEW.ID) = TRUE) THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'CartLineItem cartlineitem_price error with this CartLineItem ID: % and this cartlineitem_price: %.' , NEW.ID, NEW.cartlineitem_price;
    END IF;
END;
$$ LANGUAGE PLPGSQL;


CREATE
    OR REPLACE TRIGGER CARTLINEITEM_CARTLINEITEMPRICE_VALUE_TRIGGER
    AFTER
        INSERT OR UPDATE
    ON MarketNexus.cart_line_items
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.CHECK_CARTLINEITEM_CARTLINEITEMPRICE_VALUE_FUNCTION();

CREATE
    OR REPLACE FUNCTION CHECK_CART_USER_SALE_FUNCTION()
    RETURNS TRIGGER AS
$$
BEGIN
    IF ((SELECT CASE
                    WHEN (c._user <> s._user)
                        THEN TRUE
                    ELSE FALSE END AS are_equals
         FROM cart_line_items cli
                  JOIN MarketNexus.Carts c ON cli.cart = c.id
                  JOIN MarketNexus.Sales s ON cli.sale = s.id
                  JOIN MarketNexus.Users u ON s._user = u.id
         WHERE cli.id = NEW.ID) = TRUE) THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'User that adds to Cart his sale error, with this cart_line_items ID: % .' , NEW.ID;
    END IF;
END;
$$ LANGUAGE PLPGSQL;

CREATE
    OR REPLACE TRIGGER CART_USER_SALE_FUNCTION_TRIGGER
    AFTER
        INSERT OR UPDATE
    ON MarketNexus.cart_line_items
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.CHECK_CART_USER_SALE_FUNCTION();


CREATE OR REPLACE FUNCTION GET_CARTLINEITEMS_PRICE_SUM_FROM_CARTID(cart_id BIGINT)
    RETURNS FLOAT AS
$$
DECLARE
    cartLineItemsPriceSumFromCartId FLOAT;
BEGIN
    SELECT COALESCE(SUM(cli.cartlineitem_price),
                    0) AS cart_line_items_price_sum_from_cart_id
    FROM MarketNexus.Carts c
             JOIN MarketNexus.cart_line_items cli ON cli.cart = c.id
    WHERE c.id = cart_id
    INTO cartLineItemsPriceSumFromCartId;
    RETURN cartLineItemsPriceSumFromCartId;
END;
$$ LANGUAGE PLPGSQL;

SELECT *
FROM GET_CARTLINEITEMS_PRICE_SUM_FROM_CARTID(1);

CREATE
    OR REPLACE FUNCTION CHECK_CART_CARTPRICE_VALUE_FUNCTION()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT (c.cart_price = 0 OR (TRUNC(c.cart_price) =
                                     TRUNC(GET_CARTLINEITEMS_PRICE_SUM_FROM_CARTID(NEW.cart))))
                   AS are_equals
        FROM MarketNexus.Carts c
        WHERE c.id = NEW.cart) THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Cart cart_price error with this Cart ID: % and this cart_line_item ID: %' , NEW.cart, NEW.id;
    END IF;
END;
$$ LANGUAGE PLPGSQL;
/*
CREATE
    OR REPLACE TRIGGER CART_CARTPRICE_VALUE_TRIGGER
    BEFORE
        INSERT OR UPDATE
    ON MarketNexus.cart_line_items
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.CHECK_CART_CARTPRICE_VALUE_FUNCTION();
*/
COMMENT ON TABLE MarketNexus.cart_line_items IS 'MarketNexus User who puts a Sale Product in his Cart.';

ALTER TABLE MarketNexus.cart_line_items
    OWNER TO postgres;

--INSERT INTO MarketNexus.cart_line_items(cart, sale, quantity, cartlineitem_price) VALUES (1, 6, 1, 89.99);


CREATE TABLE IF NOT EXISTS MarketNexus.Orders
(
    id          SERIAL                                                                               NOT NULL PRIMARY KEY,
    _user       INTEGER                                                                              NOT NULL,
    cart        INTEGER                                                                              NOT NULL,
    inserted_at TIMESTAMP WITH TIME ZONE DEFAULT pg_catalog.TIMEZONE('UTC'::TEXT, CURRENT_TIMESTAMP) NOT NULL,
    CONSTRAINT orders_user_cart_insertedat_unique UNIQUE (_user, cart, inserted_at),
    CONSTRAINT orders_users_fk FOREIGN KEY (_user) REFERENCES MarketNexus.Users (id) ON DELETE CASCADE,
    CONSTRAINT orders_carts_fk FOREIGN KEY (cart) REFERENCES MarketNexus.Carts (id) ON DELETE CASCADE,
    CONSTRAINT orders_id_min_value_check CHECK (MarketNexus.Orders.id >= 1),
    CONSTRAINT orders_user_min_value_check CHECK (MarketNexus.Orders._user >= 1),
    CONSTRAINT orders_cart_min_value_check CHECK (MarketNexus.Orders.cart >= 1)
    --CONSTRAINT orders_insertedat_min_value_check CHECK (MarketNexus.Orders.inserted_at <= CURRENT_TIMESTAMP),
);


CREATE
    OR REPLACE FUNCTION CHECK_ORDER_USER_SALE_FUNCTION()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT (o._user = c._user) AS are_equals
        FROM MarketNexus.Orders o
                 JOIN MarketNexus.Carts c ON o.cart = c.id
        WHERE o.id = NEW.ID) THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'User that Ordered his Cart error, with this Order ID: %, this Cart ID: % and this User ID: %.' , NEW.ID, NEW.cart, NEW._user;
    END IF;
END;
$$ LANGUAGE PLPGSQL;

CREATE
    OR REPLACE TRIGGER ORDER_USER_SALE_FUNCTION_TRIGGER
    AFTER
        INSERT OR UPDATE
    ON MarketNexus.Orders
    FOR EACH ROW
EXECUTE
    FUNCTION MarketNexus.CHECK_ORDER_USER_SALE_FUNCTION();


COMMENT ON TABLE MarketNexus.Orders IS 'MarketNexus User who buys all Sale Products that are in his Cart.';

ALTER TABLE MarketNexus.Orders
    OWNER TO postgres;

/*
INSERT INTO MarketNexus.Orders(_user, cart)
VALUES (1, 1);
*/

DROP FUNCTION IF EXISTS GET_USER_SOLD_SALES_STATS();

CREATE OR REPLACE FUNCTION GET_USER_SOLD_SALES_STATS(user_id BIGINT)
    RETURNS TABLE
            (
                day               TEXT,
                numberOfSoldSales BIGINT
            )
AS
$$
BEGIN
    RETURN QUERY
        WITH RECURSIVE date_series AS (SELECT CURRENT_TIMESTAMP AS date
                                       UNION ALL
                                       SELECT (date - INTERVAL '1 day')
                                       FROM date_series
                                       WHERE date_series.date > (CURRENT_TIMESTAMP - INTERVAL '6 days'))
        SELECT TO_CHAR(date_series.date, 'yyyy-MM-dd') AS day,
               COALESCE(COUNT(DISTINCT s.id), 0)       AS numberOfSoldSales
        FROM date_series
                 LEFT JOIN MarketNexus.Orders o ON CAST(date_series.date AS DATE) = CAST(o.inserted_at AS DATE)
                 LEFT JOIN MarketNexus.Carts c ON o.cart = c.id
                 LEFT JOIN MarketNexus.cart_line_items cli ON cli.cart = c.id
                 LEFT JOIN MarketNexus.Sales s ON cli.sale = s.id AND s._user = user_id
        GROUP BY date_series.date
        ORDER BY date_series.date;
END
$$ LANGUAGE PLPGSQL;

SELECT *
FROM GET_USER_SOLD_SALES_STATS(1);

DROP FUNCTION IF EXISTS GET_USERS_SALES_STATS();

CREATE OR REPLACE FUNCTION GET_USERS_SALES_STATS()
    RETURNS TABLE
            (
                user_username VARCHAR,
                MIN           BIGINT,
                MAX           BIGINT,
                CNT           BIGINT,
                TOT           NUMERIC,
                AVG           NUMERIC,
                "%"           TEXT,
                STDDEV        NUMERIC
            )
AS
$$
BEGIN
    RETURN QUERY
        SELECT userAndDayToSalesPublished.user_username,
               MIN(userAndDayToSalesPublished.salesPublishedPerDay)                              AS MIN,
               MAX(userAndDayToSalesPublished.salesPublishedPerDay)                              AS MAX,
               COUNT(DISTINCT CASE
                                  WHEN userAndDayToSalesPublished.salesPublishedPerDay > 0
                                      THEN (userAndDayToSalesPublished.salesPublishedPerDay,
                                            userAndDayToSalesPublished.inserted_at) END)         AS CNT,
               SUM(userAndDayToSalesPublished.salesPublishedPerDay)                              AS TOT,
               ROUND(AVG(userAndDayToSalesPublished.salesPublishedPerDay), 2)                    AS AVG,
               CAST((ROUND(
                             CAST(
                                     (
                                         CAST(SUM(userAndDayToSalesPublished.salesPublishedPerDay) AS FLOAT) /
                                         (SELECT COUNT(s2.id) FROM Sales s2)
                                         ) AS NUMERIC
                             ),
                             2
                     ) * 100) AS TEXT) || ' %'                                                   AS "%",
               ROUND(COALESCE(STDDEV(userAndDayToSalesPublished.salesPublishedPerDay), 0.00), 2) AS STDDEV
        FROM (SELECT c.username           AS user_username,
                     s.inserted_at::DATE,
                     COUNT(DISTINCT s.id) AS salesPublishedPerDay
              FROM Users u
                       JOIN Credentials c ON u.credentials = c.id
                       LEFT JOIN Sales s ON s._user = u.id
              GROUP BY c.username, s.inserted_at::DATE
              ORDER BY salesPublishedPerDay DESC) AS userAndDayToSalesPublished
        GROUP BY userAndDayToSalesPublished.user_username
        ORDER BY SUM(userAndDayToSalesPublished.salesPublishedPerDay) DESC -- TOT
        LIMIT 5;
END
$$ LANGUAGE PLPGSQL;

SELECT *
FROM GET_USERS_SALES_STATS();

