# Report sBOM Generator 

This components aims at generating PDF reports for software bill of materials based on CycloneDX standard. More info on this standard can be found [here](https://cyclonedx.org/specification/overview/). 

## How to run
As this is a Spring Boot app, compiling and running is simple:

```bash
    mvn clean package
    java -jar target/application-sbom-generator.jar 
```

You can reach API via:

    http://localhost:8080/swagger-ui/index.html