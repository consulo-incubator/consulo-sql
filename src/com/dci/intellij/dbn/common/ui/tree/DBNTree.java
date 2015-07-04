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
