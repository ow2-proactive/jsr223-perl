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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import javax.script.ScriptEngine;

import org.junit.Test;


public class PerlScriptEngineFactoryTest {
    private PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory();

    @Test
    public void testGetLanguageVersion() {
        assertThat(perlScriptEngineFactory.getLanguageVersion(),
                   is(perlScriptEngineFactory.PARAMETERS.get(ScriptEngine.LANGUAGE_VERSION)));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsScriptEngine() {
        assertThat(perlScriptEngineFactory.getScriptEngine() instanceof PerlScriptEngine, is(true));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsParameterName() {
        assertThat(perlScriptEngineFactory.getParameter(ScriptEngine.NAME),
                   is(perlScriptEngineFactory.PARAMETERS.get(ScriptEngine.NAME)));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsParameterEngineVersion() {
        assertThat(perlScriptEngineFactory.getParameter(ScriptEngine.ENGINE_VERSION),
                   is(perlScriptEngineFactory.PARAMETERS.get(ScriptEngine.ENGINE_VERSION)));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsParameterLanguage() {
        assertThat(perlScriptEngineFactory.getParameter(ScriptEngine.LANGUAGE),
                   is(perlScriptEngineFactory.PARAMETERS.get(ScriptEngine.LANGUAGE)));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsParameterEngine() {
        assertThat(perlScriptEngineFactory.getParameter(ScriptEngine.ENGINE),
                   is(perlScriptEngineFactory.PARAMETERS.get(ScriptEngine.ENGINE)));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsNonNullLanguageName() {
        assertThat(perlScriptEngineFactory.getEngineName(),
                   is(perlScriptEngineFactory.PARAMETERS.get(ScriptEngine.ENGINE)));
    }

    @Test
    public void testThatPerlScriptEngineFactoryNamesContainsPerl() {
        assertThat(perlScriptEngineFactory.getNames(), hasItem(containsString("perl")));
    }

    @Test
    public void testThatPerlScriptEngineFactoryMimesTypesContainsPerlFile() {
        assertThat(perlScriptEngineFactory.getMimeTypes(), hasItem(containsString("pl")));
    }

    @Test
    public void testThatPerlScriptEngineFactoryExtensionContainsPerlFile() {
        assertThat(perlScriptEngineFactory.getExtensions(), hasItem(containsString("pl")));
    }

    @Test
    public void testThatPerlScriptEngineFactoryEngineVersionIsNonNull() {
        assertThat(perlScriptEngineFactory.getEngineVersion(),
                   is(perlScriptEngineFactory.PARAMETERS.get(ScriptEngine.ENGINE_VERSION)));
    }

    @Test
    public void testThatPerlScriptEngineFactoryLanguageIsNonNull() {
        assertThat(perlScriptEngineFactory.getLanguageName(),
                   is(perlScriptEngineFactory.PARAMETERS.get(ScriptEngine.LANGUAGE)));
    }
}
