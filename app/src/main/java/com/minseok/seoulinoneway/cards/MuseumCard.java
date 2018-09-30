package com.minseok.seoulinoneway.cards;

public class MuseumCard extends Card {

    public String museumName;
    public String museumAddress;
    public String museumKeyword;


    public MuseumCard() {

        type = CardConstant.CardType.Museum;

        museumName = "";
        museumAddress = "";
        museumKeyword = "";
    }

}