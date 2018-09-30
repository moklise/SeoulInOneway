package com.minseok.seoulinoneway.cards.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.minseok.seoulinoneway.cards.SubwayCard;

/**
 * Created by minseok on 2018. 8. 24..
 * SeoulInOneWay.
 */
public class SubwayMapper {
    public static SubwayCard inject(SubwayCard newCard, JsonArray trainArray) {
        int size = trainArray.size();

        if (size > 4) { size = 4; }

        for (int i = 0; i < size; i++) {
            Subway thisSubway = getSubway(newCard, i);
            JsonObject json = trainArray.get(i).getAsJsonObject();

            if (!json.get("beginRow").isJsonNull())
                thisSubway.beginRow = json.get("beginRow").getAsString();
            if (!json.get("endRow").isJsonNull())
                thisSubway.endRow = json.get("endRow").getAsString();
            if (!json.get("curPage").isJsonNull())
                thisSubway.curPage = json.get("curPage").getAsString();
            if (!json.get("pageRow").isJsonNull())
                thisSubway.pageRow = json.get("pageRow").getAsString();

            thisSubway.totalCount = json.get("totalCount").getAsInt();
            thisSubway.rowNum = json.get("rowNum").getAsInt();
            thisSubway.selectedCount = json.get("selectedCount").getAsInt();
            thisSubway.subwayId = json.get("subwayId").getAsString();

            if (!json.get("subwayNm").isJsonNull())
                thisSubway.subwayNm = json.get("subwayNm").getAsString();
            thisSubway.updnLine = json.get("updnLine").getAsString();
            thisSubway.trainLineNm = json.get("trainLineNm").getAsString();
            thisSubway.subwayHeading = json.get("subwayHeading").getAsString();
            thisSubway.statnFid = json.get("statnFid").getAsString();
            thisSubway.statnTid = json.get("statnTid").getAsString();
            thisSubway.statnId = json.get("statnId").getAsString();
            thisSubway.statnNm = json.get("statnNm").getAsString();
            if (!json.get("trainCo").isJsonNull())
                thisSubway.trainCo = json.get("trainCo").getAsString();
            thisSubway.ordkey = json.get("ordkey").getAsString();
            thisSubway.subwayList = json.get("subwayList").getAsString();
            thisSubway.statnList = json.get("statnList").getAsString();
            if (!json.get("btrainSttus").isJsonNull())
                thisSubway.btrainSttus = json.get("btrainSttus").getAsString();
            thisSubway.barvlDt = json.get("barvlDt").getAsString();
            thisSubway.btrainNo = json.get("btrainNo").getAsString();
            thisSubway.bstatnId = json.get("bstatnId").getAsString();
            thisSubway.bstatnNm = json.get("bstatnNm").getAsString();
            thisSubway.recptnDt = json.get("recptnDt").getAsString();
            thisSubway.arvlMsg2 = json.get("arvlMsg2").getAsString();
            thisSubway.arvlMsg3 = json.get("arvlMsg3").getAsString();
            thisSubway.arvlCd = json.get("arvlCd").getAsString();
        }

        setCommonInfo(newCard);

        return newCard;
    }

    private static Subway getSubway(SubwayCard thisCard, int index) {
        switch (index) {
            case 0:
                return thisCard.trainUpper1;
            case 1:
                return thisCard.trainUpper2;
            case 2:
                return thisCard.trainLower1;
            case 3:
                return thisCard.trainLower2;
        }

        throw new IllegalStateException("Not Support to 4 more trains.");
    }

    private static void setCommonInfo(SubwayCard _card) {
        // 호선
        // 상행선, 하행선 역이름
        _card.getFirstLowerTrainETA();
    }
}
