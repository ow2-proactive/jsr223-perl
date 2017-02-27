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
package processbuilder.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;


@NoArgsConstructor
@Log4j
public class ProcessBuilderUtilities {

    /**
     * Creates a thread which will constantly pipe data, only active when new data is available, from a source to an attached sink. After
     * reaching the end of the source stream the @Thread will silently be destroyed.
     *
     * @param source       Data source.
     * @param attachedSink Data sink.
     */
    private void attachToInputStream(final Reader source, final Writer attachedSink) {
        new Thread() {
            public void run() {
                try {
                    pipe(source, attachedSink);
                } catch (IOException e) {
                    log.error("Input stream pipe broke: " + e);
                }
            }
        }.start();
    }

    /**
     * Pipes all data from a reader (source) to a writer (sink) until an I/O execution occurs or the
     * end of the source is reached.
     *
     * @param from Source of data.
     * @param to   Sink of data.
     * @throws IOException
     */
    public static void pipe(Reader from, Writer to) throws IOException {
        char[] buff = new char[1024];
        int n = from.read(buff);
        while (n != -1) {
            to.write(buff, 0, n);
            to.flush();
            n = from.read(buff);
        }
        from.close();
    }

    /**
     * Attaches standard and error writer as well as input to a process.
     *
     * @param process       Process which to attach Output, Error and Input to.
     * @param processOutput A data sink for the process standard output. If null nothing will ne attached.
     * @param processError  A data sink for the process' error output. If null nothing will be attached.
     * @param processInput  A data source to be streamed to the process. If null nothing will be attached.
     */
    public void attachStreamsToProcess(Process process, Writer processOutput, Writer processError,
            Reader processInput) {
        if (processOutput != null) {
            // Attach to std output
            attachToInputStream(new InputStreamReader(process.getInputStream()), processOutput);
        }

        if (processError != null) {
            // Attach error output
            attachToInputStream(new InputStreamReader(process.getErrorStream()), processError);
        }

        if (processInput != null) {
            // Attach process input
            attachToInputStream(processInput, new OutputStreamWriter(process.getOutputStream()));
        }
    }
}
