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

package com.dci.intellij.dbn.code.common.completion.options.filter;

import com.dci.intellij.dbn.code.common.completion.options.filter.ui.CheckedTreeNodeProvider;
import com.dci.intellij.dbn.code.common.completion.options.filter.ui.CodeCompletionFilterTreeNode;
import com.intellij.ui.CheckedTreeNode;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;

public class CodeCompletionFilterOptionBundle implements CheckedTreeNodeProvider {
    private List<CodeCompletionFilterOption> options = new ArrayList<CodeCompletionFilterOption>();
    private CodeCompletionFilterSettings filterSettings;
    private String name;

    public CodeCompletionFilterOptionBundle(String name, CodeCompletionFilterSettings filterSettings) {
        this.name = name;
        this.filterSettings = filterSettings;
    }

    public List<CodeCompletionFilterOption> getOptions() {
        return options;
    }

    public String getName() {
        return name;
    }

    public CodeCompletionFilterSettings getFilterSettings() {
        return filterSettings;
    }

    public void readExternal(Element element) {
        List children = element.getChildren();
        for (Object child: children) {
            CodeCompletionFilterOption option = new CodeCompletionFilterOption(filterSettings);
            Element childElement = (Element) child;
            if (childElement.getName().equals("filter-element")){
                option.readExternal(childElement);
                int index = options.indexOf(option);
                if (index == -1) {
                    options.add(option);
                } else {
                    option = options.get(index);
                    option.readExternal(childElement);
                }
            }
        }
    }

    public void writeExternal(Element element){
        for (CodeCompletionFilterOption option : options) {
            Element child = new Element("filter-element");
            option.writeExternal(child);
            element.addContent(child);
        }
    }

    public CheckedTreeNode createCheckedTreeNode() {
        CodeCompletionFilterTreeNode node = new CodeCompletionFilterTreeNode(this, false);
        //node.setChecked(true);
        for (CodeCompletionFilterOption option : getOptions()) {
            //if (!option.isSelected()) node.setChecked(false);
            node.add(option.createCheckedTreeNode());
        }
        node.updateCheckedStatusFromChildren();
        return node;
    }
}
