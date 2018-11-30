-- --------------------------------------------------------------------------------
-- Routine DDL
-- Note: comments before and after the routine body will not be stored by the server
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE FUNCTION `acme-handyworker`.`cuenta_positive_words` (comentario TEXT) RETURNS FLOAT
BEGIN
		DECLARE v_finished INTEGER DEFAULT 0;
		DECLARE totalVecesPositive INT DEFAULT 0;
		DECLARE vecesPositive INT DEFAULT 0;
		DECLARE v_positiveWord varchar(100) DEFAULT "";
		DECLARE positiveWords CURSOR FOR SELECT POSITIVE_WORDS FROM CUSTOMISATION_POSITIVE_WORDS;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET v_finished = 1;

		OPEN positiveWords;
		get_positiveWord: LOOP # EL ATRIBUTO QUE RECORRE LA COLECCION DE POSITIVE WORDS SE LLAMA POSITIVE WORDS
		FETCH positiveWords INTO v_positiveWord;
		IF v_finished = 1 THEN
		LEAVE get_positiveWord;
		END IF;
		SET vecesPositive = (SELECT (LENGTH(comentario)-length(replace(comentario,v_positiveWord,'')))/length(v_positiveWord)); # ALMACENA LAS VECES QUE APARECE LA POSITIVE WORD EN CADA COMENTARIO
		SET totalVecesPositive = totalVecesPositive + vecesPositive; # VARIABLE QUE ALMACENA EL TOTAL DE POSITIVE WORDS 
		END LOOP get_positiveWord;
		CLOSE positiveWords;

		RETURN totalVecesPositive;


END