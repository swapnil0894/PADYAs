package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import com.example.swapnils.pojo.SurveyDetailsDbPojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shrikantk on 20-02-2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "";
    private static String DB_NAME = "survey_insureance";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private String DB_query;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;

        // DB_PATH=folder.getPath();
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        // DB_PATH = myContext.getFilesDir()+ "/databases/";
        System.out.println("Database path : " + DB_PATH);
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase(boolean dbExist) throws IOException {
        if (dbExist) {
            // do nothing - database already exist
        } else {
            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();
            try {
                System.out.println("Database copying started");
                copyDataBase();
                System.out.println("Database copying completed");
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            /*
             * final String ALTER_TBL = "ALTER TABLE " + ChallanDetails +
			 * " ADD COLUMN Col1 varchar(200) null;"; db.execSQL(ALTER_TBL);
			 */
            // db.copyAppDbToDownloadFolder();
            System.out.println("Upgrading.....");
            try {
//                copyAppDbToDownloadFolder();
                copyDataBase();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void UpdateDB() {
        SQLiteDatabase database = this.getWritableDatabase();

        //  ------------------------- Check for tabel exist or not -------------------------------------

        boolean LabRecordTable_exist = checkTableExist(database, "lab_record");
        if (!LabRecordTable_exist) {
            try {
                database.execSQL("CREATE TABLE 'lab_record' " +
                        "('ID' INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                        "'DistrictCode' VARCHAR NOT NULL , " +
                        "'LabJSON' VARCHAR NOT NULL , " +
                        "'DateTime' DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP)");
                Log.e("Added new table name",
                        "dbhelper is successfully upgraded");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to upgrade",
                        "dbhelper fail to upgrade");
            }
        } else
            Log.e("table is already exist",
                    "dbhelper is already modified");



        // -------------------------------------- check for survey table exist -------------------
        boolean SurveyTable_exist = checkTableExist(database, "survey");
        if (!SurveyTable_exist) {
            try {
                database.execSQL("CREATE TABLE 'survey' " +
                        "('survey_no' INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                        "'insurer_name' VARCHAR NOT NULL , " +
                        "'insured_company' VARCHAR NOT NULL , " +
                        "'veichale_no' VARCHAR NOT NULL , " +
                        "'type' VARCHAR NOT NULL , " +
                        "'survey_date' VARCHAR NOT NULL , " +
                        "'place' VARCHAR NOT NULL , " +
                        "'estimeted_amount' VARCHAR NOT NULL , " +
                        "'asserted_amount' VARCHAR NOT NULL , " +
                        "'actvity' VARCHAR NOT NULL , " +
                        "'DateTime' DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP)");
                Log.e("Added new table name",
                        "dbhelper is successfully upgraded");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to upgrade",
                        "dbhelper fail to upgrade");
            }
        } else
            Log.e("table is already exist",
                    "dbhelper is already modified");


        //  ---------------------- Check for column exist or not ---------------------------------------

        boolean SurveyType_exist = Check_ColumnExist(database, "checklist_answers", "SurveyType");
        if (!SurveyType_exist) {
            try {
                database.execSQL("Alter table checklist_answers add column SurveyType VARCHAR");
                Log.e("dB modified",
                        "dbhelper is successfully modified");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to modified",
                        "dbhelper fail to modified");
            }
        } else
            Log.e("dB is already modified",
                    "dbhelper is already modified");



        boolean CartAadharNo_exist = Check_ColumnExist(database, "cart", "aadharno");
        if (!CartAadharNo_exist) {
            try {
                database.execSQL("Alter table cart add column aadharno VARCHAR");
                Log.e("dB modified",
                        "dbhelper is successfully modified");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to modified",
                        "dbhelper fail to modified");
            }
        } else
            Log.e("dB is already modified",
                    "dbhelper is already modified");



        boolean CartAgeYear_exist = Check_ColumnExist(database, "cart", "age_year");
        if (!CartAgeYear_exist) {
            try {
                database.execSQL("Alter table cart add column age_year VARCHAR");
                Log.e("dB modified",
                        "dbhelper is successfully modified");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to modified",
                        "dbhelper fail to modified");
            }
        } else
            Log.e("dB is already modified",
                    "dbhelper is already modified");


        boolean CartAgeMonth_exist = Check_ColumnExist(database, "cart", "age_month");
        if (!CartAgeMonth_exist) {
            try {
                database.execSQL("Alter table cart add column age_month VARCHAR");
                Log.e("dB modified",
                        "dbhelper is successfully modified");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to modified",
                        "dbhelper fail to modified");
            }
        } else
            Log.e("dB is already modified",
                    "dbhelper is already modified");


        boolean CartAgeDay_exist = Check_ColumnExist(database, "cart", "age_day");
        if (!CartAgeDay_exist) {
            try {
                database.execSQL("Alter table cart add column age_day VARCHAR");
                Log.e("dB modified",
                        "dbhelper is successfully modified");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to modified",
                        "dbhelper fail to modified");
            }
        } else
            Log.e("dB is already modified",
                    "dbhelper is already modified");









        boolean AadharNo_exist = Check_ColumnExist(database, "patient", "aadharno");
        if (!AadharNo_exist) {
            try {
                database.execSQL("Alter table patient add column aadharno VARCHAR");
                Log.e("dB modified",
                        "dbhelper is successfully modified");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to modified",
                        "dbhelper fail to modified");
            }
        } else
            Log.e("dB is already modified",
                    "dbhelper is already modified");

        boolean AgeYear_exist = Check_ColumnExist(database, "patient", "age_year");
        if (!AgeYear_exist) {
            try {
                database.execSQL("Alter table patient add column age_year VARCHAR");
                Log.e("dB modified",
                        "dbhelper is successfully modified");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to modified",
                        "dbhelper fail to modified");
            }
        } else
            Log.e("dB is already modified",
                    "dbhelper is already modified");


        boolean AgeMonth_exist = Check_ColumnExist(database, "patient", "age_month");
        if (!AgeMonth_exist) {
            try {
                database.execSQL("Alter table patient add column age_month VARCHAR");
                Log.e("dB modified",
                        "dbhelper is successfully modified");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to modified",
                        "dbhelper fail to modified");
            }
        } else
            Log.e("dB is already modified",
                    "dbhelper is already modified");




        boolean AgeDay_exist = Check_ColumnExist(database, "patient", "age_day");
        if (!AgeDay_exist) {
            try {
                database.execSQL("Alter table patient add column age_day VARCHAR");
                Log.e("dB modified",
                        "dbhelper is successfully modified");
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("dB fail to modified",
                        "dbhelper fail to modified");
            }
        } else
            Log.e("dB is already modified",
                    "dbhelper is already modified");




    }



    public boolean Check_ColumnExist(SQLiteDatabase db, String tablename, String colunmname) {
        Cursor mcursor = null;
        try {
            mcursor = db.rawQuery("SELECT * FROM " + tablename + " LIMIT 0,1", null);
            if (mcursor.getColumnIndex(colunmname) != -1)
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (mcursor != null) mcursor.close();
        }
    }





    public boolean IsrecordExist(int id)
    {
        Cursor mcursor = null;
       // DB_cursor cursor = null;
        try
        {
            DB_query = "SELECT * FROM cart WHERE patient_id=" + id + ";";
            SQLiteDatabase db = this.getReadableDatabase();
            mcursor = db.rawQuery(DB_query, null);

            if (mcursor.moveToFirst())
            {
                mcursor.close();
                return true;
            }
            else
            {
                mcursor.close();
                return false;
            }
        }
        catch (Exception ex)
        {
            mcursor.close();
            Toast.makeText(myContext,"Exception Is"+ex.toString(),Toast.LENGTH_LONG).show();
            //Console.WriteLine("Error : " + ex.Message);
            return false;
        }
    }












    public boolean checkTableExist(SQLiteDatabase database, String tablename) {
        Cursor mcursor = null;
        try {
            mcursor = database.rawQuery("SELECT name FROM sqlite_master " +
                    "WHERE type='table' AND name='" + tablename + "'", null);
            if (mcursor.getCount() >= 1)
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (mcursor != null) mcursor.close();
        }
    }





    public boolean copyDbToFolder() throws IOException {
        SimpleDateFormat sd = new SimpleDateFormat(" dd-MM-yy-hh-mm-ss");

        try {
            File folder = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "HindLab" + File.separator
                    + "File");
            if (!folder.exists())
                folder.mkdirs();
            File backupDB = new File(folder, DB_NAME + sd.format(new Date()));

            // "my_data_backup.db"
            File currentDB = new File(DB_PATH + DB_NAME); // databaseName=your
            // current
            // application
            // database name,
            // for example
            // "my_data.db"
            if (currentDB.exists()) {
                FileInputStream fis = new FileInputStream(currentDB);
                FileOutputStream fos = new FileOutputStream(backupDB);
                fos.getChannel().transferFrom(fis.getChannel(), 0,
                        fis.getChannel().size());
                // or fis.getChannel().transferTo(0, fis.getChannel().size(),
                // fos.getChannel());
                fis.close();
                fos.close();
                Log.i("Database successfully", " copied to HindLabs folder");
                return true;
            } else {
                Log.i("Copying Database", " fail, database not found");
                return false;
            }
        } catch (IOException e) {
            Log.d("Copying Database", "fail, reason:", e);
            return false;
        }
    }

    public long insertSurveyDetails(String cust_name,String insur_name,String viech_no,String sur_type,String sur_date,String sur_place,String est_amount,String assur_amount,String sur_actvity) {

        String currentDateTimeString = (String) android.text.format
                .DateFormat.format("yyyy-MM-dd HH:mm:ss",
                        new Date());
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("patient_id", facilityId);

        values.put("insurer_name", cust_name);
        values.put("insured_company", insur_name);
        values.put("veichale_no", viech_no);
        values.put("type", sur_type);
        values.put("survey_date", sur_date);
        values.put("place", sur_place);
        values.put("estimeted_amount", est_amount);
        values.put("asserted_amount", assur_amount);
        values.put("actvity", sur_actvity);



        long result = -1;
        try {
            database.beginTransaction();
            result = database.insertOrThrow("survey", null, values);
            System.out.println("Result :" + result);
            Log.i("Block inserted", String.valueOf(result));
            if (result == -1) {
                // myDataBase.endTransaction();
            } else {
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }







    public long insertDataIntoCart(int selectedPatientId, String selectedPatientName, String selectedPatientDocName, String selectedPatientAge, String selectedPatientBlGr, String selectedPatientDob, String selectedPatientPhone, String selectedPatientAddress,
                                   String barcode, String sampleNoOfUnit, String selectedTestsId, String selectedTests, String selectedTestsCode,String selectedPatientAadharNo,String selectedPatientAgeYear, String selectedPatientAgeMonth,String selectedPatientAgeDay )

    //************ ,
    {
        long result = -1;
        SQLiteDatabase database = null;
        try {
            database = this.getWritableDatabase();
            database.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("patient_id", selectedPatientId);
            values.put("patient_name", selectedPatientName);
            values.put("patient_doc_name", selectedPatientDocName);
            values.put("patient_age", selectedPatientAge);
            values.put("patient_blgr", selectedPatientBlGr);
            values.put("patient_dob", selectedPatientDob);
            values.put("patient_phone", selectedPatientPhone);
            values.put("patient_address", selectedPatientAddress);

            values.put("barcode_no", barcode);
            values.put("sample_no_of_unit", sampleNoOfUnit);
            values.put("test_id", selectedTestsId);
            values.put("test_name", selectedTests);
            values.put("test_code", selectedTestsCode);
            values.put("aadharno" , selectedPatientAadharNo);
            values.put("age_year",selectedPatientAgeYear);
            values.put("age_month",selectedPatientAgeMonth);
            values.put("age_day",selectedPatientAgeDay);

            result = database.insertOrThrow("cart", null, values);

            System.out.println("Result :" + result);
            Log.i("Block inserted", String.valueOf(result));
            if (result == -1) {
                // myDataBase.endTransaction();
            } else {
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
            database.close();
        }
        return result;
    }




    public int deleteValueFromCart(int patientId) {
        int result = 0;
        try {
           /* String query = "delete from cart where patient_id=" + patientId ;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            cursor.close();
            db.close();*/
            SQLiteDatabase db = this.getReadableDatabase();
            result = db.delete("cart", "patient_id=" + patientId, null);
            db.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    public int deleteAllValuesFromCart() {
        int result = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            result = db.delete("cart", "1", null);
            db.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    public void deleteValueFromPatient() {
        try {
          /*  String query = "delete from patient";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            db.close();*/

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public boolean copyChecklistDbToFolder() throws IOException {
        SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yy");

        try {
            File folder = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "survey_insureance" + File.separator
                    + "File");
            if (!folder.exists())
                folder.mkdirs();
            File backupDB = new File(folder, "DBBackup" + sd.format(new Date()));

            // "my_data_backup.db"
            File currentDB = new File(DB_PATH + DB_NAME); // databaseName=your
            // current
            // application
            // database name,
            // for example
            // "my_data.db"
            if (currentDB.exists()) {
                FileInputStream fis = new FileInputStream(currentDB);
                FileOutputStream fos = new FileOutputStream(backupDB);
                fos.getChannel().transferFrom(fis.getChannel(), 0,
                        fis.getChannel().size());
                // or fis.getChannel().transferTo(0, fis.getChannel().size(),
                // fos.getChannel());
                fis.close();
                fos.close();
                Log.i("Database successfully", " copied to eGovernance folder");
                return true;
            } else {
                Log.i("Copying Database", " fail, database not found");
                return false;
            }
        } catch (IOException e) {
            Log.d("Copying Database", "fail, reason:", e);
            return false;
        }
    }




    public ArrayList<SurveyDetailsDbPojo> getSurveys() {
        ArrayList<SurveyDetailsDbPojo> surveyDetailsList = new ArrayList<>();
        try {

            final String TABLE_NAME = "survey";
            String selectQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);


            if (cursor.moveToFirst()) {
//            int i = 0;
                do {
                    SurveyDetailsDbPojo data = new SurveyDetailsDbPojo();
                    int    survey_no = cursor.getInt(0);
                    String insurer_name = cursor.getString(1);
                    String insured_company  = cursor.getString(2);
                    String veichale_no = cursor.getString(3);
                    String type = cursor.getString(4);
                    String survey_date = cursor.getString(5);
                    String place = cursor.getString(6);
                    String estimeted_amount = cursor.getString(7);
                    String asserted_amount = cursor.getString(8);
                    String actvity  = cursor.getString(9);
                    String DateTime  = cursor.getString(10);



                    //String patientDetails = cursor.getString(2);
                    //data.datetime = cursor.getString(3);

                    data.setSurvey_no(survey_no);
                    data.setInsurer_name(insurer_name);
                    data.setInsured_company(insured_company);
                    data.setVeichale_no(veichale_no);
                    data.setType(type);
                    data.setSurvey_date(survey_date);
                    data.setPlace(place);
                    data.setEstimeted_amount(estimeted_amount);
                    data.setAsserted_amount(asserted_amount);
                    data.setActvity(actvity);
                    data.setDateTime(DateTime);



                    surveyDetailsList.add(data);
                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return surveyDetailsList;
    }
}
