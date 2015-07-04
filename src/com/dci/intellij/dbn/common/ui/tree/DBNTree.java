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

package com.dci.intellij.dbn.common.ui.tree;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.intellij.ui.treeStructure.Tree;

public class DBNTree extends Tree
{
	public DBNTree()
	{
		setTransferHandler(new DBNTreeTransferHandler());
	}

	public DBNTree(TreeModel treemodel)
	{
		super(treemodel);
		setTransferHandler(new DBNTreeTransferHandler());
	}

	public DBNTree(TreeNode root)
	{
		super(root);
		setTransferHandler(new DBNTreeTransferHandler());
	}
}
