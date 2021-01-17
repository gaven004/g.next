package org.hibernate.tool.maven;

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_SOURCES;

import java.io.File;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.hibernate.tool.api.export.Exporter;
import org.hibernate.tool.api.export.ExporterConstants;
import org.hibernate.tool.api.export.ExporterFactory;
import org.hibernate.tool.api.export.NextExporterType;
import org.hibernate.tool.api.metadata.MetadataDescriptor;

@Mojo(name = "hbm2repository", defaultPhase = GENERATE_SOURCES)
public class GenerateRepositoryMojo extends AbstractGenerationMojo {

    /** The directory into which the DAOs will be generated. */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/")
    private File outputDirectory;

    /** Code will contain EJB 3 features, e.g. using annotations from javax.persistence
     * and org.hibernate.annotations. */
    @Parameter(defaultValue = "false")
    private boolean ejb3;

    /** Code will contain JDK 5 constructs such as generics and static imports. */
    @Parameter(defaultValue = "false")
    private boolean jdk5;

    @Parameter
    private String entityPackageName;

    /** A path used for looking up user-edited templates. */
    @Parameter
    private String templatePath;

    protected void executeExporter(MetadataDescriptor metadataDescriptor) {
        Exporter pojoExporter = ExporterFactory.createExporter(NextExporterType.REPOSITORY.className());
        pojoExporter.getProperties().put(ExporterConstants.METADATA_DESCRIPTOR, metadataDescriptor);
        pojoExporter.getProperties().put(ExporterConstants.DESTINATION_FOLDER, outputDirectory);
        if (templatePath != null) {
            getLog().info("Setting template path to: " + templatePath);
            pojoExporter.getProperties().put(ExporterConstants.TEMPLATE_PATH, new String[] {templatePath});
        }
        pojoExporter.getProperties().setProperty("ejb3", String.valueOf(ejb3));
        pojoExporter.getProperties().setProperty("jdk5", String.valueOf(jdk5));
        if (entityPackageName != null && entityPackageName.trim().length() > 0) {
            pojoExporter.getProperties().setProperty("entityPackageName", entityPackageName.trim());
        }
        getLog().info("Starting Repository export to directory: " + outputDirectory + "...");
        pojoExporter.start();
    }

}
