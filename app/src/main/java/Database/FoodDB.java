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

import Entities.Food;

public class FoodDB extends SQLiteOpenHelper{
    private Context context;

    public FoodDB(Context context) {
        super(context, "Food.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Food(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT, Description TEXT, Price REAL, Date TEXT, DateAdded TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Food;");
        onCreate(sqLiteDatabase);
    }

    public List<Food> findAll(){

        List<Food> foods = new ArrayList<Food>();
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Food", null);

            if(cursor.moveToFirst()){
                do{
                    Food food = new Food();
                    food.setID(cursor.getInt(0));
                    food.setName(cursor.getString(1));
                    food.setDescription(cursor.getString(2));
                    food.setPrice(cursor.getDouble(3));
                    food.setDate(cursor.getString(4));
                    food.setDateAdded(cursor.getString(5));
                    foods.add(food);
                }while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
            return foods;
        }catch (Exception e){
            return null;
        }
    }

    public boolean create(Food food){
        try{
            String currentDT = DateFormat.getDateTimeInstance().format(new Date());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name",food.getName());
            contentValues.put("Description",food.getDescription());
            contentValues.put("Price",food.getPrice());
            contentValues.put("Date",food.getDate());
            contentValues.put("DateAdded",currentDT);

            long rows = sqLiteDatabase.insert("Food", null, contentValues);
            sqLiteDatabase.close();
            return rows > 0;
        }catch ( Exception e){
            return false;
        }
    }

    public boolean delete (int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int rows = sqLiteDatabase.delete("Food", "ID = ?", new String[] { String.valueOf(id)});
            sqLiteDatabase.close();
            return rows > 0;
        }catch (Exception e){
            return false;
        }
    }

    public Food find(int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM Food WHERE ID = ?", new String[] {String.valueOf(id)});
            Food food = null;

            if(cursor.moveToFirst()){
                food = new Food();
                food.setID(cursor.getInt(0));
                food.setName(cursor.getString(1));
                food.setDescription(cursor.getString(2));
                food.setPrice(cursor.getDouble(3));
                food.setDate(cursor.getString(4));
                food.setDateAdded(cursor.getString(5));
            }
            sqLiteDatabase.close();
            return food;
        }catch (Exception e){
            return  null;
        }
    }

    public int findTot(){
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM Food", null);
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
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT SUM(Price) FROM Food", null);
            if(cursor.moveToFirst()) {
                sum = cursor.getDouble(0);
            }
            sqLiteDatabase.close();
            return sum;
        }catch(Exception e){
            return -1;
        }
    }

    public boolean update(Food food){
        try{
            String currentDT = DateFormat.getDateTimeInstance().format(new Date());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("Name",food.getName());
            contentValues.put("Description",food.getDescription());
            contentValues.put("Price",food.getPrice());
            contentValues.put("Date",food.getDate());
            contentValues.put("DateAdded",currentDT);

            int rows = sqLiteDatabase.update("Food", contentValues, "ID = ?", new String[]{ String.valueOf(food.getID())});
            sqLiteDatabase.close();
            return rows > 0;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteAll (){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int rows = sqLiteDatabase.delete("Food", null, null);
            sqLiteDatabase.close();
            return rows >= 0;
        }catch (Exception e){
            return false;
        }
    }
}