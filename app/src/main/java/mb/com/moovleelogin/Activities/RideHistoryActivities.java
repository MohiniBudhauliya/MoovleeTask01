package mb.com.moovleelogin.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import mb.com.moovleelogin.DataBase.BookingDetails;
//import mb.com.moovleelogin.Interfaces.APIClient;
import mb.com.moovleelogin.Interfaces.ApiInterface;
import mb.com.moovleelogin.R;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anshul on 23-11-17.
 */

public class RideHistoryActivities extends Fragment {
    private RecyclerView recyclerView;
    private CustomAdapter adapter;     //to bind our data rom webserver
    private List<BookingDetails> bookingDetails;
    private ApiInterface apiInterface;
    private RecyclerView.LayoutManager layoutmanager;
    public static OkHttpClient defaultHttpClient;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_ridehistory, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.rideHistoryXML);
        layoutmanager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutmanager);//new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false
        recyclerView.setHasFixedSize(true);

         defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request request = chain.request().newBuilder()
                                        .addHeader("authorization", "bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoicnV0dXJhaiB0ZXN0MiIsImVtYWlsIjoiZGV2ZWxvcGVyQG1vb3ZsZWUuY29tIiwicGhvbmUiOiI5NjMyMDY1NjQ5IiwicGFzc3dvcmQiOiJ0ZXN0IiwidHlwZSI6ImN1c3RvbSIsInN1YnNjcmliZWRUbyI6W10sInJvbGUiOiJ1c2VyIiwiaXNfb3RwX3ZlcmlmaWVkIjp0cnVlLCJyYXRpbmciOjEsImNyZWF0ZWRBdCI6IjIwMTYtMTEtMjlUMDY6MTg6MTIuNTE2WiIsInVwZGF0ZWRBdCI6IjIwMTctMDgtMDJUMjE6MTI6MTYuOTUxWiIsIm90cCI6ODIzNTYsInJpZGVfY291bnQiOjE4MiwicmVmZXJyYWxfY29kZSI6IlJFRjk2MzIwNjU2NDkiLCJyZWZlcnJhbF9ib251cyI6MCwicmVmZXJyZWRfYnkiOm51bGwsImltYWdlX3VybCI6Imh0dHBzOi8vdHctdXNlci1pbWFnZXMuczMuYW1hem9uYXdzLmNvbS82ZWU4NWJlMC0wMGE0LTQyNDMtOGMwMS0xMzYzMGMyOTE1ZmQuanBnIiwicG9saWN5X2FjY2VwdGVkIjp0cnVlLCJpc19wYXl0bV9saW5rZWQiOnRydWUsInBheXRtIjp7InRva2VuIjoiSTR0THBIR3NRMVBjNXV5b0ZDVVFGZlI3dm9TaDA0RjdxNUFTTkdlVVRYdjJZSDBxV2YxRFZWcDFocVFJRmYyWiIsInBob25lIjoiODA4Nzg0NDM2NiIsImV4cGlyZXMiOiIxNTAyNDM4MzE1OTM4In0sImlkIjoiNTgzZDFkYTRmYWEwODhlZjFjYTdlM2JiIiwiaWF0IjoxNTAxNzU4OTU2LCJleHAiOjE1MzMyOTQ5NTZ9.i_D48-t_sOUBCoNNVXVrvgmQjasxA_kpF_P9dZIhQ74").build();
                                return chain.proceed(request);
                            }
                        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.base_Url)
                .client(defaultHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        progressDialog = ProgressDialog.show(getActivity(), "","Please Wait...", true);
        apiInterface = retrofit.create(ApiInterface.class);
        Call<List<BookingDetails>> call = apiInterface.getdetails();

        //ApiInterface apiInterface= APIClient.getApiClient().create(ApiInterface.class);
        //Call<List<BookingDetails>> call=apiInterface.getdetails();
        call.enqueue(new Callback<List<BookingDetails>>() {
            @Override
            public void onResponse(Call<List<BookingDetails>> call, Response<List<BookingDetails>> response) {
                bookingDetails=response.body();
                adapter=new CustomAdapter(getActivity(),bookingDetails);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Your Ride History", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<BookingDetails>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to show ride", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
