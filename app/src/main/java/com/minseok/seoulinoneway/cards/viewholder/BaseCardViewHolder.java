package com.minseok.seoulinoneway.cards.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.minseok.seoulinoneway.cards.Card;

/**
 * Created by minseok on 2018. 7. 8..
 * SeoulInOneWay.
 */
public abstract class BaseCardViewHolder extends RecyclerView.ViewHolder {
    public abstract <T extends Card> void bind(T card);

    public BaseCardViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
