#!/bin/sh

ITER=0

for i in $(echo $DEPLOYMENT_GROUP_NAME | tr "," "\n")
do
    if [ $ITER = 0 ]
    then
        FIRST=$i
    fi

    if [ $ITER = 1 ]
    then
        SECOND=$i
    fi

    ITER=$(expr $ITER + 1)
done

echo $FIRST
echo $SECOND

mkdir -p /home/ec2-user/overscore_real/$FIRST
mkdir -p /home/ec2-user/overscore_real/$FIRST/lib

yes | cp -R /home/ec2-user/overscore_deploy/lib/* /home/ec2-user/overscore_real/$FIRST/lib/
yes | cp -R /home/ec2-user/overscore_deploy/$FIRST.jar /home/ec2-user/overscore_real/$FIRST/

if [ $SECOND = "Daemon" ]
then
	for process in $(ps -ef | egrep 'java.*$FIRST' | egrep -v 'egrep' | awk '{print $2}'); do kill -9 $process; done;
	
	cd /home/ec2-user/overscore_real/$FIRST
	nohup java -Denvironment=real -jar $FIRST.jar > /dev/null &
fi