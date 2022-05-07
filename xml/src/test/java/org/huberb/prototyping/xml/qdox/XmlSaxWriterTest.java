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

import java.io.IOException;
import java.io.StringWriter;
import javax.xml.stream.XMLStreamException;
import org.huberb.prototyping.xml.qdox.XmlSaxWriter.XmlModelSaxWriterFactory;
import org.huberb.prototyping.xml.qdox.XmlSaxWriter.XmlStreamWriterConsumer;
import org.huberb.prototyping.xml.qdox.XmlSaxWriter.XmlStreamWriterConsumerTemplates;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author berni3
 */
public class XmlSaxWriterTest {

    public XmlSaxWriterTest() {
    }

    /**
     * Test of accept method, of class XmlSaxWriter.
     */
    @Test
    public void testAccept() throws XMLStreamException, IOException {

        try (final StringWriter sw = new StringWriter()) {
            try (final XmlSaxWriter instance = new XmlModelSaxWriterFactory().create(sw)) {
                final XmlStreamWriterConsumer consumer = new XmlStreamWriterConsumerTemplates()
                        .build();
                instance.accept(consumer);
            }
            sw.flush();

            assertEquals("", sw.toString());
        }
    }

    /**
     * Test of accept method, of class XmlSaxWriter.
     */
    @Test
    public void testGiven_ElementNameEmpty1_then_xml_docuement_is_okay() throws XMLStreamException, IOException {

        try (final StringWriter sw = new StringWriter()) {
            try (final XmlSaxWriter instance = new XmlModelSaxWriterFactory().create(sw)) {
                final XmlStreamWriterConsumer consumer = new XmlStreamWriterConsumerTemplates()
                        .startDocument()
                        .emptyElement("elementNameEmpty1")
                        .endDocument()
                        .build();
                instance.accept(consumer);
            }
            sw.flush();

            assertEquals(""
                    + "<?xml version=\"1.0\" ?>"
                    + "<elementNameEmpty1/>", sw.toString());
        }
    }

    /**
     * Test of accept method, of class XmlSaxWriter.
     */
    @Test
    public void testGiven_LotsOfElementNameEmpty1_then_xml_docuement_is_okay() throws XMLStreamException, IOException {

        try (final StringWriter sw = new StringWriter()) {
            try (final XmlSaxWriter instance = new XmlModelSaxWriterFactory().create(sw)) {
                XmlStreamWriterConsumerTemplates templates = new XmlStreamWriterConsumerTemplates();
                templates.startDocument();
                for (int i = 0; i < 100; i += 1) {
                    templates.emptyElement("elementNameEmpty1");
                }
                templates.endDocument();
                XmlStreamWriterConsumer consumer = templates.build();
                instance.accept(consumer);
            }
            sw.flush();

            assertAll(() -> assertTrue(sw.toString().contains("elementNameEmpty1"))
            );
        }
    }

    /**
     * Test of accept method, of class XmlSaxWriter.
     */
    @Test
    public void testGiven_ElementName12_then_xml_docuement_is_okay() throws XMLStreamException, IOException {

        try (final StringWriter sw = new StringWriter()) {
            try (final XmlSaxWriter instance = new XmlModelSaxWriterFactory().create(sw)) {
                final XmlStreamWriterConsumer consumer = new XmlStreamWriterConsumerTemplates()
                        .startDocument()
                        .startElement("elementName1")
                        .nested(new XmlStreamWriterConsumerTemplates()
                                .emptyElement("elementName2")
                                .build()
                        )
                        .endDocument()
                        .build();
                instance.accept(consumer);
            }
            sw.flush();

            assertEquals(""
                    + "<?xml version=\"1.0\" ?>"
                    + "<elementName1>"
                    + "<elementName2/>"
                    + "</elementName1>", sw.toString());
        }
    }

    /**
     * Test of accept method, of class XmlSaxWriter.
     */
    @Test
    public void testGiven_ElementName1Text_then_xml_docuement_is_okay() throws XMLStreamException, IOException {

        try (final StringWriter sw = new StringWriter()) {
            try (final XmlSaxWriter instance = new XmlModelSaxWriterFactory().create(sw)) {
                final XmlStreamWriterConsumer consumer = new XmlStreamWriterConsumerTemplates()
                        .startDocument()
                        .startElement("elementName1")
                        .text("Text")
                        .endElement()
                        .endDocument()
                        .build();
                instance.accept(consumer);
            }
            sw.flush();

            assertEquals(""
                    + "<?xml version=\"1.0\" ?>"
                    + "<elementName1>"
                    + "Text"
                    + "</elementName1>", sw.toString());
        }
    }

    /**
     * Test of accept method, of class XmlSaxWriter.
     */
    @Test
    public void testGiven_ElementName1AttributesText_then_xml_docuement_is_okay() throws XMLStreamException, IOException {

        try (final StringWriter sw = new StringWriter()) {
            try (final XmlSaxWriter instance = new XmlModelSaxWriterFactory().create(sw)) {
                final XmlStreamWriterConsumer consumer = new XmlStreamWriterConsumerTemplates()
                        .startDocument()
                        .startElement("elementName1")
                        .attributes("attKey", "attValue")
                        .text("Text")
                        .endElement()
                        .endDocument()
                        .build();
                instance.accept(consumer);
            }
            sw.flush();

            assertEquals(""
                    + "<?xml version=\"1.0\" ?>"
                    + "<elementName1 attKey=\"attValue\">"
                    + "Text"
                    + "</elementName1>",
                    sw.toString());
        }
    }

}
