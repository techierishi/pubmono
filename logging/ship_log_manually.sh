 #!/bin/bash
  1
  2 OPENSEARCH_HOST="https://localhost"
  3 OPENSEARCH_PORT="9200"
  4 INDEX_NAME="fb_index_1"
  5 TYPE="_doc"
  6 LOG_ENTRY='{"timestamp": "'$(date -u +"%Y-%m-%dT%H:%M:%SZ")'", "message": "Your log message here"}'^M
  7 curl -XPOST "${OPENSEARCH_HOST}:${OPENSEARCH_PORT}/${INDEX_NAME}/${TYPE}" -k -H 'Content-Type: application/json' -u admin:admin -d "${LOG_ENTRY}"
~