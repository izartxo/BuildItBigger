package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.androidjavalibrary.AndroidMainActivity;
import com.udacity.gradle.builditbigger.androidjavalibrary.AndroidMainActivityFragment;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.javajokeslib.JavaJokes;



import java.io.IOException;


public class MainActivity extends AppCompatActivity{

    @Nullable
    private MyIdlingResource mIdlingResource;

    private DownloadListener callback;

    private ProgressBar pb;

    private InterstitialAd interstitialAd;

    private String mJoke = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.pBar);
        pb.setVisibility(View.GONE);

        if(!BuildConfig.FLAVOR.equals("paid")) {
            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
            interstitialAd.loadAd(new AdRequest.Builder().build());

            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    launchActivity(mJoke);
                    interstitialAd.loadAd(new AdRequest.Builder().build());
                }

            /*@Override
            public void onAdFailedToLoad(int i) {

            }

            @Override
            public void onAdLeftApplication() {

            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdImpression() {

            }*/
            });

        }
        callback = new DownloadListener() {
            @Override
            public void onCompleted(String joke) {
                if(!BuildConfig.FLAVOR.equals("paid")) {
                    mJoke = joke;
                    if (interstitialAd.isLoaded())
                        interstitialAd.show();
                    else
                        Log.d("################", "NO AD NO AD");
                }else
                    launchActivity(joke);

            }
        };

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {

        pb.setVisibility(View.VISIBLE);

        new EndpointsAsyncTask(callback).execute(new Pair<Context, String>(this, new JavaJokes().getJoke()));

    }




    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;
        private Context context;
        private DownloadListener mCallback;


        EndpointsAsyncTask(DownloadListener callback){
            mCallback = callback;
            mIdlingResource = (MyIdlingResource) getIdlingResource();

        }

        @Override
        protected String doInBackground(Pair<Context, String>... params) {

            mIdlingResource.setIdleState(false);


            if(myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // I have problems with GCE and anyone response my questions on forums..
                        .setRootUrl("https://izartxobuilditbigger.appspot.com/_ah/api")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(false);
                            }
                        });


                myApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
                return myApiService.getJoke(name).execute().getData();
            } catch (IOException e) {
                return "";

            }
        }

        @Override
        protected void onPostExecute(String result) {
           pb.setVisibility(View.GONE);
           //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
           // Test data on app, joke_text invisible
           TextView jokeText = (TextView) findViewById(R.id.joke_text);
           jokeText.setText(result);

           mIdlingResource.setIdleState(true);
           mCallback.onCompleted(result);
        }
    }

    void launchActivity(String result){



        Intent intent = new Intent(getApplicationContext(), AndroidMainActivity.class);
        intent.putExtra("data", result);

        startActivity(intent);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new MyIdlingResource();
        }
        return mIdlingResource;
    }

    public interface DownloadListener{
        public void onCompleted(String joke);
    }


}
