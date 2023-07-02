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

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Objects;

/**
 * @author berni3
 */
public class XmlSaxWriter implements AutoCloseable {

    private final XMLStreamWriter xsw;

    XmlSaxWriter(XMLStreamWriter xsw) {
        this.xsw = xsw;
        String defaultNamespace = "urn:xxx";
    }

    @FunctionalInterface
    public static interface XmlStreamWriterConsumer {

        /**
         * Performs this operation on the given argument.
         *
         * @param t the input argument
         */
        public void accept(XMLStreamWriter xsw) throws XMLStreamException;

        /**
         * Returns a composed {@code Consumer} that performs, in sequence, this
         * operation followed by the {@code after} operation. If performing
         * either operation throws an exception, it is relayed to the caller of
         * the composed operation. If performing this operation throws an
         * exception, the {@code after} operation will not be performed.
         *
         * @param after the operation to perform after this operation
         * @return a composed {@code Consumer} that performs in sequence this
         * operation followed by the {@code after} operation
         * @throws NullPointerException if {@code after} is null
         */
        default XmlStreamWriterConsumer andThen(XmlStreamWriterConsumer after) {
            Objects.requireNonNull(after);
            return (XMLStreamWriter t) -> {
                accept(t);
                after.accept(t);
            };
        }

    }

    void accept(XmlStreamWriterConsumer consumer) throws XMLStreamException {
        consumer.accept(xsw);
    }

    static class XmlStreamWriterConsumerTemplates {

        private XmlStreamWriterConsumer current;

        XmlStreamWriterConsumerTemplates() {
            this((xsw) -> {
            });
        }

        XmlStreamWriterConsumerTemplates(XmlStreamWriterConsumer current) {
            this.current = current;
        }

        XmlStreamWriterConsumerTemplates startDocument() {
            final XmlStreamWriterConsumer xswc = current
                    .andThen((xsw) -> xsw.writeStartDocument());
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumerTemplates endDocument() {
            final XmlStreamWriterConsumer xswc = current
                    .andThen((xsw) -> xsw.writeEndDocument());
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumerTemplates comment(String comment) {
            final XmlStreamWriterConsumer xswc = current
                    .andThen((xsw) -> xsw.writeComment(comment));
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumerTemplates text(String characters) {
            final XmlStreamWriterConsumer xswc = current
                    .andThen((xsw) -> xsw.writeCharacters(characters));
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumerTemplates emptyElement(String elementname) {
            XmlStreamWriterConsumer xswc = current
                    .andThen((xsw) -> xsw.writeEmptyElement(elementname));
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumerTemplates startElement(String elementname) {
            XmlStreamWriterConsumer xswc = this.current
                    .andThen((xsw) -> xsw.writeStartElement(elementname));
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumerTemplates endElement() {
            XmlStreamWriterConsumer xswc = this.current
                    .andThen((xsw) -> xsw.writeEndElement());
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumerTemplates attributes(String k, String v) {
            final XmlStreamWriterConsumer xswcAttributes = (xsw) -> {
                xsw.writeAttribute(k, v);
            };
            final XmlStreamWriterConsumer xswc = current
                    .andThen(xswcAttributes);
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumerTemplates attributes(Map<String, String> atts) {
            final XmlStreamWriterConsumer xswcAttributes = (xsw) -> {
                for (Map.Entry<String, String> e : atts.entrySet()) {
                    xsw.writeAttribute(e.getKey(), e.getValue());
                }
            };
            final XmlStreamWriterConsumer xswc = current
                    .andThen(xswcAttributes);
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumerTemplates nested(XmlStreamWriterConsumer inner) {
            final XmlStreamWriterConsumer xswc = current
                    .andThen(inner)
                    .andThen((_xsw) -> _xsw.writeEndElement());
            this.current = xswc;
            return this;
        }

        XmlStreamWriterConsumer build() {
            return this.current;
        }
    }

    @Override
    public void close() throws XMLStreamException {
        this.xsw.close();

    }

    static class XmlModelSaxWriterFactory {

        XmlSaxWriter create(Writer w) throws XMLStreamException {
            final XMLOutputFactory xof = XMLOutputFactory.newInstance();
            final XMLStreamWriter xsw = xof.createXMLStreamWriter(w);
            return new XmlSaxWriter(xsw);
        }
    }

}
