package com.school.baiqing.lovecard.SwipeCardView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.school.baiqing.lovecard.R;
import com.school.baiqing.lovecard.Util.StringHelper;
import com.school.baiqing.lovecard.Util.TimeHelper;
import com.school.baiqing.lovecard.greendao.entity.Card;

import java.util.List;

public class LoveCardAdapter extends SwipeCardAdapter <LoveCardAdapter.MyHolder>{
    private Context mContext;

    public LoveCardAdapter(Context context, List<Card> list) {
        super(list);
        mContext = context;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.swipecard_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.cardCategory.setText(mList.get(position).getCategory());
        holder.cardFrom.setText(mList.get(position).getFrom());
        holder.cardTo.setText(mList.get(position).getTo());
        holder.cardContent.setText(mList.get(position).getContent());
        if(mList.get(position).getPublish() != null)
            holder.cardPublish.setText(TimeHelper.dateToString(mList.get(position).getPublish()));
        if(mList.get(position).getIsFinish()){
            if(mList.get(position).getIsFinish() != null ){
                String finish = "兑换于:"+TimeHelper.dateToString(mList.get(position).getFinish());
                holder.cardisFinish.setText(finish);
            }
        }else {
            holder.cardisFinish.setText("未兑换");
        }

    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView cardCategory,cardFrom,cardTo,cardContent,cardPublish;
        TextView cardisFinish;
        CardView cardView;
        public MyHolder(View itemView) {
            super(itemView);
            cardCategory = (TextView) itemView.findViewById(R.id.card_categoty);
            cardFrom = (TextView) itemView.findViewById(R.id.card_text_from);
            cardTo = (TextView) itemView.findViewById(R.id.card_text_to);
            cardContent = (TextView) itemView.findViewById(R.id.card_text_content);
            cardPublish = (TextView) itemView.findViewById(R.id.card_text_publish);
            cardisFinish = (TextView) itemView.findViewById(R.id.card_button_finish);
            cardView = itemView.findViewById(R.id.swipe_cardView);
        }

    }
}
