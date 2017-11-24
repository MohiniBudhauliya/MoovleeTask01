package mb.com.moovleelogin.Activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mb.com.moovleelogin.DataBase.BookingDetails;
import mb.com.moovleelogin.R;

/**
 * Created by Anshul on 23-11-17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    //this context we will use to inflate the layout
    Context context;
    LayoutInflater inflater;
    public List<BookingDetails> bookingdetails;
    //RecyclerView mrecyclerView;
    ViewGroup parent;
    LinearLayout view;
    public CustomAdapter(Context context, List<BookingDetails> bookingdetails)
    {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.bookingdetails=bookingdetails;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         this.parent=parent;
        LayoutInflater inflater=LayoutInflater.from(context);
        try {
           view  = (LinearLayout) inflater.inflate(R.layout.cardvieworderdetails, null);
        }catch(Exception e)
        {
            e.printStackTrace();
        }


//View view= LayoutInflater.from(context).inflate(R.layout.cardvieworderdetails,parent,false);

//        View view=inflater.inflate(R.layout.cardvieworderdetails,parent,false);
//        mrecyclerView=(RecyclerView)parent;
//        return new MyViewHolder(view);

//        View view=inflater.inflate(R.layout.cardvieworderdetails,null);
//        MyViewHolder holder=new MyViewHolder(view);
//        return holder;
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BookingDetails bdobj = bookingdetails.get(position);

        String time=bdobj.getBookingDate();
        String[] separated = time.split("T");
        String day=separated[0];
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time=separated[1];
        String[] hr=time.split(":");
        String min=hr[1];
        String[] sec=min.split(":");
        min=sec[0];
        day = outputFormat.format(date);

        //binding the data with the viewholder views
        holder.drivername.setText("Driver :"+bookingdetails.get(position).getDriverName());
        holder.orderId.setText("Order Id:"+bookingdetails.get(position).getBookingId());
        holder.date.setText("Date : "+day+"\n\nTime : "+ hr[0]+":"+min);
        Glide.with(context.getApplicationContext()).load(bdobj.getCarImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.carimage);


    }

    @Override
    public int getItemCount() {
        return bookingdetails.size();
    }
    public  class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView drivername,orderId,date;
        ImageView carimage;
        public MyViewHolder(View itemView) {
            super(itemView);
            drivername= (TextView)itemView.findViewById(R.id.drivername);
            orderId= (TextView)itemView.findViewById(R.id.bookingId);
            date= (TextView)itemView.findViewById(R.id.bookingDate);
            carimage= (ImageView)itemView.findViewById(R.id.carImage);
        }
    }
}
