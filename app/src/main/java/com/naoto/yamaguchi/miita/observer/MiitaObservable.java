package com.naoto.yamaguchi.miita.observer;

import java.util.Observable;

/**
 * Created by naoto on 2016/12/23.
 */

public final class MiitaObservable extends Observable {

    private static MiitaObservable instance = null;

    public static synchronized MiitaObservable getInstance() {
        if (instance == null) {
            instance = new MiitaObservable();
        }
        return instance;
    }

    private MiitaObservable() {
        super();
    }

    public void notify(MiitaEventObject eventObject) {
        this.setChanged();
        this.notifyObservers(eventObject);
    }
}
