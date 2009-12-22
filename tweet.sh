#########################################################################
# Author: pengjianqing@gmail.com
# Created Time: Tue 22 Dec 2009 10:30:34 PM CST
# File Name: autotweet.sh
# Description:Welcome to visit:www.impjq.net for more information. 
#########################################################################
#!/bin/bash

cd /home/pjq/tmp
#echo ${1}
tmp=""
while [ $# -gt 0 ]
do
#    echo "$1"
    tmp="${tmp} ${1}"
    shift
done

#echo ${tmp}
java -jar /home/pjq/workspace/AutoTweet/autotweet.jar ${tmp}
