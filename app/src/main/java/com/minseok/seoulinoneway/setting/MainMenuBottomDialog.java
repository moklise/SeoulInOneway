package com.minseok.seoulinoneway.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.minseok.seoulinoneway.LicenseActivity;
import com.minseok.seoulinoneway.R;

/**
 * Created by minseok on 2018. 7. 19..
 * SeoulInOneWay.
 */
public class MainMenuBottomDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_main_menus, container, false);
//
//        Button menu1 = v.findViewById(R.id.btn_1);
//        Button menu2 = v.findViewById(R.id.btn_2);
        Button menu3 = v.findViewById(R.id.btn_3);
        Button menu4 = v.findViewById(R.id.btn_4);
//
//        menu1.setOnClickListener(view -> {
//            mListener.onButtonClicked("menu 1");
//            dismiss();
//        });
//        menu2.setOnClickListener(view -> {
//            mListener.onButtonClicked("menu 2");
//            dismiss();
//        });
        menu3.setOnClickListener(view -> {
            mListener.resetLocation();
//            LocationHelper.INSTANCE.checkCurrentLocation(getContext(), (latitude, longitude) -> {
//                Toast.makeText(inflater.getContext(), "" + latitude + "/" + longitude, Toast.LENGTH_SHORT).show();
//            mListener.onButtonClicked("" + latitude + "/" + longitude);
//            });
            dismiss();
        });
        menu4.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), LicenseActivity.class);
            startActivity(intent);

            dismiss();
        });

        return v;
    }

    public interface BottomSheetListener {
        void onButtonClicked(String msg);
        void resetLocation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException();
        }
    }
}
