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

import com.thoughtworks.qdox.model.*;
import com.thoughtworks.qdox.model.JavaModuleDescriptor.JavaExports;
import com.thoughtworks.qdox.model.JavaModuleDescriptor.JavaOpens;
import com.thoughtworks.qdox.model.JavaModuleDescriptor.JavaProvides;
import com.thoughtworks.qdox.model.JavaModuleDescriptor.JavaRequires;
import com.thoughtworks.qdox.model.JavaModuleDescriptor.JavaUses;
import com.thoughtworks.qdox.model.expression.AnnotationValue;
import com.thoughtworks.qdox.writer.ModelWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import org.huberb.prototyping.xml.qdox.XmlSaxWriter.XmlModelSaxWriterFactory;
import org.huberb.prototyping.xml.qdox.XmlSaxWriter.XmlStreamWriterConsumer;
import org.huberb.prototyping.xml.qdox.XmlSaxWriter.XmlStreamWriterConsumerTemplates;

/**
 * Implementation of an {@link ModelWriter} emitting XML using
 * {@link XmlSaxWriter}.
 *
 * @author berni3
 */
public class XmlSaxModelWriter implements ModelWriter {

    private static final Logger LOG = Logger.getLogger(XmlSaxModelWriter.class.getName());
    private final XmlStreamWriterConsumerTemplates xswct;

    public XmlSaxModelWriter() throws XMLStreamException {
        this.xswct = new XmlStreamWriterConsumerTemplates();
    }

    public String emitXml() {
        try (final StringWriter sw = new StringWriter(); final XmlSaxWriter xsw = XmlModelSaxWriterFactory.create(sw)) {
            final XmlStreamWriterConsumer consumer = xswct.build();
            xsw.accept(consumer);
            sw.flush();
            return sw.toString();
        } catch (XMLStreamException | IOException ex) {
            LOG.log(Level.WARNING, "Cannot create XML from JavaSource", ex);
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeSource(JavaSource source) {

        this.xswct.startDocument();

        xswct.startElement("source");
        xswct.attributes("url", source.getURL().toString());

        // package statement
        writePackage(source.getPackage());

        // import statement
        xswct.startElement("imports");
        source.getImports().stream().
                forEach(s -> this.xswct.emptyElement("import").attributes("name", s)
                );
        xswct.endElement();

        // classes
        source.getClasses().stream()
                .forEach(jc -> writeClass(jc)
                );
        this.xswct.endElement();

        this.xswct.endDocument();

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writePackage(JavaPackage pckg) {

        if (pckg != null) {
            commentHeader(pckg);
            this.xswct.emptyElement("package")
                    .attributes("name", pckg.getName());
        }

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeClass(JavaClass cls) {
        this.xswct.startElement("class");

        commentHeader(cls);
        writeAllModifiers(cls.getModifiers());

        final String typeOf = cls.isEnum() ? "enum"
                : cls.isInterface() ? "interface"
                : cls.isAnnotation() ? "@interface"
                : "class";
        this.xswct.emptyElement("typeOf")
                .attributes("name", typeOf);

        // subclass
        if (cls.getSuperClass() != null) {
            String className = cls.getSuperClass().getFullyQualifiedName();
            if (!"java.lang.Object".equals(className)
                    && !"java.lang.Enum".equals(className)) {
                this.xswct.emptyElement("extends")
                        .attributes("name", cls.getSuperClass().getGenericCanonicalName());
            }
        }
        // implements
        if (cls.getImplements().size() > 0) {
            String extendsOrImplements = (cls.isInterface() ? "extends" : "implements");

            cls.getImplements().stream()
                    .forEach(jt -> this.xswct.emptyElement(extendsOrImplements)
                    .attributes("name", jt.getGenericCanonicalName())
                    );

        }
        writeClassBody(cls);
        this.xswct.endElement();
        return this;
    }

    private ModelWriter writeClassBody(JavaClass cls) {
        this.xswct.startElement("classbody");

        // fields
        this.xswct.startElement("fields");
        cls.getFields().stream().forEach(javaField -> writeField(javaField));
        this.xswct.endElement();

        // constructors
        this.xswct.startElement("constructors");
        cls.getConstructors().stream().forEach(javaConstructor -> writeConstructor(javaConstructor));
        this.xswct.endElement();

        // methods
        this.xswct.startElement("methods");
        cls.getMethods().stream().forEach(javaMethod -> writeMethod(javaMethod));
        this.xswct.endElement();

        // inner-classes
        this.xswct.startElement("inner-classes");
        cls.getNestedClasses().stream().forEach(innerCls -> writeClass(innerCls));
        this.xswct.endElement();

        this.xswct.endElement();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeInitializer(JavaInitializer init) {
        this.xswct.startElement("initializer");

        if (init.isStatic()) {
            this.xswct.attributes("static", "true");
        }
        this.xswct.text(init.getBlockContent());
        this.xswct.endElement();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeField(JavaField field) {
        this.xswct.startElement("field")
                .attributes("name", field.getName());

        commentHeader(field);
        writeAllModifiers(field.getModifiers());

        if (!field.isEnumConstant()) {
            this.xswct.emptyElement("type")
                    .attributes("name", field.getType().getGenericCanonicalName());
        } else if (field.isEnumConstant()) {
            if (field.getEnumConstantArguments() != null && !field.getEnumConstantArguments().isEmpty()) {
                this.xswct.startElement("enum-values");

                field.getEnumConstantArguments().stream()
                        .forEach(expr -> {
                            this.xswct.emptyElement("enum-value")
                                    .attributes("name", expr.getParameterValue().toString());
                        });
                this.xswct.endElement();
                if (field.getEnumConstantClass() != null) {
                    writeClassBody(field.getEnumConstantClass());
                }
            } else {
                if (field.getInitializationExpression() != null && field.getInitializationExpression().length() > 0) {
                    this.xswct.startElement("intialization-expression")
                            .text(field.getInitializationExpression())
                            .endElement();
                }
            }
        }
        this.xswct.endElement();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeConstructor(JavaConstructor constructor) {
        this.xswct.startElement("constructor")
                .attributes("name", constructor.getName());

        commentHeader(constructor);
        writeAllModifiers(constructor.getModifiers());

        constructor.getParameters().stream()
                .forEach(jp -> writeParameter(jp));

        if (!constructor.getExceptions().isEmpty()) {
            constructor.getExceptions().stream()
                    .forEach(jc -> {
                        this.xswct.emptyElement("throws")
                                .attributes("name", jc.getGenericCanonicalName());
                    });
        }

        if (constructor.getSourceCode() != null) {
            this.xswct.startElement("source-code")
                    .text(constructor.getSourceCode())
                    .endElement();
        }
        this.xswct.endElement();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeMethod(JavaMethod method) {
        this.xswct.startElement("method")
                .attributes("name", method.getName());
        commentHeader(method);
        writeAllModifiers(method.getModifiers());

        this.xswct.emptyElement("return-type")
                .attributes("type", method.getReturnType().getGenericCanonicalName());

        method.getParameters().stream()
                .forEach(jp -> writeParameter(jp));

        if (!method.getExceptions().isEmpty()) {

            method.getExceptions().stream()
                    .forEach(jc -> this.xswct.emptyElement("exception")
                    .attributes("type", jc.getGenericCanonicalName())
                    );
        }
//
        if (method.getSourceCode() != null && method.getSourceCode().length() > 0) {
            this.xswct.startElement("source-code")
                    .text(method.getSourceCode())
                    .endElement();
        }
        this.xswct.endElement();
        return this;
    }

    private void writeAllModifiers(List<String> modifiers) {
        if (modifiers.isEmpty()) {
            return;
        }
        this.xswct.startElement("modifiers");
        modifiers.stream()
                .forEach(s -> this.xswct.emptyElement("modifier").attributes("name", s)
                );
        this.xswct.endElement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeAnnotation(JavaAnnotation annotation) {
        this.xswct.startElement("annotation")
                .attributes("name", annotation.getType().getGenericCanonicalName());
//
        if (!annotation.getPropertyMap().isEmpty()) {
            annotation.getPropertyMap().entrySet().stream()
                    .forEach(e -> {
                        Map.Entry<String, AnnotationValue> entry = e;

                        this.xswct.emptyElement("annotation-value")
                                .attributes("key", entry.getKey())
                                .attributes("value", entry.getValue().toString());
                    });
        }
        this.xswct.endElement();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeParameter(JavaParameter parameter) {
        this.xswct.startElement("parameter")
                .attributes("name", parameter.getName());
        commentHeader(parameter);
        this.xswct.emptyElement("type")
                .attributes("type", parameter.getGenericCanonicalName());
//        if (parameter.isVarArgs()) {
//            buffer.write("...");
//        }
        this.xswct.endElement();
        return this;
    }

    protected void commentHeader(JavaAnnotatedElement entity) {
        this.xswct.startElement("comment-header");

        if (entity.getComment() != null || (entity.getTags().size() > 0)) {
            if (entity.getComment() != null && entity.getComment().length() > 0) {
                this.xswct.startElement("comment")
                        .text(entity.getComment())
                        .endElement();
            }
//
            if (entity.getTags().size() > 0) {
                for (DocletTag docletTag : entity.getTags()) {
                    this.xswct.startElement("doclet")
                            .attributes("name", docletTag.getName());
                    if (docletTag.getValue().length() > 0) {
                        this.xswct.attributes("value", docletTag.getValue());
                    }
                    this.xswct.endElement();
                }
            }
        }
        this.xswct.endElement();

        if (entity.getAnnotations() != null) {
            entity.getAnnotations().stream()
                    .forEach(annotation -> writeAnnotation(annotation)
                    );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleDescriptor(JavaModuleDescriptor descriptor
    ) {
//        if (descriptor.isOpen()) {
//            buffer.write("open ");
//        }
//        buffer.write("module " + descriptor.getName() + " {");
//        buffer.newline();
//        buffer.indent();
//
//        for (JavaRequires requires : descriptor.getRequires()) {
//            buffer.newline();
//            writeModuleRequires(requires);
//        }
//
//        for (JavaExports exports : descriptor.getExports()) {
//            buffer.newline();
//            writeModuleExports(exports);
//        }
//
//        for (JavaOpens opens : descriptor.getOpens()) {
//            buffer.newline();
//            writeModuleOpens(opens);
//        }
//
//        for (JavaProvides provides : descriptor.getProvides()) {
//            buffer.newline();
//            writeModuleProvides(provides);
//        }
//
//        for (JavaUses uses : descriptor.getUses()) {
//            buffer.newline();
//            writeModuleUses(uses);
//        }
//
//        buffer.newline();
//        buffer.deindent();
//        buffer.write('}');
//        buffer.newline();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleExports(JavaExports exports
    ) {
//        buffer.write("exports ");
//        buffer.write(exports.getSource().getName());
//        if (!exports.getTargets().isEmpty()) {
//            buffer.write(" to ");
//            Iterator<JavaModule> targets = exports.getTargets().iterator();
//            while (targets.hasNext()) {
//                JavaModule target = targets.next();
//                buffer.write(target.getName());
//                if (targets.hasNext()) {
//                    buffer.write(", ");
//                }
//            }
//        }
//        buffer.write(';');
//        buffer.newline();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleOpens(JavaOpens opens
    ) {
//        buffer.write("opens ");
//        buffer.write(opens.getSource().getName());
//        if (!opens.getTargets().isEmpty()) {
//            buffer.write(" to ");
//            Iterator<JavaModule> targets = opens.getTargets().iterator();
//            while (targets.hasNext()) {
//                JavaModule target = targets.next();
//                buffer.write(target.getName());
//                if (targets.hasNext()) {
//                    buffer.write(", ");
//                }
//            }
//        }
//        buffer.write(';');
//        buffer.newline();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleProvides(JavaProvides provides
    ) {
//        buffer.write("provides ");
//        buffer.write(provides.getService().getName());
//        buffer.write(" with ");
//        Iterator<JavaClass> providers = provides.getProviders().iterator();
//        while (providers.hasNext()) {
//            JavaClass provider = providers.next();
//            buffer.write(provider.getName());
//            if (providers.hasNext()) {
//                buffer.write(", ");
//            }
//        }
//        buffer.write(';');
//        buffer.newline();
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleRequires(JavaRequires requires
    ) {
//        buffer.write("requires ");
//        writeAccessibilityModifier(requires.getModifiers());
//        writeNonAccessibilityModifiers(requires.getModifiers());
//        buffer.write(requires.getModule().getName());
//        buffer.write(';');
//        buffer.newline();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleUses(JavaUses uses) {
//        buffer.write("uses ");
//        buffer.write(uses.getService().getName());
//        buffer.write(';');
//        buffer.newline();
        return this;
    }

}
