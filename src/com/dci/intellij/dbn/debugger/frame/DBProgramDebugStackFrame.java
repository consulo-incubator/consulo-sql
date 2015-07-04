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

package com.dci.intellij.dbn.debugger.frame;

import gnu.trove.THashMap;

import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.dci.intellij.dbn.database.common.debug.DebuggerRuntimeInfo;
import com.dci.intellij.dbn.debugger.DBProgramDebugProcess;
import com.dci.intellij.dbn.debugger.DBProgramDebugUtil;
import com.dci.intellij.dbn.debugger.evaluation.DBProgramDebuggerEvaluator;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.psql.PSQLFile;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.vfs.DatabaseEditableObjectFile;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiManager;
import com.intellij.ui.SimpleColoredComponent;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.xdebugger.XDebuggerBundle;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.XDebuggerEvaluator;
import com.intellij.xdebugger.frame.XCompositeNode;
import com.intellij.xdebugger.frame.XStackFrame;
import com.intellij.xdebugger.frame.XValueChildrenList;
import com.intellij.xdebugger.impl.XSourcePositionImpl;

public class DBProgramDebugStackFrame extends XStackFrame {
    private DBProgramDebugProcess debugProcess;
    private XSourcePosition sourcePosition;
    private boolean inhibitSourcePosition = false;
    private int index;
    private DBProgramDebuggerEvaluator evaluator;
    private Map<String, DBProgramDebugValue> valuesMap;

    public DBProgramDebugStackFrame(DBProgramDebugProcess debugProcess, DebuggerRuntimeInfo runtimeInfo, int index) {
        this.index = index;
        DatabaseEditableObjectFile databaseFile = debugProcess.getDatabaseFile(runtimeInfo);

        this.debugProcess = debugProcess;
        sourcePosition = XSourcePositionImpl.create(databaseFile, runtimeInfo.getLineNumber());
    }

    public void setInhibitSourcePosition(boolean inhibitSourcePosition) {
        this.inhibitSourcePosition = inhibitSourcePosition;
    }

    public DBProgramDebugProcess getDebugProcess() {
        return debugProcess;
    }

    public int getIndex() {
        return index;
    }

    public DBProgramDebugValue getValue(String variableName) {
        return valuesMap == null ? null : valuesMap.get(variableName.toLowerCase());
    }

    public void setValue(String variableName, DBProgramDebugValue value) {
        if (valuesMap == null) {
            valuesMap =new THashMap<String, DBProgramDebugValue>();
        }
        valuesMap.put(variableName.toLowerCase(), value);
    }

    @Override
    public XDebuggerEvaluator getEvaluator() {
        if (evaluator == null) {
            evaluator = new DBProgramDebuggerEvaluator(this);
        }
        return evaluator;
    }

    @Override
    public XSourcePosition getSourcePosition() {
        return inhibitSourcePosition ? null : sourcePosition;
    }

    public void customizePresentation(final SimpleColoredComponent component) {
        DBSchemaObject object = DBProgramDebugUtil.getObject(sourcePosition);
        if (object != null) {
            component.append(object.getQualifiedName(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
            component.append(" (line " + (sourcePosition.getLine() + 1) + ") ", SimpleTextAttributes.REGULAR_ATTRIBUTES);
            component.setIcon(object.getOriginalIcon());
        } else {
            component.append(XDebuggerBundle.message("invalid.frame"), SimpleTextAttributes.ERROR_ATTRIBUTES);
        }
    }

    @Override
    public void computeChildren(@NotNull XCompositeNode node) {
        valuesMap = new THashMap<String, DBProgramDebugValue>();

        SourceCodeFile sourceCodeFile = DBProgramDebugUtil.getSourceCodeFile(sourcePosition);
        PSQLFile psiFile = (PSQLFile) PsiManager.getInstance(sourceCodeFile.getProject()).findFile(sourceCodeFile);
        Document document = DocumentUtil.getDocument(sourceCodeFile);
        int offset = document.getLineStartOffset(sourcePosition.getLine());
        Set<BasePsiElement> variables = psiFile.lookupVariableDefinition(offset);

		XValueChildrenList values = new XValueChildrenList();
        for (final BasePsiElement basePsiElement : variables) {
            String variableName = basePsiElement.getText();
            DBProgramDebugValue value = new DBProgramDebugValue(debugProcess, variableName, basePsiElement.getIcon(true), index);
            values.add(value);
            valuesMap.put(variableName.toLowerCase(), value);
        }

        node.addChildren(values, true);
    }
}


