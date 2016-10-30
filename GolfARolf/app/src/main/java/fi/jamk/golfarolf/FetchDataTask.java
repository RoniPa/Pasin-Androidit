package fi.jamk.golfarolf;

import android.os.AsyncTask;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class FetchDataTask extends AsyncTask<String, Void, String> {
    DataDownloadListener dataDownloadListener;

    public void setDataDownloadListener(DataDownloadListener dataDownloadListener) {
        this.dataDownloadListener = dataDownloadListener;
    }

    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (MalformedURLException e) {
            if (this.dataDownloadListener != null) {
                this.dataDownloadListener.dataDownloadFailed();
            }
            e.printStackTrace();
        } catch (IOException e) {
            if (this.dataDownloadListener != null) {
                this.dataDownloadListener.dataDownloadFailed();
            }
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return null;
    }

    protected void onPostExecute(String response) {
        System.out.println("Succesfully loaded data: "+ response);

        if (this.dataDownloadListener != null) {
            try {
                dataDownloadListener.dataDownloadedSuccessfully(new JSONObject(response));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    interface DataDownloadListener {
        void dataDownloadedSuccessfully(JSONObject data);
        void dataDownloadFailed();
    }
}
