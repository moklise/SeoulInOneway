package com.minseok.seoulinoneway.cards;



public class CultureCard extends Card {

    public String title = "";
    public String image = "";
    public String startDate = "";
    public String endDate = "";
    public String place = "";
    public String gCode = "";

    public String link = "";

    public CultureCard() {
        type = CardConstant.CardType.Culture;
    }
}