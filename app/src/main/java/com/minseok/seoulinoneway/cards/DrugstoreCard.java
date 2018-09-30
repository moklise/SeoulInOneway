package com.minseok.seoulinoneway.cards;

public class DrugstoreCard extends Card {

    public String drugStoreName;
    public String drugStoreAddress;
    public String drugStoreTel;

    public String drugStoreXCODE;
    public String drugStoreYCODE;

    public Double lengthFromCurrent;


    public DrugstoreCard() {

        type = CardConstant.CardType.Drugstore;

        drugStoreName = "";
        drugStoreAddress = "";
        drugStoreTel = "";

        drugStoreXCODE = "";
        drugStoreYCODE = "";

        lengthFromCurrent = 0.0;
    }

}