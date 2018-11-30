DELIMITER $$

CREATE FUNCTION `acme-handyworker`.`cuenta_negative_words` (comentario TEXT) RETURNS FLOAT
BEGIN
		DECLARE v_finished INTEGER DEFAULT 0;
		DECLARE totalVecesNegative INT DEFAULT 0;
		DECLARE vecesNegative INT DEFAULT 0;
		DECLARE v_negativeWord varchar(100) DEFAULT "";
		DECLARE negativeWords CURSOR FOR SELECT NEGATIVE_WORDS FROM CUSTOMISATION_NEGATIVE_WORDS;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;

		OPEN negativeWords;
		get_negativeWord: LOOP # EL ATRIBUTO QUE RECORRE LA COLECCION DE POSITIVE WORDS SE LLAMA POSITIVE WORDS
		FETCH negativeWords INTO v_negativeWord;
		IF v_finished = 1 THEN
		LEAVE get_negativeWord;
		END IF;
		SET vecesNegative = (SELECT (LENGTH(comentario)-length(replace(comentario,v_negativeWord,'')))/length(v_negativeWord)); # ALMACENA LAS VECES QUE APARECE LA POSITIVE WORD EN CADA COMENTARIO
		SET totalVecesNegative = totalVecesNegative + vecesNegative; # VARIABLE QUE ALMACENA EL TOTAL DE POSITIVE WORDS 
		END LOOP get_negativeWord;
		CLOSE negativeWords;

		RETURN totalVecesNegative;

END $$