# Report sBOM Generator 

This components aims at generating reports in various for software bill of materials based on CycloneDX standard. More info on this standard can be found [here](https://cyclonedx.org/specification/overview/). 

## What does it do
Service offers an API to which other services or users can send sBOM documents (CycloneDX) and get back generated PDF/spread sheet files.

## How to run locally
As this is a Spring Boot app, compiling and running is simple:

```bash
    mvn clean package
    java -jar target/application-sbom-generator.jar 
```

You can reach API via:

    http://localhost:8080/swagger-ui/index.html

## How to install

Service so far supports running as Docker container and to be deployed to Kubernetes cluster. For Docker instructions see: https://hub.docker.com/r/starwitorg/sbom-generator

To install service to Kubernetes use Helm chart provided here: https://hub.docker.com/r/starwitorg/sbom-generator-chart


## Contact & Contribution
This project was partly funded by the government of the federal republic of Germany. It is part of a research project aiming to keep _humans in command_ and is organized by the Federal Ministry of Labour and Social Affairs.

![BMAS](doc/BMAS_Logo.svg)

# License

Software in this repository is licensed under the AGPL-3.0 license. See [license agreement](LICENSE) for more details.