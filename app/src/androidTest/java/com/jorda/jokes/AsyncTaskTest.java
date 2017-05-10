package com.jorda.jokes;

import android.os.AsyncTask;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import com.jorda.jokesapp.backend.myApi.MyApi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by xj1 on 07/05/2017.
 */

public class AsyncTaskTest extends InstrumentationTestCase
{
    CountDownLatch countDownLatch;
    private static MyApi myApiService = null;
    String returnedResult;
    AsyncTask asyncTask;

    public AsyncTaskTest()
    {}

    public void test() throws Throwable
    {
        countDownLatch = new CountDownLatch(1);

        asyncTask = new AsyncTask<Object, Void, String>()
        {
            @Override
            protected String doInBackground(Object... params)
            {
                if (myApiService == null)
                {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer()
                            {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException
                                {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
                    // end options for devappserver

                    myApiService = builder.build();
                }

                try {
                    return myApiService.getJokeFromLibrary().execute().getData();
                    //  return myApiService.sayHi("Bebo").execute().getData();

                } catch (IOException e) {
                    Log.e("IOException: ", e + "");
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                returnedResult = result;
                countDownLatch.countDown();
            }
        };

        //execute asynctask on ui thread
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {

                asyncTask.execute();
            }
        });

     /* The testing thread will wait here until the UI thread releases it
     * above with the countDown() or 10 seconds passes and it times out.
     */
        countDownLatch.await(10, TimeUnit.SECONDS);

        assertEquals(returnedResult.isEmpty(), false);
    }
}