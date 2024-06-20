package de.starwit.sbom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.ApplicationScope;

import de.starwit.sbom.generator.DocumentDesignConfig;

@SpringBootApplication
public class SBomGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SBomGeneratorApplication.class, args);
	}

	@Bean()
	@ApplicationScope
	public List<DocumentDesignConfig> getDocumentDesignConfigs() {
		return new ArrayList<DocumentDesignConfig>();
	}

}
