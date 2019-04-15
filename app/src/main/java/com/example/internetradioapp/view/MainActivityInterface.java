package com.example.internetradioapp.view;

import android.support.v4.app.Fragment;

public interface MainActivityInterface {

    void addFragment(boolean popBackStack, Fragment fragment);
    void checkInternetPermission();
}
