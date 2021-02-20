package bg.graphql.tool.mojo;

import java.io.File;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import bg.graphql.tool.GenerateModelRepositoryControllerFromGraphQL;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * 
 * @phase process-sources
 */
@Mojo(name = "generateSpring", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GenerateSpringMojo extends AbstractMojo {

	@Parameter(property = "project")
	private MavenProject mavenProject;

	@Parameter(property = "msg", defaultValue = "from maven")
	protected String msg = "";

	@Parameter(property = "pathGraphQL", defaultValue = "/schema/schema.graphqls")
	protected String pathGraphQL;

	@Parameter(property = "dirSrcGeneratedModel", defaultValue = "generated111")
	protected String dirSrcGeneratedModelPath ;

	@Parameter(property = "dirSrcGeneratedSpring", defaultValue = "generated222")
	protected String dirSrcGeneratedSpringPath;

	private File dirTarget;
	private File dirGeneratedSources;
	private File dirTargetClasses;
	
	private File dirSrcGeneratedModel;
	
	private File dirSrcGeneratedSpring;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			init();
			
			GenerateModelRepositoryControllerFromGraphQL.processGenerationFullFromGraphQl(pathGraphQL,
					dirSrcGeneratedModel, dirSrcGeneratedSpring,dirTargetClasses);
		} catch (Exception e) {
			getLog().error("Mojo Error : ",e);
			throw new MojoExecutionException(msg, e);
		}
	}

	private void init() {
		this.dirTarget= new File(mavenProject.getBuild().getDirectory());
		this.dirGeneratedSources = new File(dirTarget,"generated-sources");
		this.dirTargetClasses = new File(dirTarget,"classes");
		this.dirSrcGeneratedModel= new File(dirGeneratedSources,this.dirSrcGeneratedModelPath);
		this.dirSrcGeneratedSpring= new File(dirGeneratedSources,this.dirSrcGeneratedSpringPath);
		trace();
	}

	private void trace() {
		getLog().info("-----------------------------------------------");
		getLog().info("MAvenProject baseDir :"+mavenProject.getBasedir().getPath());
		getLog().info("MAvenProject build Directory :"+mavenProject.getBuild().getDirectory());
		getLog().info("GenerateGraphTool pathGraphQ :" + this.pathGraphQL);
		getLog().info("GenerateGraphTool dirGeneratedModel :" + this.dirSrcGeneratedModel.getPath());
		getLog().info("GenerateGraphTool dirSrcGeneratedSpring :" + this.dirSrcGeneratedSpring.getPath());
		getLog().info("-----------------------------------------------");
		
	}

	protected void cleanDirectory(File dir) {
		try {
			getLog().info("Clean directory exists : " + dir.exists() + "  " + dir.getAbsolutePath());
			if (dir.exists()) {
				int i = cleanDirectoryRecursif(dir);
				getLog().info("            " + i + "  files deleted ");
			}
		} catch (Exception e) {
			getLog().error("deleting exception " + e.getMessage());
		}
	}

	private int cleanDirectoryRecursif(File dir) {

		int i = 0;
		if (dir == null) {
			return 0;
		}

		getLog().info("Clean Directory " + dir.getAbsolutePath());
		if (dir.exists()) {
			for (File child : dir.listFiles()) {
				if (child.isDirectory()) {
					i += cleanDirectoryRecursif(child);
				} else if (child.delete()) {
					i++;
				}
			}
			return i;
		} else {
			return 0;
		}
	}

}
