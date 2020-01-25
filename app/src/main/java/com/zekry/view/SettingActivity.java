package com.zekry.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zekry.R;

import java.util.Locale;

public class SettingActivity extends AppCompatActivity {

    private Locale locale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();

        Button BtinLang = findViewById(R.id.btn_language);

        BtinLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                LayoutInflater inflater = LayoutInflater.from(SettingActivity.this);
                View view = inflater.inflate(R.layout.language_select, null);
                builder.setView(view);
                Button BtnAr = view.findViewById(R.id.btn_ar);
                Button BtnEng = view.findViewById(R.id.btn_en);

                BtnAr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setLocale("ar");
                        startActivity(new Intent(SettingActivity.this,SettingActivity.class));
                        finish();
                    }
                });

                BtnEng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setLocale("en");
                        startActivity(new Intent(SettingActivity.this,SettingActivity.class));
                        finish();
                    }
                });



                builder.show();
            }
        });


        Button BtnTheme = findViewById(R.id.btn_theme);
        BtnTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, getResources().getString(R.string.coming_soon), Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void setLocale(String localeName){

        locale = new Locale(localeName);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        res.updateConfiguration(config, dm);

        saveLanguage(localeName);
    }


    private void saveLanguage(String language){
        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("lang", language);
        editor.commit();
    }

}
