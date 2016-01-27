package potato.com.apod.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import potato.com.apod.Explan_Activity;
import potato.com.apod.Interfaces.API;
import potato.com.apod.Models.Image;
import potato.com.apod.R;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Potato on 26.01.2016.
 */
public class PagerFragment extends android.support.v4.app.Fragment {

    static final String ARGUMENT_PAGE_DIFFERENCE="arg_page_number", TAG="Fragment",
            SAVE_PAGE_NUMBER = "save_page_number", API_KEY="W15CLJUaDgxqgU5k2McIPfvdxB4JBvzPwWLGAjLy",
            URL = "https://api.nasa.gov";
    int pageNumber;
    String date;

    public static PagerFragment newInstance(int page){
        PagerFragment fragment = new PagerFragment();
        Bundle arguments = new Bundle();

        arguments.putInt(ARGUMENT_PAGE_DIFFERENCE, page);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_DIFFERENCE);
        Log.d(TAG, "Get arguments");
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState){
        View view = layoutInflater.inflate(R.layout.pager_fragment, null);
        final TextView title = (TextView) view.findViewById(R.id.title);
        final TextView explanation = (TextView) view.findViewById(R.id.explanation);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        date = getDate();
        Log.d(TAG, "Get date " + date);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URL)
                .build();
        Log.d(TAG, "Get RestAdapter");
        API api = restAdapter.create(API.class);
        api.getImage(API_KEY, date, new Callback<Image>() {
            @Override
            public void success(final Image image, Response response) {
                Log.d(TAG, "Load succes");
                title.setText(image.getTitle());
                explanation.setText(image.getExplanation().substring(0, 101) + "...");
                Glide
                        .with(getContext())
                        .load(image.getUrl())
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(imageView);
                explanation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = image.getExplanation();
                        Intent i = new Intent(getActivity(), Explan_Activity.class);
                        i.putExtra("explanat", text);
                        startActivity(i);
                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    //@Override
                    public void onClick(View v) {
                        if(title.getVisibility() == View.VISIBLE){
                        title.setVisibility(View.GONE);
                        explanation.setVisibility(View.GONE);
                            Log.d(TAG, "Make unvisible");}
                        else{
                            title.setVisibility(View.VISIBLE);
                            explanation.setVisibility(View.VISIBLE);
                            Log.d(TAG, "Make visible");
                        }
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        return view;
    }

    private String getDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -pageNumber);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(cal.getTime());
        return formatted;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_PAGE_NUMBER, pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + date);
    }

}
