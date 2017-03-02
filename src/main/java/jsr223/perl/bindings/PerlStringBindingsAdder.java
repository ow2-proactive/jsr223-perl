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

import java.util.Collection;
import java.util.Map;

import javax.script.Bindings;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;


@Log4j
@NoArgsConstructor
public class PerlStringBindingsAdder {

    /**
     * Adds all bindings which are from type @String to the environment map. All other bindings are printed
     * with toString() to log file.
     *
     * @param bindings    Bindings which will be read and added to environment. If null,
     *                    this method will return immediately.
     * @param environment Map<String,String> which will get all Entry<String,String> from the @Bindings. If null,
     *                    this method will return immediately.
     */
    public void addBindingToStringMap(Bindings bindings, Map<String, String> environment) {
        if (bindings == null || environment == null) {
            return;
        }

        for (Map.Entry<String, Object> binding : bindings.entrySet()) {
            String bindingKey = binding.getKey();
            Object bindingValue = binding.getValue();

            if (bindingValue instanceof Object[]) {
                addArrayBindingAsEnvironmentVariable(bindingKey, (Object[]) bindingValue, environment);
            } else if (bindingValue instanceof Collection) {
                addCollectionBindingAsEnvironmentVariable(bindingKey, (Collection) bindingValue, environment);
            } else if (bindingValue instanceof Map) {
                addMapBindingAsEnvironmentVariable(bindingKey, (Map<?, ?>) bindingValue, environment);
            } else {
                environment.put(bindingKey, toEmptyStringIfNull(binding.getValue()));
            }
        }
    }

    private void addMapBindingAsEnvironmentVariable(String bindingKey, Map<?, ?> bindingValue,
            Map<String, String> environment) {
        for (Map.Entry<?, ?> entry : ((Map<?, ?>) bindingValue).entrySet()) {
            environment.put(bindingKey + "_" + entry.getKey(),
                            (entry.getValue() == null ? "" : toEmptyStringIfNull(entry.getValue())));
        }
    }

    private void addCollectionBindingAsEnvironmentVariable(String bindingKey, Collection bindingValue,
            Map<String, String> environment) {
        Object[] bindingValueAsArray = bindingValue.toArray();
        addArrayBindingAsEnvironmentVariable(bindingKey, bindingValueAsArray, environment);
    }

    private void addArrayBindingAsEnvironmentVariable(String bindingKey, Object[] bindingValue,
            Map<String, String> environment) {
        for (int i = 0; i < bindingValue.length; i++) {
            environment.put(bindingKey + "_" + i,
                            (bindingValue[i] == null ? "" : toEmptyStringIfNull(bindingValue[i].toString())));
        }
    }

    private String toEmptyStringIfNull(Object value) {
        return value == null ? "" : value.toString();
    }
}
