package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * @author hiphonezhu@gmail.com
 * @version [CompilerAnnotation, 17/6/20 09:55]
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.example.BindView"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ViewInjectProcessor extends AbstractProcessor {
    // 存放同一个Class下的所有注解
    Map<String, List<VariableInfo>> classMap = new HashMap<>();
    // 存放Class对应的TypeElement
    Map<String, TypeElement> classTypeElement = new HashMap<>();

    private Filer filer;
    Elements elementUtils;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        classMap.clear();
        classTypeElement.clear();

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for(Element element : elements) {
            // 获取 BindView 注解的值
            int viewId = element.getAnnotation(BindView.class).value();

            // 代表被注解的元素
            VariableElement variableElement = (VariableElement)element;

            // 备注解元素所在的Class
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            // Class的完整路径
            String classFullName = typeElement.getQualifiedName().toString();

            // 收集Class中所有被注解的元素
            List<VariableInfo> variableList = classMap.get(classFullName);
            if (variableList == null) {
                variableList = new ArrayList<>();
                classMap.put(classFullName, variableList);

                // 保存Class对应要素（名称、完成路径等）
                classTypeElement.put(classFullName, typeElement);
            }
            VariableInfo variableInfo = new VariableInfo();
            variableInfo.setVariableElement(variableElement);
            variableInfo.setViewId(viewId);
            variableList.add(variableInfo);
        }

        try {
            for(String classFullName : classMap.keySet()) {
                TypeElement typeElement = classTypeElement.get(classFullName);

                // 使用构造函数绑定数据
                MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(ParameterSpec.builder(TypeName.get(typeElement.asType()), "activity").build());
                List<VariableInfo> variableList = classMap.get(classFullName);
                for(VariableInfo variableInfo : variableList) {
                    VariableElement variableElement = variableInfo.getVariableElement();
                    // 变量名称(比如：TextView tv 的 tv)
                    String variableName = variableElement.getSimpleName().toString();
                    // 变量类型的完整类路径（比如：android.widget.TextView）
                    String variableFullName = variableElement.asType().toString();
                    // 在构造方法中增加赋值语句，例如：activity.tv = (android.widget.TextView)activity.findViewById(215334);
                    constructor.addStatement("activity.$L=($L)activity.findViewById($L)", variableName, variableFullName, variableInfo.getViewId());
                }

                // 构建Class
                TypeSpec typeSpec = TypeSpec.classBuilder(typeElement.getSimpleName() + "$$ViewInjector")
                        .addModifiers(Modifier.PUBLIC)
                        .addMethod(constructor.build())
                        .build();

                // 与目标Class放在同一个包下，解决Class属性的可访问性
                String packageFullName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
                JavaFile javaFile = JavaFile.builder(packageFullName, typeSpec)
                        .build();
                // 生成class文件
                javaFile.writeTo(filer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
}
