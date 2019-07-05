package net.fayola.hydraulica;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/***
 * Getting the image from image-chart.com
 * https://image-charts.com/chart?chts=000000,20,r&chtt=Engine+Check+Chart&cht=pc&chd=t:20,50,30|63,20,17|5,19,41,35&chs=300x300
 */
public class GetImageFromURL extends AsyncTask<String, Void, Bitmap>{
    ImageView imgV;

    public GetImageFromURL(ImageView imgV){
        this.imgV = imgV;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String urldisplay = url[0];
        Bitmap bitmap= null;
        try {
            InputStream srt = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(srt);
        } catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imgV.setImageBitmap(bitmap);
    }
}
