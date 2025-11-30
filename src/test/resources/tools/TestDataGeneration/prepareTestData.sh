#!/bin/bash

patient_data_dir="basePatientData/*"
date_regex_pattern='<([[:digit:]]*)_DAYS_AGO>'

actual_file_content=""

replaceDateParameter() {
  today=$(date)
  calculated_date=$(echo $(date -d "${today} -$2 days" +"%Y-%m-%d"))
  calculated_date_formatted=$(echo $(date -d "${today} -$2 days" +"%b %d, %Y"))
  printf "| %-15s | %-15s |\n" "$1" "$calculated_date_formatted"
  actual_file_content="${actual_file_content//$1/$calculated_date}"
}

for file in ${patient_data_dir}; do
  printf "\nActual patient file: $file\n"
  actual_file_content=$(<${file})

  printf "\n"
  # Replace "<TODAY>" placeholder if there is any in the file
  replaceDateParameter "<TODAY>" 0

  # Replace all other placeholders if there is any in the file
  replace=true
  while $replace; do
    [[ $actual_file_content =~ $date_regex_pattern ]]
    if [[ ${BASH_REMATCH[0]} ]]; then
      stringToReplace=${BASH_REMATCH[0]}
      daysAgo=${BASH_REMATCH[1]}
      replaceDateParameter $stringToReplace $daysAgo
    else
      replace=false
    fi
  done
  printf "\n"

  # Create filename for generated patient data
  splittedFilePath=(${file///// })
  splittedFileName=(${splittedFilePath[1]//./ })
  new_file_name="generated/${splittedFileName[0]}_generated.${splittedFileName[1]}"

  echo ${actual_file_content} >${new_file_name}
  printf "Updated patient data is saved into file: $new_file_name\n"
  printf "__________________________________________________\n"
done

read -p "Press Enter to exit..."
