package br.com.techbh.fbperf2.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.POST;

/** Defines API interface
 *
 * @author : Claudio Bastos
 * @since : 19/01/16.
 */
public interface iAclinAPIv0 {

    //This last character '?' is the problem, with it, request crashes
    @POST("handler2.php?")
    Call<JsonObject> getCrashUrl2();

    //This don't crashes
    @POST("handler2.php")
    Call<JsonObject> getNoCrash();


}
