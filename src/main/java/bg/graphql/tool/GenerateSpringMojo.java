package bg.graphql.tool;

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


/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * 
 * @phase process-sources
 */
@Mojo(name = "generateSpring", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GenerateSpringMojo extends AbstractMojo {

	@Parameter(property = "msg", defaultValue = "from maven")
	protected String msg = "";

	@Parameter(property = "pathGraphQL", defaultValue = "/schema/schema.graphqls")
	protected String pathGraphQL;

	@Parameter
	protected File dirGeneratedModel = new File("generated11");

	@Parameter
	protected File dirSrcGeneratedSpring = new File("generated33");

	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			trace();
				// GenerateModelRepositoryControllerFromGraphQL.processGenerationFullFromGraphQl(pathGraphQL,		dirGeneratedModel, dirSrcGeneratedSpring);
		} catch (Exception e) {
			throw new MojoExecutionException(msg, e);
		}
	}

	private void trace() {
		getLog().info( "GenerateGraphTool pathGraphQ :"+this.pathGraphQL);
		getLog().info( "GenerateGraphTool dirGeneratedModel :"+this.dirGeneratedModel);
		getLog().info( "GenerateGraphTool dirSrcGeneratedSpring :"+this.dirSrcGeneratedSpring);
		
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
