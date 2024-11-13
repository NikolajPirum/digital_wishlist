--CREATE DATABASE WishListDB IF NOT EXIST;
-- Use the database
CREATE SCHEMA IF NOT EXISTS WishListDb;

DROP TABLE IF EXISTS Appuser CASCADE;
DROP TABLE IF EXISTS WishList CASCADE;
DROP TABLE IF EXISTS Present CASCADE;
DROP TABLE IF EXISTS Reserve CASCADE;
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
                          FOREIGN KEY (UserID) REFERENCES AppUser(UserID) ON DELETE CASCADE
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
                         FOREIGN KEY (WishlistID) REFERENCES WishList(WIshlistID) ON DELETE CASCADE
);


-- Create Reserve table
CREATE TABLE Reserve (
                         ReserveID INTEGER NOT NULL AUTO_INCREMENT,
                         PresentID INTEGER,
                         UserID INTEGER,
                         PRIMARY KEY(ReserveID),
                         FOREIGN KEY (PresentID) REFERENCES Present(PresentID) ON DELETE CASCADE,
                         FOREIGN KEY (UserID) REFERENCES AppUser(UserID) ON DELETE CASCADE,
                         CONSTRAINT unique_reservation UNIQUE (PresentID, UserID)
);

