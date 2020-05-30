package blackmafia.gogoanime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class favDB extends SQLiteOpenHelper {

    public static int DB_VERSION = 209;
    public static String DB_NAME = "Fav_DB";
    public static String TABLE_FAV = "favoriteTable";
    public static String KEY = "id";
    public static String ITEM_ANIME_NAME = "Anime_Name";
    public static String IMG_URL = "Img_URL";
    public static String LINK_SUMMARY = "Link_Summary";
    public static String STATUS_FAV = "fav_item";
    private int index = 1;

    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_FAV + "("
            + KEY + " integer primary key AUTOINCREMENT, " + ITEM_ANIME_NAME + " TEXT, " + IMG_URL + " TEXT, " + LINK_SUMMARY
            + " TEXT, " + STATUS_FAV +
            " TEXT)";



    public favDB(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ITEM_ANIME_NAME);
        onCreate(sqLiteDatabase);
    }
    public void InsertEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ITEM_ANIME_NAME,"null");
        values.put(IMG_URL,"null");
        values.put(LINK_SUMMARY,"null");
        values.put(STATUS_FAV,"0");
        db.insert(TABLE_FAV,null,values);
    }
    public void InsertFavorite(String name,String IMG, String LINK, String code){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_ANIME_NAME,name);
        cv.put(IMG_URL,IMG);
        cv.put(LINK_SUMMARY,LINK);
        cv.put(STATUS_FAV,code);
        db.insert(TABLE_FAV,null,cv);
        db.close();
        Log.d("test", "table name" + TABLE_FAV + " anime name: "
        + ITEM_ANIME_NAME + " status fav: " + STATUS_FAV + " index " + index);
    }

    public void RemoveFav(String AnimeName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAV,ITEM_ANIME_NAME+" = '" + AnimeName + "'",null);
    }
    public List<FavItem> getList(){
        String[] columns = {
                KEY,
                ITEM_ANIME_NAME,
                IMG_URL,
                LINK_SUMMARY,
                STATUS_FAV
        };
        String sortOrder = KEY + " DESC";
        List<FavItem> FavList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAV,columns,null,null,null,null,sortOrder);
        if(cursor.moveToFirst()){
            do{
                FavItem favItem = new FavItem();
                favItem.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY))));
                favItem.setAnimeName(cursor.getString(cursor.getColumnIndex(ITEM_ANIME_NAME)));
                favItem.setImgUrl(cursor.getString(cursor.getColumnIndex(IMG_URL)));
                favItem.setLinkSummary(cursor.getString(cursor.getColumnIndex(LINK_SUMMARY)));
                favItem.setFavStatus(cursor.getColumnName(cursor.getColumnIndex(STATUS_FAV)));
                FavList.add(favItem);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return FavList;
    }
}
