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

import Entities.Card;

public class CardDB extends SQLiteOpenHelper{
    private Context context;

    public CardDB(Context context) {
        super(context, "Card.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Card(ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, CardNo TEXT, Type TEXT, ExpDate TEXT, Date TEXT, Description TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Card;");
        onCreate(sqLiteDatabase);
    }

    public List<Card> findAll(){

        List<Card> cards = new ArrayList<Card>();
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Card", null);

            if(cursor.moveToFirst()){
                do{
                    Card card = new Card();
                    card.setID(cursor.getInt(0));
                    card.setName(cursor.getString(1));
                    card.setCardNo(cursor.getString(2));
                    card.setType(cursor.getString(3));
                    card.setExpDate(cursor.getString(4));
                    card.setDate(cursor.getString(5));
                    card.setDescription(cursor.getString(6));
                    cards.add(card);
                }while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
            return cards;
        }catch (Exception e){
            return null;
        }
    }

    public boolean create(Card card){
        try{
            String currentDT = DateFormat.getDateTimeInstance().format(new Date());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name",card.getName());
            contentValues.put("CardNo",card.getCardNo());
            contentValues.put("Type",card.getType());
            contentValues.put("ExpDate",card.getExpDate());
            contentValues.put("Date",currentDT);
            contentValues.put("Description", card.getDescription());

            long rows = sqLiteDatabase.insert("Card", null, contentValues);
            sqLiteDatabase.close();
            return rows > 0;
        }catch ( Exception e){
            return false;
        }
    }

    public boolean delete (int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int rows = sqLiteDatabase.delete("Card", "ID = ?", new String[] { String.valueOf(id)});
            return rows > 0;
        }catch (Exception e){
            return false;
        }
    }

    public Card find(int id){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM Card WHERE ID = ?", new String[] {String.valueOf(id)});
            Card card = null;

            if(cursor.moveToFirst()){
                card = new Card();
                card.setID(cursor.getInt(0));
                card.setName(cursor.getString(1));
                card.setCardNo(cursor.getString(2));
                card.setType(cursor.getString(3));
                card.setExpDate(cursor.getString(4));
                card.setDate(cursor.getString(5));
                card.setDescription(cursor.getString(6));
            }
            sqLiteDatabase.close();
            return card;
        }catch (Exception e){
            return  null;
        }
    }

    public int findTot(){
        try{
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM Card", null);
            int count = cursor.getCount();
            sqLiteDatabase.close();
            return count;
        }catch(Exception e){
            return -1;
        }
    }

    public boolean update(Card card){
        try{
            String currentDT = DateFormat.getDateTimeInstance().format(new Date());

            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name",card.getName());
            contentValues.put("CardNo",card.getCardNo());
            contentValues.put("Type",card.getType());
            contentValues.put("ExpDate",card.getExpDate());
            contentValues.put("Date",currentDT);
            contentValues.put("Description", card.getDescription());

            int rows = sqLiteDatabase.update("Card", contentValues, "ID = ?", new String[]{ String.valueOf(card.getID())});
            sqLiteDatabase.close();
            return rows > 0;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteAll (){
        try{
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            int rows = sqLiteDatabase.delete("Card", null, null);
            sqLiteDatabase.close();
            return rows >= 0;
        }catch (Exception e){
            return false;
        }
    }
}
