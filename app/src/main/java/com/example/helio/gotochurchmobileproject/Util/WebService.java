package com.example.helio.gotochurchmobileproject.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by dayvson on 15/11/17.
 */

public class WebService {

    public String getUrlContents(String theUrl) throws Exception {

        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);

            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }

            bufferedReader.close();
        } catch(Exception e) {
            // Eh bom tratar as exceptions :D
            throw new Exception(e.getMessage());
            //e.printStackTrace();
        }

        return content.toString();
    }
}
