package net.fayola.hydraulica;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

public abstract class AnimationDrawableCallback implements Drawable.Callback {
    private final int mTotalFrames;
    private final Drawable mLastFrame;
    private int mCurrentFrame;

    private Drawable.Callback mWrappedCallback;

    private boolean mIsCallbackTriggered = false;

    //Constructor
    public AnimationDrawableCallback(AnimationDrawable animationDrawable, Drawable.Callback callback){
        mTotalFrames = animationDrawable.getNumberOfFrames();
        mLastFrame = animationDrawable.getFrame(mTotalFrames - 1);
        mWrappedCallback = callback;
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        if(mWrappedCallback != null) {
            mWrappedCallback.invalidateDrawable(who);
        }
        if(!mIsCallbackTriggered && mLastFrame != null && mLastFrame.equals(who.getCurrent())) {
            mIsCallbackTriggered = true;
            onAnimationCompleted();
        }
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
        if(mWrappedCallback != null){
            mWrappedCallback.scheduleDrawable(who,what,when);
        }

        onAnimationAdvanced(mCurrentFrame, mTotalFrames);
        mCurrentFrame++;
    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
        if(mWrappedCallback != null) {
            mWrappedCallback.unscheduleDrawable(who,what);
        }
    }


    //
    //package abstract functions
    //


    abstract void onAnimationAdvanced(int currentFrame, int totalFrames);
    abstract void onAnimationCompleted();


}
