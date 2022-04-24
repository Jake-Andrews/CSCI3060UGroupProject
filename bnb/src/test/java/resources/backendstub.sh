#data.txt will hold the input to the frontend
touch data.txt
login_or_not=1
line1='USER1'
#read mergedtransactionsfile.txt, cases for each command
#outut commands to the frontend corresponding to the transactions strings 
while IFS= read -r line; do
    echo "$line"
    #login_or_not is turned to a 1 after a logout, so the next
    #command do the frontend is to login
    if [[ $login_or_not -eq 1 ]]; then
        echo $login_or_not
        login_or_not=0 
        echo "login" >> data.txt
        echo ${line:3:13} >> data.txt  
    fi
    #logout
    case ${line:0:2} in 
    "00")
    echo "logout" >> data.txt
    login_or_not=1
    ;;
    #create
    "01")
    echo "create" >> data.txt
    echo "$line1" >> data.txt
    echo "AA" >> data.txt
    ;;
    #delete
    "02")
    echo "delete" >> data.txt
    echo "$line1" >> data.txt
    ;;
    #post
    "03")
    echo "post" >> data.txt
    echo ${line:26:41} >> data.txt
    echo ${line:42:43} >> data.txt
    echo ${line:51:53} >> data.txt
    ;;
    #search
    "04")
    echo "search" >> data.txt
    echo ${line:26:41} >> data.txt
    echo ${line:42:43} >> data.txt
    echo ${line:51:53} >> data.txt
    ;;
    #rent
    "05")
    echo "rent" >> data.txt
    echo ${line:17:25} >> data.txt
    echo ${line:51:53} >> data.txt
    echo "yes" >> data.txt
    ;;
    *)
    echo "logout" >> data.txt
    ;;
    esac
done < mergedtransactionsfile.txt
echo "exit" >> data.txt
#remove underscores from file
sed 's/[_]//g' data.txt > mock_data.txt