package org.dg.calendar.manual;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class PruneCalendarManual extends Activity {
    // private static final String ACCEPTED_EULA = "ACCEPTED_EULA_TIMESTAMP";
    //
    // private static final int AD_COUNTER_START = 9;
    //
    // private static Uri CALENDAR_BASE_URI = Uri.parse("content://calendar");
    //
    // private static Uri CALENDAR_BASE_URI_8_PLUS = Uri.parse("content://com.android.calendar");
    //
    // private static final long MS_IN_A_DAY = 86400000L;
    //
    // private static final String PREF_AD_COUNTER = "adcounter";
    //
    // private static final String PREF_CALEDARS = "calendars";
    //
    // private static final String PREF_DAYS = "days";
    //
    // private static final String TAG = "PruneCalendar";
    //
    // String SELECTION_CRITERIA = "((rrule is not null and lastDate < ?) or (rrule is null and dtstart < ?)) and calendar_id = ? and deleted == 0";
    //
    // DatePicker datePicker;
    //
    // EditText days;
    //
    // Button dec;
    //
    // Cursor events;
    //
    // Uri eventsUri;
    //
    // Button inc;
    //
    // private SharedPreferences mPrefs;
    //
    // TextView numberSelected;
    //
    // private boolean refreshCursorNeeded;
    //
    // private Integer selectedCalID;
    //
    // private String selectedCalName;
    //
    // Spinner spinner;
    //
    // SimpleCursorAdapter spinnerAdapter;
    //
    // Button start;
    //
    // private void cleanCalendar() {
    //     Integer integer;
    //     Log.d("PruneCalendar", "cleanCalendar starts");
    //     Calendar calendar = Calendar.getInstance();
    //     calendar.set(11, 0);
    //     calendar.set(12, 0);
    //     calendar.set(13, 0);
    //     calendar.set(14, 0);
    //     long l = calendar.getTimeInMillis();
    //     ContentResolver contentResolver = getContentResolver();
    //     String str = this.days.getText().toString();
    //     try {
    //         if (str.length() == 0) {
    //             integer = Integer.valueOf(0);
    //         } else {
    //             int i = Integer.parseInt((String) integer);
    //             integer = Integer.valueOf(i);
    //         }
    //         deleteFromOneCalendar(l, contentResolver, integer, this.selectedCalID, this.selectedCalName);
    //         Log.d("PruneCalendar", "cleanCalendar ends");
    //         return;
    //     } catch (NumberFormatException numberFormatException) {
    //         Log.e("PruneCalendar", "delete aborted - cannot parse the days field value: " + integer);
    //         return;
    //     }
    // }
    //
    // private void deleteFromOneCalendar(long paramLong, ContentResolver paramContentResolver, Integer paramInteger1, Integer paramInteger2, String paramString) {
    //     paramLong -= 86400000L * paramInteger1.intValue();
    //     Log.d("PruneCalendar", "removing (async) older than " + paramInteger1 + " days (" + new Date(paramLong) + ") from " + paramInteger2 + ":" + paramString);
    //     (new AsyncTask<DeleteFromOneCalendarRequest, Void, Integer>() {
    //         protected Integer doInBackground(PruneCalendarManual.DeleteFromOneCalendarRequest... param1VarArgs) {
    //             PruneCalendarManual.DeleteFromOneCalendarRequest deleteFromOneCalendarRequest = param1VarArgs[0];
    //             return Integer.valueOf(deleteFromOneCalendarRequest.getContentResolver().delete(deleteFromOneCalendarRequest.getUri(), deleteFromOneCalendarRequest.getSelectionCriteria(), deleteFromOneCalendarRequest.getArgs()));
    //         }
    //
    //         protected void onPostExecute(Integer param1Integer) {
    //             Log.i("PruneCalendar", "removed " + param1Integer + " event(s)");
    //             PruneCalendarManual.this.start.setText(2131034130);
    //             PruneCalendarManual.this.executeSelection();
    //         }
    //
    //         protected void onPreExecute() {
    //             PruneCalendarManual.this.start.setText("...");
    //         }
    //     }).execute((Object[]) new DeleteFromOneCalendarRequest[]{new DeleteFromOneCalendarRequest(paramContentResolver, this.eventsUri, this.SELECTION_CRITERIA,
    //             new String[]{Long.toString(paramLong), Long.toString(paramLong), Integer.toString(paramInteger2.intValue())}
    //             )});
    // }
    //
    // private void executeSelection() {
    //     boolean bool;
    //     String str;
    //     Calendar calendar = Calendar.getInstance();
    //     calendar.clear();
    //     calendar.set(this.datePicker.getYear(), this.datePicker.getMonth(), this.datePicker.getDayOfMonth());
    //     if (this.selectedCalID == null) {
    //         this.events = null;
    //     } else {
    //         str = this.selectedCalID.toString();
    //         String str1 = Long.toString(calendar.getTime().getTime());
    //         Uri uri = this.eventsUri;
    //         String str2 = this.SELECTION_CRITERIA;
    //         this.events = managedQuery(uri, new String[]{"_id"}, str2, new String[]{str1, str1, str}, null);
    //     }
    //     TextView textView = this.numberSelected;
    //     if (this.events == null) {
    //         str = "0";
    //     } else {
    //         str = Integer.toString(this.events.getCount());
    //     }
    //     textView.setText(str);
    //     Button button = this.start;
    //     if (calendar.getTime().getTime() < System.currentTimeMillis() && this.events != null && this.events.getCount() > 0) {
    //         bool = true;
    //     } else {
    //         bool = false;
    //     }
    //     button.setEnabled(bool);
    // }
    //
    // private String getAccessLevelCN() {
    //     return (Integer.parseInt(Build.VERSION.SDK) <= 13) ? "access_level" : "calendar_access_level";
    // }
    //
    // private Uri getCalendarBaseUri() {
    //     return (Integer.parseInt(Build.VERSION.SDK) <= 7) ? CALENDAR_BASE_URI : CALENDAR_BASE_URI_8_PLUS;
    // }
    //
    // private String getDisplayNameCN() {
    //     return (Integer.parseInt(Build.VERSION.SDK) <= 13) ? "displayName" : "calendar_displayName";
    // }
    //
    // private void modify_days(int paramInt) {
    //     byte b;
    //     Log.d("PruneCalendar", "modify days, change: " + paramInt);
    //     try {
    //         b = Integer.parseInt(this.days.getText().toString());
    //     } catch (Throwable throwable) {
    //         b = 0;
    //     }
    //     this.days.setText(Integer.toString(b + paramInt));
    // }
    //
    // private void setupCursorSpinner() {
    //     Cursor cursor;
    //     String[] arrayOfString = new String[2];
    //     arrayOfString[0] = "_id";
    //     arrayOfString[1] = getDisplayNameCN();
    //     Uri uri = Uri.withAppendedPath(getCalendarBaseUri(), "calendars");
    //     if (Prefs.getShowAll((Context) this)) {
    //         cursor = getContentResolver().query(uri, arrayOfString, null, null, null);
    //     } else {
    //         cursor = getContentResolver().query(uri, (String[]) cursor, getAccessLevelCN() + " >= 500", null, null);
    //     }
    //     byte b = 0;
    //     int i = b;
    //     if (this.selectedCalID != null) {
    //         cursor.moveToFirst();
    //         while (!cursor.isAfterLast() && cursor.getInt(0) != this.selectedCalID.intValue())
    //             cursor.moveToNext();
    //         if (cursor.isAfterLast()) {
    //             Log.d("PruneCalendar", "setupCursorSpinner could not find calendar with id: " + this.selectedCalID);
    //             this.selectedCalID = null;
    //             this.selectedCalName = null;
    //             i = b;
    //         } else {
    //             i = cursor.getPosition();
    //             Log.d("PruneCalendar", "setupCursorSpinner find calendar with id: " + cursor.getInt(0) + " name: " + cursor.getString(1) + " cursor position: " + i);
    //         }
    //     }
    //     this.spinnerAdapter.changeCursor(cursor);
    //     this.spinner.setSelection(i);
    //     Log.d("PruneCalendar", "spinner position set at: " + this.spinner.getSelectedItemPosition() + ", should have been " + i);
    // }
    //
    // public void onCreate(Bundle paramBundle) {
    //     super.onCreate(paramBundle);
    //     setContentView(2130903040);
    //     Log.d("PruneCalendar", "onCreate bundle: " + paramBundle);
    //     final SharedPreferences pref_eula = getSharedPreferences("EULA", 0);
    //     if (!sharedPreferences.contains("ACCEPTED_EULA_TIMESTAMP")) {
    //         AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
    //         builder.setMessage(getString(2131034120)).setPositiveButton(getString(2131034118), new DialogInterface.OnClickListener() {
    //             public void onClick(DialogInterface param1DialogInterface, int param1Int) {
    //                 pref_eula.edit().putLong("ACCEPTED_EULA_TIMESTAMP", System.currentTimeMillis()).commit();
    //             }
    //         }).setNegativeButton(getString(2131034119), new DialogInterface.OnClickListener() {
    //             public void onClick(DialogInterface param1DialogInterface, int param1Int) {
    //                 PruneCalendarManual.this.finish();
    //             }
    //         });
    //         AlertDialog alertDialog = builder.create();
    //         alertDialog.setCancelable(false);
    //         alertDialog.show();
    //     }
    //     this.spinner = (Spinner) findViewById(2130837518);
    //     this.datePicker = (DatePicker) findViewById(2130837508);
    //     this.days = (EditText) findViewById(2130837509);
    //     this.start = (Button) findViewById(2130837505);
    //     this.numberSelected = (TextView) findViewById(2130837519);
    //     this.inc = (Button) findViewById(2130837507);
    //     this.dec = (Button) findViewById(2130837506);
    //     this.eventsUri = Uri.withAppendedPath(getCalendarBaseUri(), "events");
    //     this.spinnerAdapter = new SimpleCursorAdapter((Context) this, 17367048, null, new String[]{getDisplayNameCN()}, new int[]{16908308});
    //     this.spinnerAdapter.setDropDownViewResource(17367049);
    //     this.spinner.setAdapter((SpinnerAdapter) this.spinnerAdapter);
    //     this.mPrefs = getPreferences(0);
    //     int i = this.mPrefs.getInt("calendars", -1);
    //     if (i >= 0) {
    //         Integer integer = Integer.valueOf(i);
    //     } else {
    //         sharedPreferences = null;
    //     }
    //     this.selectedCalID = (Integer) sharedPreferences;
    //     setupCursorSpinner();
    //     this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    //         public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
    //             Cursor cursor = (Cursor) PruneCalendarManual.this.spinner.getItemAtPosition(param1Int);
    //             PruneCalendarManual.access$002(PruneCalendarManual.this, Integer.valueOf(cursor.getInt(0)));
    //             PruneCalendarManual.access$102(PruneCalendarManual.this, cursor.getString(1));
    //             Log.d("PruneCalendar", "spinner selected pos: " + param1Int + ", id: " + PruneCalendarManual.this.selectedCalID + ", name: " + PruneCalendarManual.this.selectedCalName);
    //             PruneCalendarManual.this.executeSelection();
    //         }
    //
    //         public void onNothingSelected(AdapterView<?> param1AdapterView) {
    //             PruneCalendarManual.access$002(PruneCalendarManual.this, (Integer) null);
    //             PruneCalendarManual.access$102(PruneCalendarManual.this, (String) null);
    //             Log.d("PruneCalendar", "spinner selected nothing, reset selection. id: " + PruneCalendarManual.this.selectedCalID + ", name: " + PruneCalendarManual.this.selectedCalName);
    //             PruneCalendarManual.this.executeSelection();
    //         }
    //     });
    //     this.datePicker.init(this.datePicker.getYear(), this.datePicker.getMonth(), this.datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
    //         public void onDateChanged(DatePicker param1DatePicker, int param1Int1, int param1Int2, int param1Int3) {
    //             Calendar calendar1 = Calendar.getInstance();
    //             Calendar calendar2 = Calendar.getInstance();
    //             calendar2.set(param1Int1, param1Int2, param1Int3);
    //             String str1 = Long.toString(Math.round((calendar1.getTime().getTime() - calendar2.getTime().getTime()) / 60.0D / 60.0D / 24.0D / 1000.0D));
    //             String str2 = PruneCalendarManual.this.days.getText().toString();
    //             Log.d("PruneCalendar", "datePicker.onDateChanged days old: '" + str2 + "', new: '" + str1 + "'");
    //             if (!str1.equals(str2)) {
    //                 Log.d("PruneCalendar", "DatePicker sets the new days value '" + str1 + "'");
    //                 PruneCalendarManual.this.days.setText(str1);
    //                 return;
    //             }
    //             Log.d("PruneCalendar", "DatePicker won't set new days value");
    //         }
    //     });
    //     this.days.addTextChangedListener(new TextWatcher() {
    //         public void afterTextChanged(Editable param1Editable) {
    //         }
    //
    //         public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
    //         }
    //
    //         public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
    //             try {
    //                 param1Int1 = Integer.parseInt(param1CharSequence.toString());
    //             } catch (Throwable throwable) {
    //                 param1Int1 = 0;
    //             }
    //             Calendar calendar = Calendar.getInstance();
    //             calendar.add(5, -param1Int1);
    //             param1Int1 = calendar.get(1);
    //             param1Int2 = calendar.get(2);
    //             param1Int3 = calendar.get(5);
    //             int i = PruneCalendarManual.this.datePicker.getYear();
    //             int j = PruneCalendarManual.this.datePicker.getMonth();
    //             int k = PruneCalendarManual.this.datePicker.getDayOfMonth();
    //             if (param1Int1 != i || param1Int2 != j || param1Int3 != k) {
    //                 Log.d("PruneCalendar", "days.onTextChanged updates datePicker from " + i + "-" + j + "-" + k + " to " + param1Int1 + "-" + param1Int2 + "-" + param1Int3);
    //                 PruneCalendarManual.this.datePicker.updateDate(param1Int1, param1Int2, param1Int3);
    //             } else {
    //                 Log.d("PruneCalendar", "days.onTextChanged won't update datePicker, date is the same " + param1Int1 + "-" + param1Int2 + "-" + param1Int3);
    //             }
    //             PruneCalendarManual.this.executeSelection();
    //         }
    //     });
    //     this.start.setOnClickListener(new View.OnClickListener() {
    //         public void onClick(View param1View) {
    //             PruneCalendarManual.this.start.setEnabled(false);
    //             PruneCalendarManual.this.cleanCalendar();
    //         }
    //     });
    //     this.inc.setOnClickListener(new View.OnClickListener() {
    //         public void onClick(View param1View) {
    //             PruneCalendarManual.this.modify_days(1);
    //         }
    //     });
    //     this.dec.setOnClickListener(new View.OnClickListener() {
    //         public void onClick(View param1View) {
    //             PruneCalendarManual.this.modify_days(-1);
    //         }
    //     });
    //     i = (getResources().getDisplayMetrics()).widthPixels;
    //     if (i > (getResources().getDisplayMetrics()).heightPixels && i <= 320) {
    //         this.inc.setVisibility(8);
    //         this.dec.setVisibility(8);
    //     }
    //     this.days.setText(this.mPrefs.getString("days", "0"));
    //     if (Build.VERSION.SDK_INT >= 7) {
    //         i = this.mPrefs.getInt("adcounter", 9);
    //         if (i == 0) {
    //             i = 9;
    //             AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
    //             builder.setMessage(getString(2131034113)).setNegativeButton(getString(2131034114), null).setPositiveButton(getString(2131034117), new DialogInterface.OnClickListener() {
    //                 public void onClick(DialogInterface param1DialogInterface, int param1Int) {
    //                     Intent intent = new Intent("android.intent.action.VIEW");
    //                     intent.setData(Uri.parse("market://details?id=org.dg.calendar.auto"));
    //                     PruneCalendarManual.this.startActivity(intent);
    //                 }
    //             });
    //             AlertDialog alertDialog = builder.create();
    //             alertDialog.setCancelable(false);
    //             alertDialog.show();
    //         } else {
    //             i--;
    //         }
    //         this.mPrefs.edit().putInt("adcounter", i).commit();
    //     }
    //     PreferenceManager.getDefaultSharedPreferences((Context) this).registerOnSharedPreferenceChangeListener(this);
    // }
    //
    // public boolean onCreateOptionsMenu(Menu paramMenu) {
    //     super.onCreateOptionsMenu(paramMenu);
    //     getMenuInflater().inflate(2130968576, paramMenu);
    //     return true;
    // }
    //
    // public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    //     switch (paramMenuItem.getItemId()) {
    //         default:
    //             return super.onOptionsItemSelected(paramMenuItem);
    //         case 2130837517:
    //             startActivity(new Intent((Context) this, Prefs.class));
    //             return true;
    //         case 2130837504:
    //             break;
    //     }
    //     AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
    //     builder.setMessage(getString(2131034116)).setPositiveButton(getString(2131034115), null);
    //     builder.create().show();
    //     return true;
    // }
    //
    // protected void onPause() {
    //     int i = 0;
    //     super.onPause();
    //     Log.d("PruneCalendar", "onPause");
    //     this.refreshCursorNeeded = false;
    //     Log.d("PruneCalendar", "onPause saves the activity state");
    //     SharedPreferences.Editor editor = this.mPrefs.edit();
    //     editor.putString("days", this.days.getText().toString());
    //     if (this.selectedCalID != null)
    //         i = this.selectedCalID.intValue();
    //     editor.putInt("calendars", i);
    //     editor.commit();
    // }
    //
    // protected void onResume() {
    //     super.onResume();
    //     Log.d("PruneCalendar", "onResume");
    //     if (this.refreshCursorNeeded) {
    //         this.refreshCursorNeeded = false;
    //         setupCursorSpinner();
    //     }
    // }
    //
    // public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString) {
    //     Log.d("PruneCalendar", "onSharedPreferenceChanged");
    //     if ("show_all".equals(paramString)) {
    //         Log.d("PruneCalendar", "preference changed - refresh the list of calendars");
    //         this.refreshCursorNeeded = true;
    //     }
    // }
    //
    // class DeleteFromOneCalendarRequest {
    //     String[] args;
    //
    //     ContentResolver contentResolver;
    //
    //     String selectionCriteria;
    //
    //     Uri uri;
    //
    //     public DeleteFromOneCalendarRequest(ContentResolver param1ContentResolver, Uri param1Uri, String param1String, String[] param1ArrayOfString) {
    //         this.contentResolver = param1ContentResolver;
    //         this.uri = param1Uri;
    //         this.selectionCriteria = param1String;
    //         this.args = param1ArrayOfString;
    //     }
    //
    //     public String[] getArgs() {
    //         return this.args;
    //     }
    //
    //     public ContentResolver getContentResolver() {
    //         return this.contentResolver;
    //     }
    //
    //     public String getSelectionCriteria() {
    //         return this.selectionCriteria;
    //     }
    //
    //     public Uri getUri() {
    //         return this.uri;
    //     }
    // }
}
