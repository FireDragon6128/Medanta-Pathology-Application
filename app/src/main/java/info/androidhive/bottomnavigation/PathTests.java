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
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import info.androidhive.bottomnavigation.cart.ContractClass;
import info.androidhive.bottomnavigation.cart.MedCursorAdapter;
import info.androidhive.bottomnavigation.cart.MedDbHelper;
import info.androidhive.bottomnavigation.fragment.CartFragment;

public class PathTests extends AppCompatActivity {

    ListView listView;
    EditText editText;
    CheckBox checkbox;
    List<Subject> SubjectList;
    ArrayList<String> checkedState;
    private Menu menu;

    String HttpURL = "http://192.168.1.101/pathology.php";
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

        setContentView(R.layout.activity_path_tests);
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

                                subject.isChecked=false;


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
            MedDbHelper dbHelper = new MedDbHelper(PathTests.this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            int numRows = (int) DatabaseUtils.queryNumEntries(db, ContractClass.ContractEntry.TABLE_NAME);
            if(numRows>0) {
                Cursor cursor = db.rawQuery("Select name from tests " + ";", null);
                ArrayList<String> str = new ArrayList<>();
                cursor.moveToFirst();
                int i = 0;
                do {
                    str.add(cursor.getString(cursor.getColumnIndex("name")));

                    i++;
                } while (cursor.moveToNext());


                for (int j = 0; j < SubjectList.size(); j++) {
                    i = 0;
                    while(i<numRows){
                        if (SubjectList.get(j).Name.equals(str.get(i))) {
                            SubjectList.get(j).isChecked = true;
                        }
                        i++;
                    }
                }
            }

            listAdapter = new ListAdapter(getApplicationContext(),R.layout.listview_items, SubjectList);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Subject ListViewClickData = SubjectList.get(position);
                    checkedState = new ArrayList<String>();
                    String nameString = ListViewClickData.getSubName();
                    String priceString = ListViewClickData.getPrice();

                    if(!ListViewClickData.getCheckedState()) {
                        checkedState.add(ListViewClickData.getSubName());


                        MedDbHelper dbHelper = new MedDbHelper(PathTests.this);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();

                        values.put(ContractClass.ContractEntry.COLUMN_TEST_NAME,nameString);
                        values.put(ContractClass.ContractEntry.COLUMN_TEST_PRICE,priceString);

                        long newRowId = db.insert(ContractClass.ContractEntry.TABLE_NAME, null, values);

                        if(newRowId == -1)
                        {
                            Toast.makeText(PathTests.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(PathTests.this, "Moved to Cart", Toast.LENGTH_SHORT).show();
                        }



                    }
                    else{

                        checkedState.remove(ListViewClickData.Name);
                        MedDbHelper dbHelper = new MedDbHelper(PathTests.this);
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        String[] args = new String[1];
                        args[0] = ListViewClickData.Name;
                        //Toast.makeText(context, ListViewClickData.Name, Toast.LENGTH_SHORT).show();
                        int deletedRows = db.delete(ContractClass.ContractEntry.TABLE_NAME, ContractClass.ContractEntry.COLUMN_TEST_NAME + " LIKE ?", args);

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
