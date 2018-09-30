package com.minseok.seoulinoneway.cards.viewholder;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.WeatherCard;


public class WeatherCardViewHolder extends BaseCardViewHolder {
    public CardView cardView;
    public ImageView ivWeatherIcon;
    public TextView tvLocation;
    public TextView tvTemperature;
//    public TextView tvRainfallProbability;
    public TextView tvLowestTemp;
    public TextView tvHighestTemp;

    private LinearLayout containerDetailInfo;
    public TextView tvDetailInfo;
    public TextView tvSkyValue;

    private ConstraintLayout containerMoreWeatherInfo;
    public TextView tvTomorrowLowTemp;
    public TextView getTvTomorrowHighTemp;
    public TextView tvDayAfterTomorrowLowTemp;
    public TextView tvDayAfterTomorrowHighTemp;

    public ConstraintLayout layoutMaxMinTemp;

    public TextView tvClothingSuggestion;


    @Override
    public <T extends Card> void bind(T card) {
        WeatherCard weatherCard = (WeatherCard) card;

        tvLocation.setText(weatherCard.location);
        tvTemperature.setText(String.valueOf(weatherCard.temperature));




        /* 2시 이후임에도 불구하고 당일 데이터가 제대로 나와있지 않은 경우가 있음. */
        if (weatherCard.highestTemp == 999 || weatherCard.lowestTemp == 999) {
            layoutMaxMinTemp.setVisibility(View.INVISIBLE);
        }

        tvHighestTemp.setText(String.valueOf(weatherCard.highestTemp));
        tvLowestTemp.setText(String.valueOf(weatherCard.lowestTemp));

        tvTomorrowLowTemp.setText(String.valueOf(weatherCard.tomorrow_lowTemp));
        getTvTomorrowHighTemp.setText(String.valueOf(weatherCard.tomorrow_highTemp));
        tvDayAfterTomorrowLowTemp.setText(String.valueOf(weatherCard.dayAfterTomorrow_lowTemp));
        tvDayAfterTomorrowHighTemp.setText(String.valueOf(weatherCard.dayAfterTomorrow_highTemp));

        tvClothingSuggestion.setText(String.valueOf(weatherCard.clothing_suggestion));


        cardView.setOnClickListener(view -> {
            if (containerMoreWeatherInfo.getVisibility() == View.VISIBLE) {
                containerMoreWeatherInfo.setVisibility(View.GONE);
            } else {
                AutoTransition transition = new AutoTransition();
                transition.setDuration(500);
                TransitionManager.beginDelayedTransition(cardView, transition);
                
                containerMoreWeatherInfo.setVisibility(View.VISIBLE);
            }
        });

//        tvSkyValue.setText(weatherCard.skyValue);

//        if (weatherCard.rainSum > 0) {
//            containerDetailInfo.setVisibility(View.VISIBLE);
//            tvDetailInfo.setText(String.format("오늘 %s가 올 수 도 있어요", "비"));
//        }
        // 강수형태 - (PTY)
        // 0: 맑음, 1: 비, 2: 비눈섞임, 3: 눈
        switch (weatherCard.PTYValue){
            case 0: // 비가 안오는 상태이므로 그 안에서 맑음~~구름 정도를 판단

                // 하늘상태- (SKY)
                // 1: 맑음, 2: 구름조금, 3: 구름많음, 4: 흐림
                switch (weatherCard.skyValue) {
                    case 1:
                        ivWeatherIcon.setImageResource(R.drawable.icon_sun);
                        break;
                    case 2:
                        ivWeatherIcon.setImageResource(R.drawable.icon_cloudy);
                        break;
                    case 3:
                        ivWeatherIcon.setImageResource(R.drawable.icon_cloudy);
                        break;
                    case 4:
                        ivWeatherIcon.setImageResource(R.drawable.icon_cloudy_more);
                        containerDetailInfo.setVisibility(View.VISIBLE);
                        tvDetailInfo.setText(String.format("오늘 %s가 올 수 도 있어요", "비"));
                        break;
                }
                break;

            case 1: // 비
                ivWeatherIcon.setImageResource(R.drawable.icon_rain);
                containerDetailInfo.setVisibility(View.VISIBLE);
                tvDetailInfo.setText(String.format("지금 %s가 내리고 있어요", "비"));
                break;

            case 2: // 진눈깨비
                ivWeatherIcon.setImageResource(R.drawable.icon_rain);
                containerDetailInfo.setVisibility(View.VISIBLE);
                tvDetailInfo.setText(String.format("지금 %s가 내리고 있어요", "진눈깨비"));
                break;

            case 3: // 눈
                ivWeatherIcon.setImageResource(R.drawable.icon_snow);
                containerDetailInfo.setVisibility(View.VISIBLE);
                tvDetailInfo.setText(String.format("지금 %s이 내리고 있어요", "눈"));
                break;
        }



        if(weatherCard.temperature > 28){
            tvClothingSuggestion.setText(String.format("오늘은 반팔, 반바지, 민소매, 얇은 원피스가 좋겠군요!"));
        } else if(weatherCard.temperature >23){
            tvClothingSuggestion.setText(String.format("오늘은 반팔, 얇은 셔츠, 반바지, 얇은 면바지가 좋겠군요!"));

        } else if(weatherCard.temperature >20){
            tvClothingSuggestion.setText(String.format("오늘은 긴팔, 면바지, 청바지에 얇은 가디건을 챙기면 좋겠군요!"));

        } else if(weatherCard.temperature >17){
            tvClothingSuggestion.setText(String.format("오늘은 얇은 니트, 맨투맨, 가디건, 청바지가 좋겠군요!"));

        } else if(weatherCard.temperature >12){
            tvClothingSuggestion.setText(String.format("오늘은 자켓, 가디건, 야상, 면바지, 청바지가 좋겠군요!"));

        } else if(weatherCard.temperature >9){
            tvClothingSuggestion.setText(String.format("오늘은 자켓, 트렌치코트, 야상, 니트, 청바지가 좋겠군요!"));

        } else if(weatherCard.temperature >5){
            tvClothingSuggestion.setText(String.format("오늘은 코트, 가죽자켓, 히트텍, 니트에 장갑, 목도리를 꼭 챙기세요!"));

        } else if(weatherCard.temperature >4) {
            tvClothingSuggestion.setText(String.format("오늘은 패딩, 두꺼운 코트를 꼭 입으시고 모자, 목도리, 장갑을 꼭 챙기세요!"));
        }




    }

    public WeatherCardViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.layout_cardview);
        ivWeatherIcon = itemView.findViewById(R.id.icon_weather);
        tvLocation = itemView.findViewById(R.id.txt_location);
        tvTemperature = itemView.findViewById(R.id.txt_temperature);
        layoutMaxMinTemp = itemView.findViewById(R.id.layout_Max_MinTemp);
//        tvRainfallProbability = itemView.findViewById(R.id.txt_rainfall_probability);
        tvLowestTemp = itemView.findViewById(R.id.txt_lowest_temp);
        tvHighestTemp = itemView.findViewById(R.id.txt_highest_temp);

        containerDetailInfo = itemView.findViewById(R.id.container_detail_info);
        tvDetailInfo = itemView.findViewById(R.id.txt_detail_info);
        tvSkyValue = itemView.findViewById(R.id.txt_skyValue);

        containerMoreWeatherInfo = itemView.findViewById(R.id.container_moreWeather_info);
        tvTomorrowLowTemp = itemView.findViewById(R.id.txt_tomorrow_lowTemp);
        getTvTomorrowHighTemp = itemView.findViewById(R.id.txt_tomorrow_highTemp);
        tvDayAfterTomorrowLowTemp = itemView.findViewById(R.id.txt_dayAfterTomorrow_lowTemp);
        tvDayAfterTomorrowHighTemp = itemView.findViewById(R.id.txt_dayAfterTomorrow_highTemp);

        tvClothingSuggestion = itemView.findViewById(R.id.txt_clothing_suggestion);
    }

}

