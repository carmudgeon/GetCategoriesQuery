#!/bin/bash
#----------------------------------------------------------------------------------------#
# Script to run gcq.jar, this file is in charge of building a database of Ebay categories#
# To allow users search for category information offline                                 #
# ver 0.1 Beta 05/01/2014.                                                               #
#----------------------------------------------------------------------------------------#
#
#
echo “Get Categories Query Application.”
#
#
jar_file=categories.jar
#
#
if [ -f $jar_file ]; then
  java -jar $jar_file $@
  resultado=$?
else
  echo “99 - It was not possible to find $jar_file file”
  exit 0
fi
#
#
if [ $resultado = 0 ]; then
  echo "Result:"
  echo “0 - Application ended successfully.”
    exit 0
fi
if [ $resultado = 1 ]; then
  echo “1 - No valid option.”
    exit 0
fi
if [ $resultado = 2 ]; then
  echo "Result:"
  echo “2 - please refer to the README.txt for the complete list of options.”
    exit 0
fi