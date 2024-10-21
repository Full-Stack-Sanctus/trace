#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -ne 3 ]; then
    echo "Usage: $0 <groupId> <artifactId> <version>"
    exit 1
fi

# Get the parameters
groupId="$1"
artifactId="$2"
version="$3"

# Define the path to your pom.xml file
pomFile="pom.xml"

# Check if the dependency already exists in pom.xml
if grep -q "<groupId>$groupId</groupId>" "$pomFile" && grep -q "<artifactId>$artifactId</artifactId>" "$pomFile"; then
    echo "Dependency: $groupId:$artifactId:$version already exists in $pomFile"
    exit 0
fi

# Use sed to add the new dependency to the pom.xml
if grep -q "<dependencies>" "$pomFile"; then
    sed -i "/<dependencies>/a \ \ \ \ <dependency>\n\ \ \ \ \ <groupId>$groupId</groupId>\n\ \ \ \ \ <artifactId>$artifactId</artifactId>\n\ \ \ \ \ <version>$version</version>\n\ \ \ \ </dependency>" "$pomFile"
else
    echo "<dependencies>" >> "$pomFile"
    echo "    <dependency>" >> "$pomFile"
    echo "        <groupId>$groupId</groupId>" >> "$pomFile"
    echo "        <artifactId>$artifactId</artifactId>" >> "$pomFile"
    echo "        <version>$version</version>" >> "$pomFile"
    echo "    </dependency>" >> "$pomFile"
    echo "</dependencies>" >> "$pomFile"
fi

echo "Added dependency: $groupId:$artifactId:$version to $pomFile"
