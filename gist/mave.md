### Settings disable ssl

```xml
    <profile>
      <id>insecure-ssl</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <properties>
        <maven.wagon.http.ssl.insecure>true</maven.wagon.http.ssl.insecure>
        <maven.wagon.http.ssl.allowall>true</maven.wagon.http.ssl.allowall>
      </properties>
    </profile>
```
