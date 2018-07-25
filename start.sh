#!/bin/sh

if [ ! -f jenkins_compose/compose.yml ]; then
    echo "Run $0 from the sunet-jenkins-developer top level directory"
    exit 1
fi

#
# Set up entrys in /etc/hosts for the containers with externally accessible services
#
(printf "172.16.12.100\tjenkins.dev jenkins.jenkins.docker\n";
) \
    | while read line; do
    if ! grep -q "^${line}$" /etc/hosts; then
	echo "$0: Adding line '${line}' to /etc/hosts"
	if [ "x`whoami`" = "xroot" ]; then
	    echo "${line}" >> /etc/hosts
	else
	    echo "${line}" | sudo tee -a /etc/hosts
	fi
    else
	echo "Line '${line}' already in /etc/hosts"
    fi
done

./bin/docker-compose -f jenkins_compose/compose.yml rm -s -f
./bin/docker-compose -f jenkins_compose/compose.yml up $*
./bin/docker-compose -f jenkins_compose/compose.yml logs -tf
