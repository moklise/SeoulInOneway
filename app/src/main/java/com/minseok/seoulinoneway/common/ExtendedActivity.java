package com.minseok.seoulinoneway.common;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by minseok on 2018. 7. 8..
 * SeoulInOneWay.
 */
public class ExtendedActivity extends AppCompatActivity {
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
