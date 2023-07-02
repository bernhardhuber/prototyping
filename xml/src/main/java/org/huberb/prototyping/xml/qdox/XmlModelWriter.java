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
import com.thoughtworks.qdox.model.expression.Expression;
import com.thoughtworks.qdox.writer.ModelWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.function.Function;

/**
 * @author berni3
 */
public class XmlModelWriter implements ModelWriter {

    //private final IGenericXmlEmitter buffer = new XmlIndentBufferEmitter();
    private final IGenericXmlEmitter buffer = new XmlStreamWriterEmitter(new StringWriter());

    /**
     * All information is written to this buffer. When extending this class you
     * should write to this buffer
     *
     * @return the buffer
     */
    protected final IGenericXmlEmitter getBuffer() {
        return buffer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeSource(JavaSource source) {
        buffer.startDocument();

        buffer.writeStartElement("source");

        buffer.writeInlineElement("url", source.getURL().toString());

        // package statement
        writePackage(source.getPackage());

        // import statement
        buffer.writeStartElement("imports");
        for (String imprt : source.getImports()) {
            buffer.writeStartElement("import");
            buffer.writeInlineElement("name", imprt);
            buffer.writeEndElement("import");
        }
        buffer.writeEndElement("imports");

        // classes
        buffer.writeStartElement("classes");
        for (ListIterator<JavaClass> iter = source.getClasses().listIterator(); iter.hasNext();) {
            JavaClass cls = iter.next();
            writeClass(cls);
        }
        buffer.writeEndElement("classes");

        buffer.writeEndElement("source");
        buffer.endDocument();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writePackage(JavaPackage pckg) {
        buffer.writeStartElement("package");
        if (pckg != null) {
            commentHeader(pckg);
            buffer.writeInlineElement("name", pckg.getName());
        }
        buffer.writeEndElement("package");
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeClass(JavaClass cls) {
        buffer.writeStartElement("class");
        commentHeader(cls);

        writeAllModifiers(cls.getModifiers());

        Function<JavaClass, String> typeOfF = (JavaClass jc) -> {
            if (cls.isEnum()) {
                return "enum";
            } else if (cls.isInterface()) {
                return "interface";
            } else if (cls.isAnnotation()) {
                return "@interface";
            } else {
                return "class";
            }
        };

        final String typeOf = typeOfF.apply(cls);
        buffer.writeStartElement(typeOf);
        buffer.writeInlineElement("name", cls.getName());
        buffer.writeEndElement(typeOf);

        // subclass
        if (cls.getSuperClass() != null) {
            String className = cls.getSuperClass().getFullyQualifiedName();
            if (!"java.lang.Object".equals(className) && !"java.lang.Enum".equals(className)) {
                buffer.writeInlineElement("extends", cls.getSuperClass().getGenericCanonicalName());
            }
        }

        // implements
        if (cls.getImplements().size() > 0) {
            String exendsOrImplements = cls.isInterface() ? "extends" : "implements";
            for (ListIterator<JavaType> iter = cls.getImplements().listIterator(); iter.hasNext();) {
                buffer.writeInlineElement(exendsOrImplements, iter.next().getGenericCanonicalName());
            }
        }
        writeClassBody(cls);
        buffer.writeEndElement("class");
        return this;
    }

    private ModelWriter writeClassBody(JavaClass cls) {
        buffer.writeStartElement("classbody");

        // fields
        buffer.writeStartElement("fields");
        for (JavaField javaField : cls.getFields()) {
            writeField(javaField);
        }
        buffer.writeEndElement("fields");

        // constructors
        buffer.writeStartElement("constructors");
        for (JavaConstructor javaConstructor : cls.getConstructors()) {
            writeConstructor(javaConstructor);
        }
        buffer.writeEndElement("constructors");

        // methods
        buffer.writeStartElement("methods");
        for (JavaMethod javaMethod : cls.getMethods()) {
            writeMethod(javaMethod);
        }
        buffer.writeEndElement("methods");

        // inner-classes
        buffer.writeStartElement("inner-classes");
        for (JavaClass innerCls : cls.getNestedClasses()) {
            writeClass(innerCls);
        }
        buffer.writeEndElement("inner-classes");

        buffer.writeEndElement("classbody");
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeInitializer(JavaInitializer init) {
        buffer.writeStartElement("initializer");
        if (init.isStatic()) {
            buffer.write("static");
        }
        buffer.write(init.getBlockContent());

        buffer.writeEndElement("initializer");
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeField(JavaField field) {
        buffer.writeStartElement("field");
        commentHeader(field);

        writeAllModifiers(field.getModifiers());
        if (!field.isEnumConstant()) {
            buffer.writeStartElement("type-canonical-name");
            buffer.write("<![CDATA[");
            buffer.write(field.getType().getGenericCanonicalName());
            buffer.write("]]>");
            buffer.writeEndElement("type-canonical-name");
        }
        buffer.writeInlineElement("name", field.getName());

        if (field.isEnumConstant()) {
            if (field.getEnumConstantArguments() != null && !field.getEnumConstantArguments().isEmpty()) {
                buffer.writeStartElement("enums");
                for (Iterator<Expression> iter = field.getEnumConstantArguments().listIterator(); iter.hasNext();) {
                    buffer.writeInlineElement("enum", iter.next().getParameterValue().toString());
                }
                buffer.writeEndElement("enums");
            }
            if (field.getEnumConstantClass() != null) {
                writeClassBody(field.getEnumConstantClass());
            }
        } else {
            if (field.getInitializationExpression() != null && field.getInitializationExpression().length() > 0) {
                buffer.writeInlineElement("initialization-expression", field.getInitializationExpression());
            }
        }
        buffer.writeEndElement("field");
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeConstructor(JavaConstructor constructor) {
        buffer.writeStartElement("constructor");

        commentHeader(constructor);
        writeAllModifiers(constructor.getModifiers());

        buffer.writeInlineElement("name", constructor.getName());

        buffer.writeStartElement("parameters");
        for (ListIterator<JavaParameter> iter = constructor.getParameters().listIterator(); iter.hasNext();) {
            writeParameter(iter.next());
        }
        buffer.writeEndElement("parameters");

        if (!constructor.getExceptions().isEmpty()) {
            buffer.writeStartElement("throws");
            for (Iterator<JavaClass> excIter = constructor.getExceptions().iterator(); excIter.hasNext();) {
                buffer.writeInlineElement("exception", excIter.next().getGenericCanonicalName());
            }
            buffer.writeEndElement("throws");
        }

        if (constructor.getSourceCode() != null) {
            buffer.writeStartElement("source");
            buffer.write("<![CDATA[");
            buffer.write(constructor.getSourceCode());
            buffer.write("]]>");
            buffer.writeEndElement("source");
        }

        buffer.writeEndElement("constructor");

        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeMethod(JavaMethod method) {
        buffer.writeStartElement("method");
        commentHeader(method);
        writeAllModifiers(method.getModifiers());

        buffer.writeInlineElement("return-type", method.getReturnType().getGenericCanonicalName());

        buffer.writeInlineElement("name", method.getName());

        buffer.writeStartElement("parameters");
        for (ListIterator<JavaParameter> iter = method.getParameters().listIterator(); iter.hasNext();) {
            writeParameter(iter.next());
        }
        buffer.writeEndElement("parameters");

        if (!method.getExceptions().isEmpty()) {
            buffer.writeStartElement("throws");
            for (Iterator<JavaClass> excIter = method.getExceptions().iterator(); excIter.hasNext();) {
                buffer.writeInlineElement("exception", excIter.next().getGenericCanonicalName());
            }
            buffer.writeEndElement("throws");
        }

        buffer.writeStartElement("source");
        if (method.getSourceCode() != null && method.getSourceCode().length() > 0) {
            buffer.write("<![CDATA[");
            buffer.write(method.getSourceCode());
            buffer.write("]]>");
        }
        buffer.writeEndElement("source");

        buffer.writeEndElement("method");

        return this;
    }

    private void writeAllModifiers(Collection<String> modifiers) {
        buffer.writeStartElement("modifiers");
        for (String modifier : modifiers) {
            buffer.writeInlineElement("modifier", modifier);
        }
        buffer.writeEndElement("modifiers");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeAnnotation(JavaAnnotation annotation) {
        buffer.writeStartElement("annotation");

        buffer.writeInlineElement("name", "@" + annotation.getType().getGenericCanonicalName());

        if (!annotation.getPropertyMap().isEmpty()) {
            buffer.writeStartElement("values");

            Iterator<Map.Entry<String, AnnotationValue>> iterator = annotation.getPropertyMap().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, AnnotationValue> entry = iterator.next();
                buffer.writeInlineElement("key", entry.getKey());
                buffer.writeInlineElement("value", entry.getValue().toString());
            }
            buffer.writeEndElement("values");
        }
        buffer.writeEndElement("annotation");
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeParameter(JavaParameter parameter) {
        buffer.writeStartElement("parameter");
        commentHeader(parameter);

        buffer.writeStartElement("canonical-name");
        buffer.write("<![CDATA[");
        buffer.write(parameter.getGenericCanonicalName());
        buffer.write("]]>");
        buffer.writeEndElement("canonical-name");
        if (parameter.isVarArgs()) {
            buffer.writeInlineElement("var-args", "...");
        }
        buffer.writeInlineElement("name", parameter.getName());
        buffer.writeEndElement("parameter");
        return this;
    }

    protected void commentHeader(JavaAnnotatedElement entity) {
        buffer.writeStartElement("comment-header");
        if (entity.getComment() != null || (entity.getTags().size() > 0)) {
            if (entity.getComment() != null && entity.getComment().length() > 0) {
                buffer.writeStartElement("comment");
                buffer.write("<![CDATA[");
                buffer.write(entity.getComment());
                buffer.write("]]>");
                buffer.writeEndElement("comment");
            }

            if (!entity.getTags().isEmpty()) {
                buffer.writeStartElement("doclets");
                for (DocletTag docletTag : entity.getTags()) {
                    buffer.writeStartElement("doclet");
                    buffer.writeInlineElement("name", docletTag.getName());
                    if (docletTag.getValue().length() > 0) {
                        buffer.writeInlineElement("value", docletTag.getValue());
                    }
                    buffer.writeEndElement("doclet");
                }
                buffer.writeEndElement("doclets");
            }
        }
        buffer.writeEndElement("comment-header");

        if (entity.getAnnotations() != null) {
            buffer.writeStartElement("annotations");

            for (JavaAnnotation annotation : entity.getAnnotations()) {
                writeAnnotation(annotation);
            }
            buffer.writeEndElement("annotations");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleDescriptor(JavaModuleDescriptor descriptor) {
        if (descriptor.isOpen()) {
            buffer.write("open ");
        }
        buffer.write("module " + descriptor.getName() + " {");

        for (JavaRequires requires : descriptor.getRequires()) {
            buffer.newline();
            writeModuleRequires(requires);
        }

        for (JavaExports exports : descriptor.getExports()) {
            buffer.newline();
            writeModuleExports(exports);
        }

        for (JavaOpens opens : descriptor.getOpens()) {
            buffer.newline();
            writeModuleOpens(opens);
        }

        for (JavaProvides provides : descriptor.getProvides()) {
            buffer.newline();
            writeModuleProvides(provides);
        }

        for (JavaUses uses : descriptor.getUses()) {
            buffer.newline();
            writeModuleUses(uses);
        }

        buffer.newline();
        buffer.write('}');
        buffer.newline();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleExports(JavaExports exports) {

        buffer.write("exports ");
        buffer.write(exports.getSource().getName());
        if (!exports.getTargets().isEmpty()) {
            buffer.write(" to ");
            Iterator<JavaModule> targets = exports.getTargets().iterator();
            while (targets.hasNext()) {
                JavaModule target = targets.next();
                buffer.write(target.getName());
                if (targets.hasNext()) {
                    buffer.write(", ");
                }
            }
        }
        buffer.write(';');
        buffer.newline();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleOpens(JavaOpens opens) {
        buffer.write("opens ");
        buffer.write(opens.getSource().getName());
        if (!opens.getTargets().isEmpty()) {
            buffer.write(" to ");
            Iterator<JavaModule> targets = opens.getTargets().iterator();
            while (targets.hasNext()) {
                JavaModule target = targets.next();
                buffer.write(target.getName());
                if (targets.hasNext()) {
                    buffer.write(", ");
                }
            }
        }
        buffer.write(';');
        buffer.newline();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleProvides(JavaProvides provides) {
        buffer.write("provides ");
        buffer.write(provides.getService().getName());
        buffer.write(" with ");
        Iterator<JavaClass> providers = provides.getProviders().iterator();
        while (providers.hasNext()) {
            JavaClass provider = providers.next();
            buffer.write(provider.getName());
            if (providers.hasNext()) {
                buffer.write(", ");
            }
        }
        buffer.write(';');
        buffer.newline();
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleRequires(JavaRequires requires) {
        buffer.write("requires ");
        writeAllModifiers(requires.getModifiers());
        buffer.write(requires.getModule().getName());
        buffer.write(';');
        buffer.newline();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeModuleUses(JavaUses uses) {
        buffer.write("uses ");
        buffer.write(uses.getService().getName());
        buffer.write(';');
        buffer.newline();
        return this;
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
