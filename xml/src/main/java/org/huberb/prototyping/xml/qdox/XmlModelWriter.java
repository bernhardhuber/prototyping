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
import java.util.*;
import java.util.function.Function;

/**
 * @author berni3
 */
public class XmlModelWriter implements ModelWriter {

    private XmlIndentBuffer buffer = new XmlIndentBuffer();

    /**
     * All information is written to this buffer. When extending this class you
     * should write to this buffer
     *
     * @return the buffer
     */
    protected final XmlIndentBuffer getBuffer() {
        return buffer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeSource(JavaSource source) {

        buffer.writeStartElement("source");

        buffer.writeStartElement("url");
        buffer.write(source.getURL().toString());
        buffer.writeEndElement("url");

        // package statement
        writePackage(source.getPackage());

        // import statement
        buffer.writeStartElement("imports");
        for (String imprt : source.getImports()) {
            buffer.writeStartElement("import");
            buffer.writeStartElement("name");
            buffer.write(imprt);
            buffer.writeEndElement("name");
            buffer.writeEndElement("import");
            buffer.newline();
        }
        if (source.getImports().size() > 0) {
            buffer.newline();
        }
        buffer.writeEndElement("imports");

        // classes
        for (ListIterator<JavaClass> iter = source.getClasses().listIterator(); iter.hasNext();) {
            JavaClass cls = iter.next();
            writeClass(cls);
            if (iter.hasNext()) {
                buffer.newline();
            }
        }
        buffer.writeEndElement("source");
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
            //buffer.write("package ");
            buffer.writeStartElement("name");
            buffer.write(pckg.getName());
            //buffer.write(';');
            buffer.writeEndElement("name");
            buffer.newline();
            buffer.newline();
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

        writeAccessibilityModifier(cls.getModifiers());
        writeNonAccessibilityModifiers(cls.getModifiers());

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
        buffer.writeStartElement("name");
        buffer.write(cls.getName());
        buffer.writeEndElement("name");
        buffer.writeEndElement(typeOf);

        // subclass
        if (cls.getSuperClass() != null) {
            String className = cls.getSuperClass().getFullyQualifiedName();
            if (!"java.lang.Object".equals(className) && !"java.lang.Enum".equals(className)) {
                buffer.write(" extends ");
                buffer.write(cls.getSuperClass().getGenericCanonicalName());
            }
        }

        // implements
        if (cls.getImplements().size() > 0) {
            buffer.write(cls.isInterface() ? " extends " : " implements ");

            for (ListIterator<JavaType> iter = cls.getImplements().listIterator(); iter.hasNext();) {
                buffer.write(iter.next().getGenericCanonicalName());
                if (iter.hasNext()) {
                    buffer.write(", ");
                }
            }
        }

        writeClassBody(cls);
        buffer.writeEndElement("class");
        return this;
    }

    private ModelWriter writeClassBody(JavaClass cls) {
        buffer.writeStartElement("classbody");

        buffer.write(" {");
        buffer.newline();
        buffer.indent();

        // fields
        for (JavaField javaField : cls.getFields()) {
            buffer.newline();
            writeField(javaField);
        }

        // constructors
        for (JavaConstructor javaConstructor : cls.getConstructors()) {
            buffer.newline();
            writeConstructor(javaConstructor);
        }

        // methods
        for (JavaMethod javaMethod : cls.getMethods()) {
            buffer.newline();
            writeMethod(javaMethod);
        }

        // inner-classes
        for (JavaClass innerCls : cls.getNestedClasses()) {
            buffer.newline();
            writeClass(innerCls);
        }

        buffer.deindent();
        buffer.newline();
        buffer.write('}');
        buffer.newline();
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
            buffer.write("static ");
        }
        buffer.write('{');
        buffer.newline();
        buffer.indent();

        buffer.write(init.getBlockContent());

        buffer.deindent();
        buffer.newline();
        buffer.write('}');
        buffer.newline();
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
            buffer.write(field.getType().getGenericCanonicalName());
            buffer.write(' ');
        }
        buffer.write(field.getName());

        if (field.isEnumConstant()) {
            if (field.getEnumConstantArguments() != null && !field.getEnumConstantArguments().isEmpty()) {
                buffer.write("( ");
                for (Iterator<Expression> iter = field.getEnumConstantArguments().listIterator(); iter.hasNext();) {
                    buffer.write(iter.next().getParameterValue().toString());
                    if (iter.hasNext()) {
                        buffer.write(", ");
                    }
                }
                buffer.write(" )");
            }
            if (field.getEnumConstantClass() != null) {
                writeClassBody(field.getEnumConstantClass());
            }
        } else {
            if (field.getInitializationExpression() != null && field.getInitializationExpression().length() > 0) {
                {
                    buffer.write(" = ");
                }
                buffer.write(field.getInitializationExpression());
            }
        }
        buffer.write(';');
        buffer.newline();
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

        buffer.write(constructor.getName());
        buffer.write('(');
        for (ListIterator<JavaParameter> iter = constructor.getParameters().listIterator(); iter.hasNext();) {
            writeParameter(iter.next());
            if (iter.hasNext()) {
                buffer.write(", ");
            }
        }
        buffer.write(')');

        if (!constructor.getExceptions().isEmpty()) {
            buffer.write(" throws ");
            for (Iterator<JavaClass> excIter = constructor.getExceptions().iterator(); excIter.hasNext();) {
                buffer.write(excIter.next().getGenericCanonicalName());
                if (excIter.hasNext()) {
                    buffer.write(", ");
                }
            }
        }

        buffer.write(" {");
        buffer.newline();
        if (constructor.getSourceCode() != null) {
            buffer.write(constructor.getSourceCode());
        }
        buffer.write('}');
        buffer.newline();
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
        writeAccessibilityModifier(method.getModifiers());
        writeNonAccessibilityModifiers(method.getModifiers());

        buffer.write(method.getReturnType().getGenericCanonicalName());
        buffer.write(' ');

        buffer.write(method.getName());
        buffer.write('(');

        for (ListIterator<JavaParameter> iter = method.getParameters().listIterator(); iter.hasNext();) {
            writeParameter(iter.next());
            if (iter.hasNext()) {
                buffer.write(", ");
            }

        }
        buffer.write(')');

        if (!method.getExceptions().isEmpty()) {
            buffer.write(" throws ");
            for (Iterator<JavaClass> excIter = method.getExceptions().iterator(); excIter.hasNext();) {
                buffer.write(excIter.next().getGenericCanonicalName());
                if (excIter.hasNext()) {
                    buffer.write(", ");
                }
            }
        }

        if (method.getSourceCode() != null && method.getSourceCode().length() > 0) {
            buffer.write(" {");
            buffer.newline();
            buffer.write(method.getSourceCode());
            buffer.write('}');
            buffer.newline();
        } else {
            buffer.write(';');
            buffer.newline();
        }
        buffer.writeEndElement("method");
        return this;
    }

    private void writeNonAccessibilityModifiers(Collection<String> modifiers) {
        buffer.writeStartElement("nonAccessibilityModifiers");

        for (String modifier : modifiers) {
            if (!modifier.startsWith("p")) {
                buffer.write(modifier);
                buffer.write(' ');
            }
        }
        buffer.writeEndElement("nonAccessibilityModifiers");
    }

    private void writeAccessibilityModifier(Collection<String> modifiers) {
        buffer.writeStartElement("accessibilityModifier");
        for (String modifier : modifiers) {
            if (modifier.startsWith("p")) {
                buffer.write(modifier);
                buffer.write(' ');
            }
        }
        buffer.writeEndElement("accessibilityModifier");
    }

    private void writeAllModifiers(List<String> modifiers) {
        buffer.writeStartElement("allModifiers");
        for (String modifier : modifiers) {
            buffer.write(modifier);
            buffer.write(' ');
        }
        buffer.writeEndElement("allModifiers");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelWriter writeAnnotation(JavaAnnotation annotation) {
        buffer.writeStartElement("annotation");

        buffer.write('@');
        buffer.write(annotation.getType().getGenericCanonicalName());
        if (!annotation.getPropertyMap().isEmpty()) {
            buffer.indent();
            buffer.write('(');
            Iterator<Map.Entry<String, AnnotationValue>> iterator = annotation.getPropertyMap().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, AnnotationValue> entry = iterator.next();
                buffer.write(entry.getKey());
                buffer.write('=');
                buffer.write(entry.getValue().toString());
                if (iterator.hasNext()) {
                    buffer.write(',');
                    buffer.newline();
                }
            }
            buffer.write(')');
            buffer.deindent();
        }
        buffer.newline();
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
        buffer.write(parameter.getGenericCanonicalName());
        if (parameter.isVarArgs()) {
            buffer.write("...");
        }
        buffer.write(' ');
        buffer.write(parameter.getName());
        buffer.writeEndElement("parameter");
        return this;
    }

    protected void commentHeader(JavaAnnotatedElement entity) {
        buffer.writeStartElement("commentHeader");
        if (entity.getComment() != null || (entity.getTags().size() > 0)) {
            buffer.write("/**");
            buffer.newline();

            if (entity.getComment() != null && entity.getComment().length() > 0) {
                buffer.write(" * ");

                buffer.write(entity.getComment().replaceAll("\n", "\n * "));

                buffer.newline();
            }

            if (!entity.getTags().isEmpty()) {
                if (entity.getComment() != null && entity.getComment().length() > 0) {
                    buffer.write(" *");
                    buffer.newline();
                }
                for (DocletTag docletTag : entity.getTags()) {
                    buffer.write(" * @");
                    buffer.write(docletTag.getName());
                    if (docletTag.getValue().length() > 0) {
                        buffer.write(' ');
                        buffer.write(docletTag.getValue());
                    }
                    buffer.newline();
                }
            }

            buffer.write(" */");
            buffer.newline();
        }
        buffer.writeEndElement("commentHeader");

        if (entity.getAnnotations() != null) {
            for (JavaAnnotation annotation : entity.getAnnotations()) {
                writeAnnotation(annotation);
            }
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
        buffer.newline();
        buffer.indent();

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
        buffer.deindent();
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
        writeAccessibilityModifier(requires.getModifiers());
        writeNonAccessibilityModifiers(requires.getModifiers());
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
