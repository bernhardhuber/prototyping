/*
 * Copyright 2023 berni3.
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

import java.io.Writer;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author berni3
 */
public class XmlStreamWriterEmitter implements IGenericXmlEmitter, AutoCloseable {

    XMLStreamWriter xsw;
    final Writer w;
    final XMLOutputFactory xof = XMLOutputFactory.newInstance();

    public XmlStreamWriterEmitter(Writer w) {
        this.w = w;
    }

    @Override
    public void startDocument() {

        try {
            this.xsw = xof.createXMLStreamWriter(this.w);
            this.xsw.writeStartDocument();
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void endDocument() {
        try {
            this.xsw.writeEndDocument();
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void write(String s) {
        try {
            xsw.writeCharacters(s);
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void writeStartElement(String elementname) {
        try {
            xsw.writeStartElement(elementname);
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void writeEndElement(String elementname) {
        try {
            xsw.writeEndElement();
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void writeInlineElement(String elementname, String value) {
        try {
            xsw.writeStartElement(elementname);
            xsw.writeCharacters(value);
            xsw.writeEndElement();
        } catch (XMLStreamException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return w.toString();
    }

    @Override
    public void close() throws Exception {
        if (this.w != null) {
            w.close();
        }
    }

}
