CREATE TABLE App_user (
                          user_id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          role VARCHAR(1) NOT NULL,
                          date_registered DATE NOT NULL DEFAULT CURRENT_DATE
);


CREATE TABLE Folder (
                        folder_id BIGSERIAL PRIMARY KEY,
                        folder_name VARCHAR(40) NOT NULL,
                        parent_id BIGINT,
                        user_id BIGINT NOT NULL,
                        FOREIGN KEY (parent_id)
                            REFERENCES Folder (folder_id) ON DELETE CASCADE,
                        FOREIGN KEY (user_id)
                            REFERENCES App_user (user_id)
);


CREATE TABLE Category (
                          category_id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          category_name VARCHAR(40) NOT NULL,
                          FOREIGN KEY (user_id)
                              REFERENCES App_user (user_id)
);


CREATE TABLE Item (
                      item_id BIGSERIAL PRIMARY KEY,
                      item_name VARCHAR(40) NOT NULL,
                      folder_id BIGINT,
                      user_id BIGINT NOT NULL,
                      category_id BIGINT,
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
                       image_id BIGINT PRIMARY KEY,
                       image_bytes BYTEA NOT NULL,
                       FOREIGN KEY (image_id)
                           REFERENCES Item (item_id) ON DELETE CASCADE
);
