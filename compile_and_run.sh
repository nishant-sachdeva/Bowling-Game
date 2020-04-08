#! /bin/bash

cp -r source_code/* code/ 
cd code 
javac *.java
echo "type the command 'java drive' to start the game now"