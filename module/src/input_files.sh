#!/bin/bash
#Getting the path where the bash script is located irrespective of your current directory
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path" #change directory to the scripts directory
find -type f -name '*dailytransactionsfile*' -delete
for file in $parent_path/test/inputs/*; do #loop through all the files in the inputs folder
    echo "running test ${file##*/}" 
    java -cp . main/Main.java availablerentalsfile.txt useraccounts.txt dailytransactionsfile.txt < $file > $parent_path/test/outputs/${file##*/} #${file##*/} contains the filename
done