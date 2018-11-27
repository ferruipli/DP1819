DROP FUNCTION IF EXISTS cuenta_positive_words;
DROP FUNCTION IF EXISTS cuenta_negative_words;
DROP FUNCTION IF EXISTS my_max;
DROP PROCEDURE IF EXISTS computar_score_customer;

DELIMITER $$

CREATE FUNCTION cuenta_positive_words(comment TEXT) RETURNS INT
	BEGIN
		SET @i := 0;
		WHILE @i <= (SELECT COUNT(POSITIVE_WORDS) FROM CUSTOMISATION_POSITIVE_WORDS) DO
			SET @veces := (SELECT(LENGTH(comment) - LENGTH(REPLACE(comment, @i, '')))/LENGTH(@i));
			SET @totalVeces := @totalVeces + SUM(@veces);
			SET @i := @i +1;
		END WHILE;
		RETURN @totalVeces;
	END$$
		
#DELIMITER $$


CREATE FUNCTION cuenta_negative_words(comment TEXT) RETURNS INT
	BEGIN
		SET @i := 0;
		WHILE @i <= (SELECT COUNT(NEGATIVE_WORDS) FROM CUSTOMISATION_NEGATIVE_WORDS) DO
			SET @veces := (SELECT (LENGTH(comment)-length(replace(comment,@i,'')))/length(@i));
			SET @totalVeces := @totalVeces + SUM(@veces);
			SET @i := @i +1;
		END WHILE;
		RETURN @totalVeces;
	END $$

#DELIMITER $$

CREATE FUNCTION my_max(val1 INT, val2 INT) RETURNS INT
	BEGIN
		IF val1 > val2
			THEN RETURN val1;
		ELSE
			RETURN val2;
		END IF;
	END $$
	
#DELIMITER $$

CREATE PROCEDURE computar_score_customer (customer_id IN customer.id%TYPE) IS
	BEGIN
	 SET @comments1 := (SELECT ENDORSEMENT.COMMENTS FROM ENDORSEMENT WHERE ENDORSEMENT.RECIPIENT = customer_id);
	 SET @commentsSize := (SELECT COUNT(ENDORSEMENT.COMMENTS) FROM ENDORSEMENT WHERE ENDORSEMENT.RECIPIENT = customer_id);
	 FOR comment IN 1..@commentsSize LOOP
		SET @p := cuenta_positive_words(comment);
		SET @n := cuenta_negative_words(comment);
		SET @score := ( (@p - @n)/ my_max(@p,@n));
	 END LOOP;
	 INSERT INTO CUSTOMER.SCORE
		VALUES @score;
	 COMMIT WORK;
	END computar_score_customer;
$


DELIMITER $$
