package barmau.catalogomoviles;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallService extends IntentService {

    public static final String RESPONSE_ACTION = "Respuesta del servidor";
    public static final String RESPONSE = "DATA RESPONSE";
    public static final String SERVICE_TYPE = "SERVICE_TYPE";
    final String BASE_URL = "http://192.168.0.18:8080/RestWebService/catalogo/";
    static final String TAG = CallService.class.getCanonicalName();

    public CallService() {
        super("CallerServiceTest");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Uri builtURI = Uri.parse(BASE_URL + "getcatalogo").buildUpon().build();
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL requestURL = new URL(builtURI.toString());

            conn = (HttpURLConnection) requestURL.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int responseCode = conn.getResponseCode();

            is = conn.getInputStream();
            String contentAsString = toString(is);

            Log.d(TAG, contentAsString);

            Intent response = new Intent(RESPONSE_ACTION);
            response.putExtra(RESPONSE, contentAsString);
            LocalBroadcastManager.getInstance(this).sendBroadcast(response);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String toString(InputStream stream) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        BufferedReader buffer = new BufferedReader(reader);
        String line = buffer.readLine();
        return line;
    }
}
