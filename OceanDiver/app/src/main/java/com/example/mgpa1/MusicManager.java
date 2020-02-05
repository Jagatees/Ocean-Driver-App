package com.example.mgpa1;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;

// Code Written By : Yanson , 180500N

public class MusicManager {

    private static final String TAG = "MusicManager";
    public static final int MUSIC_PREVIOUS = -1;
    public static final int MUSIC_BACKGROUND = 0;
    public static final int MUSIC_GAMEPLAY = 1;

    private static HashMap<Integer, MediaPlayer> players = new HashMap<Integer, MediaPlayer>();
    private static int currentMusic = -1;
    private static int previousMusic = -1;

    public static float getMusicVolume(Context context) {
        return 1.0f;
    }

    public static int getCurrentMusic() {
        return currentMusic;
    }

    public static void start(Context context, int music) {
        start(context, music, false);
    }

    public static void start(Context context, int music, boolean force) {
        if (!force && currentMusic > -1 || currentMusic == music) {
            return;
        }
        if (music == MUSIC_PREVIOUS) {
            Log.d(TAG, "Using previous music [" + previousMusic + "]");
            music = previousMusic;
        }
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
            pause();
        }

        currentMusic = music;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
        MediaPlayer mp = players.get(music);
        if (mp != null) {
            if (!mp.isPlaying()) {
                mp.start();
            }
        } else {
            if (music == MUSIC_BACKGROUND) {
                mp = MediaPlayer.create(context, R.raw.menu);
            } else if (music == MUSIC_GAMEPLAY) {
                mp = MediaPlayer.create(context, R.raw.gameplay);
            } else {
                Log.e(TAG, "unsupported music number - " + music);
                return;
            }

            players.put(music, mp);
            float volume = getMusicVolume(context);
            Log.d(TAG, "Setting music volume to " + volume);
            try {
                mp.setLooping(true);
                mp.start();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    public static void pause() {
        Collection<MediaPlayer> mps = players.values();
        for (MediaPlayer p : mps) {
            if (p.isPlaying()) {
                p.pause();
            }
        }
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
        }
        currentMusic = -1;
        Log.d(TAG, "Current music is now [" + previousMusic + "]");
    }

    public static void updateVolumeFromPrefs(Context context) {
        try {
            float volume = getMusicVolume(context);
            Log.d(TAG, "Setting music volume to " + volume);
            Collection<MediaPlayer> mps = players.values();
            for (MediaPlayer p : mps) {
                p.setVolume(volume, volume);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    public static void release() {
        Log.d(TAG, "Releasing media players");
        Collection<MediaPlayer> mps = players.values();
        for (MediaPlayer mp : mps) {
            try {
                if (mp != null) {
                    if (mp.isPlaying()) {
                        mp.stop();
                    }
                    mp.release();
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        mps.clear();
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
        }
        currentMusic = -1;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
    }

    public static void reset(Context context, int music, boolean force) {
        if (!force && currentMusic > -1 || currentMusic == music) {
            return;
        }
        if (music == MUSIC_PREVIOUS) {
            Log.d(TAG, "Using previous music [" + previousMusic + "]");
            music = previousMusic;
        }
        if (currentMusic != -1) {
            previousMusic = currentMusic;
            Log.d(TAG, "Previous music was [" + previousMusic + "]");
            pause();
        }

        currentMusic = music;
        Log.d(TAG, "Current music is now [" + currentMusic + "]");
        MediaPlayer mp = players.get(music);
        mp.release();
        if (music == MUSIC_BACKGROUND) {
            mp = MediaPlayer.create(context, R.raw.menu);
        } else if (music == MUSIC_GAMEPLAY) {
            mp = MediaPlayer.create(context, R.raw.gameplay);
        } else {
            Log.e(TAG, "unsupported music number - " + music);
            return;
        }

        players.put(music, mp);
        float volume = getMusicVolume(context);
        Log.d(TAG, "Setting music volume to " + volume);
        try {
            mp.setLooping(true);
            mp.start();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }
}
