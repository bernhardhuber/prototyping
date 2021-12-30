#!/bin/sh

CLASSPATH=/mnt/kingston_120gb/mvn_repository/com/googlecode/lanterna/lanterna/3.1.1/lanterna-3.1.1.jar:target/classes
MAINCLASS=org.huberb.lanterna.MainMessageDialog

java -cp $CLASSPATH $MAINCLASS
