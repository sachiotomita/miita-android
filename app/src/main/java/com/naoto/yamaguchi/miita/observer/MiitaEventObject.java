package com.naoto.yamaguchi.miita.observer;

import java.util.EventObject;

/**
 * Created by naoto on 2016/12/23.
 */

public final class MiitaEventObject extends EventObject {

    public enum Type {
        LOGOUT
    }

    private final Type type;

    public MiitaEventObject(Object source, Type type) {
        super(source);
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }
}
