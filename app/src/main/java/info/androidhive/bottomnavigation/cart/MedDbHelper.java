package info.androidhive.bottomnavigation.cart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import info.androidhive.bottomnavigation.cart.ContractClass.ContractEntry;

public class MedDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "path.db";

    public MedDbHelper(Context context)
    {

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String SQL_CREATE_ENTERIES = "CREATE TABLE " + ContractEntry.TABLE_NAME + " ("
                + ContractEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ContractEntry.COLUMN_TEST_NAME + " TEXT NOT NULL, "
                + ContractEntry.COLUMN_TEST_PRICE + " TEXT NOT NULL );";
        db.execSQL(SQL_CREATE_ENTERIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV)
    {
        final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " +  ContractEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
    }

}
