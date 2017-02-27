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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.junit.Test;


public class StringBindingsAdderTest {

    @Test
    public void testStringStringMapsAsVariables() throws ScriptException, IOException {
        Bindings bindings = new SimpleBindings();
        Map<String, String> variables = new HashMap<>();
        variables.put("name", "EchoUbuntu");
        variables.put("key", "value");
        variables.put("greetings", "Hello World");
        bindings.put("variables", variables);

    }

    @Test
    public void testAddStringVariables() throws ScriptException, IOException {
        StringBindingsAdder stringBindingsAdder = new StringBindingsAdder(new MapBindingsAdder());

        Bindings bindings = new SimpleBindings();
        Map<String, String> variables = new HashMap<>();
        bindings.put("name", "EchoUbuntu");
        bindings.put("key", "value");
        bindings.put("greetings", "Hello World");

        stringBindingsAdder.addBindingToStringMap(bindings, variables);

        for (Map.Entry<String, String> expectedEntry : variables.entrySet()) {
            assertThat(variables, hasEntry(expectedEntry.getKey(), expectedEntry.getValue()));
        }
    }

    @Test(expected = NullPointerException.class)
    public void testKeyIsNull() throws ScriptException, IOException {
        StringBindingsAdder stringBindingsAdder = new StringBindingsAdder(new MapBindingsAdder());

        Bindings bindings = new SimpleBindings();
        Map<String, String> variables = new HashMap<>();
        bindings.put("name", "EchoUbuntu");
        bindings.put(null, "value");

        stringBindingsAdder.addBindingToStringMap(bindings, variables);
    }

    @Test
    public void testValueIsNull() throws ScriptException, IOException {
        StringBindingsAdder stringBindingsAdder = new StringBindingsAdder(new MapBindingsAdder());

        Bindings bindings = new SimpleBindings();
        Map<String, String> variables = new HashMap<>();
        bindings.put("name", "EchoUbuntu");
        bindings.put("key", null);

        stringBindingsAdder.addBindingToStringMap(bindings, variables);

        assertThat(variables, hasEntry("name", "EchoUbuntu"));
        assertThat(variables.containsKey("value"), is(false));
    }

    @Test
    public void testBindingsIsNull() throws ScriptException, IOException {
        StringBindingsAdder stringBindingsAdder = new StringBindingsAdder(new MapBindingsAdder());

        Map<String, String> variables = new HashMap<>();

        stringBindingsAdder.addBindingToStringMap(null, variables);

        assertThat(variables.size(), is(0));
    }

    @Test
    public void testVariablesIsNull() throws ScriptException, IOException {
        StringBindingsAdder stringBindingsAdder = new StringBindingsAdder(new MapBindingsAdder());

        Bindings bindings = new SimpleBindings();
        bindings.put("name", "EchoUbuntu");
        bindings.put("key", "value");

        stringBindingsAdder.addBindingToStringMap(bindings, null);

        assertThat(bindings.size(), is(2));
    }

    @Test
    public void testVariablesAndBindingsIsNull() throws ScriptException, IOException {
        StringBindingsAdder stringBindingsAdder = new StringBindingsAdder(new MapBindingsAdder());

        stringBindingsAdder.addBindingToStringMap(null, null);
    }

    @Test
    public void testUseObjectBindingsAdderIFObjectIsPassed() throws ScriptException, IOException {
        MapBindingsAdder mapBindingsAdderMock = mock(MapBindingsAdder.class);
        doNothing().when(mapBindingsAdderMock).addEntryToEnvironmentOtherThanPureStrings(any(Map.class),
                                                                                         any(Map.Entry.class));
        StringBindingsAdder stringBindingsAdder = new StringBindingsAdder(mapBindingsAdderMock);
        // Create bindings and variables
        Map<String, String> variables = new HashMap<>();
        Bindings bindings = new SimpleBindings();

        // Add objects to bindings
        bindings.put("int", new Integer(1));
        bindings.put("float", new Float(1.56));
        bindings.put("object", new Object());
        // Add pure strings, on those the object adder should not be called
        bindings.put("name", "EchoUbuntu");
        bindings.put("key", "value");

        stringBindingsAdder.addBindingToStringMap(bindings, variables);

        // Verify that the object adder was executed three times, for each of the not string objects.
        verify(mapBindingsAdderMock, times(3)).addEntryToEnvironmentOtherThanPureStrings(any(Map.class),
                                                                                         any(Map.Entry.class));
    }

}
