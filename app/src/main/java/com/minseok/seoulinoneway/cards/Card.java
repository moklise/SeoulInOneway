package com.minseok.seoulinoneway.cards;

/**
 * Created by minseok on 2018. 7. 9..
 * SeoulInOneWay.
 */
public abstract class Card {
    protected CardConstant.CardType type = CardConstant.CardType.None;

    public CardConstant.CardType getType() {
        return type;
    }
}