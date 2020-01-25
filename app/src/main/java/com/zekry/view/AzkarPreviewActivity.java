package com.zekry.view;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zekry.R;
import com.zekry.model.Zekr;
import com.zekry.utils.DatabaseHelper;
import com.zekry.utils.OnSwipeTouchListener;

import java.util.LinkedList;

public class AzkarPreviewActivity extends AppCompatActivity {

    private TextView TxtTitle;
    private TextView TxtZekr;
    private TextView TxtNum;
    private LinkedList<Zekr> list = new LinkedList<>();
    private DatabaseHelper db;
    private Zekr zekr;
    private int index_zekr = 0;
    private int num = 1;
    private ImageButton BtnNext;
    private ImageButton BtnPrev;
    private ImageButton BtnSkip;
    private LinearLayout ZekrContainer;
    private float max_size = 100;
    private float min_size = 20;
    private float currentSize = 20;
    private ImageButton BtnZoomIn;
    private ImageButton BtnZoomOut;
    private ScrollView ScrollLay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azkar_preview);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);

        //init views
        initViews();

        Intent intent = getIntent();
        TxtTitle.setText(intent.getStringExtra("azkar"));
        list = db.getAllZekr(intent.getStringExtra("table"));
        zekr = list.get(index_zekr);
        TxtZekr.setText(zekr.getZekr());
        TxtNum.setText(num + " / " + zekr.getNum() + "");

        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipNext();
                Animation hyperspaceJump = AnimationUtils.loadAnimation(AzkarPreviewActivity.this, R.anim.slide_in_right);
                TxtZekr.startAnimation(hyperspaceJump);
            }
        });

        BtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipPrev();
                Animation hyperspaceJump = AnimationUtils.loadAnimation(AzkarPreviewActivity.this, R.anim.slide_in_left);
                TxtZekr.startAnimation(hyperspaceJump);
            }
        });

        BtnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skip();
                Animation hyperspaceJump = AnimationUtils.loadAnimation(AzkarPreviewActivity.this, R.anim.slide_in_right);
                TxtZekr.startAnimation(hyperspaceJump);
            }
        });

        BtnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomIn();
            }
        });

        BtnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOut();
            }
        });

        ZekrContainer.setOnTouchListener(new OnSwipeTouchListener(AzkarPreviewActivity.this){
            @Override
            public void onSwipeLeft() {
                Animation hyperspaceJump = AnimationUtils.loadAnimation(AzkarPreviewActivity.this, R.anim.slide_in_right);
                TxtZekr.startAnimation(hyperspaceJump);
                // change text
                swipNext();
            }

            @Override
            public void onSwipeRight() {
                Animation hyperspaceJump = AnimationUtils.loadAnimation(AzkarPreviewActivity.this, R.anim.slide_in_left);
                TxtZekr.startAnimation(hyperspaceJump);
                // change text
                swipPrev();
            }
        });

        TxtZekr.setOnTouchListener(new OnSwipeTouchListener(AzkarPreviewActivity.this){
            @Override
            public void onSwipeLeft() {
                Animation hyperspaceJump = AnimationUtils.loadAnimation(AzkarPreviewActivity.this, R.anim.slide_in_right);
                TxtZekr.startAnimation(hyperspaceJump);
                // change text
                swipNext();
            }

            @Override
            public void onSwipeRight() {
                Animation hyperspaceJump = AnimationUtils.loadAnimation(AzkarPreviewActivity.this, R.anim.slide_in_left);
                TxtZekr.startAnimation(hyperspaceJump);
                // change text
                swipPrev();
            }
        });


        ScrollLay.setOnTouchListener(new OnSwipeTouchListener(AzkarPreviewActivity.this){
            @Override
            public void onSwipeLeft() {
                Animation hyperspaceJump = AnimationUtils.loadAnimation(AzkarPreviewActivity.this, R.anim.slide_in_right);
                TxtZekr.startAnimation(hyperspaceJump);
                // change text
                swipNext();
            }

            @Override
            public void onSwipeRight() {
                Animation hyperspaceJump = AnimationUtils.loadAnimation(AzkarPreviewActivity.this, R.anim.slide_in_left);
                TxtZekr.startAnimation(hyperspaceJump);
                // change text
                swipPrev();
            }
        });

    }

    private void swipNext() {
        if(num >= zekr.getNum()){
            incrementIndex();
            zekr = list.get(index_zekr);
            TxtZekr.setText(zekr.getZekr());
            num = 1;
            TxtNum.setText(num + " / " + zekr.getNum() + "");
        }else {
            num++;
            TxtNum.setText(num + " / " + zekr.getNum() + "");
        }
    }


    private void skip(){
        incrementIndex();
        zekr = list.get(index_zekr);
        TxtZekr.setText(zekr.getZekr());
        num = 1;
        TxtNum.setText(num + " / " + zekr.getNum() + "");
    }

    private void swipPrev() {
        num = 1;
        decrementIndex();
        zekr = list.get(index_zekr);
        TxtZekr.setText(zekr.getZekr());
        num = 1;
        TxtNum.setText(num + " / " + zekr.getNum() + "");
    }

    private void incrementIndex() {
        if (index_zekr >= list.size() - 1){

        }else{
            index_zekr++;
        }
    }

    private void decrementIndex() {
        if (index_zekr > 0){
            index_zekr--;
        }else{

        }
    }

    private void initViews() {
        TxtTitle = findViewById(R.id.txt_title);
        TxtZekr = findViewById(R.id.txt_zekr);
        TxtNum = findViewById(R.id.txt_num);
        BtnNext = findViewById(R.id.btn_next);
        BtnPrev = findViewById(R.id.btn_prev);
        BtnSkip = findViewById(R.id.btn_skip);

        ZekrContainer = findViewById(R.id.zekr_container);

        BtnZoomIn = findViewById(R.id.btn_zoom_in);
        BtnZoomOut = findViewById(R.id.btn_zoom_out);

        ScrollLay = findViewById(R.id.scroll_lay);

        TxtZekr.setTextSize(min_size);

    }


    private void zoomIn(){

        if (TxtZekr.getTextSize() < max_size){
            TxtZekr.setTextSize(currentSize + 1);
            currentSize++;
        }
    }

    private void zoomOut(){

        if (TxtZekr.getTextSize() > min_size){
            TxtZekr.setTextSize(currentSize - 1);
            currentSize--;
        }
    }
}