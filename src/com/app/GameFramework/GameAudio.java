package com.app.GameFramework;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: hung
 * Date: 8/9/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameAudio {

    public int soundpool = 10;
    public MediaPlayer music = null;
    public SoundPool clips = null;

    protected Context context;

    private AssetManager assets;

    public GameAudio(Context context) {
        this.context = context;
        assets = this.context.getAssets();
        music = new MediaPlayer();
        clips = new SoundPool(soundpool, AudioManager.STREAM_MUSIC, 0);
    }

    public GameAudio(Context context, int[] soundClipResources) {
        this.context = context;
        assets = this.context.getAssets();
        music = new MediaPlayer();
        soundpool = soundClipResources.length;
        clips = new SoundPool(soundpool, AudioManager.STREAM_MUSIC, 0);
        int i;
        for (i = 0; i < soundClipResources.length; i++) {
            clips.load(this.context, soundClipResources[i], 1);
        }
    }

    public void reinitialize() {
        if (clips == null) {
            clips = new SoundPool(soundpool, AudioManager.STREAM_MUSIC, 0);
        }

        if (music == null) {
            music = new MediaPlayer();
        }
    }

    // Soundpool stuff

    public void loadAndPlayClip(int res, int priority) {
        if (clips == null) { return; }
        int clip = clips.load(context, res, priority);
        int id = clips.play(clip, 1f, 1f, priority, 0, 1f);
    }

    public void playClipAt(int index, int priority) {
        if (clips == null) { return; }
        int id = clips.play(index, 1f, 1f, priority, 0, 1f);
    }

    public void stopAllClips() {
        if (clips == null) { return; }
        clips.autoPause();
    }

    public void releaseSoundPool() {
        if (clips == null) { return; }
        clips.release();
        clips = null;
    }

    // Media Player stuff

    public boolean loadMediaPlayer(int res, boolean loop) {
        try {
            music = MediaPlayer.create(context, res);
            music.setLooping(loop);
        } catch(Exception e) {
            Log.e("Error", "error");
            return false;
        }
        return true;
    }

    public void playMediaPlayer() {
        if (music == null) { return; }
        music.start();
    }

    public void pauseMediaPlayer() {
        if (music == null) { return; }
        music.pause();
    }

    public void stopMediaPlayer() {
        if (music == null) { return; }
        music.stop();
    }

    public void adjustVolMediaPlayer(float v) {
        if (music == null) { return; }
        music.setVolume(v, v);
    }

    public void releaseMediaPlayer() {
        if (music == null) { return; }
        music.release();
        music = null;
    }

}
