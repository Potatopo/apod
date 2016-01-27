package potato.com.apod.Interfaces;

import potato.com.apod.Models.Image;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Potato on 26.01.2016.
 */
public interface API {
    @GET("/planetary/apod")
    void getImage(@Query("api_key") String api_key, @Query("date")String date, Callback<Image> cb);
}
