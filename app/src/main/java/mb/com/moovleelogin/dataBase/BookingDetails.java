package mb.com.moovleelogin.dataBase;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

/**
 * Created by Anshul on 23-11-17.
 */

public class BookingDetails {

    @SerializedName("driver_name")
    private String driverName;
    @SerializedName("order_id")
    private String bookingId;
    @SerializedName("createdAt")
    private String bookingDate;
    @SerializedName("image")
    private String carImage;

    public BookingDetails(String booking_id, String image, String driver_name,String date) throws JSONException {
        this.bookingId=booking_id;
        this.carImage=image;
        this.driverName=driver_name;
        this.bookingDate=date;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getBookingDate() {

        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingId() {

        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getDriverName() {

        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
