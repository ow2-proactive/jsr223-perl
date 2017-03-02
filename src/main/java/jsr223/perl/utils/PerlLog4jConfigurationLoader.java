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

public class PerlLog4jConfigurationLoader {

    private static final String LOG4J_CONFIGURATION_FILE = "config/log/scriptengines.properties";

    public void loadLog4jConfiguration() {
        // Catch all exceptions to not sacrifice functionality for logging.
        try {
            // configure the logger here, quick and dirty
            org.apache.log4j.PropertyConfigurator.configure(getClass().getClassLoader()
                                                                      .getResourceAsStream(LOG4J_CONFIGURATION_FILE));
        } catch (NullPointerException e) { //NOSONAR
            warnAboutMissingConfigurationFileOnErrorStream();
        } catch (Exception e) { //NOSONAR
            warnAboutNotFunctioningLoggingOnErrorStream(e);
        }

    }

    private void warnAboutNotFunctioningLoggingOnErrorStream(Exception e) {
        System.err.println("Log4j initialization failed: " + LOG4J_CONFIGURATION_FILE + //NOSONAR
                           ". Perl script engine is functional but logging is disabled." + //NOSONAR
                           "Stacktrace is: "); //NOSONAR
        e.printStackTrace(); //NOSONAR
    }

    private void warnAboutMissingConfigurationFileOnErrorStream() {
        System.err.println("Log4j configuration file not found: " + LOG4J_CONFIGURATION_FILE + //NOSONAR
                           ". Any output for the Perl script engine is disabled."); //NOSONAR
    }
}
