package com.minseok.seoulinoneway.cards;

public class FoodtruckCard extends Card {

    public String foodtruckName;
    public String foodtruckAddress;
    public String foodtruckAddressOld;
    public String foodtruckStatus;

    public String foodtruckXCODE;
    public String foodtruckYCODE;

    public Double lengthFromCurrent;


    public FoodtruckCard() {

        type = CardConstant.CardType.Foodtruck;

        foodtruckName = "";
        foodtruckAddress = "";
        foodtruckAddressOld = "";
        foodtruckStatus = "";

        foodtruckXCODE = "";
        foodtruckYCODE = "";

        lengthFromCurrent = 0.0;

    }

}