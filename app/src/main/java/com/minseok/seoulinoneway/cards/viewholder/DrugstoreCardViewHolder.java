package com.minseok.seoulinoneway.cards.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.DrugstoreCard;
import com.minseok.seoulinoneway.common.WebActivity;
import com.minseok.seoulinoneway.network.Request;
import com.minseok.seoulinoneway.network.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrugstoreCardViewHolder extends BaseCardViewHolder {

    public TextView tvDrugstoreName;
    public TextView tvDrugstoreAddress;
    public TextView tvDrugstoreTel;


    @Override
    public <T extends Card> void bind(T card) {
        DrugstoreCard drugstoreCard = (DrugstoreCard) card;

        tvDrugstoreName.setText(drugstoreCard.drugStoreName);
        tvDrugstoreAddress.setText(drugstoreCard.drugStoreAddress);
        tvDrugstoreTel.setText(drugstoreCard.drugStoreTel);


        itemView.setOnClickListener(view -> {
            /* 카카오 좌표변환계 WGS84 to WTM */
            RetrofitAPI.getInstance().createKakaoService(Request.class)
                    .convertWTMtoWWGS84(drugstoreCard.drugStoreXCODE, drugstoreCard.drugStoreYCODE)
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
                                    Uri.parse("geo:" + posY +","+ posX +"?q="+posY+","+posX+"("+drugstoreCard.drugStoreName+")"));
                            intent.setPackage("com.google.android.apps.maps");
                            mContext.startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                        }
                    });
        });


    }


    public DrugstoreCardViewHolder(@NonNull View itemView) {
        super(itemView);

        tvDrugstoreName = itemView.findViewById(R.id.txt_drugstore_name);
        tvDrugstoreAddress = itemView.findViewById(R.id.txt_drugstore_address);
        tvDrugstoreTel = itemView.findViewById(R.id.txt_drugstore_tel);
    }


}