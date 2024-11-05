CREATE DATABASE IF NOT EXISTS WishlistDB;

USE WishlistDB;

-- Create User table
CREATE TABLE User (
                      UserID INTEGER NOT NULL AUTO_INCREMENT,
                      Name VARCHAR(15),
                      Username VARCHAR(20) UNIQUE,
                      Password VARCHAR(50),
                      Email VARCHAR(50),
                      WishlistID INTEGER,
                      PRIMARY KEY(UserID)
);

-- Create Present table
CREATE TABLE Present (
                         PresentID INTEGER NOT NULL AUTO_INCREMENT,
                         Presentname VARCHAR(60),
                         Brand VARCHAR(30),
                         Price DOUBLE,
                         WishlistID INTEGER,
                         Link VARCHAR(50),
                         PRIMARY KEY(PresentID)
);

-- Create WishList table
CREATE TABLE WishList (
                          WishlistID INTEGER NOT NULL AUTO_INCREMENT,
                          Wishlistname VARCHAR(30),
                          UserID INTEGER,
                          PRIMARY KEY(WishlistID)
);

-- Create Reserve table
CREATE TABLE Reserve (
                         ReserveID INTEGER NOT NULL AUTO_INCREMENT,
                         PresentID INTEGER,
                         UserID INTEGER,
                         PRIMARY KEY(ReserveID)
);

-- Add foreign key constraints with consistent casing
ALTER TABLE WishList
    ADD CONSTRAINT FK_Wishlist_UserID FOREIGN KEY (UserID) REFERENCES User(UserID);

ALTER TABLE User
    ADD CONSTRAINT FK_User_WishlistID FOREIGN KEY (WishlistID) REFERENCES WishList(WishlistID);

ALTER TABLE Present
    ADD CONSTRAINT FK_Present_WishlistID FOREIGN KEY (WishlistID) REFERENCES WishList(WishlistID);

ALTER TABLE Reserve
    ADD CONSTRAINT FK_Reserve_PresentID FOREIGN KEY (PresentID) REFERENCES Present(PresentID);

ALTER TABLE Reserve
    ADD CONSTRAINT FK_Reserve_UserID FOREIGN KEY (UserID) REFERENCES User(UserID);