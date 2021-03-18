CREATE DATABASE Inventory;

CREATE TABLE Category (
                          category_id SERIAL PRIMARY KEY,
                          category_name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE App_user (
                          user_id SERIAL PRIMARY KEY,
                          username VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          role VARCHAR(10) NOT NULL
);

CREATE TABLE Folder (
                        folder_id SERIAL PRIMARY KEY,
                        folder_name VARCHAR(255) NOT NULL,
                        folder_path_name VARCHAR(255) NOT NULL,
                        parent_id INTEGER,
                        user_id INTEGER NOT NULL,
                        FOREIGN KEY (parent_id)
                            REFERENCES Folder (folder_id) ON DELETE CASCADE,
                        FOREIGN KEY (user_id)
                            REFERENCES App_user (user_id)
);

CREATE TABLE Item (
                      item_id SERIAL PRIMARY KEY,
                      item_name VARCHAR(255) NOT NULL,
                      folder_id INTEGER,
                      user_id INTEGER NOT NULL,
                      category_id INTEGER,
                      date_added DATE NOT NULL DEFAULT CURRENT_DATE,
                      description TEXT,
                      FOREIGN KEY (folder_id)
                          REFERENCES Folder (folder_id) ON DELETE CASCADE,
                      FOREIGN KEY (category_id)
                          REFERENCES Category (category_id),
                      FOREIGN KEY (user_id)
                          REFERENCES App_user (user_id)
);

CREATE TABLE Image (
                       image_id INTEGER PRIMARY KEY,
                       image_bytes BYTEA NOT NULL,
                       FOREIGN KEY (image_id)
                           REFERENCES Item (item_id) ON DELETE CASCADE
);


insert into app_user (username, password, role) values ('test', 'test', 'test');
insert into folder (folder_name, user_id) values ('test folder', 1);
insert into folder (folder_name, parent_id, user_id) values ('test folder 2', 1, 1);
