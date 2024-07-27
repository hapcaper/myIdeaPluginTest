package com.github.hapcaper.myideaplugintest.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class MyEditorPopupMenu extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        editor.getSelectionModel().copySelectionToClipboard();
        Document document = editor.getDocument();
        CharSequence charsSequence = document.getCharsSequence();
        Messages.showDialog(String.valueOf(charsSequence), project.getName(), new String[]{"ok"}, 0, null);

    }
}
