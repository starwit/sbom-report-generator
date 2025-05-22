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

__Note__: Build file contains a code signing step. Make sure to change config to your own signing key. For more details see [plugin page](https://maven.apache.org/plugins/maven-gpg-plugin/usage.html)

You can reach API via:

    http://localhost:8080/swagger-ui/index.html

## How to install

Service so far supports running as Docker container and to be deployed to Kubernetes cluster. For Docker instructions see: https://hub.docker.com/r/starwitorg/sbom-generator

To install service to Kubernetes use Helm chart provided here: https://hub.docker.com/r/starwitorg/sbom-generator-chart


## Contact & Contribution

The “KI-Cockpit” (AI Cockpit) project was funded by the Federal Ministry of Labor and Social Affairs and executed by the nexus Institute, Aalen University, the University of Stuttgart, Chemistree, Caritas Dortmund & Starwit Technologies with the support of Awesome Technologies Innovationslabor, the Institute for Innovation and Technology (iit) at VDI/VDE Innovation + Technik and keytec.

<img src="doc/foerderlogo.png" alt="BMAS Logo" style="width:33%; height:auto;">

# License

Software in this repository is licensed under the AGPL-3.0 license. See [license agreement](LICENSE) for more details.
