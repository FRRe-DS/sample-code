Desarrollo de Aplicaciones Cliente Servidor
=

Mapeo de Objetos a Relacional con Java Persistence API
-

Build
-
$ mvn clean package 

Run
-
$ mvn flyway:migrate -Dflyway.dacs.url=jdbc:mysql://localhost/dacs?useSSL=false -Dflyway.dacs.user=dacs -Dflyway.dacs.password=dacs

