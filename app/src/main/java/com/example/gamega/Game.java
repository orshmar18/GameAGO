package com.example.gamega;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;


public class Game extends LevelsPath {

    private boolean touch = false;

    private boolean level2 = false, level3 = false, level4 = false;

    private ImageView piki;
    private AnimationDrawable pikiAnimation;
    private int pikiY,pikiSpeed,pikiSize;

    private ImageView life1,life2,life3, life44;
    private Boolean buy_life;

    private Boolean buyTimer;

    private Sound sound;

    private int screenHeight,screenWidth,frameHeight;

    private TextView pauseText,levelUp;

    private ImageView coin;
    private AnimationDrawable coinAnimation;
    private int coinX,coinY,coinSpeed;

    private ImageView coin2;
    private AnimationDrawable coin2Animation;
    private int coin2X,coin2Y,coin2Speed;

    private ImageView ground_bad;
    private AnimationDrawable ground_bad_Animation;
    private int ground_bad_X,ground_bad_Y,ground_bad_Speed;

    private ImageView toto;
    private AnimationDrawable totoAnimation;
    private int totoX,totoY,totoSpeed;

    private ImageView dragon;
    private AnimationDrawable dragonAnimation;
    private int dragonX,dragonY,dragonSpeed;

    private Timer timer = new Timer();
    private Handler handler = new Handler();

    private Button tap_to_start;
    private ImageView tapPic;
    private AnimationDrawable tapAnimation;
    private ImageButton pause_Btn;
    private FrameLayout pause_Frame;
    private ImageButton homeBtn,muteBtn,unMuteBtn,playBtn;
    private boolean volume=true;

    private TextView timer_text_view;

    private TextView scoreTV;
    int score=0;
    //int num;
    //boolean TorFPath;

    int progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        //SharedPreferences LevelsCoin = getSharedPreferences("LevelScore",0);
        //num = LevelsCoin.getInt("LevelScore",0);
        //TorFPath=LevelsCoin.getBoolean("TorF",false);

        timer_text_view=findViewById(R.id.timerTextView);

        SharedPreferences buylife4=getSharedPreferences("Life4",MODE_PRIVATE);
        buy_life=buylife4.getBoolean("Life4",false);

        SharedPreferences buyTimer_=getSharedPreferences("time",MODE_PRIVATE);
        buyTimer=buyTimer_.getBoolean("time",false);

        sound = new Sound(this);

        pauseText=findViewById(R.id.paused_pic);
        levelUp=findViewById(R.id.win_pic);

        tapPic=findViewById(R.id.finger_tap);
        tapAnimation = (AnimationDrawable) tapPic.getDrawable();
        tapAnimation.start();

        tap_to_start = findViewById(R.id.tap_to_start_btn);
        tap_to_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tap_to_start.setVisibility(View.GONE);
                pause_Btn.setVisibility(View.VISIBLE);
                tapAnimation.stop();
                tapPic.setVisibility(View.GONE);
                totoY=-500;
                dragonY =-500;
                ground_bad_Y=-500;



                if(buyTimer)
                {
                    buyTimer=false;
                    SharedPreferences.Editor editor=buyTimer_.edit();
                    editor.putBoolean("time",buyTimer);
                    editor.commit();

                    timer_text_view.setVisibility(View.VISIBLE);
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            changeSpeed(200);
                            progress= Integer.parseInt(timer_text_view.getText().toString());
                            progress--;
                            if(progress==0)
                            {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        timer_text_view.setVisibility(View.INVISIBLE);
                                        changeSpeed(100);//change to the level speed
                                    }
                                });
                            }
                            else
                            {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        timer_text_view.setText(progress+"");
                                    }
                                });
                            }
                        }
                    },1000,1000);
                }

                coin.setVisibility(View.VISIBLE);
                coin2.setVisibility(View.VISIBLE);
                ground_bad.setVisibility(View.VISIBLE);
                pikiAnimation.start();
                ground_bad_Animation.start();
                coinAnimation.start();
                coin2Animation.start();

                FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
                frameHeight = frame.getHeight();

                pikiY = (int) piki.getY();
                pikiSize = piki.getHeight();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                moveObjects();
                            }
                        });
                    }
                }, 0, 20);
            }
        });

        piki = findViewById(R.id.piki);
        pikiAnimation = (AnimationDrawable) piki.getDrawable();

        coin = findViewById(R.id.coin1);
        coinAnimation = (AnimationDrawable) coin.getDrawable();

        coin2 = findViewById(R.id.coin2);
        coin2Animation = (AnimationDrawable) coin2.getDrawable();

        ground_bad = findViewById(R.id.angry1);
        ground_bad_Animation = (AnimationDrawable) ground_bad.getDrawable();

        toto = findViewById(R.id.angry2);
        totoAnimation = (AnimationDrawable) toto.getDrawable();

        dragon =findViewById(R.id.angry3);
        dragonAnimation =(AnimationDrawable) dragon.getDrawable();

        life44 =findViewById(R.id.heart_four);
        life3 = findViewById(R.id.heart_one);
        life2 = findViewById(R.id.heart_two);
        life1 = findViewById(R.id.heart_three);

        if(buy_life)
        {
            life44.setVisibility(View.VISIBLE);

            SharedPreferences.Editor editor=buylife4.edit();
            buy_life=false;
            editor.putBoolean("Life4",buy_life);
            editor.commit();
        }

        scoreTV = (TextView) findViewById(R.id.score_tv);

        pause_Frame = findViewById(R.id.pauseFrame);

        pause_Btn = findViewById(R.id.pauseBtn);
        pause_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    timer.cancel();
                    timer = null;
                    pikiAnimation.stop();
                    coinAnimation.stop();
                    coin2Animation.stop();
                    totoAnimation.stop();
                    dragonAnimation.stop();
                    ground_bad_Animation.stop();
                    visibility(false);
                    pause_Frame.setVisibility(View.VISIBLE);
                    pauseText.setVisibility(View.VISIBLE);
            }
        });

        unMuteBtn=findViewById(R.id.mute);
        unMuteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(volume==false) {
                    volume = true;
                    muteBtn.setVisibility(View.VISIBLE);
                    unMuteBtn.setVisibility(View.INVISIBLE);
                }
            }
        });

        muteBtn=findViewById(R.id.umMute);
        muteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(volume==true) {
                    volume = false;
                    muteBtn.setVisibility(View.INVISIBLE);
                    unMuteBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        homeBtn = findViewById(R.id.home_btn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game.this, Menu.class);
                startActivity(intent);

            }
        });

        playBtn = findViewById(R.id.play_btn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause_Frame.setVisibility(View.INVISIBLE);
                play();
            }
        });

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenHeight = size.y;
        screenWidth = size.x;

        pikiSpeed = Math.round(screenHeight / 80);
        changeSpeed(150);

    }


    public void moveObjects() {

        coinX -= coinSpeed;
        if (coinX < 0) {
            coinX = screenWidth + 20;
            coinY = (int) Math.floor(Math.random() * (frameHeight - coin.getHeight()));
        }
        coin.setX(coinX);
        coin.setY(coinY);


        coin2X -= coin2Speed;
        if (coin2X < 0) {
            coin2X = screenWidth + 500;
            coin2Y = (int) Math.floor(Math.random() * (frameHeight - coin2.getHeight()));
        }
        coin2.setX(coin2X);
        coin2.setY(coin2Y);

        ground_bad_X -= ground_bad_Speed;
        if (ground_bad_X < 0) {
            ground_bad_X = screenWidth + 300;
            ground_bad_Y = (int) Math.floor(Math.random() * (frameHeight - ground_bad.getHeight()));
        }
        ground_bad.setX(ground_bad_X);
        ground_bad.setY(ground_bad_Y);

        if(level2) {
            totoX -= totoSpeed;
            if (totoX < 0) {
                totoX = screenWidth + 100;
                totoY = (int) Math.floor(Math.random() * (frameHeight - toto.getHeight()));
            }
            toto.setX(totoX);
            toto.setY(totoY);
        }

        if(level3) {
            dragonX -= dragonSpeed;
            if (dragonX < 0) {
                dragonX = screenWidth + 500;
                dragonY = (int) Math.floor(Math.random() * (frameHeight - dragon.getHeight()));
            }
            dragon.setX(dragonX);
            dragon.setY(dragonY);
        }


        if (score >= 100) {
            if(level2==false)
            {
                level2=true;
                levelUp();
                changeSpeed(120);
            }
        }

        if (score >= 200) {
            if(level3==false)
            {
                level3=true;
                levelUp();
                changeSpeed(100);
            }
        }

        if (score >= 300) {
            if(level4==false)
            {
                level4=true;
                levelUp();
                changeSpeed(80);
            }
        }

        if(score==320)
        {
            timer.cancel();
            timer = null;
            Intent intent= new Intent(Game.this, Win.class);
            intent.putExtra("Score", score);
            startActivity(intent);
            //add wining sound - not children!!!!!!
        }

        if (touch == true) { pikiY -= pikiSpeed; }
        else { pikiY += pikiSpeed; }

        if (pikiY < 0) { pikiY = 0; }

        if (pikiY > frameHeight - pikiSize) { pikiY = frameHeight - pikiSize; }

        piki.setY(pikiY);
        scoreTV.setText("Score: " + score);
        hit();
    }


    public void hit() {
        int coinCenterX = coinX + coin.getWidth() / 2;
        int coinCenterY = coinY + coin.getHeight() / 2;

        if (0 <= coinCenterX && coinCenterX <= pikiSize && pikiY <= coinCenterY && coinCenterY <= pikiY + pikiSize) {
            score += 10;
            coinX = -10;
            if(volume)
            sound.coinSound();
        }

        int coin2CenterX = coin2X + coin2.getWidth() / 2;
        int coin2CenterY = coin2Y + coin2.getHeight() / 2;

        if (0 <= coin2CenterX && coin2CenterX <= pikiSize && pikiY <= coin2CenterY && coin2CenterY <= pikiY + pikiSize) {
            score += 10;
            coin2X = -10;
            if(volume)
                sound.coinSound();
        }


        int ground_bad_centerX = ground_bad_X + ground_bad.getWidth() / 2;
        int ground_bad_centerY = ground_bad_Y + ground_bad.getHeight() / 2;

        if (0 <= ground_bad_centerX && ground_bad_centerX <= pikiSize && pikiY <= ground_bad_centerY && ground_bad_centerY <= pikiY + pikiSize) {
            ground_bad_X = -10;
            if(volume)
                sound.punchSound();
            checkLife();
        }

        int toto_centerX = totoX + toto.getWidth() / 2;
        int toto_centerY = totoY + toto.getHeight() / 2;

        if (0 <= toto_centerX && toto_centerX <= pikiSize && pikiY <= toto_centerY && toto_centerY <= pikiY + pikiSize) {
            totoX = -10;
            if(volume)
                sound.punchSound();
            checkLife();
        }

        int dragon_centerX = dragonX + dragon.getWidth() / 2;
        int dragon_centerY = dragonY + dragon.getHeight() / 2;

        if (0 <= dragon_centerX && dragon_centerX <= pikiSize && pikiY <= dragon_centerY && dragon_centerY <= pikiY + pikiSize) {
            dragonX = -10;
            if(volume)
                sound.punchSound();
            checkLife();
        }
    }


    public void checkLife() {
        if(life44.getVisibility()==View.VISIBLE){ life44.setVisibility(View.INVISIBLE); }
        else if (life1.getVisibility()==View.VISIBLE) { life1.setVisibility(View.INVISIBLE); }
        else if (life2.getVisibility()==View.VISIBLE) { life2.setVisibility(View.INVISIBLE); }
        else if (life3.getVisibility()==View.VISIBLE) { life3.setVisibility(View.INVISIBLE); }
        else if (life3.getVisibility()==View.INVISIBLE
                && life2.getVisibility()==View.INVISIBLE
                && life1.getVisibility()==View.INVISIBLE) {
            if(volume)
                sound.gameOverSound();
            timer.cancel();
            timer = null;
            Intent intent = new Intent(getApplicationContext(), GameOver.class);
            intent.putExtra("Score", score);
            startActivity(intent);
        }
    }

    public void changeSpeed(int speed) {
        coinSpeed = Math.round(screenWidth / speed);
        coin2Speed = Math.round(screenWidth / speed);
        ground_bad_Speed = Math.round(screenWidth / speed);
        totoSpeed = Math.round(screenWidth / speed);
        dragonSpeed =Math.round(screenWidth/speed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;
            piki.setImageDrawable(getResources().getDrawable(R.drawable.piki1));
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            touch = false;
            piki.setImageDrawable(getResources().getDrawable(R.drawable.piki_animation));
            pikiAnimation = (AnimationDrawable) piki.getDrawable();
            pikiAnimation.start();
        }
        return true;
    }


    public void play() {

        pikiAnimation.start();
        coin2Animation.start();
        coinAnimation.start();
        totoAnimation.start();
        dragonAnimation.start();
        ground_bad_Animation.start();
        pauseText.setVisibility(View.INVISIBLE);
        levelUp.setVisibility(View.INVISIBLE);
        if(level2) { toto.setVisibility(View.VISIBLE); }
        if(level3) { dragon.setVisibility(View.VISIBLE); }
        visibility(true);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        moveObjects();
                    }
                });
            }
        }, 0, 20);
    }

    public void visibility(boolean v)
    {
        if(v) {
            piki.setVisibility(View.VISIBLE);
            coin.setVisibility(View.VISIBLE);
            coin2.setVisibility(View.VISIBLE);
            pause_Btn.setVisibility(View.VISIBLE);
            ground_bad.setVisibility(View.VISIBLE);
        }
        else
        {
            piki.setVisibility(View.INVISIBLE);
            coin.setVisibility(View.INVISIBLE);
            coin2.setVisibility(View.INVISIBLE);
            pause_Btn.setVisibility(View.INVISIBLE);
            ground_bad.setVisibility(View.INVISIBLE);
            toto.setVisibility(View.INVISIBLE);
            dragon.setVisibility(View.INVISIBLE);
        }
    }

    public void levelUp()
    {
        timer.cancel();
        timer = null;
        pikiAnimation.stop();
        coinAnimation.stop();
        coin2Animation.stop();
        totoAnimation.stop();
        dragonAnimation.stop();
        ground_bad_Animation.stop();
        totoY=-500;
        dragonY =-500;
        ground_bad_Y=-500;
        coinY=-500;
        coin2Y=-500;
        visibility(false);
        pause_Frame.setVisibility(View.VISIBLE);
        levelUp.setVisibility(View.VISIBLE);
        sound.winSound();
    }
}