package br.com.techbh.fbperf2;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;

import br.com.techbh.fbperf2.api.iAclinAPIv0;
import br.com.techbh.fbperf2.utils.ConnectivityInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Request taking place ...", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null).show();

                doRequest();
            }
        });
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


    public void doRequest(){
        final Context appContext;
        try {
            appContext = this.getApplication().getApplicationContext();
        } catch (NullPointerException e){
            Timber.e(e, "Can't retrieve application context");
            return;
        }

        if(!ConnectivityInfo.isInternetUp(appContext)){
            Toast.makeText(appContext, "Verifique a internet antes de sincronizar as informações", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(appContext, "Request...", Toast.LENGTH_SHORT).show();

        MyApplication app = ((MyApplication) this.getApplication());

        iAclinAPIv0 apiService = app.getApiService();

        //chama a URI do resource desejado
        Call<JsonObject> call = apiService.getCrashUrl2();
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // request successful (status code 200, 201)
                    JsonObject json = response.body();
                    Timber.e("Success");

                } else {
                    Timber.e("Failed %s", response.toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Timber.e(t,"Acesso realizado com falha");
            }

        });

    }



}
