package com.minseok.seoulinoneway.cards;

public class TradMarketCard extends Card {

    public String marketName;
    public String marketAddress;
    public String marketCity;

    public String marketXCODE;
    public String marketYCODE;

    public Double lengthFromCurrent;


    public TradMarketCard() {

        type = CardConstant.CardType.Market;

        marketName = "";
        marketAddress = "";
        marketCity = "";

        marketXCODE = "";
        marketYCODE = "";
    }

}