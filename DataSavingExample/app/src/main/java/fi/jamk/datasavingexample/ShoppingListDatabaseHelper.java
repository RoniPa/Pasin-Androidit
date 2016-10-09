package fi.jamk.datasavingexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


final class ShoppingListDatabaseHelper {
    private static SQLiteDatabase DB;
    private static final String DATABASE_NAME = "533345_SHOPPING_LIST";
    private final String DATABASE_TABLE = "Shoppinglist";
    final String ID = "_id";
    final String NAME = "Name";
    final String COUNT = "Count";
    final String PRICE = "Price";

    ShoppingListDatabaseHelper(Context context) {
        DB = (new InnerSQLiteOpenHelper(context)).getWritableDatabase();
    }

    void insertItem(String name, int count, float price){
        // create sample data
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(COUNT, count);
        values.put(PRICE, price);
        DB.insert(DATABASE_TABLE, null, values);
    }

    void deleteItem(String id) {
        DB.delete(DATABASE_TABLE, "_id = ?", new String[]{id});
    }

    Cursor getData() {
        return DB.query(
                DATABASE_TABLE,
                new String[]{ID,NAME,COUNT,PRICE},
                null,null,null,null,
                NAME +" DESC",
                null);
    }

    public void resetData() {
        DB.delete(DATABASE_TABLE, null, null);
    }

    private class InnerSQLiteOpenHelper extends SQLiteOpenHelper {
        InnerSQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            DB = db;
            db.execSQL("CREATE TABLE "+DATABASE_TABLE+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" TEXT, "+COUNT+" INTEGER, "+PRICE+" REAL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DB.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(DB);
        }
    }
}
