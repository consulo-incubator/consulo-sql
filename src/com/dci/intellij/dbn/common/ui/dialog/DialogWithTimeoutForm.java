package com.dci.intellij.dbn.common.ui.dialog;

import com.dci.intellij.dbn.common.TimeUtil;
import com.dci.intellij.dbn.common.thread.SimpleLaterInvocator;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class DialogWithTimeoutForm extends DBNFormImpl {
    private JPanel mainPanel;
    private JPanel contentPanel;
    private JLabel timeLeftLabel;

    public DialogWithTimeoutForm(int secondsLeft) {
        updateTimeLeft(secondsLeft);
    }

    public void setContentComponent(JComponent contentComponent) {
        contentPanel.add(contentComponent, BorderLayout.CENTER);
    }

    @Override
    public JComponent getComponent() {
        return mainPanel;
    }

    public void updateTimeLeft(final int secondsLeft) {
        new SimpleLaterInvocator() {
            @Override
            public void run() {
                int minutes = 0;
                int seconds = secondsLeft;
                if (secondsLeft > 60) {
                    minutes = TimeUtil.getMinutes(secondsLeft);
                    seconds = secondsLeft - TimeUtil.getSeconds(minutes);
                }
                timeLeftLabel.setText(minutes +":" + (seconds < 10 ? "0" :"") + seconds + " minutes");
            }
        }.start();
    }
}
