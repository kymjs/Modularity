package com.kymjs.core.apt;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by ZhangTao on 10/24/16.
 */

@SupportedAnnotationTypes("com.kymjs.core.apt.RList")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class APTProcessor extends AbstractProcessor {
    public static final String CLASSNAME = "RouterList";
    public static final String PACKAGENAME = "com.kymjs.core.apt";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            JavaFileObject f = processingEnv.getFiler().createSourceFile(CLASSNAME);
            Writer w = f.openWriter();
            PrintWriter pw = new PrintWriter(w);
            for (TypeElement te : annotations) {
                pw.println("package " + PACKAGENAME + ";");
                pw.println("\npublic class " + CLASSNAME + " { ");
                for (Element element : roundEnv.getElementsAnnotatedWith(te)) {
                    RList annotation = element.getAnnotation(RList.class);
                    String name = annotation.name();
                    String value = element.toString();
//                    String packageName = element.getEnclosingElement().toString();

                    pw.println("    public static final String " + name + " = \"" + value + "\";");
                }
                pw.println("}");
                pw.flush();
            }
        } catch (IOException x) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    x.toString());
        }
        return true;
    }
}