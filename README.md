# dynamic-expression-eval
This repository has a simple working code snippet which evaluates all possible logical operations with dynamic variables using ANTLR grammer.

For Generating ANTLR Visitor classes,

1) Add a new environment variable with the path of the ANTLR complete jar file,
           SET CLASSPATH=;C:\Users\XXXXX\antlr-4.9-complete.jar;%CLASSPATH%
2) Frame the required grammer file (SimpleBoolean.g4) and execute the below command to generate Visitor classes.
           java -cp antlr-4.9-complete.jar org.antlr.v4.Tool SimpleBoolean.g4 -visitor
3) Include the generated .java files into the project and add package statements for them.
