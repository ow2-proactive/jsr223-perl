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
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.script.ScriptEngine;

import org.junit.Test;

import jsr223.perl.utils.PerlVersionGetter;
import processbuilder.ProcessBuilderFactory;
import processbuilder.utils.ProcessBuilderUtilities;


public class PerlScriptEngineFactoryTest {

    @Test(expected = NullPointerException.class)
    public void testThatVersionGetterCannotBeNull() {
        new PerlScriptEngineFactory(new ProcessBuilderUtilities(), null);
    }

    @Test(expected = NullPointerException.class)
    public void testThatProcessBuilderCannotBeNull() {
        new PerlScriptEngineFactory(null, new PerlVersionGetter(new ProcessBuilderUtilities()));
    }

    @Test(expected = NullPointerException.class)
    public void testThatVersionGetterAndProcessBuilderCannotBeNull() {
        new PerlScriptEngineFactory(null, null);
    }

    @Test
    public void testThatPerlVersionGetterIsUsed() {
        PerlVersionGetter perlVersionGetterMock = mock(PerlVersionGetter.class);
        String testVersion = "someVersion 44";
        when(perlVersionGetterMock.getPerlVersion(any(ProcessBuilderFactory.class))).thenReturn(testVersion);

        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      perlVersionGetterMock);

        assertThat(perlScriptEngineFactory.getLanguageVersion(), is(testVersion));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsScriptEngine() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getScriptEngine() instanceof PerlScriptEngine, is(true));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsNonNullParameterName() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getParameter(ScriptEngine.NAME), is(notNullValue()));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsNonNullParameterEngineVersion() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getParameter(ScriptEngine.ENGINE_VERSION), is(notNullValue()));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsNonNullParameterLanguage() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getParameter(ScriptEngine.LANGUAGE), is(notNullValue()));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsNonNullParameterEngine() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getParameter(ScriptEngine.ENGINE), is(notNullValue()));
    }

    @Test
    public void testThatPerlScriptEngineFactoryReturnsNonNullLanguageName() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getEngineName(), is(notNullValue()));
    }

    @Test
    public void testThatPerlScriptEngineFactoryNamesContainsPerl() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getNames(), hasItem(containsString("perl")));
    }

    @Test
    public void testThatPerlScriptEngineFactoryMimesTypesContainsPerlFile() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getMimeTypes(), hasItem(containsString("pl")));
    }

    @Test
    public void testThatPerlScriptEngineFactoryExtensionContainsPerlFile() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getExtensions(), hasItem(containsString("pl")));
    }

    @Test
    public void testThatPerlScriptEngineFactoryEngineVersionIsNonNull() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getEngineVersion(), is(notNullValue()));
    }

    @Test
    public void testThatPerlScriptEngineFactoryLanguageIsNonNull() {
        PerlScriptEngineFactory perlScriptEngineFactory = new PerlScriptEngineFactory(new ProcessBuilderUtilities(),
                                                                                      new PerlVersionGetter(new ProcessBuilderUtilities()));

        assertThat(perlScriptEngineFactory.getLanguageName(), is(notNullValue()));
    }
}
