package mb.com.moovleelogin.interfaces;

import java.util.List;

import mb.com.moovleelogin.dataBase.BookingDetails;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Anshul on 23-11-17.
 */

public interface ApiInterface
{
    //Url from where data will come
    String base_Url="http://54.255.191.100:1337/user/ride/";
    @GET("history")
    Call<List<BookingDetails>> getdetails();
}
