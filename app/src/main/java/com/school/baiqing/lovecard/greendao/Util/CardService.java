package com.school.baiqing.lovecard.greendao.Util;

import android.database.Cursor;

import com.school.baiqing.lovecard.Util.StringHelper;
import com.school.baiqing.lovecard.greendao.entity.Card;
import com.school.baiqing.lovecard.greendao.gen.CardDao;
import com.school.baiqing.lovecard.greendao.gen.DaoSession;

import java.util.ArrayList;
import java.util.List;

public class CardService  extends BaseService{

    CardDao cardDao ;
    public CardService(){
        cardDao = GreenDaoManager.getInstance().getSession().getCardDao();
    }

    private List<Card> findBooks(String sql, String[] selectionArgs) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            Cursor cursor = selectBySql(sql, selectionArgs);
            while (cursor.moveToNext()) {
                Card card = new Card();
                card.setId(cursor.getLong(0));
                card.setCategory(cursor.getString(1));
                card.setFrom(cursor.getString(2));
                card.setTo(cursor.getString(3));
                card.setContent(cursor.getString(4));
                card.setPublish(StringHelper.stringToDate(cursor.getString(5)));
                card.setFinish(StringHelper.stringToDate(cursor.getString(6)));
                cards.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cards;
    }

    /**
     * 通过ID查书
     * @param id
     * @return
     */
    public Card getBookById(long id) {
        return cardDao.load(id);
    }

    /**
     * 获取所有的书
     * @return
     */
    public List<Card> getAllCards(){
        List<Card> list = cardDao.loadAll();
        return  list;
    }

    /**
     * 新增书
     * @param card
     */
    public void addCard(Card card){
        addEntity(card);
    }

    /**
     * 查
     *
     */
    public List<Card > findCardByCategory(String category){
        List<Card > cards = null;
        cards = cardDao.queryBuilder().where(CardDao.Properties.Category.eq(category)).list();
        return cards;
    }
    public List<Card > findCardByFrom(String from) {
        List<Card> cards = null;
        cards = cardDao.queryBuilder().where(CardDao.Properties.From.eq(from)).list();
        return cards;
    }
    public List<Card > findCardByTo(String to) {
        List<Card> cards = null;
        cards = cardDao.queryBuilder().where(CardDao.Properties.To.eq(to)).list();
        return cards;
    }
    public List<Card > findCardByFinish(Boolean isfinish) {
        List<Card> cards = null;
        cards = cardDao.queryBuilder().where(CardDao.Properties.IsFinish.eq(isfinish)).list();
        return cards;
    }


        /**
         * 删除书
         * @param id
         */
    public void deleteCardById(long id){
        cardDao.deleteByKey(id);
    }
    public void deleteAllCard(){
        cardDao.deleteAll();
    }
    /**
     * 删除书
     * @param card
     */
    public void deleteCard(Card card){
        deleteEntity(card);
    }

    public void updateCard(Card card){
        cardDao.update(card);
    }

    public void updateCards(List<Card> cards){
        cardDao.updateInTx(cards);
    }
}
