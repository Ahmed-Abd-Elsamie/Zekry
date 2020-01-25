package com.zekry.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageButton;

import com.zekry.R;
import com.zekry.adapters.AlarmAdapter;
import com.zekry.model.Alarm;
import com.zekry.services.LoadAlarmsReceiver;
import com.zekry.services.LoadAlarmsService;
import com.zekry.utils.AlarmUtils;
import com.zekry.utils.EmptyRecyclerView;

import java.util.ArrayList;

public class AlarmActivity  extends AppCompatActivity implements LoadAlarmsReceiver.OnAlarmsLoadedListener {


    private LoadAlarmsReceiver mReceiver;
    private AlarmAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getSupportActionBar().setTitle(R.string.my_alarms);

        mReceiver = new LoadAlarmsReceiver(this);


        final EmptyRecyclerView rv = (EmptyRecyclerView) findViewById(R.id.recycler_food);
        mAdapter = new AlarmAdapter();
        rv.setEmptyView(findViewById(R.id.empty_view));
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(new LinearLayoutManager(AlarmActivity.this));
        rv.setItemAnimator(new DefaultItemAnimator());

        ImageButton fab = (ImageButton) findViewById(R.id.btn_add_food);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmUtils.checkAlarmPermissions(AlarmActivity.this);
                final Intent i = new Intent(AlarmActivity.this, AddEditAlarmActivity.class);
                i.putExtra("mode_extra", 2);
                startActivity(i);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter(LoadAlarmsService.ACTION_COMPLETE);
        LocalBroadcastManager.getInstance(AlarmActivity.this).registerReceiver(mReceiver, filter);
        LoadAlarmsService.launchLoadAlarmsService(AlarmActivity.this);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(AlarmActivity.this).unregisterReceiver(mReceiver);
    }

    @Override
    public void onAlarmsLoaded(ArrayList<Alarm> alarms) {
        mAdapter.setAlarms(alarms);
    }


}