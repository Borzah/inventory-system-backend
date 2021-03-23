CREATE DATABASE Inventory;

CREATE TABLE App_user (
user_id BIGSERIAL PRIMARY KEY,
username VARCHAR(255) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
role VARCHAR(10) NOT NULL,
date_registered DATE NOT NULL DEFAULT CURRENT_DATE
);


CREATE TABLE Folder (
folder_id BIGSERIAL PRIMARY KEY,
folder_name VARCHAR(255) NOT NULL,
parent_id BIGINTEGER,
user_id BIGINTEGER NOT NULL,
FOREIGN KEY (parent_id)
	REFERENCES Folder (folder_id) ON DELETE CASCADE,
FOREIGN KEY (user_id)
    REFERENCES App_user (user_id)
);


CREATE TABLE Category (
category_id BIGSERIAL PRIMARY KEY,
user_id BIGINTEGER NOT NULL,
category_name VARCHAR(255) NOT NULL,
FOREIGN KEY (user_id)
    REFERENCES App_user (user_id)
);


CREATE TABLE Item (
item_id BIGSERIAL PRIMARY KEY,
item_name VARCHAR(255) NOT NULL,
folder_id BIGINTEGER,
user_id BIGINTEGER NOT NULL,
category_id BIGINTEGER,
date_added DATE NOT NULL DEFAULT CURRENT_DATE,
description TEXT,
serial_number VARCHAR(50),
item_price REAL,
FOREIGN KEY (folder_id)
    REFERENCES Folder (folder_id) ON DELETE CASCADE,
FOREIGN KEY (category_id)
    REFERENCES Category (category_id),
FOREIGN KEY (user_id)
    REFERENCES App_user (user_id)
);

CREATE TABLE Image (
image_id BIGINTEGER PRIMARY KEY,
image_bytes BYTEA NOT NULL,
FOREIGN KEY (image_id)
    REFERENCES Item (item_id) ON DELETE CASCADE
);
