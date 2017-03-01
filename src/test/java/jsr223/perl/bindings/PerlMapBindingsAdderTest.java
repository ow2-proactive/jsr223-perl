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
package jsr223.perl.bindings;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;

import org.junit.Assert;
import org.junit.Test;


public class PerlMapBindingsAdderTest {

    @Test
    public void testAddEntryToEnvironmentOtherThanPureStringsDoesntThrowNullpointer() throws Exception {
        PerlMapBindingsAdder perlMapBindingsAdder = new PerlMapBindingsAdder();
        Map<String, String> environment = new HashMap<>();

        // Construct Map which contains null values
        Map<String, Object> nullValues = new HashMap<>();
        nullValues.put("Test", null);
        nullValues.put(null, null);

        // Iterate through the null values and pass them to the add method.
        for (Map.Entry<String, Object> entry : nullValues.entrySet()) {
            perlMapBindingsAdder.addEntryToEnvironmentOtherThanPureStrings(environment, entry);
        }

        // Nothing added so the size will be 0
        Assert.assertEquals(environment.size(), 0);
    }

    @Test
    public void testSkipsIfEnvironmentIsNull() throws Exception {
        PerlMapBindingsAdder perlMapBindingsAdder = new PerlMapBindingsAdder();

        Map<String, Object> variableMap = new HashMap<>();
        variableMap.put("name", null);
        variableMap.put("key", "value");
        variableMap.put("greetings", "Hello World");

        // Iterate through the null values and pass them to the add method.
        for (Map.Entry<String, Object> entry : variableMap.entrySet()) {
            perlMapBindingsAdder.addEntryToEnvironmentOtherThanPureStrings(null, entry);
        }
    }

    @Test
    public void testSkipsIfEntryIsNull() throws Exception {
        PerlMapBindingsAdder perlMapBindingsAdder = new PerlMapBindingsAdder();
        Map<String, String> environment = new HashMap<>();

        perlMapBindingsAdder.addEntryToEnvironmentOtherThanPureStrings(environment, null);

        // Nothing added so the size will be 0
        Assert.assertEquals(environment.size(), 0);
    }

    @Test
    public void testMapInsideMap() throws ScriptException, IOException {
        PerlMapBindingsAdder perlMapBindingsAdder = new PerlMapBindingsAdder();
        Map<String, String> environment = new HashMap<>();
        Map<String, String> variableMap = new HashMap<>();
        Map<String, Object> outsideMap = new HashMap<>();
        variableMap.put("name", null);
        variableMap.put("key", "value");
        variableMap.put("greetings", "Hello World");
        outsideMap.put("variables", variableMap);

        // Add map which contains map
        for (Map.Entry<String, Object> entry : outsideMap.entrySet()) {
            perlMapBindingsAdder.addEntryToEnvironmentOtherThanPureStrings(environment, entry);
        }

        assertThat(environment, hasEntry("key", "value"));
        assertThat(environment, hasEntry("greetings", "Hello World"));
    }

}
