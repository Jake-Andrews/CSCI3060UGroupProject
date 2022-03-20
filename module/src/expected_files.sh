#!/bin/bash
#Getting the path where the bash script is located irrespective of your current directory
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"
for file in $PWD/test/outputs/*; do
    echo "checking outputs of test ${file##*/}" 
    diff $file $PWD/test/expected_outputs/${file##*/}
done