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

import Entities.Transport;

public class TransportDB extends SQLiteOpenHelper{
    private Context context;

    public TransportDB(Context context) {
        super(context, "Transport.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Transport(ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT, Description TEXT, Price REAL, Date TEXT, DateAdded TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Transport;");
        onCreate(sqLiteDatabase);
    }

    public List<Transport> findAll(){

        List<Transport> transports = new ArrayList<Transport>();
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Transport", null);

            if(cursor.moveToFirst()){
                do{
                    Transport transport = new Transport();
                    transport.setID(cursor.getInt(0));
                    transport.setName(cursor.getString(1));
                    transport.setDescription(cursor.getString(2));
                    transport.setPrice(cursor.getDouble(3));
                    transport.setDate(cursor.getString(4));
                    transport.setDateAdded(cursor.getString(5));
                    transports.add(transport);
                }while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
            return transports;
        }catch (Exception e){
            return null;
        }
    }

    public boolean create(Transport transport){
        try{
            String currentDT = DateFormat.getDateTimeInstance().format(new Date());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name",transport.getName());
            contentValues.put("Description",transport.getDescription());
            contentValues.put("Price",transport.getPrice());
            contentValues.put("Date",transport.getDate());
            contentValues.put("DateAdded",currentDT);

            long rows = sqLiteDatabase.insert("Transport", null, contentValues);
            sqLiteDatabase.close();
            return rows > 0;
        }catch ( Exception e){
            return false;
        }
    }

    public boolean delete (int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int rows = sqLiteDatabase.delete("Transport", "ID = ?", new String[] { String.valueOf(id)});
            return rows > 0;
        }catch (Exception e){
            return false;
        }
    }

    public Transport find(int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM Transport WHERE ID = ?", new String[] {String.valueOf(id)});
            Transport transport = null;

            if(cursor.moveToFirst()){
                transport = new Transport();
                transport.setID(cursor.getInt(0));
                transport.setName(cursor.getString(1));
                transport.setDescription(cursor.getString(2));
                transport.setPrice(cursor.getDouble(3));
                transport.setDate(cursor.getString(4));
                transport.setDateAdded(cursor.getString(5));
            }
            sqLiteDatabase.close();
            return transport;
        }catch (Exception e){
            return  null;
        }
    }

    public int findTot(){
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM Transport", null);
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
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT SUM(Price) FROM Transport", null);
            if(cursor.moveToFirst()) {
                 sum = cursor.getDouble(0);
            }
            sqLiteDatabase.close();
            return sum;
        }catch(Exception e){
            return -1;
        }
    }

    public boolean update(Transport transport){
        try{
            String currentDT = DateFormat.getDateTimeInstance().format(new Date());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("Name",transport.getName());
            contentValues.put("Description",transport.getDescription());
            contentValues.put("Price",transport.getPrice());
            contentValues.put("Date",transport.getDate());
            contentValues.put("DateAdded",currentDT);

            int rows = sqLiteDatabase.update("Transport", contentValues, "ID = ?", new String[]{ String.valueOf(transport.getID())});
            sqLiteDatabase.close();
            return rows > 0;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteAll (){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int rows = sqLiteDatabase.delete("Transport", null, null);
            sqLiteDatabase.close();
            return rows >= 0;
        }catch (Exception e){
            return false;
        }
    }
}