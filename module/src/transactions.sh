#!/bin/bash
#Getting the path where the bash script is located irrespective of your current directory
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"
#Moving the tranasction files to a folder in test
for file in *; do
    if [[ ${file##*/} =  "dailytransactionsfile"* ]]; then
        echo "Transaction File: ${file##*/}"
        mv $file $PWD/test/transaction_outputs
    else
        echo "Normal File: ${file##*/}"
    fi
done
#looping through expected transaction files, note the counter
#since every transaction file has a number appended to it, starting at 1
COUNTER=1
for file in $PWD/test/expected_transaction/*; do
    echo "Checking transaction from test: ${file##*/}" 
    diff $file $PWD/test/transaction_outputs/dailytransactionsfile$COUNTER.txt
done