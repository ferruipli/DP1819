DROP FUNCTION IF EXISTS `acme-handyworker`.`my_max`;

DELIMITER $$

CREATE FUNCTION `acme-handyworker`.`my_max`(val1 INT, val2 INT) RETURNS INT
	BEGIN
		IF val1 > val2
			THEN RETURN val1;
		ELSE
			RETURN val2;
		END IF;
	END $$