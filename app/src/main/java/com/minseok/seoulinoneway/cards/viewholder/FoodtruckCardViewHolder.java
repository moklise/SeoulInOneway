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
import com.minseok.seoulinoneway.cards.FoodtruckCard;
import com.minseok.seoulinoneway.network.Request;
import com.minseok.seoulinoneway.network.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodtruckCardViewHolder extends BaseCardViewHolder {

    public TextView tvFoodtruckName;
    public TextView tvFoodtruckAddress;
    public TextView tvFoodtruckStatus;


    @Override
    public <T extends Card> void bind(T card) {
        FoodtruckCard foodtruckCard = (FoodtruckCard) card;

        tvFoodtruckName.setText(foodtruckCard.foodtruckName);
        tvFoodtruckAddress.setText(foodtruckCard.foodtruckAddress);
        tvFoodtruckStatus.setText(foodtruckCard.foodtruckStatus);


        //만약 신주소가 공백이라면 구주소로 설정
        if (foodtruckCard.foodtruckAddress == ""){
            tvFoodtruckAddress.setText(foodtruckCard.foodtruckAddressOld);
        }



        itemView.setOnClickListener(view -> {
            /* 카카오 좌표변환계 WGS84 to WTM */
            RetrofitAPI.getInstance().createKakaoService(Request.class)
                    .convertWTMtoWWGS84(foodtruckCard.foodtruckXCODE, foodtruckCard.foodtruckYCODE)
                    .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject result = response.body();
                            if (result.has("documents")) {
                                JsonArray documents = result.get("documents").getAsJsonArray();

                                JsonObject position = documents.get(0).getAsJsonObject();
                                String posX = position.get("x").getAsString();
                                String posY = position.get("y").getAsString();

                                Context mContext = itemView.getContext();
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("geo:" + posY + "," + posX + "?q=" + posY + "," + posX + "(" + foodtruckCard.foodtruckName + ")"));
                                intent.setPackage("com.google.android.apps.maps");
                                mContext.startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                        }
                    });
        });


    }


    public FoodtruckCardViewHolder(@NonNull View itemView) {
        super(itemView);

        tvFoodtruckName = itemView.findViewById(R.id.txt_foodtruck_name);
        tvFoodtruckAddress = itemView.findViewById(R.id.txt_foodtruck_address);
        tvFoodtruckStatus = itemView.findViewById(R.id.txt_foodtruck_status);
    }


}