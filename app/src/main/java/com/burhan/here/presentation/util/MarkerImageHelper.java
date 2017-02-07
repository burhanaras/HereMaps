package com.burhan.here.presentation.util;

import android.util.Log;

import com.burhan.here.R;
import com.burhan.here.network.model.Category;
import com.here.android.mpa.common.Image;

import java.io.IOException;

/**
 * Created by BURHAN on 2/6/2017.
 */

public class MarkerImageHelper {
    public static final String TAG = MarkerImageHelper.class.getName();

    public static Image getMarkerImage(String category) {

        com.here.android.mpa.common.Image myImage = new com.here.android.mpa.common.Image();

        try {
            switch (category) {
                case Category.ACCOMMODATION:
                    myImage.setImageResource(R.mipmap.pin_accomodation);
                    break;
                case Category.ATM_BANK_EXCHANGE:
                    myImage.setImageResource(R.mipmap.pin_atm);
                    break;
                case Category.EAT_DRINK:
                    myImage.setImageResource(R.mipmap.pin_eat_drink);
                    break;
                case Category.HOSPITAL_HEALTH_CARE_FACILITY:
                    myImage.setImageResource(R.mipmap.pin_health);
                    break;
                case Category.PETROL_STATION:
                    myImage.setImageResource(R.mipmap.pin_fuel);
                    break;
                case Category.SHOPPING:
                    myImage.setImageResource(R.mipmap.pin_shopping);
                    break;
                case Category.SIGHTS_MUSEUMS:
                    myImage.setImageResource(R.mipmap.pin_sights);
                    break;
                case Category.GOING_OUT:
                    myImage.setImageResource(R.mipmap.pin_getting_around);
                    break;
                case Category.CINEMA:
                    myImage.setImageResource(R.mipmap.pin_entertainment);
                    break;

                default:
                    myImage.setImageResource(R.drawable.pin);
            }

        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
        return myImage;

    }
}
