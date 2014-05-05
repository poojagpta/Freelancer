#!/bin/bash

#Program to copy or sync the file from local machine to s3.
# Useage: copyFile inputPath s3storagePath
# There are 2 option avaliable. First copy files are local machine to 
# Amazon s3 storage. Second, it act as a sync from local machine to s3.

PROGNAME=$(basename $0)
CURRENT_PATH=`pwd`
TIMESTAMP=`date "+%Y%m%d%H%M"`
LOG_FILE=$CURRENT_PATH/run_s3.log.$TIMESTAMP
START=`date "+%Y-%m-%d %H:%M"`

echo $START >> $LOG_FILE

function usage {
cat << EOF 
usage: $PROGNAME options
This script run the test1 or test2 over a machine.

  OPTIONS:
   -h      Show this message 
   -o      Required.Provide option value 1 For copy files from local system 
				2 For sync folder from local to s3 
                                3 Confirm the files that will be deleted from s3
                                4 Delete the file from s3         
   -f      Required.Specify the local system file name
   -s      Required.Specify s3 path.
   -a      Specify s3 access key
   -k      Specify s3 secreat key


EOF
}

function error_exit {

	# Display error message and exit
	echo "${PROGNAME}: ${1:-"Unknown Error"}" >> $LOG_FILE 2>&1
	clean_up 1
}

function perform {
   if [ ! -n "$optionval" ]||[ ! -n "$localfile" ]||[ ! -n "$s3file" ]; then
     echo "Please check value of -o -f -s"
     exit 0
   fi
  
  case "$optionval" in
	    1 )  
	         s3cmd put -r --acl-public "$localfile"  "$s3file" >> $LOG_FILE 2>&1;;
	    2 ) 
	         s3cmd sync --acl-public  "$localfile"  "$s3file" >> $LOG_FILE 2>&1;;
	    3 ) 
		 s3cmd sync --dry-run --delete-removed "$localfile"  "$s3file";;
	    4 ) 	
 		  s3cmd sync  --delete-removed "$arg2"  "$arg3" >> $LOG_FILE 2>&1;;
			
	    * ) echo "You did not enter a number"
	        echo "between 1 and 4."
  esac
} 

while getopts "h:a:k:o:f:s:" OPTION
do
     case $OPTION in
         h)
             usage
             exit 1
             ;;
         o)
             optionval=$OPTARG
             ;;
         f)
             localfile=$OPTARG
             ;;
         s)
             s3file=$OPTARG
	     ;;
         ?)
             usage
             exit 1
             ;;
     esac
done

perform
