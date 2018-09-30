package com.minseok.seoulinoneway.cards.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.SubwayCard;


/**
 * TODO 혼동에 조심할 것.
 * tvTrainInfoToLeft1
 * 지하철 도착정보를 나타냄
 * 화면 구성에서의 왼쪽, 오른쪽으로 나누었음.
 * 1, 2는 현재 역 기준으로 거리로 구분하였음. (1: 이번열차, 2: 다음열차)
 *
 * Left: 상행선, Right: 하행선
 *
 * e.g) tvTrainInfoToLeft1 상행선 방향으로 가는 이번 열차의 시간을 나타내는 텍스트 뷰
 * e.g) tvTrainInfoToRight2 하행선 방향으로 가는 다음 열차의 시간을 나타내는 텍스트 뷰
 */
public class SubwayCardViewHolder extends BaseCardViewHolder {
//    public TextView tvLineNo;
    public TextView tvStationName;
    public TextView tvStationNow;

    public TextView tvLeftStationName;
    public TextView tvRightStationName;

    public TextView tvLeftDirectionName;
    public TextView tvRightDirectionName;

    public TextView tvTrainInfoToLeft1;
    public TextView tvTrainInfoToLeft2;
    public TextView tvTrainInfoToRight1;
    public TextView tvTrainInfoToRight2;

    @Override
    public <T extends Card> void bind(T card) {
        SubwayCard subwayCard = (SubwayCard) card;

        tvStationName.setText(subwayCard.getStationName());
        tvStationNow.setText("←" + subwayCard.getStationName()  + "→");

        tvLeftStationName.setText(subwayCard.getSecondLowerTrainETA());

        tvTrainInfoToLeft1.setText(subwayCard.getFirstUpperTrainETA());
        tvTrainInfoToLeft2.setText(subwayCard.getSecondUpperTrainETA());
        tvTrainInfoToRight1.setText(subwayCard.getFirstLowerTrainETA());
        tvTrainInfoToRight2.setText(subwayCard.getSecondLowerTrainETA());

        tvRightStationName.setText(subwayCard.getLowerStationName());
        tvLeftStationName.setText(subwayCard.getUpperStationName());

//        tvLineNo.setText(subwayCard.line + "호선");
    }

    public SubwayCardViewHolder(@NonNull View itemView) {
        super(itemView);

//        tvLineNo = itemView.findViewById(R.id.txt_line_no);
        tvStationName = itemView.findViewById(R.id.txt_station_name);
        tvStationNow = itemView.findViewById(R.id.txt_station_now);
        tvLeftStationName = itemView.findViewById(R.id.txt_station_before);
        tvRightStationName = itemView.findViewById(R.id.txt_station_next);

        tvLeftDirectionName = itemView.findViewById(R.id.txt_bound1);
        tvRightDirectionName = itemView.findViewById(R.id.txt_bound2);

        tvTrainInfoToLeft1 = itemView.findViewById(R.id.txt_stCount_first1);
        tvTrainInfoToLeft2 = itemView.findViewById(R.id.txt_stCount_second1);
        tvTrainInfoToRight1 = itemView.findViewById(R.id.txt_stCount_first2);
        tvTrainInfoToRight2 = itemView.findViewById(R.id.txt_stCount_second2);
    }
}
