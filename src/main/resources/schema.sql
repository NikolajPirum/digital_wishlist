--CREATE DATABASE WishListDB IF NOT EXIST;
-- Use the database


-- Create AppUser table
CREATE TABLE AppUser (
                         UserID INTEGER NOT NULL AUTO_INCREMENT,
                         Name VARCHAR(50),
                         Username VARCHAR(30),
                         Password VARCHAR(30),
                         Email VARCHAR(50),
                         PRIMARY KEY(UserID)
    -- This column will hold foreign key
);

-- Create WishList table
CREATE TABLE WishList (
                          WishlistID INTEGER NOT NULL AUTO_INCREMENT,
                          Wishlistname VARCHAR(30),
                          UserID INTEGER,
                          PRIMARY KEY(WishlistID),
                          FOREIGN KEY (UserID) REFERENCES AppUser(UserID)
);

-- Create Present table
CREATE TABLE Present (
                         PresentID INTEGER NOT NULL AUTO_INCREMENT,
                         Presentname VARCHAR(30),
                         Brand VARCHAR(30),
                         Price DECIMAL(10,2),
                         WishlistID INTEGER,  -- This column will hold foreign key
                         Link VARCHAR(255),
                         PRIMARY KEY(PresentID),
                         FOREIGN KEY (WishlistID) REFERENCES WishList(WIshlistID)
);


-- Create Reserve table
CREATE TABLE Reserve (
                         ReserveID INTEGER NOT NULL AUTO_INCREMENT,
                         PresentID INTEGER,
                         UserID INTEGER,
                         PRIMARY KEY(ReserveID),
                         FOREIGN KEY (PresentID) REFERENCES Present(PresentID) ON DELETE CASCADE,
                         FOREIGN KEY (UserID) REFERENCES AppUser(UserID)
);

-- Insert data into AppUser table
INSERT INTO AppUser (Name, Username, Password, Email)
VALUES ('Alice', 'alice01', 'pass1234', 'alice@example.com'),
       ('Bob', 'bob_the_builder', 'bobpass', 'bob@example.com'),
       ('Charlie', 'charlie123', 'charliepass', 'charlie@example.com');

-- Insert data into WishList table
INSERT INTO WishList (Wishlistname, UserID)
VALUES ('Alices Birthday Wishlist', 1),
       ('Bobs Christmas Wishlist', 2),
       ('Charlies Wedding Wishlist', 3);

-- Insert data into Present table
INSERT INTO Present (Presentname, Brand, Price, WishlistID, Link)
VALUES ('Wireless Headphones', 'Sony', 150.00, 1, 'https://example.com/sony-headphones'),
       ('Toolset', 'Bosch', 80.00, 2, 'https://example.com/bosch-toolset'),
       ('Coffee Maker', 'Nespresso', 120.00, 3, 'https://example.com/nespresso-coffee-maker');

-- Insert data into Reserve table
INSERT INTO Reserve (PresentID, UserID)
VALUES (1, 2),
       (2, 1),
       (3, 1);