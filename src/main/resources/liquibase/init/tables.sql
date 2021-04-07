CREATE TABLE App_user (
    user_id BIGSERIAL NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(1) NOT NULL,
    date_registered TIMESTAMP NOT NULL DEFAULT Now(),
    CONSTRAINT pk_app_user PRIMARY KEY (user_id),
    CONSTRAINT ak_username UNIQUE (username)
);


CREATE TABLE Folder (
    folder_id BIGSERIAL NOT NULL,
    folder_name VARCHAR(40) NOT NULL,
    parent_id BIGINT,
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_folder PRIMARY KEY (folder_id),
    CONSTRAINT ak_folder_name_parent_id_user_id UNIQUE (folder_name, parent_id, user_id),
    FOREIGN KEY (parent_id)
        REFERENCES Folder (folder_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id)
        REFERENCES App_user (user_id)
);


CREATE TABLE Category (
    category_id BIGSERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    category_name VARCHAR(40) NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (category_id),
    CONSTRAINT ak_user_id_category_name UNIQUE (user_id, category_name),
    FOREIGN KEY (user_id)
        REFERENCES App_user (user_id)
);


CREATE TABLE Item (
    item_id BIGSERIAL NOT NULL,
    item_name VARCHAR(40) NOT NULL,
    folder_id BIGINT,
    user_id BIGINT NOT NULL,
    category_id BIGINT,
    date_added TIMESTAMP NOT NULL DEFAULT Now(),
    description TEXT,
    serial_number VARCHAR(50),
    item_price REAL,
    CONSTRAINT pk_item PRIMARY KEY (item_id),
    CONSTRAINT positive_item_price CHECK (item_price > 0),
    FOREIGN KEY (folder_id)
        REFERENCES Folder (folder_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id)
        REFERENCES Category (category_id),
    FOREIGN KEY (user_id)
        REFERENCES App_user (user_id)
);


CREATE TABLE Image (
    image_id BIGINT NOT NULL,
    image_bytes BYTEA NOT NULL,
    CONSTRAINT pk_image PRIMARY KEY (image_id),
    FOREIGN KEY (image_id)
        REFERENCES Item (item_id) ON DELETE CASCADE
);
