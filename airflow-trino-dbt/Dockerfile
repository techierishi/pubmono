FROM apache/airflow

RUN pip install wtforms && \
    pip install typing_extensions \
    pip install MarkupSafe \
    pip install dbt-core \
    pip install dbt-trino \
    pip install dbt-sqlite \
    pip install airflow-dbt \
    pip install apache-airflow-providers-trino

COPY scripts /opt/airflow/scripts/
COPY dags /opt/airflow/dags/
USER root
RUN chmod +x /opt/airflow/scripts/init.sh

USER airflow 
ENTRYPOINT [ "/opt/airflow/scripts/init.sh" ]