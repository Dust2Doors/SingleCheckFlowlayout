package com.zym.widget.helper.pinner;

/**
 * Created by zym on 2017/8/22.
 */

public class PinnerItem {
    public String week;
    public String date;
    public double money;
    public String desc;
    public String month;
    public boolean showMonth;

    public PinnerItem(String week, String date, double money, String desc,String month) {
        this.week = week;
        this.date = date;
        this.money = money;
        this.desc = desc;
        this.month = month;
    }
}
