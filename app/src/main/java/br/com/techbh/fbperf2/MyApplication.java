package br.com.techbh.fbperf2;


import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import br.com.techbh.fbperf2.api.APIConstants;
import br.com.techbh.fbperf2.api.iAclinAPIv0;
import br.com.techbh.fbperf2.utils.CrashReportingTree;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * @author : devel
 * @since : 14/03/16.
 */
public class MyApplication extends Application {
    private iAclinAPIv0 mApiService;
    private SharedPreferences mSharedPref;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {

            //Solução de log diferente para versão de produção
            Timber.plant(new CrashReportingTree());
        }

        //Prepare Database session
        //Setup Retrofit API
        setupAclinServerAPI();

    }


    private void setupAclinServerAPI(){

        //factory for deserializing the (json) response
        /*
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        */

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // add my interceptors
        // ...
        // end of interceptors

        // add logging as last interceptor
        // Very important !!!!
        httpClient.addInterceptor(logging);

        //Creating the Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        //Create a service based on interface
        mApiService = retrofit.create(iAclinAPIv0.class);

    }

    public iAclinAPIv0 getApiService(){
        return mApiService;
    }



    public SharedPreferences getSharedPrefs() {
        return mSharedPref;
    }





}
