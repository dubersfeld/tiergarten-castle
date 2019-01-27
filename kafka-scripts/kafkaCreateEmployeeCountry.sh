#!/bin/bash

sudo /opt/Kafka/kafka_2.11-1.1.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 5 --partitions 4 --topic employeeCountry
