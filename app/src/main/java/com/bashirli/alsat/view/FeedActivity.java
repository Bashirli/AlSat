package com.bashirli.alsat.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bashirli.alsat.R;
import com.bashirli.alsat.app.AddPostFragmentDirections;
import com.bashirli.alsat.app.HaqqindaFragmentDirections;
import com.bashirli.alsat.app.MainFragmentDirections;
import com.bashirli.alsat.app.MyPostsFragmentDirections;
import com.bashirli.alsat.app.ProfileFragmentDirections;
import com.bashirli.alsat.app.SearchFragmentDirections;
import com.bashirli.alsat.app.WhatCanIdoFragment;
import com.bashirli.alsat.app.WhatCanIdoFragmentDirections;
import com.bashirli.alsat.databinding.ActivityFeedBinding;
import com.bashirli.alsat.model.Data;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


public class FeedActivity extends AppCompatActivity {
 private ActivityFeedBinding binding;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private FirebaseAuth auth;
    public BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFeedBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,binding.drawer,R.string.nav_open,R.string.nav_close);
        binding.drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth=FirebaseAuth.getInstance();

        bottomNavigationView=binding.bottomid;


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.mainpage){
                    try {
                        NavDirections navDirections = WhatCanIdoFragmentDirections.actionWhatCanIdoFragmentToMainFragment();
                        Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                    }catch(Exception e){

                    }
                    try {
                        NavDirections navDirections = HaqqindaFragmentDirections.actionHaqqindaFragmentToMainFragment();
                        Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                    }catch(Exception e){

                    }



                    try {
                        NavDirections navDirections = SearchFragmentDirections.actionSearchFragmentToAddPostFragment();
                        Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                    }catch(Exception e){

                    }
                    try {
                        NavDirections navDirections = MyPostsFragmentDirections.actionMyPostsFragmentToMainFragment();
                        Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                    }catch(Exception e){

                    }
                    try {
                        NavDirections navDirections = ProfileFragmentDirections.actionProfileFragmentToMainFragment();
                        Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                    }catch(Exception e){

                    }
                    try{
                        NavDirections navDirections= AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
                        Navigation.findNavController(FeedActivity.this,R.id.fragmentContainerView2).navigate(navDirections);

                    }catch (Exception e){

                    }
                }else if(item.getItemId()==R.id.addpost) {
                    if(auth.getCurrentUser().isAnonymous()){
                        Snackbar.make(view,"Anonim giriş etdiyiniz üçün bura girə bilməzsiniz!",Snackbar.LENGTH_SHORT).show();
                        return false;
                    }

                    try {
                        try {
                            NavDirections navDirections = WhatCanIdoFragmentDirections.actionWhatCanIdoFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = HaqqindaFragmentDirections.actionHaqqindaFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = MyPostsFragmentDirections.actionMyPostsFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = ProfileFragmentDirections.actionProfileFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = SearchFragmentDirections.actionSearchFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        MainFragmentDirections.ActionMainFragmentToAddPostFragment action= MainFragmentDirections.actionMainFragmentToAddPostFragment();
                        action.setInfo("new");
                        Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(action);
                    }catch (Exception e){
                        System.out.println(e.getLocalizedMessage());
                    }
                }else if(item.getItemId()==R.id.search){
                    try {
                        try {
                            NavDirections navDirections = WhatCanIdoFragmentDirections.actionWhatCanIdoFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = HaqqindaFragmentDirections.actionHaqqindaFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = MyPostsFragmentDirections.actionMyPostsFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = SearchFragmentDirections.actionSearchFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        try {
                            NavDirections navDirections = ProfileFragmentDirections.actionProfileFragmentToMainFragment();
                            Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                        }catch(Exception e){

                        }
                        NavDirections navDirections=MainFragmentDirections.actionMainFragmentToSearchFragment();
                        Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                    }catch (Exception e){
                        System.out.println(e.getLocalizedMessage());
                    }
                }

                return false;
            }
        });


        binding.navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if(item.getItemId()==R.id.logout){
                   auth.signOut();
                   Toast.makeText(FeedActivity.this,"Hesabdan çıxıldı!",Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(FeedActivity.this,ScreenActivity.class);
                   startActivity(intent);
                   finish();
               }else if(item.getItemId()==R.id.exit){
                   finish();
               }else if(item.getItemId()==R.id.myprofile){
                   if(auth.getCurrentUser().isAnonymous()){
                       Snackbar.make(view,"Anonim giriş etdiyiniz üçün bura girə bilməzsiniz!",Snackbar.LENGTH_SHORT).show();
                       return false;
                   }
                   try {
                       try {
                           NavDirections navDirections = WhatCanIdoFragmentDirections.actionWhatCanIdoFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = HaqqindaFragmentDirections.actionHaqqindaFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = MyPostsFragmentDirections.actionMyPostsFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = SearchFragmentDirections.actionSearchFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       NavDirections navDirections=MainFragmentDirections.actionMainFragmentToProfileFragment();
                       Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                   }catch (Exception e){
                       System.out.println(e.getLocalizedMessage());
                   }


               }else if(R.id.myposts==item.getItemId()){
                   if(auth.getCurrentUser().isAnonymous()) {
                       Snackbar.make(view,"Anonim giriş etdiyiniz üçün bura girə bilməzsiniz!",Snackbar.LENGTH_SHORT).show();
                       return false;
                   }
                   try {
                       try {
                           NavDirections navDirections = WhatCanIdoFragmentDirections.actionWhatCanIdoFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = HaqqindaFragmentDirections.actionHaqqindaFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {

                           NavDirections navDirections = ProfileFragmentDirections.actionProfileFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = MyPostsFragmentDirections.actionMyPostsFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = SearchFragmentDirections.actionSearchFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       NavDirections navDirections=MainFragmentDirections.actionMainFragmentToMyPostsFragment();
                       Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                   }catch (Exception e){
                       System.out.println(e.getLocalizedMessage());
                   }



               }else if(R.id.haqqinda==item.getItemId()){
                   try {
                       try {
                           NavDirections navDirections = WhatCanIdoFragmentDirections.actionWhatCanIdoFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = HaqqindaFragmentDirections.actionHaqqindaFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {

                           NavDirections navDirections = ProfileFragmentDirections.actionProfileFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = MyPostsFragmentDirections.actionMyPostsFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = SearchFragmentDirections.actionSearchFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       NavDirections navDirections=MainFragmentDirections.actionMainFragmentToHaqqindaFragment();
                       Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                   }catch (Exception e){
                       System.out.println(e.getLocalizedMessage());
                   }



               }else if(R.id.whatCanIDo==item.getItemId()){
                   try {
                       try {
                           NavDirections navDirections = WhatCanIdoFragmentDirections.actionWhatCanIdoFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = HaqqindaFragmentDirections.actionHaqqindaFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {

                           NavDirections navDirections = ProfileFragmentDirections.actionProfileFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = MyPostsFragmentDirections.actionMyPostsFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = AddPostFragmentDirections.actionAddPostFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       try {
                           NavDirections navDirections = SearchFragmentDirections.actionSearchFragmentToMainFragment();
                           Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                       }catch(Exception e){

                       }
                       NavDirections navDirections=MainFragmentDirections.actionMainFragmentToWhatCanIdoFragment();
                       Navigation.findNavController(FeedActivity.this, R.id.fragmentContainerView2).navigate(navDirections);
                   }catch (Exception e){
                       System.out.println(e.getLocalizedMessage());
                   }



               }
                return false;
            }
        });



    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}