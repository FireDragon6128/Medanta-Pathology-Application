package info.androidhive.bottomnavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class accountTextAdapter extends ArrayAdapter<accountText> {

    //constructor
    public accountTextAdapter(Context context, ArrayList<accountText> accountTexts)
    {
        super(context,0,accountTexts);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        accountText currentWord = getItem(position);
        TextView mTextView = (TextView) listItemView.findViewById(R.id.hintTextView);
        mTextView.setText(currentWord.getAboutMainString());

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.mainTextView);
        nameTextView.setText(currentWord.getMainString());

        return listItemView;

    }
}
