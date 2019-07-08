package com.example.test;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecipeActivity extends AppCompatActivity {
    int position = 0;
    private ImagePagerAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipeObject");

        TextView name= findViewById(R.id.textViewName_recipeActivity);
        TextView description= findViewById(R.id.textViewDescription_recipeActivity);
        TextView difficulty= findViewById(R.id.textViewDifficulty_recipeActivity);
        TextView instructions= findViewById(R.id.textViewRecipe_recipeActivity);

        if(recipe.getName()!=null )
            name.setText(recipe.getName());
        if(recipe.getDescription()!=null)
            description.setText(getText(R.string.descriptionString)+" "+recipe.getDescription());
       // difficulty.setText(getText(R.string.difficultyString)+" "+recipe.getDifficulty());
        if(recipe.getInstructions()!=null){
            Spanned textInstruction  =  Html.fromHtml(recipe.getInstructions(), Html.FROM_HTML_MODE_LEGACY);
            instructions.setText(getText(R.string.instructionsString)+" "+textInstruction);
        }

        LinearLayout starLayout = findViewById(R.id.linearLayout_DifficultyStar);
        for (int i=0; i<recipe.getDifficulty(); i++){
            ImageView imageView = new ImageView(RecipeActivity.this);

            imageView.setImageResource(R.drawable.btn_star_big_on);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(70, 70));
            starLayout.addView(imageView);
        }

        ViewPager pager=findViewById(R.id.pager);
        adapter = new ImagePagerAdapter(RecipeActivity.this, recipe.getImages());
        pager.setAdapter(adapter);

    }
}

