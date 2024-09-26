package com.github.hapcaper.myideaplugintest.parser;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.intellij.platform.uast.testFramework.common.UastClassToString;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeVisitor;
import com.intellij.psi.util.PsiEditorUtil;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.uast.UClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParserClass {


    public static String parse(PsiFile psiFile) {
        Map<String, Object> allData = new HashMap<>();

        PsiClass psiClass = PsiTreeUtil.findChildOfType(psiFile, PsiClass.class);
        for (PsiField field : psiClass.getFields()) {
            PsiType type = field.getType();
            if (type instanceof PsiPrimitiveType) {


            }
            String name = field.getName();


            System.out.println(type + "," + name);
        }
        return "";

    }
}
