-- Users schema

-- !Ups
CREATE TABLE products (
    id bigint NOT NULL primary key,
    title varchar(255) NOT NULL,
    price bigint NOT NULL,
    description varchar(255) NOT NULL,
    category varchar(255) NOT NULL,
    image varchar(255) NOT NULL,
    rating_rate bigint NOT NULL,
    rating_count  bigint NOT NULL
);

-- !Downs

DROP TABLE products;