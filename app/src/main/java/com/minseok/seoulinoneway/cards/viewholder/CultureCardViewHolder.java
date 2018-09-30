package com.minseok.seoulinoneway.cards.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.CultureCard;
import com.minseok.seoulinoneway.common.WebActivity;

public class CultureCardViewHolder extends BaseCardViewHolder {

    public ImageView ivImage;
    public TextView tvTitle;
    public TextView tvStartDate;
    public TextView tvEndDate;
    public TextView tvPlace;
    public TextView tvGcode;

    @Override
    public <T extends Card> void bind(T card) {
        CultureCard cultureCard = (CultureCard) card;

        String apost = "&#39;"; // 어퍼스트로피 체크
        String quot = "&quot;"; // 쌍따옴표 체크
        cultureCard.title = cultureCard.title.replace(apost, "\'");
        cultureCard.title = cultureCard.title.replace(quot, "\"");

        tvTitle.setText(cultureCard.title);
        tvStartDate.setText(cultureCard.startDate);
        tvEndDate.setText(cultureCard.endDate);
        tvPlace.setText(cultureCard.place);
        tvGcode.setText(cultureCard.gCode);

        Glide.with(itemView.getContext()).load(cultureCard.image.toLowerCase()).into(ivImage);

        itemView.setOnClickListener(view -> {
            Context mContext = itemView.getContext();
            Intent intent = new Intent(mContext, WebActivity.class);
            intent.putExtra("url", cultureCard.link);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mContext.startActivity(intent);
        });
    }

    public CultureCardViewHolder(@NonNull View itemView) {
        super(itemView);

        tvTitle = itemView.findViewById(R.id.txt_title);
        ivImage = itemView.findViewById(R.id.culture_main_img);
        tvStartDate = itemView.findViewById(R.id.txt_start_date);
        tvEndDate = itemView.findViewById(R.id.txt_end_date);
        tvPlace = itemView.findViewById(R.id.txt_place);
        tvGcode = itemView.findViewById(R.id.txt_GCODE);
    }
}