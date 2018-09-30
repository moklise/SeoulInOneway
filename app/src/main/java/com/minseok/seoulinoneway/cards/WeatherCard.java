package com.minseok.seoulinoneway.cards;

import android.view.View;

public class WeatherCard extends Card {

    public int weatherImage;
    public String location;
    public int temperature;
    public String rainfallProbability;
    public int lowestTemp;
    public int highestTemp;
    public int rainSum;
    public int skyValue;
    public int PTYValue;

    public String tomorrow_lowTemp; //내일
    public String tomorrow_highTemp;
    public String dayAfterTomorrow_lowTemp; //모레
    public String dayAfterTomorrow_highTemp;

    public String clothing_suggestion;


    public View.OnClickListener onClick;




    public WeatherCard() {
        type = CardConstant.CardType.Weather;

        weatherImage  = 0 ;
        location = "";
        temperature = 0;
        rainfallProbability = "";
        lowestTemp = 0;
        highestTemp = 0;
        rainSum = 0;
        skyValue = 0; //구름 양 정도 맑음 구름 구름많이 흐림
        PTYValue = 0;  //강수형태 - (PTY) 코드 맑음0 비1 비눈섞임2 눈3

        tomorrow_lowTemp="";
        tomorrow_highTemp="";
        dayAfterTomorrow_lowTemp="";
        dayAfterTomorrow_highTemp="";

        clothing_suggestion="";


        onClick = null;

    }
}
