package com.example.emergencyalertadmin;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ChangeFragment {
    private Context context;

    public ChangeFragment(Context context) {
        this.context = context;
    }

    public void change(Fragment fragment){
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.main2ActivityFragment,fragment,"fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}