package com.obaniu.inappfloatview;

import java.util.ArrayList;
import java.util.List;

public interface ISynchronization {
    enum Command {
        SHOW,
        HIDE,
        UPDATE,
        REMOVE,
        PULL,
        PULL_ACK
    }

    void pull();

    void pullAck(ArrayList<SyncData> list);

    void synchronize(Command cmd, String tag, LayoutParams params);

    void setCallback(Callback callback);

    interface Callback {
        void onEvent(Command cmd, ArrayList<SyncData> list);
    }

    void register();

    void unregister();
}
