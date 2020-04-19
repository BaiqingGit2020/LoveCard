package com.school.baiqing.lovecard.greendao.entity;



import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String Category;
    private String From;
    private String To;
    private String Content;
    private Date Publish;
    private Date Finish;
    private Boolean isFinish;
    @Generated(hash = 2040114407)
    public Card(Long id, @NotNull String Category, String From, String To,
            String Content, Date Publish, Date Finish, Boolean isFinish) {
        this.id = id;
        this.Category = Category;
        this.From = From;
        this.To = To;
        this.Content = Content;
        this.Publish = Publish;
        this.Finish = Finish;
        this.isFinish = isFinish;
    }
    @Generated(hash = 52700939)
    public Card() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCategory() {
        return this.Category;
    }
    public void setCategory(String Category) {
        this.Category = Category;
    }
    public String getFrom() {
        return this.From;
    }
    public void setFrom(String From) {
        this.From = From;
    }
    public String getTo() {
        return this.To;
    }
    public void setTo(String To) {
        this.To = To;
    }
    public String getContent() {
        return this.Content;
    }
    public void setContent(String Content) {
        this.Content = Content;
    }
    public Date getPublish() {
        return this.Publish;
    }
    public void setPublish(Date Publish) {
        this.Publish = Publish;
    }
    public Date getFinish() {
        return this.Finish;
    }
    public void setFinish(Date Finish) {
        this.Finish = Finish;
    }
    public Boolean getIsFinish() {
        return this.isFinish;
    }
    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }


}
