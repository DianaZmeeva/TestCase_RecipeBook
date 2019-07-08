package com.example.test;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<String> arrayList;
    public ImagePagerAdapter(Context context, List<String> arrayList)
    {
        this.context = context;
        layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = arrayList;
    }
    @Override public int getCount()
    {
        if(arrayList != null)
        {
            return arrayList.size();
        }
        return 0;
    }
    @Override public boolean isViewFromObject(View view, Object object)
    {
        return view == ((FrameLayout) object);
    }
    @Override public Object instantiateItem(ViewGroup container, int position)
    {
        View itemView = layoutInflater.inflate(R.layout.fragment_page, container, false);
        ImageView imageView = itemView.findViewById(R.id.imageView_RecipeActivity);
        Picasso.with(context).load(arrayList.get(position)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((FrameLayout) object);
    }
}
