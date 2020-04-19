package com.school.baiqing.lovecard.AddDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.school.baiqing.lovecard.R;
import com.school.baiqing.lovecard.Util.TextHelper;
import com.school.baiqing.lovecard.Util.TimeHelper;
import com.school.baiqing.lovecard.greendao.entity.Card;

public class CardDialogFragment  extends DialogFragment {

    private TextView cardCategory,cardFrom,cardTo,cardContent;
    private Button finish,cancle;

    private DialogListener dialogListener;

    public static CardDialogFragment newInstance() {
        Bundle args = new Bundle();
        CardDialogFragment fragment = new CardDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        window.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.redius_of_constrainlayout));
        window.setWindowAnimations(R.style.Animation_Design_BottomSheetDialog);
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_card_dialog_fragment, container, false);
        cardCategory = view.findViewById(R.id.add_card_catogory_text);
        cardFrom = view.findViewById(R.id.add_card_from_text);
        cardTo = view.findViewById(R.id.add_card_to_text);
        cardContent = view.findViewById(R.id.add_card_content_text);
        cancle = view.findViewById(R.id.add_card_cancle);
        finish = view.findViewById(R.id.add_card_finish);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onCancle(getCardFromDialog());
                dismiss();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCard()){
                    dialogListener.onFinish(getCardFromDialog());
                    dismiss();
                }
            }
        });
        return view;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        return dialog ;
    }
    public void setDialogListener(DialogListener dialogListener){
        this.dialogListener = dialogListener;
    }
    public boolean checkCard(){
        if(cardCategory.getText().toString().isEmpty()){
            TextHelper.showText("卡卡类型不能为空");
            return false;
        }
        if(cardFrom.getText().toString().isEmpty()){
            TextHelper.showText("卡卡从哪里来不能为空");
            return false;
        }
        if(cardTo.getText().toString().isEmpty()){
            TextHelper.showText("卡卡到哪里去不能为空");
            return false;
        }
        if(cardContent.getText().toString().isEmpty()){
            TextHelper.showText("卡卡规则不能为空");
            return false;
        }
        return true;
    }
    public Card getCardFromDialog(){
        Card addCard= new Card();
        addCard.setCategory(cardCategory.getText().toString());
        addCard.setFrom(cardFrom.getText().toString());
        addCard.setTo(cardTo.getText().toString());
        addCard.setContent(cardContent.getText().toString());
        addCard.setPublish(TimeHelper.getNow());
        addCard.setIsFinish(false);
        return addCard;
    }
}
