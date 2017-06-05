#!/bin/bash

set -e # Aborts script if anything goes wrong

# Parameters ($1: path to jar, $2: path to directory containing config. files,
#                                       $3: path to directory to save stdout output,
#                                       $4: path to directory where output files for Matlab/Octave must be stored)

JAR=${1};
DIRECTORY=${2};
STDOUT_OUTPUT=${3};
MATLAB_OUTPUT_DIRECTORY=${4};

CONFIG_FILES=$(ls ${DIRECTORY});

mkdir -p ${STDOUT_OUTPUT}; # Creates directory for storing output files.
for config in ${CONFIG_FILES};
do
	JUST_NAME=${config%%.*};
	
	CONFIG_FILE=${DIRECTORY}/${config};
	TXT_OUTPUT_DIR=${STDOUT_OUTPUT}/${JUST_NAME}.txt;
	MATLAB_OUTPUT_DIR=${MATLAB_OUTPUT_DIRECTORY}/${JUST_NAME}.m;
	
	echo 'Executing with' ${CONFIG_FILE} 'config file';
	echo 'Saving stdout output in' ${TXT_OUTPUT_DIR}
	echo 'Saving output file for Matlab/Octave in' ${MATLAB_OUTPUT_DIR};
	
	java -jar ${JAR} --config ${CONFIG_FILE} --octave-file ${MATLAB_OUTPUT_DIR} > ${TXT_OUTPUT_DIR};
done

