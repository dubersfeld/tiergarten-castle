#!/bin/bash

echo restarting a broker...

# edit to match your own Kafka installation folder
a=/opt/Kafka/kafka_2.11-1.1.0/config/$1

echo $a 

# edit to match your own Kafka installation folder
/opt/Kafka/kafka_2.11-1.1.0/bin/kafka-server-start.sh $a 

disown
