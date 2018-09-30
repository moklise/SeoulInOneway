package com.minseok.seoulinoneway.network;

import android.util.Log;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by minseok on 2018. 7. 10..
 * SeoulInOneWay.
 */
public class RetrofitAPI {
//    private String SEOUL_API_URL = "https://pokeapi.co/api/v2/";
    static public final String SEOUL_API_URL = "http://openapi.seoul.go.kr:8088/";
    static public final String SEOUL_TRAFFIC_API_URL = "http://swopenAPI.seoul.go.kr/api/subway/";
    static public final String SEOUL_WEATHER_API_URL = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/";
    static public final String KAKAO_API_URL = "https://dapi.kakao.com/v2/local/";

    private static RetrofitAPI instance = new RetrofitAPI();

    public static RetrofitAPI getInstance() { return instance; }

    private Retrofit getBasicRetrofit() {
        NetworkInterceptor interceptor = new NetworkInterceptor();
        interceptor.setLevel(NetworkInterceptor.Level.BODY);

        return new Retrofit.Builder()
                .baseUrl(SEOUL_API_URL)
                .client(getUnsafeOkHttpClient(interceptor))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Retrofit getTrafficRetrofit() {
        NetworkInterceptor interceptor = new NetworkInterceptor();
        interceptor.setLevel(NetworkInterceptor.Level.BODY);

        return new Retrofit.Builder()
                .baseUrl(SEOUL_TRAFFIC_API_URL)
                .client(getUnsafeOkHttpClient(interceptor))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Retrofit getWeatherRetrofit() {
        NetworkInterceptor interceptor = new NetworkInterceptor();
        interceptor.setLevel(NetworkInterceptor.Level.BODY);

        return new Retrofit.Builder()
                .baseUrl(SEOUL_WEATHER_API_URL)
                .client(getUnsafeOkHttpClient(interceptor))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private Retrofit getKakaoRetrofit() {
        NetworkInterceptor interceptor = new NetworkInterceptor();
        interceptor.setLevel(NetworkInterceptor.Level.BODY);

        return new Retrofit.Builder()
                .baseUrl(KAKAO_API_URL)
                .client(getUnsafeOkHttpClient(interceptor))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <S> S createService(Class<S> serviceClass) { return getBasicRetrofit().create(serviceClass); }

    public <S> S createTrafficService(Class<S> serviceClass) { return getTrafficRetrofit().create(serviceClass); }

    public <S> S createWeatherService(Class<S> serviceClass) { return getWeatherRetrofit().create(serviceClass); }

    public <S> S createKakaoService(Class<S> serviceClass) { return getKakaoRetrofit().create(serviceClass); }

    public static OkHttpClient getUnsafeOkHttpClient(NetworkInterceptor interceptor) {
        Log.d("RetrofitAPITrace", "Start getUnsafeOkHttpClient");

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder()
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();

            Log.d("RetrofitAPITrace", "END getUnsafeOkHttpClient");

            return okHttpClient;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}