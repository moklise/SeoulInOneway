package com.minseok.seoulinoneway.cards;

import android.view.View;

/**
 * Created by minseok on 2018. 9. 25..
 * SeoulInOneWay.
 */
public class SimpleTextCard extends Card{
    public String text;
    public View.OnClickListener onclick;

    public String getText() {
        return text;
    }

    public SimpleTextCard() {
        type = CardConstant.CardType.SimpleText;
        text = "";
        onclick = null;
    }
}
