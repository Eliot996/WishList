CREATE SCHEMA `wishlist` ;

CREATE TABLE `wishlist`.`users` (
                                    `ID` INT NOT NULL AUTO_INCREMENT,
                                    `email` VARCHAR(255) NOT NULL,
                                    `password` VARCHAR(64) NOT NULL,
                                    `salt` VARCHAR(16) NOT NULL,
                                    `name` VARCHAR(100) NOT NULL,
                                    PRIMARY KEY (`ID`),
                                    UNIQUE INDEX `email_UNIQUE` (`email` ASC));

CREATE TABLE `wishlist`.`login-tokens` (
                                           `token` VARCHAR(255) NOT NULL,
                                           `UserID` INT NULL,
                                           PRIMARY KEY (`token`),
                                           INDEX `UserID_idx` (`UserID` ASC),
                                           CONSTRAINT `UserID`
                                               FOREIGN KEY (`UserID`)
                                                   REFERENCES `wishlist`.`users` (`ID`)
                                                   ON DELETE NO ACTION
                                                   ON UPDATE NO ACTION);

CREATE TABLE `wishlist`.`wishlists` (
                                        `ID` INT NOT NULL AUTO_INCREMENT,
                                        `name` VARCHAR(50) NOT NULL,
                                        `UserID` INT NOT NULL,
                                        PRIMARY KEY (`ID`),
                                        INDEX `UserID_idx` (`UserID` ASC),
                                        FOREIGN KEY (`UserID`)
                                            REFERENCES `wishlist`.`users` (`ID`)
                                            ON DELETE NO ACTION
                                            ON UPDATE NO ACTION);

CREATE TABLE `wishlist`.`wishes` (
                                     `WishlistID` INT NOT NULL,
                                     `position` INT NOT NULL,
                                     `title` VARCHAR(50) NULL,
                                     `description` VARCHAR(255) NULL,
                                     `link` VARCHAR(255) NULL,
                                     `reserverID` INT NULL,
                                     PRIMARY KEY (`WishlistID`, `position`),
                                     INDEX `reserverID_idx` (`reserverID` ASC),
                                     CONSTRAINT `reserverID`
                                         FOREIGN KEY (`reserverID`)
                                             REFERENCES `wishlist`.`users` (`ID`)
                                             ON DELETE NO ACTION
                                             ON UPDATE NO ACTION,
                                     CONSTRAINT `wishlistID`
                                         FOREIGN KEY (`WishlistID`)
                                             REFERENCES `wishlist`.`wishlists` (`ID`)
                                             ON DELETE NO ACTION
                                             ON UPDATE NO ACTION);
