package com.sb.goldandsilver.list;

import java.io.Serializable;

/**
 * Created by namudak on 2015-09-14.
 */
public class GoldSilverItem implements Serializable {
    private String time;
    private String goldam;
    private String silver;

    public GoldSilverItem(String time, String goldam, String silver) {
        this.time= time;
        this.goldam= goldam;
        this.silver= silver;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoldAm() {
        return goldam;
    }

    public void setGoldAm(String goldam) {
        this.goldam = goldam;
    }

    public String getSilver() {
        return silver;
    }

    public void setSilver(String silver) {
        this.silver = silver;
    }

    @Override
    public String toString() {
        return this.toString();
    }
}
