package com.minseok.seoulinoneway.cards.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.SampleCard;


/**
 * Created by minseok on 2018. 7. 8..
 * SeoulInOneWay.
 */
public class SampleCardViewHolder extends BaseCardViewHolder {
    public TextView tvContent;

    @Override
    public <T extends Card> void bind(T card) {
        SampleCard sampleCard = (SampleCard) card;
        tvContent.setText(sampleCard.content);
    }

    public SampleCardViewHolder(@NonNull View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.txt_content);
    }
}
