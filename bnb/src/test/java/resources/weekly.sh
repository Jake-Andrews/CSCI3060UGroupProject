# Run the daily script 5 times (once for each workday)
day = 1
while [ $number -lt 6 ] do
    sh ./daily.sh
done