package com.obaniu.inappfloatview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by obaniu on 2018/12/19.
 */
/*public*/ class FloatViewOperator {
    private Activity activity;
    private FrameLayout content;
    private final LayoutInflater inflater;

    FloatViewOperator(Context context) {
        inflater = LayoutInflater.from(context);
    }

    void updateActivity(Activity activity) {
        if (activity == null) {
            this.activity = null;
            content = null;
        } else if (!activity.equals(this.activity)) {
            this.activity = activity;
            content = getContentView(activity);
        }
    }

    View createView(int layoutRId, LayoutParams params, String tag) {
        View view = inflater.inflate(layoutRId, null);
        view.setLayoutParams(params);
        view.setVisibility(params.getVisibility());
        view.setTag(R.id.in_app_float_view_tag, tag);
        params.layoutId = layoutRId;
        return view;
    }

    boolean attach(View view, String tag) {
        if (content != null && findView(tag) == null) {
            content.addView(view);
            return true;
        }
        return false;
    }

    FrameLayout getContent() {
        return content;
    }

    boolean detach(View view) {
        if (view.getParent() instanceof FrameLayout) {
            ((FrameLayout) view.getParent()).removeView(view);
            return true;
        }
        return false;
    }

    List<String> detachAllFloatView(Activity activity) {
        final FrameLayout layout = getContentView(activity);
        if (layout == null) return null;
        List<String> list = new ArrayList<>(layout.getChildCount());
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            Object tag = view.getTag(R.id.in_app_float_view_tag);
            if (tag instanceof String) {
                layout.removeView(view);
                list.add((String) tag);
            }
        }
        return list;
    }

    View findView(String tag) {
        return content == null ? null : findView(content, tag);
    }

    private FrameLayout getContentView(Activity activity) {
        if (activity == null) return null;
        View view = activity.findViewById(android.R.id.content);
        if (view == null) {
            view = activity.getWindow().getDecorView();
        }
        while (!(view instanceof FrameLayout)) {
            view = (View) view.getParent();
        }
        return view instanceof FrameLayout ? (FrameLayout) view : null;
    }

    private View findView(FrameLayout layout, String tag) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);
            Object t = view.getTag(R.id.in_app_float_view_tag);
            if (t instanceof String && tag.equals(t)) {
                return view;
            }
        }
        return null;
    }
}
