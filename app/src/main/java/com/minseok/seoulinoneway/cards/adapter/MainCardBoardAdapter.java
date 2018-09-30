package com.minseok.seoulinoneway.cards.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.minseok.seoulinoneway.R;
import com.minseok.seoulinoneway.cards.Card;
import com.minseok.seoulinoneway.cards.CardConstant;
import com.minseok.seoulinoneway.cards.viewholder.AtmosphereCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.BaseCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.BilliardCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.CultureCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.DrugstoreCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.FoodtruckCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.MuseumCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.NetworkCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.ParkingCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.SampleCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.SimpleTextCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.SubwayCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.ToiletCardViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.TradMarketViewHolder;
import com.minseok.seoulinoneway.cards.viewholder.WeatherCardViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by minseok on 2018. 7. 8..
 * SeoulInOneWay.
 */
public class MainCardBoardAdapter extends RecyclerView.Adapter<BaseCardViewHolder> {
    Context mContext;
    private ArrayList<Card> mList = new ArrayList<>();

    public MainCardBoardAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public BaseCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v;
        CardView.LayoutParams lp = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 5);

        switch (viewType) {
            case 0:
                v = (CardView) CardView.inflate(mContext,  R.layout.item_example_card, null);
                v.setLayoutParams(lp);
                return new SampleCardViewHolder(v);

            case 1:
                v = (CardView) CardView.inflate(mContext,  R.layout.item_atmosphere_card, null);
                v.setLayoutParams(lp);
                return new AtmosphereCardViewHolder(v);

            case 2:
                v = (CardView) CardView.inflate(mContext, R.layout.item_example_card, null);
                v.setLayoutParams(lp);
                return new NetworkCardViewHolder(v);

            case 3:
                v = (CardView) CardView.inflate(mContext, R.layout.item_weather_card, null);
                v.setLayoutParams(lp);
                return new WeatherCardViewHolder(v);

            case 4:
                v = (CardView) CardView.inflate(mContext, R.layout.item_culture_card, null);
                v.setLayoutParams(lp);
                return new CultureCardViewHolder(v);

            case 5:
                v = (CardView) CardView.inflate(mContext, R.layout.item_subway_card, null);
                v.setLayoutParams(lp);
                return new SubwayCardViewHolder(v);

            case 6:
                v = (CardView) CardView.inflate(mContext, R.layout.item_drugstore_card, null);
                v.setLayoutParams(lp);
                return new DrugstoreCardViewHolder(v);

            case 7:
                v = (CardView) CardView.inflate(mContext, R.layout.item_billiard_card, null);
                v.setLayoutParams(lp);
                return new BilliardCardViewHolder(v);

            case 8:
                v = (CardView) CardView.inflate(mContext, R.layout.item_museum_card, null);
                v.setLayoutParams(lp);
                return new MuseumCardViewHolder(v);

            case 9:
                v = (CardView) CardView.inflate(mContext, R.layout.item_toilet_card, null);
                v.setLayoutParams(lp);
                return new ToiletCardViewHolder(v);

            case 10:
                v = (CardView) CardView.inflate(mContext, R.layout.item_foodtruck_card, null);
                v.setLayoutParams(lp);
                return new FoodtruckCardViewHolder(v);

            case 11:
                v = (CardView) CardView.inflate(mContext, R.layout.item_parking_card, null);
                v.setLayoutParams(lp);
                return new ParkingCardViewHolder(v);

            case 12:
                v = (CardView) CardView.inflate(mContext, R.layout.item_market_card, null);
                v.setLayoutParams(lp);
                return new TradMarketViewHolder(v);

            case 13:
                v = (CardView) CardView.inflate(mContext, R.layout.item_simple_text_card, null);
                v.setLayoutParams(lp);
                return new SimpleTextCardViewHolder(v);

            default:
                v = (CardView) CardView.inflate(mContext,  R.layout.item_example_card, null);
                v.setLayoutParams(lp);
                return new SampleCardViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseCardViewHolder holder, int position) {
        Card card = mList.get(position);
        holder.bind(card);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        CardConstant.CardType type = mList.get(position).getType();
        if (type.equals(CardConstant.CardType.Sample)) {
            return 0;
        } else if (type.equals(CardConstant.CardType.Atmosphere)) {
            return 1;
        } else if (type.equals(CardConstant.CardType.Network)) {
            return 2;
        } else if (type.equals(CardConstant.CardType.Weather)) {
            return 3;
        } else if (type.equals(CardConstant.CardType.Culture)) {
            return 4;
        } else if (type.equals(CardConstant.CardType.Metro)) {
            return 5;
        } else if (type.equals(CardConstant.CardType.Drugstore)) {
            return 6;
        } else if (type.equals(CardConstant.CardType.Billiard)) {
            return 7;
        }  else if (type.equals(CardConstant.CardType.Museum)) {
            return 8;
        } else if (type.equals(CardConstant.CardType.Toilet)) {
            return 9;
        } else if (type.equals(CardConstant.CardType.Foodtruck)) {
            return 10;
        } else if (type.equals(CardConstant.CardType.Parking)) {
            return 11;
        } else if (type.equals(CardConstant.CardType.Market)) {
            return 12;
        } else if (type.equals(CardConstant.CardType.SimpleText)) {
            return 13;
        } else {
            return super.getItemViewType(position);
        }
    }


    public <T extends Card> void add(T card) {
        mList.add(card);
        notifyDataSetChanged();
    }

    public <T extends Card> void addAll(List<T> cards) {
        mList.addAll(cards);
        notifyDataSetChanged();
    }
}
