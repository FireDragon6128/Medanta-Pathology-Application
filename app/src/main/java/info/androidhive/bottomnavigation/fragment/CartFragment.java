package info.androidhive.bottomnavigation.fragment;
import info.androidhive.bottomnavigation.PathTests;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.accountText;
import info.androidhive.bottomnavigation.accountTextAdapter;
import info.androidhive.bottomnavigation.cart.ContractClass;
import info.androidhive.bottomnavigation.cart.MedCursorAdapter;
import info.androidhive.bottomnavigation.cart.MedDbHelper;

public class CartFragment extends Fragment{

    MedDbHelper dbHelper;

    public CartFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View  v = inflater.inflate(R.layout.fragment_cart, container, false);
        viewData(v);


        return v;
    }



    public void viewData(View v)
    {
        String[] projection = {
                BaseColumns._ID,
                ContractClass.ContractEntry.COLUMN_TEST_NAME,
                ContractClass.ContractEntry.COLUMN_TEST_PRICE
        };
        dbHelper = new MedDbHelper(getContext());
        SQLiteDatabase db2 = dbHelper.getReadableDatabase();

        Cursor cursor = db2.query(
                ContractClass.ContractEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );


        final ListView listView = (ListView) v.findViewById(R.id.list);
        final MedCursorAdapter adapter = new MedCursorAdapter(getContext(), cursor);
        Button b = v.findViewById(R.id.emptycart);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                listView.invalidateViews();
                // viewData(v);
            }
        });
        listView.setAdapter(adapter);

        View emptyView = (View) v.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

    }

    public void deleteData()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int deletedRows = db.delete(ContractClass.ContractEntry.TABLE_NAME, null, null);
    }

}
