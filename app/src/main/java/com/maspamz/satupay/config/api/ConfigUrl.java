package com.maspamz.satupay.config.api;

import static com.maspamz.satupay.BuildConfig.BASE_URL;

/**
 * Created by Maspamz on 29/09/2018.
 */

public class ConfigUrl {

    //URL Depan
    public static String homeURL = BASE_URL + "ini/";

    //URL Produk
    public static String productURL = BASE_URL + "/ini/member/?m=ppob";

    //URL Transaksi
    public static String transactionURL = BASE_URL + "/ini/member/?m=transaksi";

    //URL Promo
    public static String promoURL = BASE_URL + "/ini/member/?m=promo";

    //URL Peraturan
    public static String faqURL = BASE_URL + "/ini/?m=tos";

    //URL Tanya Jawab
    public static String questionURL = BASE_URL + "/ini/?m=faq";

    //URL Bantuan
    public static String helpURL = BASE_URL + "/ini/?m=help";

    //URL Tentang
    public static String aboutURL = BASE_URL + "/ini/?m=about";

}
