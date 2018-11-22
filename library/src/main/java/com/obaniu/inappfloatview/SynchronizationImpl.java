package com.obaniu.inappfloatview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Process;
import android.util.Log;

import java.util.ArrayList;

public class SynchronizationImpl implements ISynchronization {
    private Callback callback;
    private final static String CMD = "cmd";
    private final static String DATA = "data";
    private final static String PID = "PID";

    private final static String ACTION = SynchronizationImpl.class.getName();
    private final Context context;
    private final int mPId= Process.myPid();
    public SynchronizationImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    public void synchronize(Command cmd, String tag, LayoutParams params) {
        ArrayList<SyncData> list=new ArrayList<>(1);
        if (cmd != Command.PULL) list.add(new SyncData(tag, params));
        synchronize(cmd, list);
    }

    private void synchronize(Command cmd,ArrayList<SyncData> data) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(CMD, cmd.ordinal());
        intent.putExtra(PID,mPId);
        intent.putParcelableArrayListExtra(DATA, data);
        //android.util.Log.i("InAppFloatView","synchronize:cmd="+cmd+", data="+data);
        context.sendBroadcast(intent);
    }
    @Override
    public void pull() {
        synchronize(Command.PULL, null, null);
    }

    @Override
    public void pullAck(ArrayList<SyncData> list) {
        synchronize(Command.PULL_ACK, list);
    }

    @Override
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void register() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION);
            context.registerReceiver(receiver, filter);
        } catch (Exception e) {
        }
    }

    @Override
    public void unregister() {
        try {
            context.unregisterReceiver(receiver);
        } catch (Exception e) {
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null
                    || callback == null
                    || intent.getIntExtra(PID, 0) == mPId
                    || !ACTION.equals(intent.getAction()) )
                return;

            int cmd = intent.getIntExtra(CMD, 0);
            if (cmd >= 0 && cmd < Command.values().length) {
                ArrayList<SyncData> list = intent.getParcelableArrayListExtra(DATA);
                callback.onEvent(Command.values()[cmd], list);
            }
        }
    };
}
