package mb.com.moovleelogin.Interfaces;

import java.util.List;

import mb.com.moovleelogin.DataBase.BookingDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Anshul on 23-11-17.
 */

public interface ApiInterface {
    String base_Url="http://54.255.191.100:1337/user/ride/";
    @GET("history")
    Call<List<BookingDetails>> getdetails();
}
