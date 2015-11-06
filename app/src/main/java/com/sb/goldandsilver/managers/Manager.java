package com.sb.goldandsilver.managers;

import android.support.v4.app.Fragment;

import com.sb.goldandsilver.fragment.CurrencyFragment;
import com.sb.goldandsilver.fragment.GsTextFragment;

/**
 * Created by junsuk on 2015. 10. 15..
 *
 * Fragment 관리 클래스
 */
public class Manager {

    // 메뉴 순서대로 Fragment 를 배열로 지정
    public static Class FRAGMENTS[] = {
            GsTextFragment.class,
            CurrencyFragment.class
    };

    private Manager() {
    }

    public static Fragment getInstance(int position) {
        try {
            return (Fragment) FRAGMENTS[position].newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
