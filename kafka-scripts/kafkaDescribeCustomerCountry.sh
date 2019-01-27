#!/bin/bash

sudo /opt/Kafka/kafka_2.11-1.1.0/bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic customerCountry
