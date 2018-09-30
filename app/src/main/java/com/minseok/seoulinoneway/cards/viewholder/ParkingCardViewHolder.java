package com.minseok.seoulinoneway.cards.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.ParkingCard;

public class ParkingCardViewHolder extends BaseCardViewHolder {

    public TextView tvParkingName;
    public TextView tvParkingAddress;
    public TextView tvParkingPayNM;

    public TextView tvParkingRates;
    public TextView tvParkingTimeRate;

    public TextView tvParkingAddRates;
    public TextView tvParkingAddTimeRate;



    @Override
    public <T extends Card> void bind(T card) {
        ParkingCard parkingCard = (ParkingCard) card;

        tvParkingName.setText(parkingCard.parkingName);
        tvParkingAddress.setText(parkingCard.pakringAddress);
        tvParkingPayNM.setText(parkingCard.parkingPayNM);

        tvParkingRates.setText("" + Double.valueOf(parkingCard.parkingRates).intValue() );
        tvParkingTimeRate.setText("" + Double.valueOf(parkingCard.parkingTimeRate).intValue() );

        tvParkingAddRates.setText("" + Double.valueOf(parkingCard.parkingAddRates).intValue() );
        tvParkingAddTimeRate.setText("" + Double.valueOf(parkingCard.parkingAddTimeRate).intValue() );


        itemView.setOnClickListener(view -> {

            String posY = parkingCard.parkingXCODE;
            String posX = parkingCard.parkingYCODE;

            Context mContext = itemView.getContext();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("geo:" + posY +","+ posX +"?q="+posY+","+posX+"("+parkingCard.parkingName+")"));
            intent.setPackage("com.google.android.apps.maps");

            mContext.startActivity(intent);
        });


        if(parkingCard.parkingTimeRate == ""){
            String parkingTimeRateNew = parkingCard.parkingTimeRate;
            parkingTimeRateNew = "";
        }


    }




    public ParkingCardViewHolder(@NonNull View itemView) {
        super(itemView);

        tvParkingName = itemView.findViewById(R.id.txt_parking_name);
        tvParkingAddress = itemView.findViewById(R.id.txt_parking_address);
        tvParkingPayNM = itemView.findViewById(R.id.txt_parking_pay);

        tvParkingRates = itemView.findViewById(R.id.txt_parking_minimum_fare);
        tvParkingTimeRate = itemView.findViewById(R.id.txt_parking_minimum_time);

        tvParkingAddRates = itemView.findViewById(R.id.txt_parking_extra_fare);
        tvParkingAddTimeRate = itemView.findViewById(R.id.txt_parking_extra_time);

    }


}