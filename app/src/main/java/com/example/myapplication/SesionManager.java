package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
public class SesionManager {

    private static final String PREFS_NAME="my_prefs";
    private static final String KEY_EMAIL = "email_usuario";
    private static final String KEY_ALIAS = "alias_usuario";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SesionManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void guardarEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String obtenerEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }


    public void guardarAlias(String alias) {
        editor.putString(KEY_ALIAS, alias);
        editor.apply();
    }

    public String obtenerAlias() {
        return prefs.getString(KEY_ALIAS, null);
    }

    //al cerrar sesion se elimina el mail
    public void cerrarSesion() {
        editor.clear();
        editor.apply();
    }

}
