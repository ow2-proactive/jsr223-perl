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

import java.util.ArrayList;
import java.util.List;

import jsr223.perl.utils.PerlPropertyLoader;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class PerlCommandCreator {

    // Constants
    public static final String PERL_FILE_NAME = "file.pl";

    /**
     * This method creates a bash command which executes perl with a given perl file.
     *
     * @return A String array which contains the command as a separate @String and each
     * argument as a separate String.
     */
    public String[] createPerlExecutionCommand() {
        List<String> command = new ArrayList<>();

        // Add perl command
        command.add(PerlPropertyLoader.getInstance().getPerlCommand());

        // Add filename
        command.add(PERL_FILE_NAME);

        return command.toArray(new String[command.size()]);
    }
}
