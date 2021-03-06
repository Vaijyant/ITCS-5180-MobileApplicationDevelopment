package com.vaijyant.musicsearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by vaijy on 2017-10-08.
 */

class ImageAsyncTask extends AsyncTask<Object/*input*/, Void/*progress*/, Bitmap/*result*/> {
    String TAG ="VT";
    ImageView imageViewTrack;
    @Override
    protected void onPreExecute() {
    }

    @Override
    protected /*result*/Bitmap doInBackground(/*input*/Object... params) {
        imageViewTrack = (ImageView) params[1];
        Bitmap image = null;
        InputStream in = null;
        try {
            //provide URL string
            URL url = new URL(params[0].toString());
            //to set connection to the url
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            //set the Request method for the http page
            con.setRequestMethod("GET");
            in = con.getInputStream();
            //to read images
            image = BitmapFactory.decodeStream(in);
            return image;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        return image;

    }

    @Override
    protected void onProgressUpdate(/*progress*/Void... values) {

    }

    @Override
    protected void onPostExecute(/*result*/Bitmap result) {
        imageViewTrack.setImageBitmap(result);
    }

}