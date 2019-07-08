package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends ArrayAdapter<Recipe> {
    List<Recipe> recipeList;
    Context context;
    private LayoutInflater mInflater;

    public Adapter(Context context, List<Recipe> objects){
        super(context, 0, objects);
        this.context=context;
        this.mInflater=LayoutInflater.from(context);
        recipeList=objects;
    }

    @Override
    public Recipe getItem(int position) {
        return recipeList.get(position);
    }

    @Override public int getCount()
    {
        if (recipeList != null)
        {
            return recipeList.size();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Recipe item= getItem(position);
        vh.textViewName.setText(item.getName());
        vh.textViewDescription.setText(item.getDescription());
        Picasso.with(context).load(item.getImages().get(0)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewName;
        public final TextView textViewDescription;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName, TextView textViewDescription) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewDescription = textViewDescription;
        }

        public static ViewHolder create (RelativeLayout rootView){
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewDescription = (TextView) rootView.findViewById(R.id.textViewDescription);
            return new ViewHolder(rootView, imageView, textViewName, textViewDescription);
        }
    }
}
