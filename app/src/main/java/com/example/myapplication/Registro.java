package com.example.myapplication;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;


public class Registro extends AppCompatActivity {

    Button btnUsuario, btnAlumno, inicioSesion; ImageButton btnVolver; EditText mail, alias; TextView notifAlias, mailEnUso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_registro);


        mail = findViewById(R.id.mail);
        alias = findViewById(R.id.ingreseAlias);
        btnUsuario = findViewById(R.id.btn_usuario);
        btnAlumno = findViewById(R.id.btn_alumno);
        btnVolver = findViewById(R.id.btn_volver);
        notifAlias = findViewById(R.id.notifText1);
        mailEnUso = findViewById(R.id.mailEnUso);
        inicioSesion = findViewById(R.id.btn_inicioSesionMailEnUso);




    }
    public boolean validarMail(){
        SesionManager sesionManager = new SesionManager(this);
        String texto_mail= mail.getText().toString().trim();
        boolean valido = true;
        if (texto_mail.isEmpty()){
            mail.setError("El campo no puede estar vacio");
            valido = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(texto_mail).matches()){
            mail.setError("Correo inválido");
            valido = false;
        }if (sesionManager.obtenerEmail().equals(texto_mail)){
            mail.setError("Correo inválido");
            mailEnUso.setVisibility(View.VISIBLE);
            inicioSesion.setVisibility(View.VISIBLE);
            valido = false;
        }else{
            sesionManager.guardarEmail(texto_mail);
        }
        return valido;
    }

    int recom = 0;
    String[] aliasRecom = new String[5];

    public boolean validarAlias() {
        SesionManager sesionManager = new SesionManager(this);
        String texto_alias = alias.getText().toString().trim();
        boolean valido = true;

        if (texto_alias.isEmpty()) {
            alias.setError("El campo no puede estar vacío");
            valido = false;
        }if (sesionManager.obtenerAlias().equals(texto_alias)) {
            recom = 0;
            aliasRecom = new String[5];
            while (recom < 5) {
                aliasRecom[recom] = generarRecomendacionesAlias();
                recom++;
            }
            String aliasOpciones = String.join("\n", aliasRecom);

            SpannableString spannable = new SpannableString("El alias \"" + texto_alias + "\" ya se encuentra en uso.  \n Por favor, elige otro. \n Aquí tienes algunas opciones disponibles: \n" + aliasOpciones);
            spannable.setSpan(new ForegroundColorSpan(Color.WHITE), 9, 11 + texto_alias.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(Color.WHITE), spannable.length() - aliasOpciones.length(), spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            notifAlias.setText(spannable);
            notifAlias.setVisibility(View.VISIBLE);
            valido = false;
        }else {
            sesionManager.guardarAlias(texto_alias);
        }
        return valido;
    }

    //las recomendaciones vienen del back
    public String generarRecomendacionesAlias(){
        String texto_alias = alias.getText().toString().trim();
        String[] alternativas= {"2025","_123","_777","001","_Tasty","_Magic","_Express","_Yummy","_Master","_Chef","_Cook","_Gourmet","_Kitchen","_Recipe","_Delicious"};
        int randomIndex= new Random().nextInt(alternativas.length);
        return texto_alias+alternativas[randomIndex];

    }

    private String tipoUsuario = "";
    public void seleccionarUsuario (View view) {
        btnUsuario.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_ok));
        btnAlumno.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_off));

        tipoUsuario = "usuario";
    }

    public void seleccionarAlumno (View view) {
        btnAlumno.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_ok));
        btnUsuario.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_off));

        tipoUsuario="alumno";
    }

    public void verificarDatos(View view){

        Intent intentUser = new Intent(this, RegistroUsuario.class);
        Intent intentAlum = new Intent(this, RegistroAlumno.class);
        if (validarMail()&&validarAlias()){
            Toast.makeText(this,"OK",Toast.LENGTH_SHORT).show();
            if (tipoUsuario.isEmpty()) {
                Toast.makeText(this, "elija como se quiere registrar",Toast.LENGTH_SHORT).show();

            }else if(tipoUsuario.trim().equals("usuario")){
                startActivity(intentUser);
            }else { startActivity(intentAlum);}

        }else{
            Toast.makeText(this,"No puede continuar con el registro",Toast.LENGTH_SHORT).show();
        }

    }

    public void btnVolverInicio(View view){
        Intent inicio = new Intent(this, Login.class);
            startActivity(inicio);
    }

    public void btn_volver(View view){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}