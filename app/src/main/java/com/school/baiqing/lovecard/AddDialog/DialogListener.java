package com.school.baiqing.lovecard.AddDialog;

import com.school.baiqing.lovecard.greendao.entity.Card;

public interface DialogListener {
    void onFinish(Card card);
    void onCancle(Card card);
}
