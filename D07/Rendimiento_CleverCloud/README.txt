CONTENIDO DE LA CARPETA

- Archivo CleverCloud_CRUD_Test.jmx: contiene el test ejecutable por Apache Jmeter.
- Capturas gr�fico: contiene las capturas de pantalla de los gr�ficos obtenidos como resultado del test.
- Capturas informe: contiene capturas de la tabla del informe del test. Esta tabla contiene informaci�n similar a la del gr�fico pero desglosada, y adem�s contiene una columna muy interesante la columna de porcentaje de errores producidos.

NOTA ACLARATORIA

- Dentro de las carpetas de Capturas hay otras dos subcarpetas llamadas test 100_50 y test 150_50. Estos n�mero indican los par�metros utilizados durante la ejecuci�n de las pruebas. El primer n�mero indica el n�mero de hilos ejecutado (simulan el n�mero de usuarios concurrentes). El segundo n�mero indica el n�mero de veces que cada usuario (hilo) repite las acciones programadas para el tests. Este n�mero no se muy bien para que se utiliza pero creo que sirve simplemente para aumentar la carga al sistema.
- Solo he hecho hasta el test con los datos 150_50, porque a partir de estos datos, el sistema comienza a tener errores. En la tabla del tests con estos datos se puede ver que hay un n�mero muy peque�o de errores en algunas llamadas al servidor.