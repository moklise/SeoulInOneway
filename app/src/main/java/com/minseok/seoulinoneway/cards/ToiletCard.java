package com.minseok.seoulinoneway.cards;

public class ToiletCard extends Card {

    public String toiletName;
    public String toiletAddress;
    public String toiletTel;

    public String toiletXCODE;
    public String toiletYCODE;

    public Double lengthFromCurrent;


    public ToiletCard() {

        type = CardConstant.CardType.Toilet;

        toiletName = "";
        toiletAddress = "";
        toiletTel = "";

        toiletXCODE = "";
        toiletYCODE = "";
    }

}