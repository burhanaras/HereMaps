package com.burhan.here.presentation.view.map.listener;

import android.content.Context;
import android.graphics.PointF;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.burhan.here.R;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;

import java.util.List;

/**
 * Created by BURHAN on 2/5/2017.
 */
public class HereMapsGestureListener implements MapGesture.OnGestureListener {
    private static final String TAG = HereMapsGestureListener.class.getName();
    private Context mContext;
    private View root;

    public HereMapsGestureListener(Context context, View root) {
        mContext = context;
        this.root = root;
    }

    @Override
    public void onPanStart() {

    }

    @Override
    public void onPanEnd() {

    }

    @Override
    public void onMultiFingerManipulationStart() {

    }

    @Override
    public void onMultiFingerManipulationEnd() {

    }

    @Override
    public boolean onMapObjectsSelected(List<ViewObject> list) {
        Log.d(TAG, "onMapObjectsSelected() called with: list = [" + list.size() + "]");
        for (ViewObject viewObj : list) {
            if (viewObj.getBaseType() == ViewObject.Type.USER_OBJECT) {
                if (((MapObject) viewObj).getType() == MapObject.Type.MARKER) {
                    String title = ((MapMarker) viewObj).getDescription();

                    Snackbar snackbar = Snackbar
                            .make(root, title, Snackbar.LENGTH_LONG)
                            .setAction(mContext.getString(R.string.navigate_me), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar snackbar1 = Snackbar.make(root, mContext.getString(R.string.not_implemented_yet), Snackbar.LENGTH_SHORT);
                                    snackbar1.show();
                                }
                            });

                    snackbar.show();
                }
            }
        }
        return false;
    }

    @Override
    public boolean onTapEvent(PointF pointF) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(PointF pointF) {
        return false;
    }

    @Override
    public void onPinchLocked() {

    }

    @Override
    public boolean onPinchZoomEvent(float v, PointF pointF) {
        return false;
    }

    @Override
    public void onRotateLocked() {

    }

    @Override
    public boolean onRotateEvent(float v) {
        return false;
    }

    @Override
    public boolean onTiltEvent(float v) {
        return false;
    }

    @Override
    public boolean onLongPressEvent(PointF pointF) {
        return false;
    }

    @Override
    public void onLongPressRelease() {

    }

    @Override
    public boolean onTwoFingerTapEvent(PointF pointF) {
        return false;
    }
}
