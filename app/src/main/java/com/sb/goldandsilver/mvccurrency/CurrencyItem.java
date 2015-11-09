package com.sb.goldandsilver.mvccurrency;

import java.io.Serializable;

/**
 * Created by namudak on 2015-09-14.
 */
public class CurrencyItem implements Serializable {
    private byte[] flag;
    private String currencyId;
    private String name;
    private String alpha3;
    private String id;

    public CurrencyItem(byte[] flag, String currencyId, String name, String alpha3, String id) {
        this.flag = flag;
        this.currencyId = currencyId;
        this.name = name;
        this.alpha3 = alpha3;
        this.id = id;
    }

    public byte[] getFlag() {
        return flag;
    }

    public void setFlag(byte[] flag) {
        this.flag = flag;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
