package com.sb.goldandsilver.utility.network;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;

/**
 * Created by namudak on 2015-09-15.
 */
public class NetworkUtility {

    /** url 로 부터 스트림을 읽어 String 으로 반환한다
     * @param url
     * @return
     * @throws IOException
     */
    public String getResponse(String url) throws IOException {

        // 클라이언트 오브젝트
        OkHttpClient okHttpClient = new OkHttpClient();

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
        } catch (GeneralSecurityException e) {
            throw new AssertionError(); // 시스템이 TLS를 지원하지 않습니다
        }
        okHttpClient.setSslSocketFactory(sslContext.getSocketFactory());

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();
    }

}
