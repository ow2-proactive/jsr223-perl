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
package jsr223.perl.file.write;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;


public class PerlScriptFileWriter {

    public File forceFileToDisk(String fileContent, String filenameAndPath) throws IOException {
        File perlScriptFile = new File(filenameAndPath);
        // Create configuration file
        if (!perlScriptFile.createNewFile()) {
            perlScriptFile.delete();
            if (!perlScriptFile.createNewFile()) {
                throw new FileAlreadyExistsException("Perl script was deleted but still exists: " + filenameAndPath);
            }
        }

        // Force configuration file to disk
        Writer perlScriptFileWriter = new FileWriter(perlScriptFile);
        perlScriptFileWriter.write(fileContent);
        perlScriptFileWriter.close();

        return perlScriptFile;
    }
}