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

import org.junit.Assert;
import org.junit.Test;

import jsr223.perl.utils.PerlPropertyLoader;


public class PerlCommandCreatorTest {

    private final PerlCommandCreator perlCommandCreator = new PerlCommandCreator();

    /**
     * Check whether the execution command has the right structure.
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Test
    public void testPerlExecutionCommand() throws NoSuchFieldException, IllegalAccessException {
        String[] command = perlCommandCreator.createPerlExecutionCommand();
        int index = 0;

        // Check if perl command are added correctly
        Assert.assertEquals("Perl command must be used as read from configuration.",
                            PerlPropertyLoader.getInstance().getPerlCommand(),
                            command[0]);

        // Check if correct filename is used
        Assert.assertEquals("Correct filename must be used in command.", PerlCommandCreator.PERL_FILE_NAME, command[1]);
    }
}
