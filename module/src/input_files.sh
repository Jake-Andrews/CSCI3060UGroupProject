#!/bin/bash
#Getting the path where the bash script is located irrespective of your current directory
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path" #change directory to the scripts directory
find -type f -name '*dailytransactionsfile*' -delete #delete any file that matches the string provided

#overwriting useracounts.txt file, so we have the same starting state for every run of tests
printf "UUUUUUUUUU________AA\nUSER______________AA\nUSR_______________PS\nUSR1______________PS\nUSER2_____________AA\nUSER3_____________FS\nUSER123__________AA\nUSER_____________AA" > useraccounts.txt 
#overwriting availablerentalsfile.txt
echo    "e6a5a8ec_USER___________Toronto___________________4_450.00_t_03" > availablerentalsfile.txt #> to rewrite the file
echo    "113142f7_USER___________Whitby____________________2_300.00_f_00" >> availablerentalsfile.txt #>> instead of > to append
echo    "2cd2dfcb_USER___________Oshawa____________________5_499.99_t_03" >> availablerentalsfile.txt
echo    "ae3e68e7_USR____________Stratford-Upon-Avon_______3_249.99_f_00" >> availablerentalsfile.txt
echo    "91510b4f_USR____________London____________________9_999.99_t_02" >> availablerentalsfile.txt
echo    "3de4de14_USER___________Dublin____________________6_400.00_t_01" >> availablerentalsfile.txt
echo    "06e86bc1_USER___________Ottawa____________________4_749.99_f_00" >> availablerentalsfile.txt
echo    "91da4934_USER___________Ottawa____________________2_700.00_f_00" >> availablerentalsfile.txt
echo    "117fb1bf_USER___________Toronto___________________5_500.00_f_00" >> availablerentalsfile.txt
echo    "a943c2b1_USER___________Toronto___________________3_400.00_f_00" >> availablerentalsfile.txt 
echo    "a943c2b2_USER___________Toronto___________________3_400.00_f_00" >> availablerentalsfile.txt 
echo -n "a943c2b3_USER___________Toronto___________________3_400.00_f_00" >> availablerentalsfile.txt #-n to remove trailing new line

#loop through all the files in the inputs folder
for file in $parent_path/test/inputs/*; do 
    echo "running test ${file##*/}" 
    java -cp . main/Main availablerentalsfile.txt useraccounts.txt dailytransactionsfile.txt < $file > $parent_path/test/outputs/${file##*/} #${file##*/} contains the filename
done