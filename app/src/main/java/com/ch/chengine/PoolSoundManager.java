package com.ch.chengine;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by charleston on 15/03/15.
 */
public class PoolSoundManager {

    HashMap<Integer, Integer> sounds;
    Context mContext;
    SoundPool soundPool;
    boolean soundLoaded;

    public PoolSoundManager(Context context){
        mContext = context;
        sounds = new HashMap<Integer, Integer>();
        ((Activity)mContext).setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                //todo check all sounds before set ?as loaded
                soundLoaded = true;
            }
        });

    }

    public void addSound(int soundResourceId){
        sounds.put(soundResourceId, soundPool.load(mContext, soundResourceId, 1));
    }
    public void playSound(int soundResourceId){
        // Getting the user sound settings
        AudioManager audioManager = (AudioManager) mContext.getSystemService(mContext.AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;
        // Is the sound loaded already?
        if (soundLoaded) {
            soundPool.play(sounds.get(soundResourceId), volume, volume, 1, 0, 1f);
        }
    }
}
