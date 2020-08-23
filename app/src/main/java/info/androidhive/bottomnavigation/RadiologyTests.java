package info.androidhive.bottomnavigation;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.EditText;

public class RadiologyTests extends AppCompatActivity {

    ListView listView;
    EditText editText;
    CheckBox checkbox;
    List<Subject> SubjectList;
    ArrayList<String> checkedState;
    private Menu menu;

    String HttpURL = "http://192.168.1.101/radiology.php";
    ListAdapter listAdapter;

    @Nullable
    @Override
    public ActionBar getActionBar() {
        return super.getActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        this.menu=menu;
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        android.support.v7.widget.SearchView search = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                listAdapter.getFilter().filter(query.toString());


                return true;

            }

        });
        return true;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_radiology_tests);
        listView = (ListView) findViewById(R.id.TestsListView);

        listView.setTextFilterEnabled(true);

        checkbox = findViewById(R.id.checkbox);


        new ParseJSonDataClass(this).execute();



    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;
        String FinalJSonResult;


        public ParseJSonDataClass(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpServiceClass httpServiceClass = new HttpServiceClass(HttpURL);

            try {
                httpServiceClass.ExecutePostRequest();

                if (httpServiceClass.getResponseCode() == 200) {

                    FinalJSonResult = httpServiceClass.getResponse();

                    if (FinalJSonResult != null) {

                        JSONArray jsonArray = null;
                        try {

                            jsonArray = new JSONArray(FinalJSonResult);

                            JSONObject jsonObject;
                            Subject subject;


                            SubjectList = new ArrayList<>();



                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                subject = new Subject();

                                subject.Name = jsonObject.getString("Name");

                                subject.Price = jsonObject.getString("Price");


                                SubjectList.add(subject);

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                } else {

                    Toast.makeText(context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)


        {
            listView.setVisibility(View.VISIBLE);
            listAdapter = new ListAdapter(getApplicationContext(),R.layout.listview_items, SubjectList);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Subject ListViewClickData = SubjectList.get(position);
                    checkedState = new ArrayList<String>();
                    if(!ListViewClickData.getCheckedState()) {
                        checkedState.add(ListViewClickData.getSubName());

                        Toast.makeText(RadiologyTests.this, ListViewClickData.getSubName()+" has been added to the cart." , Toast.LENGTH_SHORT).show();

                    }
                    else{
                        checkedState.remove(ListViewClickData.Name);
                        Toast.makeText(RadiologyTests.this, ListViewClickData.getSubName()+" has been removed from the cart." , Toast.LENGTH_SHORT).show();
                    }
                    ListViewClickData.isChecked=!ListViewClickData.isChecked;
                    for(String i:checkedState){
                        if(ListViewClickData.getSubName()==i){
                            ListViewClickData.isChecked=true;
                        }
                    }

                    listAdapter.notifyDataSetChanged();
                }
            });


        }
    }


    private void centerTitle() {
        ArrayList<View> textViews = new ArrayList<>();

        getWindow().getDecorView().findViewsWithText(textViews, getTitle(), View.FIND_VIEWS_WITH_TEXT);

        if(textViews.size() > 0) {
            AppCompatTextView appCompatTextView = null;
            if(textViews.size() == 1) {
                appCompatTextView = (AppCompatTextView) textViews.get(0);
            } else {
                for(View v : textViews) {
                    if(v.getParent() instanceof Toolbar) {
                        appCompatTextView = (AppCompatTextView) v;
                        break;
                    }
                }
            }

            if(appCompatTextView != null) {
                ViewGroup.LayoutParams params = appCompatTextView.getLayoutParams();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                appCompatTextView.setLayoutParams(params);
                appCompatTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
        }
    }

}
