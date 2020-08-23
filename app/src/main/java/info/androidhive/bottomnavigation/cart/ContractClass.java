package info.androidhive.bottomnavigation.cart;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContractClass {

    public ContractClass(){
    }

    public static final String CONTENT_AUTHORITY = "info.androidhive.bottomnavigation";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PETS = "tests";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

    public static final class ContractEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "tests";
        public final static String _ID = BaseColumns._ID;
        public final static String  COLUMN_TEST_NAME = "name";
        public final static String COLUMN_TEST_PRICE = "price";


        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;
    }
}
