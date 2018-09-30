package com.minseok.seoulinoneway.cards.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.ToiletCard;
import com.minseok.seoulinoneway.network.Request;
import com.minseok.seoulinoneway.network.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToiletCardViewHolder extends BaseCardViewHolder {

    public TextView tvToiletName;
    public TextView tvToiletAddress;
    public TextView tvToiletTel;


    @Override
    public <T extends Card> void bind(T card) {
        ToiletCard toiletCard = (ToiletCard) card;

        tvToiletName.setText(toiletCard.toiletName);
        tvToiletAddress.setText(toiletCard.toiletAddress);
        tvToiletTel.setText(toiletCard.toiletTel);



        itemView.setOnClickListener(view -> {

            String posY = toiletCard.toiletYCODE;
            String posX = toiletCard.toiletXCODE;

              Context mContext = itemView.getContext();
              Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                     Uri.parse("geo:" + posY +","+ posX +"?q="+posY+","+posX+"("+toiletCard.toiletName+")"));
            intent.setPackage("com.google.android.apps.maps");
              mContext.startActivity(intent);

        });


    }


    public ToiletCardViewHolder(@NonNull View itemView) {
        super(itemView);

        tvToiletName = itemView.findViewById(R.id.txt_toilet_name);
        tvToiletAddress = itemView.findViewById(R.id.txt_toilet_address);
        tvToiletTel = itemView.findViewById(R.id.txt_toilet_tel);
    }


}