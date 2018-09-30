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
import com.minseok.seoulinoneway.cards.BilliardCard;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.network.Request;
import com.minseok.seoulinoneway.network.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BilliardCardViewHolder extends BaseCardViewHolder {

    public TextView tvBilliardName;
    public TextView tvBilliardAddress;
    public TextView tvBilliardTel;


    @Override
    public <T extends Card> void bind(T card) {
        BilliardCard billiardCard = (BilliardCard) card;

        tvBilliardName.setText(billiardCard.billiardName);
        tvBilliardAddress.setText(billiardCard.billiardAddress);
        tvBilliardTel.setText(billiardCard.billiardTel);



        itemView.setOnClickListener(view -> {
            /* 카카오 좌표변환계 WGS84 to WTM */
            RetrofitAPI.getInstance().createKakaoService(Request.class)
                    .convertWTMtoWWGS84(billiardCard.billiardXCODE, billiardCard.billiardYCODE)
                    .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject result = response.body();
                            JsonArray documents = result.get("documents").getAsJsonArray();

                            JsonObject position = documents.get(0).getAsJsonObject();
                            String posX = position.get("x").getAsString();
                            String posY = position.get("y").getAsString();

                            Context mContext = itemView.getContext();
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                    Uri.parse("geo:" + posY +","+ posX +"?q="+posY+","+posX+"("+billiardCard.billiardName+")"));
                            intent.setPackage("com.google.android.apps.maps");
                            mContext.startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                        }
                    });
        });



    }


    public BilliardCardViewHolder(@NonNull View itemView) {
        super(itemView);

        tvBilliardName = itemView.findViewById(R.id.txt_billiard_name);
        tvBilliardAddress = itemView.findViewById(R.id.txt_billiard_address);
        tvBilliardTel = itemView.findViewById(R.id.txt_billiard_tel);
    }


}