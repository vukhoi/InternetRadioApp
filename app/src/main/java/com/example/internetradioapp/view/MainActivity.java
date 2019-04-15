package com.example.internetradioapp.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.internetradioapp.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static int INTERNET_PERMISSION_REQUESTCODE;
    public static boolean INTERNET_AVAILABLE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomizeRequestCode();

        checkInternetPermission();

        addFragment(true, new ChannelListFragment());

    }

    public void addFragment(boolean popBackStack, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (popBackStack) {
            fragmentManager.popBackStackImmediate();
        }
        fragmentTransaction.replace(R.id.ll_fragment_placeholder, fragment).
                addToBackStack(null).
                commit();

    }

    private void randomizeRequestCode() {
        Random random = new Random();
        INTERNET_PERMISSION_REQUESTCODE = random.nextInt(random.nextInt(1000));
    }

    private void checkInternetPermission() {
        // check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MainActivity.INTERNET_PERMISSION_REQUESTCODE);
        }
        INTERNET_AVAILABLE = true;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // restart activity
            INTERNET_AVAILABLE = true;
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
}
