package com.minseok.seoulinoneway.cards;

/**
 * Created by minseok on 2018. 7. 9..
 * SeoulInOneWay.
 */
public class SampleCard extends Card {
    public String content;

    public SampleCard() {
        type = CardConstant.CardType.Sample;
        content = "";
    }
}
