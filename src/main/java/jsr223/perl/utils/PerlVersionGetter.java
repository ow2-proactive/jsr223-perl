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
package jsr223.perl.utils;

import java.io.File;
import java.io.StringWriter;

import jsr223.perl.PerlCommandCreator;
import jsr223.perl.file.write.PerlScriptFileWriter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import processbuilder.PerlProcessBuilderFactory;
import processbuilder.PerlSingletonPerlProcessBuilderFactory;
import processbuilder.utils.PerlProcessBuilderUtilities;


@Log4j
@NoArgsConstructor
@AllArgsConstructor
public class PerlVersionGetter {

    public static final String PERL_VERSION_IF_NOT_INSTALLED = "Could not determine version";

    private static final String PERL_VERSION_COMMAND = "print $];"; // this command is needed to retrieve only specific string with perl version

    private PerlProcessBuilderUtilities processBuilderUtilities = new PerlProcessBuilderUtilities();;

    private PerlProcessBuilderFactory factory = PerlSingletonPerlProcessBuilderFactory.getInstance();

    /**
     * Retrieves the Perl version.
     *
     * @return The currently installed version return by the perl command or an string that
     * the version could not be determined.
     */
    public String getPerlVersion() {
        String result = PERL_VERSION_IF_NOT_INSTALLED; // Default error string for result if version recovery fails

        PerlCommandCreator perlCommandCreator = new PerlCommandCreator();
        PerlScriptFileWriter perlScriptFileWriter = new PerlScriptFileWriter();
        File perlFile = null;
        try {
            perlFile = perlScriptFileWriter.forceFileToDisk(PERL_VERSION_COMMAND);

            String[] perlCommand = perlCommandCreator.createPerlExecutionCommand(perlFile);

            ProcessBuilder processBuilder = factory.getProcessBuilder(perlCommand);
            Process process = processBuilder.start();

            // Attach stream to std output of process
            StringWriter commandOutput = new StringWriter();

            processBuilderUtilities.attachStreamsToProcess(process, commandOutput, null, null);

            // Wait for process to exit
            process.waitFor();

            // Extract output
            result = commandOutput.toString();
        } catch (Exception e) {
        }
        return result;
    }

}
