package com.minseok.seoulinoneway.cards;

public class ParkingCard extends Card {

    public String parkingName;
    public String pakringAddress;
    public String parkingPayNM; //유료인지 무료인지

    public String parkingRates; //기본 요금
    public String parkingTimeRate; //기본 시간

    public String parkingAddRates; //추가 요금
    public String parkingAddTimeRate; //추가 시간

    public String parkingXCODE; //LAT
    public String parkingYCODE; //LNG

    public Double lengthFromCurrent;



    public ParkingCard() {

        type = CardConstant.CardType.Parking;

        parkingName = "";
        pakringAddress = "";
        parkingPayNM = "";

        parkingRates = "";
        parkingTimeRate = "";

        parkingAddRates = "";
        parkingAddTimeRate = "";

        parkingXCODE = "";
        parkingYCODE = "";

        lengthFromCurrent = 0.0;


    }

}