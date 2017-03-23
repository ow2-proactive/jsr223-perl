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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Reader;
import java.io.Writer;

import org.junit.Assume;
import org.junit.Test;
import org.mockito.Matchers;

import processbuilder.PerlProcessBuilderFactory;
import processbuilder.utils.PerlProcessBuilderUtilities;


public class PerlVersionGetterTest {

    @Test
    public void getPerlVersionCallsProcessBuilderFactory() {
        PerlProcessBuilderFactory factory = mock(PerlProcessBuilderFactory.class);
        PerlProcessBuilderUtilities processBuilderUtilitiesMock = spy(PerlProcessBuilderUtilities.class);

        when(factory.getProcessBuilder(Matchers.<String[]> anyVararg())).thenReturn(new ProcessBuilder(""));

        PerlVersionGetter perlVersionGetter = new PerlVersionGetter(processBuilderUtilitiesMock, factory);
        perlVersionGetter.getPerlVersion();

        verify(factory).getProcessBuilder(Matchers.<String[]> anyVararg());
    }

    @Test
    public void getPerlVersionForCommandBuilder() {
        PerlVersionGetter perlVersionGetter = new PerlVersionGetter();
        String result = perlVersionGetter.getPerlVersion();

        assertThat(result, is(notNullValue()));
    }

    @Test
    public void getPerlVersionInvalidCommandReturnsDefaultString() throws Exception {
        PerlProcessBuilderFactory factory = mock(PerlProcessBuilderFactory.class);
        PerlProcessBuilderUtilities processBuilderUtilitiesMock = spy(PerlProcessBuilderUtilities.class);

        when(factory.getProcessBuilder(Matchers.<String[]> anyVararg())).thenReturn(new ProcessBuilder("...."));

        PerlVersionGetter perlVersionGetter = new PerlVersionGetter(processBuilderUtilitiesMock, factory);

        assertThat(perlVersionGetter.getPerlVersion(), is(PerlVersionGetter.PERL_VERSION_IF_NOT_INSTALLED));
    }

    @Test
    public void getPerlVersionCallsProcessBuilderUtilitiesWindows() throws Exception {
        Assume.assumeTrue(System.getProperty("os.name").toLowerCase().startsWith("win"));

        PerlProcessBuilderFactory factoryMock = mock(PerlProcessBuilderFactory.class);
        PerlProcessBuilderUtilities processBuilderUtilitiesMock = spy(PerlProcessBuilderUtilities.class);

        when(factoryMock.getProcessBuilder(Matchers.<String[]> anyVararg())).thenReturn(new ProcessBuilder("cmd",
                                                                                                           "/C",
                                                                                                           "dir"));

        PerlVersionGetter perlVersionGetter = new PerlVersionGetter(processBuilderUtilitiesMock, factoryMock);

        perlVersionGetter.getPerlVersion();

        verify(processBuilderUtilitiesMock).attachStreamsToProcess(any(Process.class),
                                                                   any(Writer.class),
                                                                   any(Writer.class),
                                                                   any(Reader.class));
    }

    @Test
    public void getPerlVersionCallsProcessBuilderUtilitiesLinux() throws Exception {
        Assume.assumeTrue(System.getProperty("os.name").toLowerCase().startsWith("lin"));

        PerlProcessBuilderFactory factoryMock = mock(PerlProcessBuilderFactory.class);
        PerlProcessBuilderUtilities processBuilderUtilitiesMock = spy(PerlProcessBuilderUtilities.class);

        when(factoryMock.getProcessBuilder(Matchers.<String[]> anyVararg())).thenReturn(new ProcessBuilder("ls"));

        PerlVersionGetter perlVersionGetter = new PerlVersionGetter(processBuilderUtilitiesMock, factoryMock);
        perlVersionGetter.getPerlVersion();

        verify(processBuilderUtilitiesMock).attachStreamsToProcess(any(Process.class),
                                                                   any(Writer.class),
                                                                   any(Writer.class),
                                                                   any(Reader.class));
    }

    @Test
    public void getPerlVersionNullFactoryReturnsDefaultErrorString() throws Exception {
        PerlVersionGetter perlVersionGetter = new PerlVersionGetter(new PerlProcessBuilderUtilities(), null);

        assertThat(perlVersionGetter.getPerlVersion(), is(PerlVersionGetter.PERL_VERSION_IF_NOT_INSTALLED));
    }
}
