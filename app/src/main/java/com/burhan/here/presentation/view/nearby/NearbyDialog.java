package com.burhan.here.presentation.view.nearby;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.burhan.here.network.model.Category;
import com.burhan.here.R;
import com.burhan.here.presentation.view.nearby.listener.NearbyLocationsSelectionListener;

/**
 * Created by BURHAN on 2/4/2017.
 */

public class NearbyDialog extends Dialog implements android.view.View.OnClickListener {
    public static final String TAG = NearbyDialog.class.getName();
    private NearbyLocationsSelectionListener context;

    private ImageButton ivClose;
    private ImageButton btnEatDrink;
    private ImageButton btnSightAttractions;
    private ImageButton btnGettingAround;
    private ImageButton btnFuelParking;
    private ImageButton btnEntertainment;
    private ImageButton btnBankAtm;
    private ImageButton btnHealth;
    private ImageButton btnHotel;
    private ImageButton btnShopping;

    private final ImageButton[] categoryButtons = {btnEatDrink, btnSightAttractions,
            btnGettingAround, btnFuelParking, btnEntertainment,
            btnBankAtm, btnHealth, btnHotel, btnShopping};

    private final int[] categoryButtonIds = {R.id.ibtn_category_eat_drink, R.id.ibtn_category_sight_attractions,
            R.id.ibtn_category_getting_around, R.id.ibtn_category_fuel_parking,
            R.id.ibtn_category_entertainment, R.id.ibtn_category_bank_atm,
            R.id.ibtn_category_health, R.id.ibtn_category_hotel,
            R.id.ibtn_category_shopping};

    private final String[] categoryIds = {Category.EAT_DRINK, Category.SIGHTS_MUSEUMS,
            Category.GOING_OUT, Category.PETROL_STATION,
            Category.CINEMA, Category.ATM_BANK_EXCHANGE,
            Category.HOSPITAL_HEALTH_CARE_FACILITY, Category.ACCOMMODATION,
            Category.SHOPPING};

    public NearbyDialog(Context context) {
        super(context);
        init(context);
    }

    public NearbyDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected NearbyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }


    private void init(Context _context) {
        if (_context instanceof NearbyLocationsSelectionListener) {
            context = (NearbyLocationsSelectionListener) _context;
        }
        setContentView(R.layout.nearby_places_dialog);

        getWindow().getAttributes().windowAnimations = R.style.NearbyDialogAnimation;
        setCancelable(true);

        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        animation.setDuration(1000);

        ivClose = (ImageButton) findViewById(R.id.iv_close);
        ivClose.setOnClickListener(this);
        ivClose.startAnimation(animation);

        for (int i = 0; i < categoryButtons.length; i++) {
            categoryButtons[i] = (ImageButton) findViewById(categoryButtonIds[i]);
            categoryButtons[i].setOnClickListener(this);
            categoryButtons[i].startAnimation(animation);
        }

    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick() called with: view = [" + view + "]");
        if (view.getId() == ivClose.getId()) {
            dismiss();
        }
        for (int i = 0; i < categoryButtons.length; i++) {
            if (view.getId() == categoryButtons[i].getId()) {
                context.onNearbyCategorySelected(categoryIds[i]);
                dismiss();
            }
        }
    }

}
