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

import java.util.Map;

import lombok.extern.log4j.Log4j;


@Log4j
public class PerlMapBindingsAdder {

    /**
     * @param environment Add strings to environment. Method returns immediately if null.
     * @param entry       Entry containing an object, the object type is checked and handled individually. Method
     *                    returns immediately if null.
     */
    public void addEntryToEnvironmentOtherThanPureStrings(Map<String, String> environment,
            Map.Entry<String, Object> entry) {
        if (environment == null || entry == null) {
            return;
        }

        if (containsKeyAndValue(entry) && valueIsMapType(entry)) {
            addEntryToEnvironmentWhichIsAMapContainingStrings(environment, entry);
        } else {
            log.warn("Ignored binding: " + entry.getKey() + "(" + getClassName(entry.getKey()) + "):" +
                     entry.getValue() + "(" + getClassName(entry.getValue()) + ")");

        }
    }

    private String getClassName(Object object) {
        return object != null ? object.getClass().getName() : null;
    }

    private boolean valueIsMapType(Map.Entry<String, Object> entry) {
        return entry.getValue() instanceof Map<?, ?>;
    }

    private boolean containsKeyAndValue(Map.Entry<String, Object> entry) {
        return entry.getKey() != null && entry.getValue() != null;
    }

    private void addEntryToEnvironmentWhichIsAMapContainingStrings(Map<String, String> environment,
            Map.Entry<String, Object> entry) {
        for (Map.Entry<?, ?> mapEntry : ((Map<?, ?>) entry.getValue()).entrySet()) {
            if (mapEntry.getValue() instanceof String && mapEntry.getKey() instanceof String) {
                environment.put((String) mapEntry.getKey(), (String) mapEntry.getValue());
                log.debug("Added binding: " + mapEntry.getKey() + ":" + mapEntry.getValue().toString());
            }
        }
    }
}
