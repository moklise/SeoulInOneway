package com.minseok.seoulinoneway.cards;

import com.minseok.seoulinoneway.cards.model.Subway;

public class SubwayCard extends Card {

    public String line;

    public Subway trainUpper1;
    public Subway trainUpper2;
    public Subway trainLower1;
    public Subway trainLower2;

    public SubwayCard() {
        type = CardConstant.CardType.Metro;

        trainUpper1 = new Subway();
        trainUpper2 = new Subway();
        trainLower1 = new Subway();
        trainLower2 = new Subway();

        line = trainLower1.subwayId.substring(trainLower1.subwayId.length()-1);
    }

    public String getStationName() { return trainUpper1.statnNm; }
    public String getUpperStationName() {
        String stationLineInfo = trainUpper1.trainLineNm;

        int pre = stationLineInfo.indexOf(" - ");
        return stationLineInfo.substring(pre + 3, stationLineInfo.length() - 2);
    }

    public String getLowerStationName() {
        String stationLineInfo = trainLower1.trainLineNm;

        int pre = stationLineInfo.indexOf(" - ");
        return stationLineInfo.substring(pre + 3, stationLineInfo.length() - 2);
    }

    public String getFirstUpperTrainETA() { return trainUpper1.arvlMsg2; }
    public String getSecondUpperTrainETA() {
        return trainUpper2.arvlMsg2;
    }
    public String getFirstLowerTrainETA() { return trainLower1.arvlMsg2; }
    public String getSecondLowerTrainETA() { return trainLower2.arvlMsg2; }
}
