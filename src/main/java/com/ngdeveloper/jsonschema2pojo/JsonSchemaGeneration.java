package com.ngdeveloper.jsonschema2pojo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class JsonSchemaGeneration {

	public static void main(String[] args) throws IOException {
		JCodeModel codeModel = new JCodeModel();
		URL source = JsonSchemaGeneration.class.getResource("/schema/myntra-products.json");
		GenerationConfig config = new DefaultGenerationConfig() {
			@Override
			public boolean isGenerateBuilders() { // set config option by overriding method
				return true;
			}

			@Override
			public SourceType getSourceType() {
				return SourceType.JSON;
			}
		};

		SchemaMapper mapper = new SchemaMapper(
				new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
		mapper.generate(codeModel, "MyntraProductsResponse", "com.ngdeveloper.products", source);

		File file = Files.createTempDirectory("required").toFile();
		codeModel.build(file);
		System.out.println("path is " + file);
	}
}
