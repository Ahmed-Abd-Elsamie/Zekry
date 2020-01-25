package com.zekry.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.support.annotation.IntDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.zekry.R;
import com.zekry.database.AlarmDatabaseHelper;
import com.zekry.model.Alarm;
import com.zekry.model.Zekr;
import com.zekry.services.AlarmReceiver;
import com.zekry.services.LoadAlarmsService;
import com.zekry.utils.DatabaseHelper;
import com.zekry.utils.ViewUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button BtnAR;
    private Button BtnEN;
    private ImageButton BtnMenu;
    private ImageButton BtnAlarm;
    private String currentLanguage = "ar";
    private Locale locale;

    //default tables names
    /*private String TABLE_SABAH = "azkar_sabah_tb";
    private String TABLE_MASA = "azkar_masa_tb";
    private String TABLE_AZAN= "azkar_azan_tb";
    private String TABLE_BATHROOM = "azkar_bathroom_tb";
    private String TABLE_DEAD = "azkar_dead_tb";
    private String TABLE_EAT = "azkar_eat_tb";
    private String TABLE_SALAH = "azkar_salah_tb";
    private String TABLE_SLEEP = "azkar_sleep_tb";
    private String TABLE_TASBEH = "azkar_tasbeh_tb";
    private String TABLE_WAKE = "azkar_wake_tb";
    private String TABLE_WODOO = "azkar_wodoo_tb";*/

    //english tables names
    /*private String TABLE_SABAH_EN = "azkar_sabah_tb";
    private String TABLE_MASA_EN = "azkar_masa_tb";
    private String TABLE_AZAN_EN = "azkar_azan_tb";
    private String TABLE_BATHROOM_EN = "azkar_bathroom_tb";
    private String TABLE_DEAD_EN = "azkar_dead_tb";
    private String TABLE_EAT_EN = "azkar_eat_tb";
    private String TABLE_SALAH_EN = "azkar_salah_tb";
    private String TABLE_SLEEP_EN = "azkar_sleep_tb";
    private String TABLE_TASBEH_EN = "azkar_tasbeh_tb";
    private String TABLE_WAKE_EN = "azkar_wake_tb";
    private String TABLE_WODOO_EN = "azkar_wodoo_tb";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        currentLanguage = getLanguage();
        // splash screen
        setContentView(R.layout.splash_screen);
        startSplashScreen();

    }

    private void startSplashScreen() {
        new CountDownTimer(3000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                // select language
                if (currentLanguage == null){
                    setContentView(R.layout.language_select);
                    BtnAR = findViewById(R.id.btn_ar);
                    BtnEN  = findViewById(R.id.btn_en);

                    BtnAR.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setLocale("ar");
                            // create default alarms
                            createDefaultAlarms();
                            // init
                            init();
                        }
                    });

                    BtnEN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setLocale("en");
                            // create default alarms
                            createDefaultAlarms();
                            // init
                            init();
                        }
                    });
                }else {
                    // init
                    setLocale(currentLanguage);
                    init();
                }


            }
        }.start();
    }

    private void createDefaultAlarms() {
        saveAlarms(30, 6, getResources().getString(R.string.azkar_sabah));
        saveAlarms(0, 16, getResources().getString(R.string.azkar_masa));
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        setContentView(R.layout.activity_main);

        Button BtnAzkarSabah = findViewById(R.id.btn_sabah);
        Button BtnAzkarMasa = findViewById(R.id.btn_masa);
        Button BtnAzkarAzan = findViewById(R.id.btn_azan);
        Button BtnAzkarBath = findViewById(R.id.btn_bathroom);
        Button BtnAzkarDead = findViewById(R.id.btn_dead);
        Button BtnAzkarEat = findViewById(R.id.btn_eat);
        Button BtnAzkarSalah = findViewById(R.id.btn_salah);
        Button BtnAzkarSleep = findViewById(R.id.btn_sleep);
        Button BtnAzkarTasbeh = findViewById(R.id.btn_tasbeh);
        Button BtnAzkarWake = findViewById(R.id.btn_wake);
        Button BtnAzkarWodoo = findViewById(R.id.btn_wodoo);

        BtnMenu = findViewById(R.id.btn_menu);
        BtnAlarm = findViewById(R.id.btn_alarm);


        BtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.menu_view, null);
                builder.setView(view);
                Button help = view.findViewById(R.id.btn_help);
                Button setting = view.findViewById(R.id.btn_setting);
                Button share = view.findViewById(R.id.btn_share);
                Button about = view.findViewById(R.id.btn_about);

                about.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                    }
                });

                setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    }
                });

                help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, HelpActivity.class));
                    }
                });


                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String body = "https://play.google.com/store/apps/details?id=com.zekry";
                        String subject = "Azkary";
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, body);
                        startActivity(Intent.createChooser(shareIntent, "Share to"));

                    }
                });

                builder.show();

            }
        });

        BtnAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AlarmActivity.class));
            }
        });




        BtnAzkarSabah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.azkar_sabah));
                intent.putExtra("table", getResources().getString(R.string.TABLE_SABAH));
                startActivity(intent);
            }
        });

        BtnAzkarMasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.azkar_masa));
                intent.putExtra("table", getResources().getString(R.string.TABLE_MASA));
                startActivity(intent);

            }
        });

        BtnAzkarAzan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.azan));
                intent.putExtra("table", getResources().getString(R.string.TABLE_AZAN));
                startActivity(intent);

            }
        });
        BtnAzkarBath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.bathroom));
                intent.putExtra("table", getResources().getString(R.string.TABLE_BATHROOM));
                startActivity(intent);

            }
        });
        BtnAzkarDead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.dead));
                intent.putExtra("table", getResources().getString(R.string.TABLE_DEAD));
                startActivity(intent);

            }
        });
        BtnAzkarEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.eat));
                intent.putExtra("table", getResources().getString(R.string.TABLE_EAT));
                startActivity(intent);

            }
        });
        BtnAzkarSalah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.salah));
                intent.putExtra("table", getResources().getString(R.string.TABLE_SALAH));
                startActivity(intent);

            }
        });
        BtnAzkarSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.sleep));
                intent.putExtra("table", getResources().getString(R.string.TABLE_SLEEP));
                startActivity(intent);

            }
        });
        BtnAzkarTasbeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.tasbeh));
                intent.putExtra("table", getResources().getString(R.string.TABLE_TASBEH));
                startActivity(intent);

            }
        });
        BtnAzkarWake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.wake));
                intent.putExtra("table", getResources().getString(R.string.TABLE_WAKE));
                startActivity(intent);

            }
        });
        BtnAzkarWodoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AzkarPreviewActivity.class);
                intent.putExtra("azkar", getResources().getString(R.string.wodoo));
                intent.putExtra("table", getResources().getString(R.string.TABLE_WODOO));
                startActivity(intent);

            }
        });



    }




    private void saveLanguage(String language){
        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("lang", language);
        editor.commit();
    }

    private String getLanguage(){
        SharedPreferences sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String language = sharedPref.getString("lang", null);
        return language;
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

    @Override
    protected void onResume() {
        super.onResume();
        // check language
        if (currentLanguage != null){
            if (!currentLanguage.equals(getLanguage())){
                currentLanguage = getLanguage();
                setLocale(currentLanguage);
                // refresh
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finish();
            }

        }


    }

    private void saveAlarms(int Min, int Hur, String lable) {

        final Alarm alarm = getAlarm();

        final Calendar time = Calendar.getInstance();
        time.set(Calendar.MINUTE, Min);
        time.set(Calendar.HOUR_OF_DAY, Hur);
        alarm.setTime(time.getTimeInMillis());


        alarm.setLabel(lable);


        alarm.setDay(Alarm.MON,true);
        alarm.setDay(Alarm.TUES,true);
        alarm.setDay(Alarm.WED, true);
        alarm.setDay(Alarm.THURS, true);
        alarm.setDay(Alarm.FRI, true);
        alarm.setDay(Alarm.SAT, true);
        alarm.setDay(Alarm.SUN, true);

        final int rowsUpdated = AlarmDatabaseHelper.getInstance(MainActivity.this).updateAlarm(alarm);
        final int messageId = (rowsUpdated == 1) ? R.string.update_complete : R.string.update_failed;

        Toast.makeText(MainActivity.this, messageId, Toast.LENGTH_SHORT).show();
        AlarmReceiver.setReminderAlarm(MainActivity.this, alarm);

    }

    private Alarm getAlarm(){
        final long id = AlarmDatabaseHelper.getInstance(this).addAlarm();
        LoadAlarmsService.launchLoadAlarmsService(this);
        return new Alarm(id);
    }

}