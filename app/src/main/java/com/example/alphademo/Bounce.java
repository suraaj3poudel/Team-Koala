package com.example.alphademo;

import android.view.animation.Interpolator;

public class Bounce implements Interpolator {
    private double mAmplitude = 1;
    private double mFrequency = 10;

    /**
     * This method declares amplitude and frequency of the bounce
     * @param amplitude This the the first parameter to bounce method
     * @param frequency This is the second parameter to bounce method
     */

    Bounce (double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    /**
     * This method decides the interpolation time of the bounce
     * @param time This is the first parameter to getInterpolation method
     * @return  interpolation
     */
    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}
