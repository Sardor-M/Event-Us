package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Html;

import com.example.final_project.fragments.FavoriteFragment;
import com.example.final_project.fragments.SearchFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private FragmentStateAdapter pageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\'black\'>"+"Event Search"+"</font>"));

        // Data binding of the search view
        viewPager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPager2.setUserInputEnabled(true);
        pageAdapter = new SearchFragmentPagerAdapter(this);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(position == 0 ? "Search" : "Favorites");
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                if(tab.getText().equals("Search")){
                    viewPager2.setCurrentItem(0, true);
                } else if (tab.getText().equals("Favorites")){
                    viewPager2.setCurrentItem(1, true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });
    }

    public class SearchFragmentPagerAdapter extends FragmentStateAdapter{

            public SearchFragmentPagerAdapter(@NonNull AppCompatActivity fragmentActivity) {
                super(fragmentActivity);
            }

            @NonNull
            @Override
            public androidx.fragment.app.Fragment createFragment(int position) {
                if(position == 0){
                    return new SearchFragment();
                } else {
                    return new FavoriteFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
    }

//    public void fetchService(String query) {
//        EventbriteApiService apiService = RetrofitClient
//                .getClient("https://www.eventbriteapi.com/v3/events/")
//                .create(EventbriteApiService.class);
//
//
//        // Will change the token with a valid one
//        Call<EventResponse> call = apiService.searchEvents(query, "Bearer JW2VU6ONX45KL5YNUMTV");
//
//        call.enqueue(new Callback<EventResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<EventResponse> call, @NonNull Response<EventResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                   adapter.updateEvents(new ArrayList<>(response.body().getEvents()));
//                } else {
//                    Log.e("API ERROR Encountered", "Response not successful or body is null");
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<EventResponse> call, @NonNull Throwable t) {
//                Log.e("API ERROR", t.getMessage());
//            }
//        });
//    }
}