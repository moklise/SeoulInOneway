package com.minseok.seoulinoneway.cards.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.SimpleTextCard;


/**
 * Created by minseok on 2018. 7. 8..
 * SeoulInOneWay.
 */
public class SimpleTextCardViewHolder extends BaseCardViewHolder {
    public TextView tvContent;

    @Override
    public <T extends Card> void bind(T card) {
        SimpleTextCard sampleCard = (SimpleTextCard) card;
        tvContent.setText(sampleCard.getText());
        itemView.setOnClickListener(sampleCard.onclick);
    }

    public SimpleTextCardViewHolder(@NonNull View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.txt_content);
    }
}
