/*
 * Copyright 2012-2014 Dan Cioca
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dci.intellij.dbn.common.ui.dialog;

import com.dci.intellij.dbn.common.TimeUtil;
import com.dci.intellij.dbn.common.thread.SimpleLaterInvocator;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import java.util.Timer;
import java.util.TimerTask;

public abstract class DialogWithTimeout extends DBNDialog{
    private DialogWithTimeoutForm form;
    private Timer timeoutTimer;
    private int secondsLeft;

    protected DialogWithTimeout(Project project, String title, boolean canBeParent, int timeoutSeconds) {
        super(project, title, canBeParent);
        secondsLeft = timeoutSeconds;
        form = new DialogWithTimeoutForm(secondsLeft);
        timeoutTimer = new Timer("Timeout dialog task [" + getProject().getName() + "]");
        timeoutTimer.schedule(new TimeoutTask(), TimeUtil.ONE_SECOND, TimeUtil.ONE_SECOND);
    }

    @Override
    protected void init() {
        form.setContentComponent(createContentComponent());
        super.init();
    }

    private class TimeoutTask extends TimerTask {
        public void run() {
            if (secondsLeft > 0) {
                secondsLeft = secondsLeft -1;
                form.updateTimeLeft(secondsLeft);
                if (secondsLeft == 0) {
                    new SimpleLaterInvocator() {
                        @Override
                        public void run() {
                            doDefaultAction();
                        }
                    }.start();

                }
            }
        }
    }

    @Nullable
    @Override
    protected final JComponent createCenterPanel() {
        return form.getComponent();
    }

    protected abstract JComponent createContentComponent();

    public abstract void doDefaultAction();

    @Override
    protected void dispose() {
        super.dispose();
        timeoutTimer.cancel();
        timeoutTimer.purge();
        form.dispose();
        form = null;
    }

}
