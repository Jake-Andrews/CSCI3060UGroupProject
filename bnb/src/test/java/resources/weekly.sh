# cd into directory where the bash script is located
# Run the daily script 5 times (once for each workday)
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"
day=1
while [ $day -le 6 ]; do /bin/bash $parent_path/daily.sh $(( day++ )); done