#!/usr/bin/env bash
airflow db init

airflow users create --role Admin --username admin --password admin --email admin@example.com --firstname foo --lastname bar
airflow webserver
