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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;


public class PerlScriptFileWriterTest {

    private final String fileContent = "Test File content";

    private final String fileName = "forcedToDiskFile.txt";

    @Test
    public void testComposeFileIsWrittenToDisk() throws IOException {
        PerlScriptFileWriter perlScriptFileWriter = new PerlScriptFileWriter();

        File fileOnDisk = perlScriptFileWriter.forceFileToDisk(fileContent, fileName);
        fileOnDisk.deleteOnExit();

        assertThat(new File(fileOnDisk.getAbsolutePath()).exists(), is(true));
        assertThat(Files.readAllBytes(Paths.get(fileOnDisk.getAbsolutePath())), is(fileContent.getBytes()));
    }

    @Test
    public void testComposeFileIsOverwritten() throws IOException {
        PerlScriptFileWriter perlScriptFileWriter = new PerlScriptFileWriter();

        // Create file with same name -- must be overwritten
        new File(fileName).createNewFile();
        File fileOnDisk = perlScriptFileWriter.forceFileToDisk(fileContent, fileName);
        fileOnDisk.deleteOnExit();

        assertThat(new File(fileOnDisk.getAbsolutePath()).exists(), is(true));
        assertThat(Files.readAllBytes(Paths.get(fileOnDisk.getAbsolutePath())), is(fileContent.getBytes()));
    }
}
