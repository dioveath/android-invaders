package com.charicha.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charicha on 12/28/2017.
 */

public class Pool<T> {

    public interface PoolObjectFactory<T>{
        T createObject();
    }

    private final PoolObjectFactory<T> factory;
    private final List<T> freeObjects;
    private final int size;

    public Pool(PoolObjectFactory factory, int size){
        this.factory = factory;
        this.freeObjects = new ArrayList<>(size);
        this.size = size;
    }

    public T getObject(){
        if(freeObjects.isEmpty())
            return factory.createObject();
        return freeObjects.remove(freeObjects.size() - 1);
    }

    public void addFreeObject(T object){
        if(freeObjects.size() >= size){
            return;
        }
        freeObjects.add(object);
    }

}
