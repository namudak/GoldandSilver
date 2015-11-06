package com.sb.goldandsilver.mvctext;

import java.io.Serializable;

/**
 * Created by namudak on 2015-09-14.
 */
public class GsTextItem implements Serializable {
    private String time;
    private String goldam;
    private String goldpm;
    private String silver;

    public GsTextItem(String time, String goldam, String goldpm, String silver) {
        this.time= time;
        this.goldam= goldam;
        this.goldpm= goldpm;
        this.silver= silver;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGoldAm() { return goldam; }

    public void setGoldAm(String goldam) {
        this.goldam = goldam;
    }

    public String getGoldPm() { return goldpm; }

    public void setGoldPm(String goldpm) { this.goldpm = goldpm; }

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
