package com.github.hapcaper.myideaplugintest.parser;

import com.intellij.psi.PsiArrayType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ParserClass {
    private final List<String> iterableTypes = List.of(
            "java.lang.Iterable",
            "java.util.Collection",
            "java.util.AbstractCollection",
            "java.util.List",
            "java.util.AbstractList",
            "java.util.Set",
            "java.util.AbstractSet");

    private final List<String> mapTypes = List.of(
            "java.util.Map"
    );


    public static Map<String, Object> parse(PsiFile psiFile) {

        PsiClass psiClass = PsiTreeUtil.findChildOfType(psiFile, PsiClass.class);

        Map<String, Object> allData = analysisClass(psiClass);
        return allData;

    }

    private static @NotNull Map<String, Object> analysisClass(PsiClass psiClass) {
        Map<String, Object> allData = new HashMap<>();
        for (PsiField field : psiClass.getFields()) {
            PsiType type = field.getType();
            Object extracted = analysisField(type);
            allData.put(field.getName(), extracted);
        }
        return allData;
    }

    private static Object analysisField(PsiType type) {
        if (type instanceof PsiPrimitiveType) {//基础类型
            if (type.equals(PsiPrimitiveType.INT)) {
                return 0;
            } else if (type.equals(PsiPrimitiveType.BOOLEAN)) {
                return true;
            } else if (type.equals(PsiPrimitiveType.CHAR)) {
                return "";
            } else if (type.equals(PsiPrimitiveType.SHORT)) {
                return 0;
            } else if (type.equals(PsiPrimitiveType.LONG)) {
                return 0L;
            } else if (type.equals(PsiPrimitiveType.FLOAT)) {
                return 0.0f;
            } else if (type.equals(PsiPrimitiveType.DOUBLE)) {
                return 0.0d;
            } else {
                return null;
            }
        } else if (type instanceof PsiArrayType) {//数组类型
            PsiType deepComponentType = type.getDeepComponentType();
            return List.of(analysisField(deepComponentType));
        } else {//引用类型
            PsiClass psiClass = PsiUtil.resolveClassInClassTypeOnly(type);

            List<String> fieldTypeNames = new ArrayList<>();
            fieldTypeNames.add(psiClass.getQualifiedName());
            fieldTypeNames.addAll(Arrays.stream(psiClass.getSupers())
                    .map(PsiClass::getQualifiedName).toList());
            fieldTypeNames = fieldTypeNames.stream().filter(Objects::nonNull).toList();

            boolean iterable = fieldTypeNames.stream().anyMatch(iterableTypes::contains);

            if (iterable) {//集合类型
                PsiType typeToDeepType = PsiUtil.extractIterableTypeParameter(type, false);
                return List.of(analysisField(typeToDeepType));
            } else {//普通对象引用类型

                List<String> fieldMapTypeNames = new ArrayList<>();
                fieldMapTypeNames.add(psiClass.getQualifiedName());
                fieldMapTypeNames.addAll(Arrays.stream(psiClass.getSupers())
                        .map(PsiClass::getQualifiedName).toList());
                fieldMapTypeNames = fieldMapTypeNames.stream().filter(Objects::nonNull).toList();

                boolean isMap = fieldMapTypeNames.stream().anyMatch(mapTypes::contains);
                if (isMap) {
                    return new Object();
                } else {
                    return analysisClass(psiClass);
                }


            }

        }

        return null;
    }
}
