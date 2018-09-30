package com.minseok.seoulinoneway.cards.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.MuseumCard;

public class MuseumCardViewHolder  extends BaseCardViewHolder {

    public TextView tvMuseumdName;
    public TextView tvMuseumAddress;
    public TextView tvMuseumKeyword;


    @Override
    public <T extends Card> void bind(T card) {
        MuseumCard museumCard = (MuseumCard) card;

        tvMuseumdName.setText(museumCard.museumName);
        tvMuseumAddress.setText(museumCard.museumAddress);
        tvMuseumKeyword.setText(museumCard.museumKeyword);
    }


    public MuseumCardViewHolder(@NonNull View itemView) {
        super(itemView);

        tvMuseumdName = itemView.findViewById(R.id.txt_museum_name);
        tvMuseumAddress = itemView.findViewById(R.id.txt_museum_address);
        tvMuseumKeyword = itemView.findViewById(R.id.txt_museum_keyword);
    }


}