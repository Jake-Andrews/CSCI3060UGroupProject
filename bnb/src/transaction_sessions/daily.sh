parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

# Run front end x number of transaction sessions
number = 0
while [ $number -lt 10 ] do
    java -jar $parent_path/../../../../bnb-1.0-SNAPSHOT-jar-with-dependencies.jar availablerentalsfile.txt useraccounts.txt
done

# Concatenate the daily transaction txt files into a merged txt file
cat *.txt >> mergedtransactionsfile.txt