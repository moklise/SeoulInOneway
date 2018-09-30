package com.minseok.seoulinoneway.cards;

import kotlin.Unit;

/**
 * Created by minseok on 2018. 7. 9..
 * SeoulInOneWay.
 */
public class AtmosphereCard extends Card {
    public String location;
    public int MaxIndex; //통합대기지수
    public int AtmosphereImage; //통합대기지수에따른 이미지변경
    public String PM10; //미세먼지
    public String PM25; //초미세먼지


    public AtmosphereCard() {
        type = CardConstant.CardType.Atmosphere;

        location = "";
        MaxIndex = 0;
        AtmosphereImage = 0;
        PM10 = "";
        PM25 = "";
    }
}
