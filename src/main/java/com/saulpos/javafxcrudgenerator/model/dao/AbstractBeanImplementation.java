package com.saulpos.javafxcrudgenerator.model.dao;

import com.saulpos.javafxcrudgenerator.DatabaseConnection;

import java.beans.PropertyVetoException;

public abstract class AbstractBeanImplementation implements AbstractBean<AbstractBeanImplementation>{

    private static final long serialVersionUID = 1L;

    @Override
    public Integer save() throws PropertyVetoException {
        return DatabaseConnection.getInstance().createEntry(this);
    }

    @Override
    public void update() throws PropertyVetoException {
        DatabaseConnection.getInstance().update(this);
    }

    @Override
    public void saveOrUpdate() throws PropertyVetoException {
        DatabaseConnection.getInstance().saveOrUpdate(this);
    }

    @Override
    public abstract void receiveChanges(AbstractBeanImplementation currentBean);

    @Override
    public abstract AbstractBeanImplementation clone();

    @Override
    public void delete() throws PropertyVetoException {
        DatabaseConnection.getInstance().delete(this);
    }
}
