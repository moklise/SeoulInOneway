package com.minseok.seoulinoneway.cards.viewholder;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.AtmosphereCard;
import com.minseok.seoulinoneway.cards.Card;

/**
 * Created by minseok on 2018. 7. 8..
 * SeoulInOneWay.
 */
public class AtmosphereCardViewHolder extends BaseCardViewHolder {
    public TextView tvLocation;
    public TextView tvMaxIndex;
    public TextView tvPM10;
    public TextView tvPM25;
    public ImageView ivAtmorphere;

    public ConstraintLayout layoutFolded;

    boolean minimalized;

    @Override
    public <T extends Card> void bind(T card) {
        AtmosphereCard atmosphereCard = (AtmosphereCard) card;

        tvLocation.setText(atmosphereCard.location);

        tvMaxIndex.setText(String.valueOf(atmosphereCard.MaxIndex)); //대기통합지수

        tvPM10.setText(atmosphereCard.PM10);  //미세먼지
        tvPM25.setText(atmosphereCard.PM25);  //초미세먼지

//        itemView.setOnClickListener(view -> {
//            if (minimalized) {
//                minimalized = false;
//                layoutFolded.setVisibility(View.VISIBLE);
//            } else {
//                minimalized = true;
//                layoutFolded.setVisibility(View.GONE);
//            }
//            TransitionManager.beginDelayedTransition((ViewGroup) itemView, new AutoTransition());
//        });


        if(atmosphereCard.MaxIndex <= 50){
            ivAtmorphere.setImageResource(R.drawable.img_clearsky);
        }else if(atmosphereCard.MaxIndex <= 100){
            ivAtmorphere.setImageResource(R.drawable.img_dust);
        }else if(atmosphereCard.MaxIndex <= 250){
            ivAtmorphere.setImageResource(R.drawable.img_smoke_yellow);
        }else if(atmosphereCard.MaxIndex > 250){
            ivAtmorphere.setImageResource(R.drawable.img_smoke_red);
        }

    }

    public AtmosphereCardViewHolder(@NonNull View itemView) {
        super(itemView);

        minimalized = true;

        tvLocation = itemView.findViewById(R.id.txt_location1);
        tvMaxIndex = itemView.findViewById(R.id.txt_MaxIndex);
        tvPM10 = itemView.findViewById(R.id.txt_PM10);
        tvPM25 = itemView.findViewById(R.id.txt_PM25);
        ivAtmorphere = itemView.findViewById(R.id.Image_MaxIndex);


//        layoutFolded = itemView.findViewById(R.id.layout_folded);
    }
}
