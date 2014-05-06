GetCategoriesQuery
==================
Author: Diego Agudelo
Mail: diego.agudelo2@gmail.com

* Java 1.8.0.5
* args4j-2.0.17.jar
* sqlite-jdbc-3.7.2.jar

How to run this app:

From the data file of this project open a command prompt and do 1 of the following options

1) Write "./categories.sh" with one of the following options:

    [--rebuild, --info <category_id>]

2) Write "java -jar categories.jar" with one of the previous options.  


Here is a sample session with the program:

% ./categories --rebuild
% ./categories --info 179281

% java -jar categories.jar --rebuild
% java -jar categories.jar --info 179281
