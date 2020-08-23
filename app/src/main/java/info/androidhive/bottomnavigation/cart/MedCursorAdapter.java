package info.androidhive.bottomnavigation.cart;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import info.androidhive.bottomnavigation.R;
import info.androidhive.bottomnavigation.cart.ContractClass.ContractEntry;

public class MedCursorAdapter extends CursorAdapter {

    public MedCursorAdapter(Context context, Cursor c)
    {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.cart_layout,parent,false);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.nameCart);
        TextView summaryTextView = (TextView) view.findViewById(R.id.priceCart);

        int nameColumnIndex = cursor.getColumnIndex(ContractEntry.COLUMN_TEST_NAME);
        int breedColumnIndex = cursor.getColumnIndex(ContractEntry.COLUMN_TEST_PRICE);

        String TestName = cursor.getString(nameColumnIndex);
        String TestPrice = cursor.getString(breedColumnIndex);

        nameTextView.setText(TestName);
        summaryTextView.setText(TestPrice);

    }
}
