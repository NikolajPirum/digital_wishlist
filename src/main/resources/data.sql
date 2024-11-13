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
