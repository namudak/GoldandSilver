package com.sb.goldandsilver.managers;

import android.support.v4.app.Fragment;

import com.sb.goldandsilver.fragments.CurrencyFragment;
import com.sb.goldandsilver.fragments.GsFragment;
import com.sb.goldandsilver.fragments.GsGraphFragment;
import com.sb.goldandsilver.fragments.GsTextFragment;

/**
 * Created by junsuk on 2015. 10. 15..
 *
 * Fragment 관리 클래스
 */
public class Manager {

    // 메뉴 순서대로 Fragment 를 배열로 지정
    public static Class FRAGMENTS[] = {
            GsFragment.class,
            GsTextFragment.class,
            GsGraphFragment.class,
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
