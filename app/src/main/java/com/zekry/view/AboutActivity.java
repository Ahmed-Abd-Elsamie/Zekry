package com.zekry.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zekry.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();

        TextView txt_url = findViewById(R.id.txt_url);

        txt_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open browser
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://iamahmed.dx.am/")));
            }
        });

    }
}
