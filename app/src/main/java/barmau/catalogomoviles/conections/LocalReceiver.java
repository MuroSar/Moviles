package barmau.catalogomoviles.conections;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.Call;

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
            String response = intent.getStringExtra(CallService.RESPONSE);

            if (response == null) {
                mainActivity.showNoElements();
                return;
            }

            if (response.contains("[") && response.contains("]")) {
                JSONArray jsonArray = new JSONArray(intent.getStringExtra(CallService.RESPONSE));

                ArrayList<Vinilo> vinilos = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);

                    Vinilo vinilo = new Vinilo(json.getString("codigo"), json.getString("nombre"), json.getString("color"), json.getDouble("precio"));

                    vinilos.add(vinilo);

                    mainActivity.showVinilos(vinilos);
                }
            } else {
                JSONObject jsonObject = new JSONObject(intent.getStringExtra(CallService.RESPONSE));

                Vinilo vinilo = new Vinilo(jsonObject.getString("codigo"), jsonObject.getString("nombre"), jsonObject.getString("color"), jsonObject.getDouble("precio"));

                mainActivity.setViniloDialog(vinilo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
