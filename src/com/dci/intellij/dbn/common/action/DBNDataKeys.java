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

package com.dci.intellij.dbn.common.action;

import com.dci.intellij.dbn.editor.data.DatasetEditor;
import com.dci.intellij.dbn.execution.statement.result.StatementExecutionResult;
import com.intellij.openapi.actionSystem.DataKey;
import com.intellij.openapi.util.Key;

public interface DBNDataKeys {
    DataKey<DatasetEditor> DATASET_EDITOR = DataKey.create("DBNavigator.DatasetEditor");
    DataKey<StatementExecutionResult> STATEMENT_EXECUTION_RESULT = DataKey.create("DBNavigator.StatementExecutionResult");
    Key<String> ACTION_PLACE_KEY = Key.create("DBNavigator.ActionPlace");
}
