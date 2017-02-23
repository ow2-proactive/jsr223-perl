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
package jsr223.perl.variable;

import java.util.Map;


public class VariablesReplacer {

    /**
     * Replaces all varialbes with look like $[VariableName]. With matching variables in the
     * provided map.
     * @param script String to replace all occurrences in.
     * @param variables Map which contains variables names mapped to values. Anything which has a key or
     *                  value null is not replaced.
     * @return Returns a copy of script where all occurrences are replaced. If any parameter is null,
     * then script is returned.
     */
    public String replaceVariables(String script, Map<String, String> variables) {
        if (script == null || variables == null) {
            return script;
        }

        String result = script;
        // Replace all variables one by one
        for (Map.Entry<String, String> variable : variables.entrySet()) {
            if (variable.getValue() != null && variable.getKey() != null) {
                result = result.replace("$" + variable.getKey(), variable.getValue());
            }
        }
        return result;
    }
}
