package com.example;

import javax.lang.model.element.VariableElement;

/**
 * @author hiphonezhu@gmail.com
 * @version [CompilerAnnotation, 17/6/20 11:05]
 */

public class VariableInfo {
    // 被注解 View 的 Id 值
    int viewId;
    // 被注解 View 的信息：变量名称、类型
    VariableElement variableElement;

    public VariableElement getVariableElement() {
        return variableElement;
    }

    public void setVariableElement(VariableElement variableElement) {
        this.variableElement = variableElement;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }
}
