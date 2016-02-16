package hms.homeautomationsystem;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Alan on 16/02/2016.
 */
public class GetReq extends AsyncTask<URL, Integer, String> {

    @Override
    protected String doInBackground(URL... params) {

        String result = null;

        try {

            URL url = new URL("http://192.167.1.31:8080/Test/hi");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            //view.setText(in.readLine());
            result = in.readLine();

        } catch (MalformedURLException e){
            result = "Malformed URL";
        } catch (IOException e){
            result = "IO Error";
        }

        return result;

    }

}
