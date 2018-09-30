package com.minseok.seoulinoneway.cards;

import android.view.View;

/**
 * Created by minseok on 2018. 7. 9..
 * SeoulInOneWay.
 */
public class NetworkCard extends Card {
    public String content;
    public View.OnClickListener onClick;

    public NetworkCard() {
        type = CardConstant.CardType.Network;

        content = "";

        onClick = null;
    }
}
