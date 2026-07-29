# Decrypt public cert


#### Usage

It's mainly for checking the public cert in k8s secrets. But it can take any base64 encoded public cert from stdin
Prerequisite: `yq` command

```bash
kubectl get secret <secret-nmae> -n <namespace> -o yaml | yq eval '.data["tls.crt"]' | deno run --allow-all https://deno.land/x/decryptpubcert@v1.0.0/index.js
```
