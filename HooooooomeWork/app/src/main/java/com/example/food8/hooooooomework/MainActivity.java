package com.example.food8.hooooooomework;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    FragmentManager manager = getSupportFragmentManager();
    MainFragment mainFragment;
    SingerDetailFragment singerDetailFragment;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFragment = new MainFragment();
        manager.beginTransaction().replace(R.id.container, mainFragment).commit();
    }

    public void showDetail(SingerItem singerItem){
        singerDetailFragment = new SingerDetailFragment();
        bundle = new Bundle();
        bundle.putParcelable("data", singerItem);
        singerDetailFragment.setArguments(bundle);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            manager.beginTransaction().replace(R.id.container,singerDetailFragment).commit();
        }
        else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager.beginTransaction().replace(R.id.container2,singerDetailFragment).commit();
        }
    }

    public void backToList(){
        mainFragment = new MainFragment();
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            manager.beginTransaction().replace(R.id.container, mainFragment).commit();
        }
        else if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager.beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.container2)).commit();
        }
    }
}
