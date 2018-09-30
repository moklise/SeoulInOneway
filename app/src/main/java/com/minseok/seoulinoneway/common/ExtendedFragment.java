package com.minseok.seoulinoneway.common;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by minseok on 2018. 9. 8..
 * SeoulInOneWay.
 */
public class ExtendedFragment extends Fragment {
    public void toast(String msg) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(),  msg, Toast.LENGTH_SHORT).show());
    }
}
