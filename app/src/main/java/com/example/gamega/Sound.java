package com.example.gamega;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class Sound {
    private AudioAttributes audio;
    final int MAX=2;

    private static  int coinSound;
    private static  int gameOverSound;
    private static  int winSound;
    private static  int punchSound;
    private static  SoundPool sound;


    public  Sound (Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            audio = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            sound = new SoundPool.Builder()
                    .setAudioAttributes(audio)
                    .setMaxStreams(MAX)
                    .build();
        }
        else {
            sound = new SoundPool(MAX, AudioManager.STREAM_MUSIC, 0);
        }

        coinSound = sound.load(context, R.raw.coin_sound, 1);
        gameOverSound = sound.load(context, R.raw.game_over_sound, 1);
        winSound = sound.load(context, R.raw.level_win_sound, 1);
        punchSound = sound.load(context, R.raw.punch_sound, 1);

    }

    public void coinSound() { sound.play(coinSound, 1.0f, 1.0f, 1, 0, 1.0f); }

    public void gameOverSound() {
        sound.play(gameOverSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void winSound() {
        sound.play(winSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void punchSound() {
        sound.play(punchSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
