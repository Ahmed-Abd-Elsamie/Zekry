package com.zekry.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zekry.model.Alarm;

import java.util.ArrayList;

public class LoadAlarmsReceiver  extends BroadcastReceiver {

    private OnAlarmsLoadedListener mListener;

    @SuppressWarnings("unused")
    public LoadAlarmsReceiver(){}

    public LoadAlarmsReceiver(OnAlarmsLoadedListener listener){
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final ArrayList<Alarm> alarms =
                intent.getParcelableArrayListExtra(LoadAlarmsService.ALARMS_EXTRA);
        mListener.onAlarmsLoaded(alarms);
    }

    public void setOnAlarmsLoadedListener(OnAlarmsLoadedListener listener) {
        mListener = listener;
    }

    public interface OnAlarmsLoadedListener {
        void onAlarmsLoaded(ArrayList<Alarm> alarms);
    }

}
