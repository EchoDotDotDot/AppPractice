package com.echo.annotation_compler;

import com.echo.annotations.BindPath;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {
    Filer mFiler;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler=processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types=new HashSet<>();
        types.add(BindPath.class.getCanonicalName() );
        return types;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //
        Set<? extends Element> elementsAnnotatedWith =
                roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        Map<String,String> map=new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            TypeElement typeElement=(TypeElement)element;
            String key = typeElement.getAnnotation(BindPath.class).value();
            String value = typeElement.getQualifiedName().toString();
            map.put(key,value);
        }

        if(map.size()>0){
            Writer writer=null;
            String utilName="ActivityUtil"+System.currentTimeMillis();

            try {
                JavaFileObject sourceFile = mFiler.createSourceFile("com.netease.util." + utilName);
                writer=sourceFile.openWriter();
                writer.write("package com.netease.util;\n" +
                        "\n" +
                        "import com.echo.aroute.ARouter;\n" +
                        "import com.echo.aroute.IRouter;\n" +
                        "\n" +
                        "public class "+utilName+" implements IRouter {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n"
                        );
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    String value = map.get(key);
                    writer.write("ARouter.getInstance().putActivity(\""+key+"\","+value+".class);\n");
                }

                writer.write("    }\n" +
                        "}");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(writer!=null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return false;
    }
}
