## Monitoring base

```bash
mkdir -p grafana/provisioning
docker-compose up -d
```

### For apply changes grafana.ini 

```bash
docker-compose restart grafana
```

> Import `jmx_dashboard_rev5.json` in grafana to see JMX dashboard