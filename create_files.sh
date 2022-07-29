#!/bin/bash

clear;

echo "Creating files in the Downloads directory";

base_directory=$(cd $(dirname $0) && pwd)
properties_file=${base_directory}/src/main/resources/application.properties

echo "BASE DIRECTORY: ${base_directory}"

source ${properties_file}

echo "Downloads directory: ${DOWNLOADS_DIRECTORY}"

cd ${DOWNLOADS_DIRECTORY}
touch file.txt file.mp3 file.mp4 file.jpg file.png file.rtf file.wav file.mov

exit;

