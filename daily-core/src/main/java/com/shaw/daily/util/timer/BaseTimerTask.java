package com.shaw.daily.util.timer;

import java.util.TimerTask;

/**
 * Created by shaw
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener iTimerListener) {
        this.mITimerListener = iTimerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
