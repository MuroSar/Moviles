package barmau.catalogomoviles.conections;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import barmau.catalogomoviles.CallService;
import barmau.catalogomoviles.MainActivity;
import barmau.catalogomoviles.elementos.Vinilo;

public class LocalReceiver extends BroadcastReceiver {

    private MainActivity mainActivity;

    public LocalReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            JSONArray jsonArray = new JSONArray(intent.getStringExtra(CallService.RESPONSE));

            ArrayList<Vinilo> vinilos = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                Vinilo vinilo = new Vinilo(json.getString("codigo"), json.getString("nombre"), json.getString("color"), json.getDouble("precio"));

                vinilos.add(vinilo);
            }
            mainActivity.showVinilos(vinilos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
