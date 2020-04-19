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

public class EditDialogFragment extends DialogFragment {

    private TextView cardCategory,cardFrom,cardTo,cardContent;
    private Button finish,delete,edit;
    private Card editCard;
    private DialogListener dialogListener;

    public static EditDialogFragment newInstance(Card card) {
        Bundle args = new Bundle();
        args.putSerializable("editCard",card);
        EditDialogFragment fragment = new EditDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editCard = (Card) getArguments().getSerializable("editCard");
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
        View view = inflater.inflate(R.layout.edit_card_dialog_fragment, container, false);
        cardCategory = view.findViewById(R.id.edit_card_catogory_text);
        cardFrom = view.findViewById(R.id.edit_card_from_text);
        cardTo = view.findViewById(R.id.edit_card_to_text);
        cardContent = view.findViewById(R.id.edit_card_content_text);
        delete = view.findViewById(R.id.edit_card_delete);
        edit = view.findViewById(R.id.edit_card_edit);
        finish = view.findViewById(R.id.edit_card_finish);
        setEditCard();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onCancle(getCardFromDialog());
                dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCard()){
                    Card card = getCardFromDialog();
                    dialogListener.onFinish(card);
                    dismiss();
                }
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCard()){
                    Card card = getCardFromDialog();
                    card.setIsFinish(!card.getIsFinish());
                    card.setFinish(TimeHelper.getNow());
                    checkState(card.getIsFinish(),finish);
                    dialogListener.onFinish(card);
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
        dialog.setCanceledOnTouchOutside(true);
        return dialog ;
    }

    public void setEditCard() {
        if(editCard != null){
            cardCategory.setText(editCard.getCategory());
            cardFrom.setText(editCard.getFrom());
            cardTo.setText(editCard.getTo());
            cardContent.setText(editCard.getContent());
            checkState(editCard.getIsFinish(),finish);
        }
    }
    public void checkState(Boolean isFinish,Button button){
        if(isFinish)button.setText("已兑换");
            else button.setText("兑换");
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

        addCard.setId(editCard.getId());
        addCard.setPublish(editCard.getPublish());
        addCard.setFinish(editCard.getFinish());
        addCard.setIsFinish(editCard.getIsFinish());
        return addCard;
    }
}

