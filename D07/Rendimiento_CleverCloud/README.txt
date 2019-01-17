CONTENIDO DE LA CARPETA

- Archivo CleverCloud_CRUD_Test.jmx: contiene el test ejecutable por Apache Jmeter.
- Capturas gráfico: contiene las capturas de pantalla de los gráficos obtenidos como resultado del test.
- Capturas informe: contiene capturas de la tabla del informe del test. Esta tabla contiene información similar a la del gráfico pero desglosada, y además contiene una columna muy interesante la columna de porcentaje de errores producidos.

NOTA ACLARATORIA

- Dentro de las carpetas de Capturas hay otras dos subcarpetas llamadas test 100_50 y test 150_50. Estos número indican los parámetros utilizados durante la ejecución de las pruebas. El primer número indica el número de hilos ejecutado (simulan el número de usuarios concurrentes). El segundo número indica el número de veces que cada usuario (hilo) repite las acciones programadas para el tests. Este número no se muy bien para que se utiliza pero creo que sirve simplemente para aumentar la carga al sistema.
- Solo he hecho hasta el test con los datos 150_50, porque a partir de estos datos, el sistema comienza a tener errores. En la tabla del tests con estos datos se puede ver que hay un número muy pequeño de errores en algunas llamadas al servidor.