package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Entities.Shopping;

public class ShoppingDB extends SQLiteOpenHelper{
    private Context context;

    public ShoppingDB(Context context) {
        super(context, "Shopping.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Shopping(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT, Description TEXT, Price REAL, Date TEXT, DateAdded TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Shopping;");
        onCreate(sqLiteDatabase);
    }

    public List<Shopping> findAll(){

        List<Shopping> shoppings = new ArrayList<Shopping>();
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Shopping", null);

            if(cursor.moveToFirst()){
                do{
                    Shopping shopping = new Shopping();
                    shopping.setID(cursor.getInt(0));
                    shopping.setName(cursor.getString(1));
                    shopping.setDescription(cursor.getString(2));
                    shopping.setPrice(cursor.getDouble(3));
                    shopping.setDate(cursor.getString(4));
                    shopping.setDateAdded(cursor.getString(5));
                    shoppings.add(shopping);
                }while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
            return shoppings;
        }catch (Exception e){
            return null;
        }
    }

    public boolean create(Shopping shopping){
        try{
            String currentDT = DateFormat.getDateTimeInstance().format(new Date());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name",shopping.getName());
            contentValues.put("Description",shopping.getDescription());
            contentValues.put("Price",shopping.getPrice());
            contentValues.put("Date",shopping.getDate());
            contentValues.put("DateAdded",currentDT);

            long rows = sqLiteDatabase.insert("Shopping", null, contentValues);
            sqLiteDatabase.close();
            return rows > 0;
        }catch ( Exception e){
            return false;
        }
    }

    public boolean delete (int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int rows = sqLiteDatabase.delete("Shopping", "ID = ?", new String[] { String.valueOf(id)});
            return rows > 0;
        }catch (Exception e){
            return false;
        }
    }

    public Shopping find(int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM Shopping WHERE ID = ?", new String[] {String.valueOf(id)});
            Shopping shopping = null;

            if(cursor.moveToFirst()){
                shopping = new Shopping();
                shopping.setID(cursor.getInt(0));
                shopping.setName(cursor.getString(1));
                shopping.setDescription(cursor.getString(2));
                shopping.setPrice(cursor.getDouble(3));
                shopping.setDate(cursor.getString(4));
                shopping.setDateAdded(cursor.getString(5));
            }
            sqLiteDatabase.close();
            return shopping;
        }catch (Exception e){
            return  null;
        }
    }

    public int findTot(){
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM Shopping", null);
            int count = cursor.getCount();
            sqLiteDatabase.close();
            return count;
        }catch(Exception e){
            return -1;
        }
    }

    public double findSum(){
        try{
            double sum = 0;
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT SUM(Price) FROM Shopping", null);
            if(cursor.moveToFirst()) {
                sum = cursor.getDouble(0);
            }
            sqLiteDatabase.close();
            return sum;
        }catch(Exception e){
            return -1;
        }
    }

    public boolean update(Shopping shopping){
        try{
            String currentDT = DateFormat.getDateTimeInstance().format(new Date());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("Name",shopping.getName());
            contentValues.put("Description",shopping.getDescription());
            contentValues.put("Price",shopping.getPrice());
            contentValues.put("Date",shopping.getDate());
            contentValues.put("DateAdded",currentDT);

            int rows = sqLiteDatabase.update("Shopping", contentValues, "ID = ?", new String[]{ String.valueOf(shopping.getID())});
            sqLiteDatabase.close();
            return rows > 0;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteAll (){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int rows = sqLiteDatabase.delete("Shopping", null, null);
            sqLiteDatabase.close();
            return rows >= 0;
        }catch (Exception e){
            return false;
        }
    }
}