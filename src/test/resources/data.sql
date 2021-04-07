DROP TABLE IF EXISTS App_user;
DROP TABLE IF EXISTS Folder;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Item;
DROP TABLE IF EXISTS Image;

CREATE TABLE App_user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(1) NOT NULL,
    date_registered TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT ak_username UNIQUE (username)
);


CREATE TABLE Folder (
    folder_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    folder_name VARCHAR(40) NOT NULL,
    parent_id BIGINT,
    user_id BIGINT NOT NULL,
    CONSTRAINT ak_folder_name_parent_id_user_id UNIQUE (folder_name, parent_id, user_id),
    FOREIGN KEY (parent_id)
        REFERENCES Folder (folder_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id)
        REFERENCES App_user (user_id)
);


CREATE TABLE Category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category_name VARCHAR(40) NOT NULL,
    CONSTRAINT ak_user_id_category_name UNIQUE (user_id, category_name),
    FOREIGN KEY (user_id)
        REFERENCES App_user (user_id)
);


CREATE TABLE Item (
    item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_name VARCHAR(40) NOT NULL,
    folder_id BIGINT,
    user_id BIGINT NOT NULL,
    category_id BIGINT,
    date_added TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
    description TEXT,
    serial_number VARCHAR(50),
    item_price REAL,
    CONSTRAINT positive_item_price CHECK (item_price > 0),
    FOREIGN KEY (folder_id)
        REFERENCES Folder (folder_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id)
        REFERENCES Category (category_id),
    FOREIGN KEY (user_id)
        REFERENCES App_user (user_id)
);

CREATE TABLE Image (
    image_id BIGINT PRIMARY KEY,
    image_bytes BLOB NOT NULL,
    FOREIGN KEY (image_id)
        REFERENCES Item (item_id) ON DELETE CASCADE
);