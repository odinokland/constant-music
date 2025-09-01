#!/bin/bash

declare -A test_runtime=([fabric]="fabric" [forge]="lexforge" [neoforge]="neoforge")

matrix_content="{\"include\":["
enabled_platforms=$(awk -F= '/stonecutter_enabled_platforms/{print $2}' gradle.properties | tr -d ' ')

for platform in $(echo $enabled_platforms | tr ',' ' '); do
  versions=$(awk -F= '/stonecutter_enabled_'$platform'_versions/{print $2}' gradle.properties | tr -d ' ')
  for version in $(echo $versions | tr ',' ' '); do
    matrix_entry="{\"mod_loader\":\"$platform\",\"test_runtime\":\"${test_runtime[${platform}]}\",\"version\":\"$version\"},"
    matrix_content+="$matrix_entry"
  done
done

matrix_content="${matrix_content%,}]}"
echo "Generated matrix: $matrix_content"
echo "matrix=$matrix_content" >> $GITHUB_OUTPUT