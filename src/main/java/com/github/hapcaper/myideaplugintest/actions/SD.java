package com.github.hapcaper.myideaplugintest.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class SD extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Messages.showDialog("sd", "sd", new String[]{"ok"}, 0, null);


    }
}
