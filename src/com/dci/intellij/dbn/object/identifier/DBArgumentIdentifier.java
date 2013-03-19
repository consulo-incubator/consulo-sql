package com.dci.intellij.dbn.object.identifier;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.object.DBArgument;
import com.dci.intellij.dbn.object.DBMethod;
import com.dci.intellij.dbn.object.DBProgram;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObjectType;

public class DBArgumentIdentifier extends DBObjectIdentifier<DBArgument>{
    private int overload;

    public DBArgumentIdentifier(DBArgument argument) {
        super(argument);
        overload = argument.getOverload();
    }

    public DBArgument lookupObject() {
        ConnectionHandler connectionHandler = lookupConnectionHandler();
        if (connectionHandler == null) return null;

        DBSchema schema = connectionHandler.getObjectBundle().getSchema(nodes[0].getName());
        if (schema == null) return null;

        DBMethod method;
        Node programNode = getProgramNode();
        Node methodNode = getMethodNode();
        DBObjectType methodObjectType = methodNode.getType();
        if (programNode != null) {
            DBProgram program = schema.getProgram(programNode.getName());
            if (program == null || program.getObjectType() != programNode.getType()) return null;

            method = program.getMethod(methodNode.getName(), overload);
        } else {
            method = schema.getMethod(methodNode.getName(), methodObjectType.getName());
        }

        if (method == null) return null;

        return method.getArgument(getArgumentNode().getName());
    }

    public int getOverload() {
        return overload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DBArgumentIdentifier that = (DBArgumentIdentifier) o;

        return overload == that.overload;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + overload;
        return result;
    }

    @Override
    public String toString() {
        return getMethodNode().getType().getName() + " " + getPath();
    }

    private Node getProgramNode() {
        return nodes.length == 4 ? nodes[1] : null;
    }

    private Node getMethodNode() {
        return nodes.length == 4 ? nodes[2] : nodes[1];
    }

    private Node getArgumentNode() {
        return nodes.length == 4 ? nodes[3] : nodes[2];
    }

}
