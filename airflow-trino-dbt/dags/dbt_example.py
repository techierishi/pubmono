from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.bash import BashOperator

schedule_interval = '0 * * * 1-5'

default_args = {
    'owner': 'The Owner',
    'email': ['theoperator@email.com'],
    'retry_interval': timedelta(minutes=15),
    'sla': timedelta(minutes=60),
    'depends_on_downstream': True,
    'email_on_failure': True,
    'email_on_retry': True,
    'provide_context': True,
}


with DAG('dbt', start_date=datetime(2016, 1, 1), schedule_interval=schedule_interval, default_args=default_args, max_active_runs=1) as dag:
    dbt_run = BashOperator(
        task_id='dbt_run',
        bash_command='cd /opt/airflow/dbt/demo && dbt run',
        dag=dag
    )

    dbt_run
