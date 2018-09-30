package com.minseok.seoulinoneway.cards.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.TradMarketCard;

public class TradMarketViewHolder extends BaseCardViewHolder {

    public TextView tvMarketName;
    public TextView tvMarketAddress;
    public TextView tvMarketCity;

    public TextView tvMarketXCODE;
    public TextView tvMarketYCODE;

    public TextView tvTradMarketWeb;

    @Override
    public <T extends Card> void bind(T card) {
        TradMarketCard tradMarketCard = (TradMarketCard) card;

        tvMarketName.setText(tradMarketCard.marketName);
        tvMarketAddress.setText(tradMarketCard.marketAddress);
        tvMarketCity.setText(tradMarketCard.marketCity);


        itemView.setOnClickListener(view -> {

            String posY = tradMarketCard.marketXCODE;
            String posX = tradMarketCard.marketYCODE;

            Context mContext = itemView.getContext();
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("geo:" + posY +","+ posX +"?q="+posY+","+posX+"("+tradMarketCard.marketName+")"));
            intent.setPackage("com.google.android.apps.maps");
            mContext.startActivity(intent);
        });
    }


    public TradMarketViewHolder(@NonNull View itemView) {
        super(itemView);

        tvMarketName = itemView.findViewById(R.id.txt_market_name);
        tvMarketAddress = itemView.findViewById(R.id.txt_market_address);
        tvMarketCity = itemView.findViewById(R.id.txt_market_city);

        tvTradMarketWeb = itemView.findViewById(R.id.txt_tradmarket_web);



    }


}