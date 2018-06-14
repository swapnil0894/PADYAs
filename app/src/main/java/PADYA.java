import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;



import java.io.File;

import database.DataBaseHelper;

/**
 * Created by tejasz on 22-01-2018.
 */

public class PADYA extends MultiDexApplication {

    private Context context;
    private boolean dbExist;
    private DataBaseHelper dataBaseHelper;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = getApplicationContext();

        try {
            File dbFile = context.getDatabasePath("survey_insureance");
            dbExist = dbFile.exists();
            dataBaseHelper = new DataBaseHelper(context);

            dataBaseHelper.createDataBase(dbExist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}