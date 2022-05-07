/*
 * Copyright 2022 berni3.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.huberb.prototyping.xml.qdox;

/**
 *
 * @author berni3
 */
public class XmlIndentBuffer {

    private String eol = "\n";
    private String indentation = "\t";
    private StringBuffer buffer = new StringBuffer();

    private int depth = 0;

    private boolean newLine;

    public void setEol(String eol) {
        this.eol = eol;
    }

    public void setIndentation(String indentation) {
        this.indentation = indentation;
    }

    public void write(String s) {
        checkNewLine();
        buffer.append(s);
    }

    public void write(char s) {
        checkNewLine();
        buffer.append(s);
    }

    public void newline() {
        buffer.append(eol);
        newLine = true;
    }

    public void indent() {
        depth++;
    }

    public void deindent() {
        depth--;
    }

    @Override
    public String toString() {
        return buffer.toString();
    }

    private void checkNewLine() {
        if (newLine) {
            for (int i = 0; i < depth; i++) {
                buffer.append(indentation);
            }
            newLine = false;
        }
    }

    void writeStartElement(String elementname) {
        this.write("<" + elementname + ">");
        this.newline();
        this.indent();
    }

    void writeEndElement(String elementname) {
        this.write("</" + elementname + ">");
        this.newline();
        this.deindent();
    }
}
