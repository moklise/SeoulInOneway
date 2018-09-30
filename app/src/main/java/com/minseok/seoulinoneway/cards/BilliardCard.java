package com.minseok.seoulinoneway.cards;

public class BilliardCard  extends Card {

    public String billiardName;
    public String billiardAddress;
    public String billiardTel;

    public String billiardXCODE;
    public String billiardYCODE;


    public BilliardCard() {

        type = CardConstant.CardType.Billiard;

        billiardName = "";
        billiardAddress = "";
        billiardTel = "";

        billiardXCODE = "";
        billiardYCODE = "";
    }

}