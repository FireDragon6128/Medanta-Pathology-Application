package info.androidhive.bottomnavigation.fragment;

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
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.EditText;

import com.bumptech.glide.load.engine.Resource;

import info.androidhive.bottomnavigation.HttpServiceClass;
import info.androidhive.bottomnavigation.ListAdapter;
import info.androidhive.bottomnavigation.MainActivity;
import info.androidhive.bottomnavigation.PathTests;
import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.Subject;


public class SearchFragment extends Fragment {
    ListView listView;
    EditText editText;
    CheckBox checkbox;
    List<Subject> SubjectList;
    ArrayList<String> checkedState;
    private Menu menu;

    String HttpURL = "http://192.168.1.101//alltests.php";
    ListAdapter listAdapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem item = menu.findItem(R.id.search);

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW|MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        listView = (ListView) v.findViewById(R.id.TestsListView);

        listView.setTextFilterEnabled(true);

        checkbox = v.findViewById(R.id.checkbox);

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();


        new SearchFragment.ParseJSonDataClass(this).execute();

        // Inflate the layout for this fragment
        return v;
    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public SearchFragment context;
        String FinalJSonResult;


        public ParseJSonDataClass(SearchFragment context) {

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
            listAdapter = new ListAdapter(getContext(),R.layout.listview_items, SubjectList);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Subject ListViewClickData = SubjectList.get(position);
                    checkedState = new ArrayList<String>();
                    if(!ListViewClickData.getCheckedState()) {
                        checkedState.add(ListViewClickData.getSubName());




                    }
                    else{
                        checkedState.remove(ListViewClickData.Name);


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
}
