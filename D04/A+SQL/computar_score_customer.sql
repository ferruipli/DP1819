DROP PROCEDURE IF EXISTS `acme-handyworker`.`computar_score_customer`;
DELIMITER $$

CREATE PROCEDURE `acme-handyworker`.`computar_score_customer` (IN customer_id INT)
BEGIN
	DECLARE v_finished INTEGER DEFAULT 0;
	DECLARE p FLOAT DEFAULT 0;
	DECLARE n FLOAT DEFAULT 0;
	DECLARE s FLOAT DEFAULT 0;

	DECLARE v_positiveWord varchar(100) DEFAULT "";
	DECLARE v_negativeWord varchar(100) DEFAULT "";
	DECLARE v_comment varchar(100) DEFAULT "";

	DECLARE positiveWords CURSOR FOR SELECT POSITIVE_WORDS FROM CUSTOMISATION_POSITIVE_WORDS;# COJO LOS POSITIVE WORDS DEL SISTEMA 			
	DECLARE negativeWords CURSOR FOR SELECT NEGATIVE_WORDS FROM CUSTOMISATION_NEGATIVE_WORDS; # COJO LAS NEGATIVE WORDS DEL SISTEMA
	DECLARE comments CURSOR FOR SELECT ENDORSEMENT.COMMENTS FROM ENDORSEMENT WHERE ENDORSEMENT.RECIPIENT = customer_id; # A LA VARIABLE COMMENTS LE ASIGNOS LOS COMENTARIOS DE LOS ENDORSEMENTS QUE TIENE EL HANDYWORKER COMO RECIPIENT

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;	

	OPEN comments;

	get_comment: LOOP # EL ATRIBUTO QUE RECORRE LA COLECCIÓN DE COMENTARIOS SE VA A LLAMAR COMMENTS
		FETCH comments INTO v_comment;
		IF v_finished = 1 THEN
		LEAVE get_comment;
		END IF;
		SET p := (SELECT `acme-handyworker`.`cuenta_positive_words`(v_comment));
		SET n := (SELECT `acme-handyworker`.`cuenta_negative_words`(v_comment));
		SET @computed_score := ((p - n)/ `acme-handyworker`.`my_max`(p,n));
		
	END LOOP get_comment;

	CLOSE comments;

	UPDATE CUSTOMER SET SCORE = @computed_score WHERE id = customer_id; 
	COMMIT WORK;

END