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

package com.dci.intellij.dbn.database.oracle.execution;

import com.dci.intellij.dbn.object.DBMethod;

public class OracleMethodDebugExecutionProcessor extends OracleMethodExecutionProcessor {
    public OracleMethodDebugExecutionProcessor(DBMethod method) {
        super(method);
    }

    @Override
    protected void preHookExecutionCommand(StringBuilder buffer) {
        super.preHookExecutionCommand(buffer);
    }

    @Override
    protected void postHookExecutionCommand(StringBuilder buffer) {
        buffer.append("\n");
        buffer.append("    SYS.DBMS_DEBUG.debug_off();\n");
        buffer.append("exception\n");
        buffer.append("    when others then\n");
        buffer.append("        SYS.DBMS_DEBUG.debug_off();\n");
        buffer.append("        raise;\n");
    }

}