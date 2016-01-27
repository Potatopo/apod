package potato.com.apod.Models;

/**
 * Created by Potato on 26.01.2016.
 */
public class Image {

    public String date;
    public String explanation;
    public String hdurl;
    public String media_type;
    public String service_version;
    public String title;
    public String url;

    public Image(String date, String explanation, String hdurl,
                 String media_type, String service_version, String title,
                 String url) {
        super();

        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.media_type = media_type;
        this.service_version = service_version;
        this.title = title;
        this.url = url;
    }
    public String getTitle(){
        return title;
    }
    public String getExplanation(){
        return explanation;
    }
    public String getUrl(){
        return url;
    }
}
