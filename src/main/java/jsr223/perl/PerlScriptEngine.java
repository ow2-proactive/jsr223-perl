/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package jsr223.perl;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Map;

import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import jsr223.perl.bindings.PerlStringBindingsAdder;
import jsr223.perl.file.write.PerlScriptFileWriter;
import jsr223.perl.utils.PerlLog4jConfigurationLoader;
import lombok.extern.log4j.Log4j;
import processbuilder.PerlSingletonPerlProcessBuilderFactory;
import processbuilder.utils.PerlProcessBuilderUtilities;


@Log4j
public class PerlScriptEngine extends AbstractScriptEngine {

    public static final String EXIT_VALUE_BINDING_NAME = "EXIT_VALUE";

    public static final String VARIABLES_BINDING_NAME = "variables";

    private PerlProcessBuilderUtilities processBuilderUtilities = new PerlProcessBuilderUtilities();

    private PerlScriptFileWriter perlScriptFileWriter = new PerlScriptFileWriter();

    private PerlStringBindingsAdder perlStringBindingsAdder = new PerlStringBindingsAdder();

    private PerlCommandCreator perlCommandCreator = new PerlCommandCreator();

    private PerlLog4jConfigurationLoader perlLog4JConfigurationLoader = new PerlLog4jConfigurationLoader();

    public PerlScriptEngine() {
        // This is the entry-point of the script engine
        perlLog4JConfigurationLoader.loadLog4jConfiguration();
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {

        // Create perl command
        String[] perlCommand = perlCommandCreator.createPerlExecutionCommand();

        // Create a process builder
        ProcessBuilder processBuilder = PerlSingletonPerlProcessBuilderFactory.getInstance()
                                                                              .getProcessBuilder(perlCommand);

        // Use process builder environment and fill it with environment variables
        Map<String, String> variablesMap = processBuilder.environment();

        // Add string bindings as environment variables
        perlStringBindingsAdder.addBindingToStringMap(context.getBindings(ScriptContext.ENGINE_SCOPE), variablesMap);

        File perlFile = null;
        Process process = null;
        try {
            perlFile = perlScriptFileWriter.forceFileToDisk(script, perlCommandCreator.PERL_FILE_NAME);

            // Start process
            process = processBuilder.start();

            // Attach streams
            processBuilderUtilities.attachStreamsToProcess(process,
                                                           context.getWriter(),
                                                           context.getErrorWriter(),
                                                           context.getReader());

            // Wait for process to exit
            int exitValue = process.waitFor();
            if (context.getBindings(ScriptContext.ENGINE_SCOPE).containsKey(VARIABLES_BINDING_NAME)) {
                Map<String, Serializable> variables = (Map<String, Serializable>) context.getBindings(ScriptContext.ENGINE_SCOPE)
                                                                                         .get(VARIABLES_BINDING_NAME);
                variables.put(EXIT_VALUE_BINDING_NAME, exitValue);
            }
            context.getBindings(ScriptContext.ENGINE_SCOPE).put(EXIT_VALUE_BINDING_NAME, exitValue);
            if (exitValue != 0) {
                throw new ScriptException("Perl process execution has failed with exit code " + exitValue);
            }
            return exitValue;
        } catch (IOException e) {
            log.warn("Failed to execute Perl.", e);
        } catch (InterruptedException e) {
            log.info("Perl script execution interrupted. " + e.getMessage());
            if (process != null) {
                process.destroy();
            }
        } finally {
            if (process != null) {
                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    log.info("Perl execution was not finished correctly after the interruption. " + e.getMessage());
                }
            }
            // Delete configuration file
            if (perlFile != null) {
                boolean deleted = perlFile.delete();
                if (!deleted) {
                    log.warn("File: " + perlFile.getAbsolutePath() + " was not deleted.");
                }
            }
        }
        return null;
    }

    // TODO: Test
    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {

        StringWriter stringWriter = new StringWriter();

        try {
            PerlProcessBuilderUtilities.pipe(reader, stringWriter);
        } catch (IOException e) {
            log.warn("Filed to convert Reader into StringWriter. Not possible to execute Perl script.");
            log.debug("Filed to convert Reader into StringWriter. Not possible to execute Perl script.", e);
        }

        return eval(stringWriter.toString(), context);
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return new PerlScriptEngineFactory();
    }
}
