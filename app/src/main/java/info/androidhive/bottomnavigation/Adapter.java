package info.androidhive.bottomnavigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.androidhive.bottomnavigation.Model;
import info.androidhive.bottomnavigation.R;

public class Adapter extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }


    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item, container, false);



        TextView name;
        TextView price;

        name = view.findViewById(R.id.package_name);
        price = view.findViewById(R.id.price);

        name.setText(models.get(position).getName());
        price.setText(models.get(position).getPrice());

        container.addView(view, 0);

        ((ViewPager) container).addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                // skip fake page (first), go to last page
                if (position == 0) {
                    ((ViewPager) container).setCurrentItem(((ViewPager) container).getAdapter().getCount()-2, false);
                }

                // skip fake page (last), go to first page
                if (position == ((ViewPager) container).getAdapter().getCount()-1) {
                    ((ViewPager) container).setCurrentItem(1, true); //notice how this jumps to position 1, and not position 0. Position 0 is the fake page!
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
