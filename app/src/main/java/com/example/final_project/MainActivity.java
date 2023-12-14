package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.Html;

import com.example.final_project.fragments.FavoriteFragment;
import com.example.final_project.fragments.SearchFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout sfTabLayout;

    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\'black\'>"+"Event Search"+"</font>"));

        viewPager2 = findViewById(R.id.viewpager); //Data Binding
        sfTabLayout = findViewById(R.id.tabLayout);

        viewPager2.setUserInputEnabled(true);

        pagerAdapter = new SearchFragmentPagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(sfTabLayout,viewPager2,(tab, position) -> {
            tab.setText(position==0?"Search":"Favorites");
        }).attach();

        sfTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Search")){
                    viewPager2.setCurrentItem(0,true);
                }
                else if(tab.getText().equals("Favorites")){
                    //Toast.makeText(MainActivity.this,tab.getText(), Toast.LENGTH_SHORT).show();
                    viewPager2.setCurrentItem(1,true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public class SearchFragmentPagerAdapter extends FragmentStateAdapter{

        public SearchFragmentPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position) {

            switch (position){
                case 0: return new SearchFragment();
                case 1: return new FavoriteFragment();
                default: return new SearchFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
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