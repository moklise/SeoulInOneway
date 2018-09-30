package com.minseok.seoulinoneway.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.minseok.seoulinoneway.R;

public class DetailBoardFragment extends Fragment {
    static CoordinatorLayout mContainer = null;



    ImageView ivTraditionalMarket;
    ImageView ivFoodTruck;
    ImageView ivDrugstore;
    ImageView ivMuseum;
//    ImageView ivBilliard;
    ImageView ivToilet;
    ImageView ivParking;



    public static DetailBoardFragment newInstance() {
        return new DetailBoardFragment();

    }

    //모아보기!!!!
    //if 여기서
    //만약 선택되면 그거에 대한 상세 페이지로 넘어가게?
    @Override
    public CoordinatorLayout onCreateView(LayoutInflater inflater, ViewGroup container,
                                         Bundle savedInstanceState) {
        if (mContainer == null) {
            mContainer = (CoordinatorLayout) inflater.inflate(R.layout.fragment_detailboard_card, null);


            ivTraditionalMarket = mContainer.findViewById(R.id.ivTraditionalMarket);
            ivFoodTruck = mContainer.findViewById(R.id.ivFoodTruck);
            ivDrugstore = mContainer.findViewById(R.id.ivDrugstore);
            ivMuseum = mContainer.findViewById(R.id.ivMuseum);
//            ivBilliard = mContainer.findViewById(R.id.ivBilliard);
            ivToilet = mContainer.findViewById(R.id.ivToilet);
            ivParking = mContainer.findViewById(R.id.ivParking);


            //onClick설정
            ivTraditionalMarket.setOnClickListener(onclickTraditionalMarket);
            ivFoodTruck.setOnClickListener(onclickFoodTruck);
            ivDrugstore.setOnClickListener(onclickDrugstore);
            ivMuseum.setOnClickListener(onClickMuseum);
//            ivBilliard.setOnClickListener(onclickBilliard);
            ivToilet.setOnClickListener(onClickToilet);
            ivParking.setOnClickListener(onClickParking);

        }
        return mContainer;
    }


    //전통시장 클릭 시
    View.OnClickListener onclickTraditionalMarket = v -> {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), TradmarketBoardActivity.class);
            startActivity(intent);
        }
    };


    //푸드트럭 클릭 시
    View.OnClickListener onclickFoodTruck = v -> {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), FoodtruckBoardActivity.class);
            startActivity(intent);
        }
    };


    //약국 클릭 시
    View.OnClickListener onclickDrugstore = v -> {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), DrugstoreBoardActivity.class);
            startActivity(intent);
        }
    };

    //주차장 클릭시
    View.OnClickListener onClickParking = v -> {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), ParkingBoardActivity.class);
            startActivity(intent);
        }
    };


    //박물관 클릭 시
    View.OnClickListener onClickMuseum = v -> {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), MuseumBoardActivity.class);
            startActivity(intent);
        }
    };

    //화장실 클릭시
    View.OnClickListener onClickToilet = v -> {
        if (getContext() != null) {
            Intent intent = new Intent(getContext(), ToiletBoardActivity.class);
            startActivity(intent);
        }
    };

}
