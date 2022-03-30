package com.rapidexsol.testapp1;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DataBase extends SQLiteOpenHelper {
    public static final String INVENTORY_TABLE = "INVENTORY_TABLE";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_ITEM_BARCODE = "ITEM_BARCODE";
    public static final String COLUMN_ITEM_QUANTITY = "ITEM_QUANTITY";
    public static final String COLUMN_ITEM_MULIPLIER = "ITEM_MULIPLIER";
    public static final String COLUMN_ID = "ID";

    public DataBase(@Nullable Context context) {
        super(context, "inventory.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + INVENTORY_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ITEM_NAME + " TEXT, " + COLUMN_ITEM_BARCODE + " TEXT, " + COLUMN_ITEM_QUANTITY + " INT, " + COLUMN_ITEM_MULIPLIER + " INT)";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    public boolean updateOne(Inventory inventUpdate){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        System.out.println(inventUpdate.getBarcode());

        String querryString = "SELECT * FROM "+ INVENTORY_TABLE + " WHERE "+ COLUMN_ID+ " = "+ inventUpdate.getId();
        Cursor cursor = db.rawQuery("SELECT * FROM " + INVENTORY_TABLE + " WHERE "+COLUMN_ITEM_BARCODE+ " = "+ inventUpdate.getBarcode(), null);
        if(cursor.moveToFirst()){
           do {
               int itemQuantity = cursor.getInt(3);


               cv.put(COLUMN_ITEM_QUANTITY, itemQuantity + 1);
               db.update(INVENTORY_TABLE, cv, COLUMN_ITEM_BARCODE + " = " + inventUpdate.getBarcode(), null);
           } while(cursor.moveToNext());

            //cursor = db.rawQuery("UPDATE " + INVENTORY_TABLE +" SET "+ COLUMN_ITEM_QUANTITY + " = "+ COLUMN_ITEM_QUANTITY+ "+1" +" WHERE "+COLUMN_ITEM_BARCODE+ " = "+ inventUpdate.getBarcode(), null);
            cursor.close();
            return true;

        }

        else { addOne(inventUpdate);

            return false;


        }
//


    }

    public void clearAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        //db.delete(INVENTORY_TABLE,null,null);
        db.execSQL("DELETE FROM "+ INVENTORY_TABLE);
        cursor = db.rawQuery("DELETE FROM `sqlite_sequence` WHERE `name` = 'INVENTORY_TABLE'", null);
        cursor.moveToFirst();
        cursor.close();
        db.close();}


//    public void exportDB() {
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
//        if (!exportDir.exists())
//        {
//            exportDir.mkdirs();
//        }
//
//        File file = new File(exportDir, "csvname.csv");
//        try
//        {
//            file.createNewFile();
//            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
//            SQLiteDatabase db = db.getReadableDatabase();
//            Cursor curCSV = db.rawQuery("SELECT * FROM contacts",null);
//            csvWrite.writeNext(curCSV.getColumnNames());
//            while(curCSV.moveToNext())
//            {
//                //Which column you want to exprort
//                String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
//                csvWrite.writeNext(arrStr);
//            }
//            csvWrite.close();
//            curCSV.close();
//        }
//        catch(Exception sqlEx)
//        {
//            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
//        }
//    }
//
//
//waifuelliiexo


//
//    public boolean exportDatabase() {
//        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
//
//        /**First of all we check if the external storage of the device is available for writing.
//         * Remember that the external storage is not necessarily the sd card. Very often it is
//         * the device storage.
//         */
//        String state = Environment.getExternalStorageState();
//        if (!Environment.MEDIA_MOUNTED.equals(state)) {
//            return false;
//        }
//        else {
//            //We use the Download directory for saving our .csv file.
//            File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            if (!exportDir.exists())
//            {
//                exportDir.mkdirs();
//            }
//
//            File file;
//            PrintWriter printWriter = null;
//            try
//            {
//                file = new File(exportDir, "MyCSVFile.csv");
//                file.createNewFile();
//                printWriter = new PrintWriter(new FileWriter(file));
//
//                /**This is our database connector class that reads the data from the database.
//                 * The code of this class is omitted for brevity.
//                 */
//                SQLiteDatabase db = this.getReadableDatabase(); //open the database for reading
//
//                /**Let's read the first table of the database.
//                 * getFirstTable() is a method in our DBCOurDatabaseConnector class which retrieves a Cursor
//                 * containing all records of the table (all fields).
//                 * The code of this class is omitted for brevity.
//                 */
//                Cursor curCSV = db.rawQuery("select * from "+INVENTORY_TABLE, null);
//                //Write the name of the table and the name of the columns (comma separated values) in the .csv file.
//                printWriter.println("FIRST TABLE OF THE DATABASE");
//                printWriter.println("ID,NAME,BARCODE,QUANTITY,MULTIPLIER");
//                while(curCSV.moveToNext())
//                {
//                    @SuppressLint("Range") int fid = curCSV.getInt(curCSV.getColumnIndex(COLUMN_ID));
//                    @SuppressLint("Range") String fname = curCSV.getString(curCSV.getColumnIndex(COLUMN_ITEM_NAME));
//                    @SuppressLint("Range") String fbarcode = curCSV.getString(curCSV.getColumnIndex(COLUMN_ITEM_BARCODE));
//                    @SuppressLint("Range") int fquantity = curCSV.getInt(curCSV.getColumnIndex(COLUMN_ITEM_QUANTITY));
//                    @SuppressLint("Range") int fmultiplier = curCSV.getInt(curCSV.getColumnIndex(COLUMN_ITEM_MULIPLIER));
//
//                    /**Create the line to write in the .csv file.
//                     * We need a String where values are comma separated.
//                     * The field date (Long) is formatted in a readable text. The amount field
//                     * is converted into String.
//                     */
//                    String record = df.format((fid)) + "," + fname + "," + fbarcode + "," + fquantity + "," + fmultiplier;
//                    printWriter.println(record); //write the record in the .csv file
//                }
//
//                curCSV.close();
//                db.close();
//            }
//
//            catch(Exception exc) {
//                //if there are any exceptions, return false
//                return false;
//            }
//            finally {
//                if(printWriter != null) printWriter.close();
//            }
//
//            //If there are no errors, return true.
//            return true;
//        }
//    }

    public void exportDatabse(){
        SQLiteDatabase db = this.getWritableDatabase();
        String FILENAME = "rapidex_log.csv";
        //Obrain the current text in the files convert it to string and save it to .csv file
        String entry = "ID"+ "," + "NAME" + "," +
                "BARCODE" + "," + "QUANTITY" + "," +
                "MULTIPLIER" + "\n";
        //Open a file with the streamer/pointer
        try {
            File directoryDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File logDir = new File (directoryDownload, "Rapidex Solutions"); //Creates a new folder in DOWNLOAD directory
            logDir.mkdirs();
            File file = new File(logDir, FILENAME);
            FileOutputStream out = new FileOutputStream(file);
            out.write(entry.getBytes()); //Write the obtained string to csv




            String queryString = "SELECT * FROM "+ INVENTORY_TABLE;

            Cursor cursor = db.rawQuery(queryString, null);
            if(cursor.moveToFirst()) {
                //loop through the results and create new item object
                do {
                    entry = cursor.getInt(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," + cursor.getInt(3) + "," + cursor.getInt(4) + "\n";
                    out.write(entry.getBytes()); //Write the obtained string to csv
                } while (cursor.moveToNext());
            }

            out.close();
            Log.e("MainActivity", "FILE CREATED");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }








//    public void exportDatabase() {





//        File exportDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "");
//        if (!exportDir.exists())
//        {
//            exportDir.mkdirs();
//        }
//
//       File file = new File(exportDir, "rapidex.csv");
//
//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
//        File file = new File(path, "/Android/data/rapidex" );
//        file.mkdirs();
//
//        try
//        {
//            file.createNewFile();
//
//            CSVWriter csvWrite = new CSVWriter(new FileWriter("rapidextable.csv", true));
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor curCSV = db.rawQuery("SELECT * FROM contacts",null);
//            csvWrite.writeNext(curCSV.getColumnNames());
//            while(curCSV.moveToNext())
//            {
//                //Which column you want to exprort
//                String arrStr[] =new String[]{COLUMN_ID,COLUMN_ITEM_NAME,COLUMN_ITEM_BARCODE,COLUMN_ITEM_QUANTITY,COLUMN_ITEM_MULIPLIER};
//                csvWrite.writeNext(arrStr);
//            }
//            csvWrite.close();
//            curCSV.close();
//            Log.e("MainActivity", "FILE CREATED");
//
//        }
//        catch(Exception sqlEx)
//        {
//            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
//        }
//    }







//    public void exportDCSV(){
//        String columnString =   "\"PersonName\",\"Gender\",\"Street1\",\"postOffice\",\"Age\"";
//        String dataString   =   "\"" + currentUser.userName +"\",\"" + currentUser.gender + "\",\"" + currentUser.street1 + "\",\"" + currentUser.postOFfice.toString()+ "\",\"" + currentUser.age.toString() + "\"";
//        String combinedString = columnString + "\n" + dataString;
//
//        File file   = null;
//        File root   = Environment.getExternalStorageDirectory();
//        if (root.canWrite()){
//            File dir    =   new File (root.getAbsolutePath() + "/PersonData");
//            dir.mkdirs();
//            file   =   new File(dir, "Data.csv");
//            FileOutputStream out   =   null;
//            try {
//                out = new FileOutputStream(file);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            try {
//                out.write(combinedString.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    Uri u1  =   null;
//    u1  =   Uri.fromFile(file);
//
//    Intent sendIntent = new Intent(Intent.ACTION_SEND);
//sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Person Details");
//sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
//sendIntent.setType("text/html");
//    startActivity(sendIntent);
//    }









    public boolean addOne(Inventory invent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_NAME,invent.getName());
        cv.put(COLUMN_ITEM_BARCODE, invent.getBarcode());
        cv.put(COLUMN_ITEM_QUANTITY,invent.getQuantity());
        cv.put(COLUMN_ITEM_MULIPLIER,invent.getMultiplier());

        long insert = db.insert(INVENTORY_TABLE, null, cv);
        if(insert==-1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean deleteOne(Inventory inventdel){
        // find customer model in database and delete if found and ret false if not found
        SQLiteDatabase db = this.getWritableDatabase();
        String querryString = " DELETE FROM "+ INVENTORY_TABLE + " WHERE "+ COLUMN_ID+ " = "+ inventdel.getId();
        Cursor cursor = db.rawQuery(querryString, null);
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        else{cursor.close();
            return false;
        }

    }
    public List<Inventory> getEveryItem(){
        List<Inventory> returnList = new ArrayList<>();
        //get data from the database
        String queryString = "SELECT * FROM "+ INVENTORY_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            //loop through the results and create new item object
            do{
                int itemID = cursor.getInt(0);
                String itemName = cursor.getString(1);
                String itemBarcode = cursor.getString(2);
                int itemQuantity = cursor.getInt(3);
                int itemMultiplier = cursor.getInt(4);
                Inventory invent = new Inventory(itemID,itemName,itemBarcode,itemQuantity,itemMultiplier);
                returnList.add(invent);
            }while(cursor.moveToNext());
        }
        else {
            //failure. do not add anything to the list


        }
        // close both the cursor and the db when done
        cursor.close();
        db.close();
        return returnList;
    }
}
