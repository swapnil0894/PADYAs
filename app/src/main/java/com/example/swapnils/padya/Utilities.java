package com.example.swapnils.padya;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    public static NotificationManager mManager;
    public static SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dfDate2 = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat dfDate3 = new SimpleDateFormat("MM/dd/yyyy");
    public static SimpleDateFormat dfDate4 = new SimpleDateFormat("yyyy/MM/dd");

    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }

    public static boolean isValidPhoneNumber(EditText edt) {
        edt.setError(null);
        if (edt.getText().toString().trim().length() < 6 ||
                edt.getText().toString().trim().length() > 13) {
            edt.setError("Enter valid phone number");
            edt.requestFocus();
            return false;
        } else
            return true;
    }

    public static boolean isEmailValid(EditText edt) {
        edt.setError(null);
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = edt.getText().toString().trim();
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        } else {
            edt.setError("Enter valid email id");
            edt.requestFocus();
            return false;
        }
    }

    public static boolean isIfscValid(EditText edt) {
        edt.setError(null);
        String expression = "[A-Z]{4}[0][0-9]{6}$";
        CharSequence inputStr = edt.getText().toString().trim();
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAccNoValid(EditText edt, int accMinLength, int accMaxLength) {
        edt.setError(null);
        Log.i("AccNo", "" + accMinLength + " " + accMaxLength);
        boolean isValid = false;
        String accNoStr = edt.getText().toString().trim();
        if (accNoStr.length() >= accMinLength && accNoStr.length() <= accMaxLength) {
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo() != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEmpty(EditText... edt) {
        int cnt = 0;
        for (int i = 0; i < edt.length; i++)
            edt[i].setError(null);

        for (int i = 0; i < edt.length; i++)
            if (edt[i].getText().toString().trim().length() == 0
                    || edt[i].getText().toString().trim().equalsIgnoreCase("")
                    || edt[i].getText().toString().trim().equalsIgnoreCase(" ")) {
                edt[i].setError("Please enter mandatory fields");
                edt[i].requestFocus();
                cnt++;
            }
        return (cnt == 0) ? false : true;
    }

    public static void setEmpty(EditText... edt) {
        for (int i = 0; i < edt.length; i++) {
            if (edt[i].getText().toString().length() == 0) {
                edt[i].setText("");
            }
        }
    }

    public static boolean setEmptyError(EditText... edt) {
        boolean flag = true;
        for (int i = 0; i < edt.length; i++) {
            if (edt[i].getText().toString().length() == 0) {
                edt[i].setError("Invalid");
                flag = false;
            } else
                edt[i].setError(null);
        }
        return flag;
    }

    public static boolean isPanNum(EditText edt) {
        edt.setError(null);
        if ((edt.getText().toString().trim().length() == 10)
                && (isValidPanNum(edt.getText().toString().trim()))
                || (edt.getText().toString().trim().length() == 0))
            return true;
        else {
            edt.setError("Enter valid Pan number");
            edt.requestFocus();
            return false;
        }
    }

    public static boolean isMobileNo(EditText edt) {
        edt.setError(null);
        if ((edt.getText().toString().trim().length() == 10)
                && (isValidMobileno(edt.getText().toString().trim())))
            return true;

        else {
            edt.setError("Enter valid mobile number");
            edt.requestFocus();
            return false;
        }
    }

//    public static boolean isAdhar(EditText edt) {
//        edt.setError(null);
//        if (validateAadharNumber(edt.getText().toString().trim())) {
//            return true;
//        } else if (edt.getText().toString().trim().length() == 0) {
//            return true;
//        } else {
//            edt.setError("Enter valid aadhar number");
//            edt.requestFocus();
//            return false;
//        }
//    }

//    public static boolean validateAadharNumber(String aadharNumber) {
//        Pattern aadharPattern = Pattern.compile("\\d{12}");
//        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
//        if (isValidAadhar) {
//            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
//        }
//        return isValidAadhar;
//    }

    public static boolean isVoterID(EditText edt) {
        edt.setError(null);
        if (edt.getText().toString().trim().length() == 10
                || edt.getText().toString().trim().length() == 14
                || edt.getText().toString().trim().length() == 0)
            return true;
        else {
            edt.setError("Enter valid Voter ID number");
            edt.requestFocus();
            return false;
        }
    }

    public static boolean isBankAcc(EditText edt, String min, String max) {
        int lenthmin = Integer.parseInt(min);
        int lenthmax = Integer.parseInt(max);
        edt.setError(null);

        if ((edt.getText().toString().trim().length() >= lenthmin
                && edt.getText().toString().trim().length() <= lenthmax)) {
            return true;
        } else {
            edt.setError("Enter valid Acc. No between " + min + " - " + max);
            edt.requestFocus();
            return false;
        }
    }

    public static int get_count_of_days(String Created_date_String, String Expire_date_String) {
        Date Created_convertedDate = null, Expire_CovertedDate = null;
//        Date todayWithZeroTime = null;
        try {
            Created_convertedDate = dfDate.parse(Created_date_String);
            Expire_CovertedDate = dfDate.parse(Expire_date_String);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int c_year = 0, c_month = 0, c_day = 0;

        Calendar c_cal = Calendar.getInstance();
        c_cal.setTime(Created_convertedDate);
        c_year = c_cal.get(Calendar.YEAR);
        c_month = c_cal.get(Calendar.MONTH);
        c_day = c_cal.get(Calendar.DAY_OF_MONTH);
        Calendar e_cal = Calendar.getInstance();
        e_cal.setTime(Expire_CovertedDate);

        int e_year = e_cal.get(Calendar.YEAR);
        int e_month = e_cal.get(Calendar.MONTH);
        int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(c_year, c_month, c_day);
        date2.clear();
        date2.set(e_year, e_month, e_day);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return (int) dayCount;
    }

    public static boolean CheckDates(String fromdate, String todate) {
        boolean b = false;
        try {
            if (dfDate3.parse(fromdate).before(dfDate3.parse(todate)))
                b = true;//If start date is before end date

            if (dfDate3.parse(fromdate).equals(dfDate3.parse(todate)))
                b = true;//If start date is before end date

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    public static boolean CheckTwoDates(DateFormat dateFormat, String fromdate, String todate) {
        boolean b = false;
        try {
            if (dateFormat.parse(fromdate).before(dateFormat.parse(todate)))
                b = true;                                                                           //If start date is before end date

            if (dateFormat.parse(fromdate).equals(dateFormat.parse(todate)))
                b = true;                                                                           //If start date is before end date

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    public static String ConvertDateFormat(DateFormat dateFormat, int day, int month, int year) {
        String startDateString = String.valueOf(day) + "/"
                + String.valueOf(month) + "/"
                + String.valueOf(year);
        Date startDate;
        String newDateString = "";
        try {
            startDate = dfDate2.parse(startDateString);
            newDateString = dateFormat.format(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;
    }

    public static boolean CheckDatesValidation(String fromdate, String todate) {
        boolean b = false;
        try {
            if (dfDate2.parse(fromdate).before(dfDate2.parse(todate)))
                b = true;//If start date is before end date
            if (dfDate2.parse(fromdate).equals(dfDate2.parse(todate)))
                b = true;//If start date is before end date
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    public static boolean isInBetweenStartAndEndDate(DateFormat dateFormat, String currentDate,
                                                     String PastDate, String selectedDate) {
        boolean b = false;
        try {
            Date CDate = dateFormat.parse(currentDate);
            Date PDate = dateFormat.parse(PastDate);
            Date SDate = dateFormat.parse(selectedDate);
            if (SDate.after(PDate) && SDate.before(CDate))
                b = true;
            if (SDate.equals(PDate) || SDate.equals(CDate))
                b = true;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    private static boolean isValidMobileno(String mobileno) {
        String Mobile_PATTERN = "^[6-9]{1}[0-9]{9}$";                                               //^[+]?[0-9]{10,13}$
        Pattern pattern = Pattern.compile(Mobile_PATTERN);
        Matcher matcher = pattern.matcher(mobileno);
        return matcher.matches();
    }

    private static boolean isValidPincode(String pincode) {
        String Pincode_PATTERN = "[4]{1}[0-9]{5}";
        Pattern pattern = Pattern.compile(Pincode_PATTERN);
        Matcher matcher = pattern.matcher(pincode);
        return matcher.matches();
    }

    private static boolean isValidPanNum(String pannum) {
        String Pannum_PATTERN = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        Pattern pattern = Pattern.compile(Pannum_PATTERN);
        Matcher matcher = pattern.matcher(pannum);
        return matcher.matches();
    }

    public static String compressImage(String filePath) {

        //String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

//        float maxHeight = 816.0f;
//        float maxWidth = 612.0f;

        float maxHeight = 500.0f;
        float maxWidth = 500.0f;

        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    //******************************* Massages Methods *********************************************

    /* show message int*/
    public static void showMessage(int msg, Context context) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /* show message String*/
    public static void showMessageString(String msg, Context context) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    static AlertDialog alertDialog;

    @SuppressWarnings("deprecation")
    public static void showAlertDialog(Context context, String title,
                                       String message, String status) {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if (status.equalsIgnoreCase(ApplicationConstants.SUCCESS))
            alertDialog.setIcon(R.drawable.icon_success);
        else if (status.equalsIgnoreCase(ApplicationConstants.FAIL))
            alertDialog.setIcon(R.drawable.icon_fail);
        else if (status.equalsIgnoreCase(ApplicationConstants.ALERT))
            alertDialog.setIcon(R.drawable.icon_alert);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null)
            return false;
        else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true; // <-- -- -- Connected
        }
        return false; // <-- -- -- NOT Connected
    }

    public static List<String> GetSundays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<String> saturdaysAndSundays = new ArrayList<String>();
        int count = 0;
        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(year, month - 1, day);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SUNDAY) {
                saturdaysAndSundays.add(String.valueOf(day));
                count++;
            }
        }
        return saturdaysAndSundays;
    }

    public static ArrayList<String> roundOffValuesSizeTwo(ArrayList<String> item) {
        for (int i = 0; i != item.size(); i++) {
            if (item.get(i).length() == 1) {
                item.set(i, "0" + item.get(i));
            }
        }
        return item;
    }

    public static void showAlertDialogFail(Context context, String title,
                                           String message, Boolean status) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_alert_dialog_layout);
        dialog.setCancelable(false);
        ImageView errorImv = (ImageView) dialog.findViewById(R.id.cd_error_imv_id);
        TextView titleTv = (TextView) dialog.findViewById(R.id.custom_dialog_title_tv);
        TextView msgTv = (TextView) dialog.findViewById(R.id.cd_msg_tv_id);
        Button okBtn = (Button) dialog.findViewById(R.id.cd_ok_btn_id);
        titleTv.setText(title);
        if (status) {
            errorImv.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_success));
        } else {
            errorImv.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_fail));
        }
        msgTv.setText(message);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
