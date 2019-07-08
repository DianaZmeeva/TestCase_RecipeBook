package com.example.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private View parentView;
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private ArrayList<Recipe> changedRecipeList = new ArrayList<>();
    private Adapter adapter;
    private String mSearchString;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        parentView = findViewById(R.id.parentLayout);

        listView =  findViewById(R.id.listView);

        if (savedInstanceState != null) {
            mSearchString=savedInstanceState.getString("SEARCH_KEY");
            recipeList = (ArrayList<Recipe>) savedInstanceState.getSerializable("oldRecipe");
            if (recipeList != null) {
                adapter = new Adapter(MainActivity.this, recipeList);
                listView.setAdapter(adapter);
            }
        }
        else{
            update();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);

                intent.putExtra("recipeObject", recipeList.get(position));
                startActivity(intent);
            }
        });

        final SwipeRefreshLayout mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
            mSearchString = searchView.getQuery().toString();
        outState.putString("SEARCH_KEY", mSearchString);
        outState.putSerializable("oldRecipe", recipeList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        MenuItem menuItem=menu.findItem(R.id.search_badge_ID);

        searchView= (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");
        if (mSearchString != null && !mSearchString.isEmpty()) {
            menuItem.expandActionView();
            searchView.setQuery(mSearchString, true);
            searchView.clearFocus();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String textToSearch = s.toLowerCase();
               /* adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();*/
                changedRecipeList.clear();
                for(Recipe item:recipeList){

                    if (((item.getName() != null) ? item.getName() : "").toLowerCase().contains(textToSearch)||((item.getDescription() != null) ? item.getDescription() : "").toLowerCase().contains(textToSearch)|| ((item.getInstructions() != null) ? item.getInstructions() : "").toLowerCase().contains(textToSearch)){
                       if(!changedRecipeList.contains(item))
                        changedRecipeList.add(item);
                    }
                }
                //adapter.recipeList=changedRecipeList;
                //adapter.notifyDataSetChanged();
                adapter = new Adapter(MainActivity.this, changedRecipeList);
                listView.setAdapter(adapter);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.nav_sortName) {
                SortFunction(1);
            } else if (id == R.id.nav_sortDate) {
                SortFunction(2);
        }
            return true;
    }

    private void update() {
        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle(getString(R.string.jsonWaitString));
            dialog.setMessage(getString(R.string.jsonMessageString));
            dialog.show();

            ApiService api = RetroClient.getApiService();
            Call<RecipeList> call = api.getMyJSON();

            call.enqueue(new Callback<RecipeList>() {
                @Override
                public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                    dialog.dismiss();

                    if (response.isSuccessful()) {
                        recipeList = response.body().getRecipes();
                        adapter = new Adapter(MainActivity.this, recipeList);
                        listView.setAdapter(adapter);

                    } else {
                        Snackbar.make(parentView, R.string.failString, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RecipeList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            Snackbar.make(parentView, R.string.internetProblemString, Snackbar.LENGTH_LONG).show();
        }
    }

    private void SortFunction(int i) {
        if(i==1){
            adapter.sort(new Comparator<Recipe>() {
                @Override
                public int compare(Recipe s1, Recipe s2) {
                    return s1.getName().compareTo(s2.getName());
                }
            });
        }
        if(i==2){
            adapter.sort(new Comparator<Recipe>() {
                @Override
                public int compare(Recipe s1, Recipe s2) {
                    return (s1.getLastUpdated() < s2.getLastUpdated()) ? -1 : ((s1.getLastUpdated() == s2.getLastUpdated()) ? 0 : 1);
                }
            });
        }
        adapter.notifyDataSetChanged();
    }
}
