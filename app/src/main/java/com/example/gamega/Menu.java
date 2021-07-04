package com.example.gamega;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity
{
    private ImageButton settings;
    Dialog settings_dialog;
    int sum;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        settings=findViewById(R.id.settings_btn);
        settings_dialog =new Dialog(this);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                settings_dialog.setContentView(R.layout.settings);
                settings_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                settings_dialog.show();
            }
        });

        Button Score_Btn =findViewById(R.id.ScoreBtn);
        Score_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu.this, ScoreBoard.class);
                startActivity(intent);
            }
        });

        Button sGame=findViewById(R.id.StartBtn);
        sGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu.this, LevelsPath.class);
                startActivity(intent);
            }
        });


        Button ourStory=findViewById(R.id.ourStoryBtn);
        ourStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu.this, OurStory.class);
                startActivity(intent);
            }
        });

        ImageButton store=findViewById(R.id.storeBtn);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Menu.this, Store.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        sp = getSharedPreferences("Details",0);
        sum = sp.getInt("sum",0);


        TextView score_Lable=(TextView) findViewById(R.id.tv_coins);
        score_Lable.setText(""+ sum);

    }
}
