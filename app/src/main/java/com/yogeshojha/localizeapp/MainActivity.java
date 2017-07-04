package com.yogeshojha.localizeapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button translate_button;
    private Locale locale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration config = getBaseContext().getResources().getConfiguration();

        //Retrieve from the shared preference the language and apply the locale.
        String lang = settings.getString("LANG", "");
        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translate_button = (Button) findViewById(R.id.translate);
        //listen to the button if clicked
        translate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the button is clicked then we show the spinner to the user and allow them
                //to choose the language
                showlanguagechangedialog();
            }
        });
    }
    public void showlanguagechangedialog() {
        //build the alert dialog from language_dialog.xml
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.language_dialog, null);
        dialogBuilder.setView(dialogView);
        final Spinner spinner1 = (Spinner) dialogView.findViewById(R.id.spinner1);
        dialogBuilder.setTitle(getResources().getString(R.string.translate_button_title));
        dialogBuilder.setMessage("Do you really wish to change the language?");
        dialogBuilder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                int langpos = spinner1.getSelectedItemPosition();
                switch(langpos) {
                    case 0: //English
                        //save the selection in the shared preference
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "en").commit();
                        setLangRecreate("en");
                        return;
                    case 1: //Spanish
                        //save the selection in the shared preference
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "es").commit();
                        setLangRecreate("es");
                        return;
                    case 2: //Hindi
                        //save the selection in the shared preference
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "hi").commit();
                        setLangRecreate("hi");
                        return;
                    case 3: //Japanese
                        //save the selection in the shared preference
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "ja").commit();
                        setLangRecreate("ja");
                        return;
                    default: //By default we set the language to english
                        setLangRecreate("en");
                        return;
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //if clicked on cancel, do nothing
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void setLangRecreate(String langval) {
        //change the language
        Configuration config = getBaseContext().getResources().getConfiguration();
        locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }
}
