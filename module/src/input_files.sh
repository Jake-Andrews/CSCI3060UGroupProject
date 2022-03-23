#!/bin/bash
#Getting the path where the bash script is located irrespective of your current directory
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path" #change directory to the scripts directory
find -type f -name '*dailytransactionsfile*' -delete #delete any file that matches the string provided

#overwriting useracounts.txt file, so we have the same starting state for every run of tests
printf "UUUUUUUUUU________AA\nUSER______________AA\nUSR_______________PS\nUSR1______________PS\nUSER2_____________AA\nUSER3_____________FS\nUSER123__________AA\nUSER_____________AA" > useraccounts.txt 
#overwriting availablerentalsfile.txt
echo "e6a5a8ec__USER_____________Toronto____________________4__450.0___true___03" > availablerentalsfile.txt #> to rewrite the file
echo "113142f7__USER_____________Whitby_____________________2__300.0___false__00" >> availablerentalsfile.txt #>> instead of > to append
echo "2cd2dfcb__USER_____________Oshawa_____________________5__499.99__true___03" >> availablerentalsfile.txt
echo "ae3e68e7__USR______________Stratford-Upon-Avon________3__249.99__false__00" >> availablerentalsfile.txt
echo "91510b4f__USR______________London_____________________9__999.99__true___02" >> availablerentalsfile.txt
echo "3de4de14__USER_____________Dublin_____________________6__400.0___true___01" >> availablerentalsfile.txt
echo "06e86bc1__USER_____________Ottawa_____________________4__749.99__false__00" >> availablerentalsfile.txt
echo "91da4934__USER_____________Ottawa_____________________2__700.0___false__00" >> availablerentalsfile.txt
echo "117fb1bf__USER_____________Toronto____________________5__500.0___false__00" >> availablerentalsfile.txt
echo "a943c2b1__USER_____________Toronto____________________3__400.0___false__00" >> availablerentalsfile.txt 
echo "a943c2b2__USER_____________Toronto____________________3__400.0___false__00" >> availablerentalsfile.txt 
echo -n "a943c2b3__USER_____________Toronto____________________3__400.0___false__00" >> availablerentalsfile.txt #-n to remove trailing new line

#loop through all the files in the inputs folder
for file in $parent_path/test/inputs/*; do 
    echo "running test ${file##*/}" 
    java -cp . main/Main.java availablerentalsfile.txt useraccounts.txt dailytransactionsfile.txt < $file > $parent_path/test/outputs/${file##*/} #${file##*/} contains the filename
done