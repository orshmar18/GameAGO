package com.example.gamega;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LevelsPath extends Activity {

    int sum;
    int score;

    ImageButton level2B,level3B,level4B;
    ImageButton level2G,level3G,level4G;

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);

        sp = getSharedPreferences("Details",0);
        sum = sp.getInt("sum",0);
        TextView score_Lable=(TextView) findViewById(R.id.scoreLabel);
        score_Lable.setText(""+ sum);

        SharedPreferences sharedPreferences=getSharedPreferences("FIRST_PLACE",MODE_PRIVATE);
        score=sharedPreferences.getInt("FIRST_SCORE",0);


        level2B=findViewById(R.id.level2b);
        level3B=findViewById(R.id.level3b);
        level4B=findViewById(R.id.level4b);

        level2G=findViewById(R.id.level2g);
        level3G=findViewById(R.id.level3g);
        level4G=findViewById(R.id.level4g);


        ImageButton level_1 = findViewById(R.id.level1);
        level_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LevelsPath.this, Game.class);
                startActivity(intent);
            }
        });

        //SharedPreferences Level = getSharedPreferences("LevelScore",MODE_PRIVATE);
        //SharedPreferences.Editor editor2 = Level.edit();

        if (score >=100){
            level2B.setVisibility(View.INVISIBLE);
            level2G.setVisibility(View.VISIBLE);
            ImageButton level_2 = findViewById(R.id.level2g);
            level_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //editor2.putInt("LevelScore",100);
                    //editor2.putBoolean("TorF",true);
                    //editor2.commit();
                    Intent intent = new Intent(LevelsPath.this, Game.class);
                    startActivity(intent);
                }
            });
        }

        if (score >=200){
            level3B.setVisibility(View.INVISIBLE);
            level3G.setVisibility(View.VISIBLE);
            ImageButton level_3 = findViewById(R.id.level3g);
            level_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
              //      editor2.putInt("LevelScore",200);
          //          editor2.putBoolean("TorF",true);
            //        editor2.commit();
                    Intent intent = new Intent(LevelsPath.this, Game.class);
                    startActivity(intent);
                }
            });
        }


        if (score >=300){
            level4B.setVisibility(View.INVISIBLE);
            level4G.setVisibility(View.VISIBLE);
            ImageButton level_4 = findViewById(R.id.level4g);
            level_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //editor2.putInt("LevelScore",300);
                //    editor2.putBoolean("TorF",true);
                  //  editor2.commit();
                    Intent intent = new Intent(LevelsPath.this, Game.class);
                    startActivity(intent);
                }
            });
        }



        ImageButton back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    AlertDialog.Builder builder=new AlertDialog.Builder(LevelsPath.this);
                View dialogView=getLayoutInflater().inflate(R.layout.levels,null);

                builder.setView(dialogView).setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(LevelsPath.this,"anat pitimson",Toast.LENGTH_SHORT).show();
                    }
                }).show();*/


                Intent intent = new Intent(LevelsPath.this, Menu.class);
                startActivity(intent);
            }
        });
    }
}