package com.ppp2k1.btc;

import android.os.StrictMode;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.util.Locale;

public class WazirXClient {
    public static String getBTC() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpGet get = new HttpGet("https://api.wazirx.com/api/v2/tickers/btcinr");
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getConnectionManager().getSchemeRegistry().register( new Scheme("https", SSLSocketFactory.getSocketFactory(), 443) );
        HttpResponse response = null;
        try {
            response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String result = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
                String btcValue = result.split("\"buy\":")[1].split(",")[0].replaceAll("\"","");
                Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                System.out.println(format.format(new BigDecimal(btcValue)));
                return "BTC: " + format.format(new BigDecimal(btcValue));
            } else {
                return "error";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
