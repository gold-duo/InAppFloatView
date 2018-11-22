package com.obaniu.inappfloatview;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by obaniu on 2018/12/19.
 */
/*public*/class InAppFloatViewImpl implements Application.ActivityLifecycleCallbacks {
    private Activity mActivity;
    private boolean mActPause = true;
    private FloatViewOperator floatViewOperator;

    private final Map<String, View> floatViews = new LinkedHashMap<>();
    private Context mContext = null;

    /*private*/ OnFloatViewAttached onFloatViewAttached;
    /*private*/ OnFloatViewCreated onFloatViewCreated;
    /*private*/ OnFloatViewDetached onFloatViewDetached;
    /*private*/ OnFloatViewChanged onFloatViewChanged;
    private ISynchronization iSynchronization;

    /*public*/void init(Context context,boolean isMainProcess, ISynchronization iSynchronization) {
//        Log.i("InAppFloatView", "init...");
        if (mContext == null) {
            context = context.getApplicationContext();
            floatViewOperator = new FloatViewOperator(context);
            ((Application) context).registerActivityLifecycleCallbacks(this);
            this.iSynchronization = iSynchronization;
            mContext = context;
            if (iSynchronization != null) {
                iSynchronization.register();
                iSynchronization.setCallback(mISynchronizationCallback);
                if(!isMainProcess)iSynchronization.pull();
            }
        }
    }

    /*public*/LayoutParams getLayoutParams(String tag) {
        View view = getView(tag);
        if (view != null) {
            if (view.getLayoutParams() instanceof LayoutParams) {
                return (LayoutParams) view.getLayoutParams();
            }
        }
        return null;
    }
    /*public*/View getView(String tag){
        if (TextUtils.isEmpty(tag)) throw new IllegalArgumentException("tag empty!");
        return floatViews.get(tag);
    }

    /*public*/void show(int layoutRId, String tag, LayoutParams params, boolean multiProcess, boolean sync) {
        if (TextUtils.isEmpty(tag)) throw new IllegalArgumentException("tag empty!");
        if (params == null) throw new NullPointerException("params!");
        View view = floatViews.get(tag);
        if (view == null) {
            view = floatViewOperator.createView(layoutRId, params, tag);
            params.setMultiProcess( multiProcess);
            floatViews.put(tag, view);
            if (onFloatViewCreated != null) {
                onFloatViewCreated.onCreated(view, tag);
            }
            if (!mActPause && attach(view, tag)) {

            }
            //android.util.Log.i("InAppFloatView","show:"+params);
            //notify show
            if (sync && params.isMultiProcess() && iSynchronization != null) {
                iSynchronization.synchronize(ISynchronization.Command.SHOW, tag, params);
            }
        } else {
            updateInternal(tag,params,view,sync);
        }
    }

    /*public*/void update(String tag, LayoutParams newParams, boolean sync) {
        if (TextUtils.isEmpty(tag)) throw new IllegalArgumentException("tag empty!");
        if (newParams == null) throw new NullPointerException("newParams!");
        View view = floatViews.get(tag);
        if (view != null) {
            updateInternal(tag, newParams, view, sync);
        }
    }

    private void updateInternal(String tag, LayoutParams newParams, View view, boolean sync) {
        LayoutParams params;
        if (view.getLayoutParams() instanceof LayoutParams) {
            params = (LayoutParams) view.getLayoutParams();
        } else {
            params = new LayoutParams();
        }
        if (!params.partialEquals(newParams)) {
            params.copy(newParams);
            view.setLayoutParams(params);
            if(onFloatViewChanged!=null){
                onFloatViewChanged.onChanged(view,tag);
            }
            //notify update
            if (sync && params.isMultiProcess() && iSynchronization != null) {
                iSynchronization.synchronize(ISynchronization.Command.UPDATE, tag, params);
            }
        }
    }

    /*public*/void setVisibility(String tag, int visibility, boolean sync) {
        if (TextUtils.isEmpty(tag)) throw new IllegalArgumentException("tag empty!");
        View view = floatViews.get(tag);
        if (view != null && view.getVisibility() != visibility&& view.getLayoutParams() instanceof LayoutParams ) {
            LayoutParams params=(LayoutParams) view.getLayoutParams();
            if(!params.isRemoved()) {
                view.setVisibility(visibility);
                params.setVisibility(visibility);
                if (sync && iSynchronization != null && params.isMultiProcess()) {
                    iSynchronization.synchronize(visibility == View.VISIBLE ? ISynchronization.Command.SHOW : ISynchronization.Command.HIDE, tag, null);
                }
            }
        }
    }

    /*public*/void remove(String tag, boolean sync) {
        if (TextUtils.isEmpty(tag)) throw new IllegalArgumentException("tag empty!");
        View view = floatViews.get(tag);
        if (view != null) {
            LayoutParams params;
            if (view.getLayoutParams() instanceof LayoutParams) {
                params = (LayoutParams) view.getLayoutParams();
            } else {
                params = new LayoutParams();
            }
            if (!params.isRemoved()) {
                params.setRemoved(true);
                if (detach(view, tag)) {
                    floatViews.remove(tag);
                }
                ///notify remove
                if (sync && params.isMultiProcess() && iSynchronization != null) {
                    iSynchronization.synchronize(ISynchronization.Command.REMOVE, tag, null);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        mActivity = activity;
        mActPause = false;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        mActivity = activity;
        mActPause = false;
        floatViewOperator.updateActivity(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mActivity = activity;
        mActPause = false;
        floatViewOperator.updateActivity(activity);
        attachAllViews(floatViews);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (mActivity == activity) mActPause = true;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (mActivity == activity) mActPause = true;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        detachAllViews(activity);
    }

    private void detachAllViews(Activity activity) {
        List<String> tags = floatViewOperator.detachAllFloatView(activity);
        if (tags != null && onFloatViewDetached != null) {
            for (String tag : tags) {
                final View view=floatViews.get(tag);
                if(view!=null) {
                    if(view.getLayoutParams() instanceof LayoutParams && ((LayoutParams)view.getLayoutParams()).isRemoved()){
                        floatViews.remove(tag);
                    }
                    onFloatViewDetached.onDetached(view, tag);
                }
            }
        }
    }

    private void attachAllViews(Map<String, View> map) {
        FrameLayout content = floatViewOperator.getContent();
        if (content == null) return;
        for (Map.Entry<String, View> entry : map.entrySet()) {
            ViewGroup parent = (ViewGroup) entry.getValue().getParent();
            if (content.equals(parent)) break;//already attach to view  tree
            attach(entry.getValue(), entry.getKey());
        }
    }

    private boolean attach(View view, String tag) {
        detach(view, tag);
        ViewGroup.LayoutParams params = null;
        final boolean ret = (params = view.getLayoutParams()) instanceof LayoutParams
                && !((LayoutParams) params).isRemoved() && floatViewOperator.attach(view, tag);
        if (ret && onFloatViewAttached != null) onFloatViewAttached.onAttached(mActivity, view, tag);
        return ret;
    }

    private boolean detach(View view, String tag) {
        final boolean ret = view != null && floatViewOperator.detach(view);
        if (ret && onFloatViewDetached != null) onFloatViewDetached.onDetached(view, tag);
        return ret;
    }

    private void handlePull() {
        if (iSynchronization == null) return;
        final ArrayList<SyncData> list = new ArrayList<>(floatViews.size());
        for (Map.Entry<String, View> entry : floatViews.entrySet()) {
            list.add(new SyncData(entry.getKey(), (LayoutParams) entry.getValue().getLayoutParams()));
        }
        iSynchronization.pullAck(list);
    }

    private void handlePullAck(ArrayList<SyncData> data) {
        if (!floatViews.isEmpty()) {
            for (String tag : floatViews.keySet()) {
                remove(tag, false);
            }
            floatViews.clear();
        }
        for (SyncData syncData : data) {
            final LayoutParams params = syncData.layoutParams;
            show(params.layoutId, syncData.key, params, params.isMultiProcess(), false);
        }
    }

    private void handle(ISynchronization.Command cmd, String tag, LayoutParams params) {
        switch (cmd) {
            case HIDE:
                setVisibility(tag, View.GONE,false);
                break;
            case SHOW:
                if(params==null){
                    setVisibility(tag,View.VISIBLE,false);
                }else {
                    show(params.layoutId, tag, params, params.isMultiProcess(), false);
                }
                break;
            case REMOVE:
                remove(tag, false);
                break;
            case UPDATE:
                update(tag, params, false);
                break;
        }
    }

    private final ISynchronization.Callback mISynchronizationCallback = new ISynchronization.Callback() {
        @Override
        public void onEvent(ISynchronization.Command cmd, ArrayList<SyncData> data) {
            //android.util.Log.i("InAppFloatView","onEvent:cmd="+cmd+","+data);
            switch (cmd) {
                case PULL:
                    handlePull();
                    break;
                case PULL_ACK:
                    handlePullAck(data);
                    break;
                default:
                    String tag = null;
                    LayoutParams params = null;
                    if (data != null && data.size() > 0) {
                        tag = data.get(0).key;
                        params = data.get(0).layoutParams;
                    }
                    handle(cmd, tag, params);
                    break;
            }
        }
    };
}
