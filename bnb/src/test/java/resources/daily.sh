parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path" #cd into transaction_sessions

#Deletes transactions file in the directory
find -type f -name '*dailytransactionsfile*' -delete #delete any file that matches the string provided
#Remove all the files in merged_transaction_files
rm $parent_path/merged_transaction_files


#overwriting useracounts.txt file, so we have the same starting state for every run of tests 
echo    "UUUUUUUUUU_____AA" > useraccounts.txt #> overwrites
echo    "USER___________AA" >> useraccounts.txt #>> appends
echo    "USR____________PS" >> useraccounts.txt
echo    "USR1___________PS" >> useraccounts.txt
echo    "USER2__________AA" >> useraccounts.txt
echo    "USER3__________FS" >> useraccounts.txt
echo    "USER123________AA" >> useraccounts.txt
echo    "USER___________AA" >> useraccounts.txt
echo    "USER01_________AA" >> useraccounts.txt
echo    "USER011________FS" >> useraccounts.txt
echo    "USER02_________FS" >> useraccounts.txt 
echo    "USER03_________RS" >> useraccounts.txt
echo    "USER04_________PS" >> useraccounts.txt
echo    "USER05_________PS" >> useraccounts.txt
echo -n "USERTEST_______AA" >> useraccounts.txt #don't append new line character

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

#Run the program on all of the input files in "inputs_transactions"
for file in $parent_path/inputs_transactions/*; do 
    echo "running test ${file##*/}" 
    java -jar $parent_path/../../../../target/bnb-1.0-SNAPSHOT-jar-with-dependencies.jar availablerentalsfile.txt useraccounts.txt < $file  #${file##*/} contains the filename
done

#Moving the transaction files into merged_transaction_files
for file in *; do
    if [[ ${file##*/} =  "dailytransactionsfile"* ]]; then
        echo "Transaction File: ${file##*/}"
        mv $file $PWD/merged_transaction_files
    else
        echo "Normal File: ${file##*/}"
    fi
done

#cd into folder containing the transaction files, then 
#Concatenate the daily transaction txt files into a merged txt file
cd $PWD/merged_transaction_files
cat *.txt >> mergedtransactionsfile.txt
find -type f -name '*dailytransactionsfile*' -delete #delete any file that matches the string provided

# Create a new txt file for the data to be stored
touch data.txt

isNewSession = true

# Read the merged daily transaction files
read mergedtransactionsfile.txt
while read line
do
    if [ $isNewSession = true ];
    then
    # Login with the username on the transaction
    echo "login" >> data.txt
    echo ${line:3:13} >> data.txt
    isNewSession = false
    fi

    case ${line:0:2} in 
    "01")
    # Create
    echo "create" >> data.txt
    echo ${line:3:13} >> data.txt
    echo "AA" >> data.txt
    ;;

    "02")
    # Delete
    echo "delete" >> data.txt
    echo ${line:3:13} >> data.txt
    ;;

    "03")
    # Post
    echo "post" >> data.txt
    echo ${line:26:41} >> data.txt
    echo ${line:42:43} >> data.txt
    echo ${line:51:53} >> data.txt
    ;;

    "04")
    # Search
    echo "search" >> data.txt
    echo ${line:26:41} >> data.txt
    echo ${line:42:43} >> data.txt
    echo ${line:51:53} >> data.txt
    ;;

    "05")
    # Rent
    echo "rent" >> data.txt
    echo ${line:17:25} >> data.txt
    echo ${line:51:53} >> data.txt
    echo "yes" >> data.txt
    ;;

    *)
    # Logout
    echo "logout" >> data.txt
    echo "exit" >> data.txt
    isNewSession = true
    ;;
    esac  
done

# Replace the underscores in the data file with white space
sed 's/[_]//g' data.txt > mock_data.txt