package com.imen_nmn.widgetpok;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Random;

/**
 * Created by imen_nmn on 27/03/17.
 */

public class Utils {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static int  getRandomInteger(int maxInt) {
        Random rn = new Random();
        int randomInt = rn.nextInt(maxInt) + 1;
        return randomInt;
    }

    public static long  getRandomLong(long maxLong) {
        Random rn = new Random();
        long randomLong = (rn.nextInt(100)*maxLong)/100 + 1;
        return randomLong;
    }
}
