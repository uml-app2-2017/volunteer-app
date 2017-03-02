package edu.uml.android.volun_t;

import android.net.Uri;

/**
 * Created by Ashley on 2/28/2017.
 */

public class Achievement {
    /** Achievement Name */
    private String mAchName;

    /** Achievement Description */
    private String mAchDesc;

    /** Image resource ID for the achievement */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this achievement */
    private static final int NO_IMAGE_PROVIDED = -1;

    /**
     * Create a new Achievement object.
     *
     * @param AchName is the name of the achievement
     * @param AchDesc is the description of the achievement
     */
    public Achievement(String AchName, String AchDesc) {
        mAchName = AchName;
        mAchDesc = AchDesc;
    }

    /**
     * Create a new Achievement object.
     *
     * @param AchName is the name of the achievement
     * @param AchDesc is the description of the achievement
     * @param imageResourceId is the drawable resource ID for the image associated with the achievement
     *
     * imageID for btn_star_big_off = 17301515
     * imageID for btn_star_big_on = 17301516
     */

    public Achievement(String AchName, String AchDesc, int imageResourceId) {
        mAchName = AchName;
        mAchDesc = AchDesc;
        mImageResourceId = imageResourceId;
    }

    /**
     * Get the achievement name.
     */
    public String getAchName() {
        return mAchName;
    }

    /**
     * Get the achievement description.
     */
    public String getAchDesc() {
        return mAchDesc;
    }

    /**
     * Return the image resource ID of the achievement.
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Returns whether or not there is an image for this achievement.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
