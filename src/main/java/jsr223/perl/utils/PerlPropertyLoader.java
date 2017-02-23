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

import java.io.IOException;
import java.util.Properties;

import lombok.Getter;
import lombok.extern.log4j.Log4j;


@Log4j
public class PerlPropertyLoader {

    private final static String CONFIGURATION_FILE = "config/scriptengines/perl.properties";

    @Getter
    private final String perlCommand;

    @Getter
    private final String sudoCommand;

    @Getter
    private final boolean useSudo;

    private final Properties properties;

    private PerlPropertyLoader() {
        properties = new Properties();
        try {
            log.debug("Load properties from configuration file: " + CONFIGURATION_FILE);
            properties.load(getClass().getClassLoader().getResourceAsStream(CONFIGURATION_FILE));
        } catch (IOException | NullPointerException e) {
            log.info("Configuration file " + CONFIGURATION_FILE + " not found. Standard values will be used.");
            log.debug("Configuration file " + CONFIGURATION_FILE + " not found. Standard values will be used.", e);
        }

        // Get property, specify default value
        this.perlCommand = properties.getProperty("perl.command", "/usr/bin/perl");
        // Get property, specify default value
        this.sudoCommand = properties.getProperty("perl.sudo.command", "/usr/bin/sudo");
        // Get property, specify default value
        this.useSudo = Boolean.parseBoolean(properties.getProperty("perl.use.sudo", "false"));
    }

    public static PerlPropertyLoader getInstance() {
        return PerlPropertyLoaderHolder.INSTANCE;
    }

    /**
     * Initializes PerlPropertyLoader.
     * <p>
     * PerlPropertyLoaderHolder is loaded on the first execution of PerlPropertyLoader.getInstance()
     * or the first access to PerlPropertyLoaderHolder.INSTANCE, not before.
     **/
    private static class PerlPropertyLoaderHolder {
        private static final PerlPropertyLoader INSTANCE = new PerlPropertyLoader();

        private PerlPropertyLoaderHolder() {
        }
    }
}
