package info.androidhive.bottomnavigation.fragment;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.bottomnavigation.Adapter;
import info.androidhive.bottomnavigation.AdvancedTests;
import info.androidhive.bottomnavigation.AutoScrollViewPager;
import info.androidhive.bottomnavigation.Model;
import info.androidhive.bottomnavigation.OtherTests;
import info.androidhive.bottomnavigation.Packages;
import info.androidhive.bottomnavigation.PathTests;
import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.RadiologyTests;

public class HomeFragment extends Fragment {

    AutoScrollViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Context mContext;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private int[] def = {R.drawable.non_active_dot,R.drawable.active_dot};

    public HomeFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        models = new ArrayList<>();
        models.add(new Model("PACKAGE6", "abc"));
        models.add(new Model("PACKAGE1", "abc"));
        models.add(new Model("PACKAGE2", "abc"));
        models.add(new Model("PACKAGE3", "abc"));
        models.add(new Model("PACKAGE4", "abc"));
        models.add(new Model("PACKAGE5", "abc"));
        models.add(new Model("PACKAGE6", "abc"));
        models.add(new Model("PACKAGE1", "abc"));


        sliderDotspanel = view.findViewById(R.id.pager_dots);
        viewPager = view.findViewById(R.id.viewPagerHome);
        adapter = new Adapter(models, getContext());
        viewPager.startAutoScroll();
        viewPager.setInterval(3000);
        viewPager.setCycle(true);
        viewPager.setStopScrollWhenTouch(true);

        dotscount = adapter.getCount()-2;
        dots=new ImageView[dotscount];



        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), def[0]));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), def[1]));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, def[0]));
                }

                if(position > (adapter.getCount()-2)){
                    position = position - 6;
                }

                if(position==0){
                    position = position + 6;
                }

                dots[position-1].setImageDrawable(ContextCompat.getDrawable(mContext,def[1]));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        TextView seeAll = view.findViewById(R.id.seeAll);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Packages.class);
                startActivity(i);
            }
        });




        Button one = view.findViewById(R.id.labTests);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent labTestsIntent = new Intent(getActivity(), PathTests.class);
                startActivity(labTestsIntent);

            }
        });


        Button two = view.findViewById(R.id.radTests);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent radTestsIntent = new Intent(getActivity(), RadiologyTests.class);
                startActivity(radTestsIntent);

            }
        });

        Button three = view.findViewById(R.id.oDTests);
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oDTestsIntent = new Intent(getActivity(), OtherTests.class);
                startActivity(oDTestsIntent);

            }
        });

        Button four = view.findViewById(R.id.advTests);
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent advTestsIntent = new Intent(getActivity(), Packages.class);
                startActivity(advTestsIntent);

            }
        });


        return view;


    }


}

