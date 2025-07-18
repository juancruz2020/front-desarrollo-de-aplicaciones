package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.dto.cursoFront;
import com.example.myapplication.dto.SedeDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetalleSedeActivity extends AppCompatActivity {

    LinearLayout contenedorCursos, contenedorPromos;
    private SedeDTO sedeSeleccionada;

    List<cursoFront> cursos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_sede);

        sedeSeleccionada = (SedeDTO) getIntent().getSerializableExtra("sede");

        contenedorCursos=findViewById(R.id.contenedorCursos);
        contenedorPromos=findViewById(R.id.contenedorPromos);

        TextView tvNombreSede = findViewById(R.id.tvNombreSede);
        TextView tvDescripcion = findViewById(R.id.tvDescripcionSede);
        TextView tvDireccion = findViewById(R.id.tvDireccion);
        TextView tvTelefono = findViewById(R.id.tvTelefono);
        TextView tvDias = findViewById(R.id.tvDias);
        TextView tvHorario = findViewById(R.id.tvHorario);
        ImageView imgSede = findViewById(R.id.imgSede);

        tvNombreSede.setText(sedeSeleccionada.nombre);
        tvDescripcion.setText(sedeSeleccionada.descripcion);
        tvDireccion.setText(sedeSeleccionada.direccion);
        tvTelefono.setText(sedeSeleccionada.telefono);
        tvDias.setText(sedeSeleccionada.dias);
        tvHorario.setText(sedeSeleccionada.horarios);
        imgSede.setImageResource(sedeSeleccionada.imagenResId);

        cargarCursos();

    }

    public void cerrar(View view){
        Intent intent = new Intent(this, CursosActivity.class);
        startActivity(intent);
    }

    private void cargarCursos() {
        cursos.clear(); // por si se recarga

        cursos.add(new cursoFront(
                "Curso de Pizza Napolitana",
                "Aprendé la técnica original de pizza al horno de leña.",
                "$12000",
                "Presencial",
                "Sábado",
                "19:00 hs",
                "05/06/2025",
                "19/06/2025",
                "Dominar pizza napolitana desde la masa hasta la cocción.",
                "• Harinas y levaduras\n• Fermentación lenta\n• Cocción en horno de leña",
                "• Harina 000 (Empresa)\n• Levadura fresca (Empresa)\n• Pala de pizza (Alumno)",
                "$9500",
                R.drawable.imagenpromocionalinicio2,
                R.drawable.ic_presencial,
                R.drawable.imagenpromocionalinicio2,
                "Carlos Nápoles",
                "Maestro pizzero napolitano con 20 años de experiencia.",
                Arrays.asList("Sede Palermo", "Sede Caballito", "Sede Microcentro", "Sede Devoto", "Sede Retiro", "Sede Barrio Mitre")
        ));

        cursos.add(new cursoFront(
                "Pastelería Clásica Argentina",
                "Tortas, postres y dulces tradicionales.",
                "$15000",
                "Presencial",
                "Miércoles",
                "18:00 hs",
                "10/06/2025",
                "24/06/2025",
                "Dominar técnicas base de pastelería clásica.",
                "• Bizcochuelos\n• Cremas\n• Baños y decoraciones",
                "• Harinas y azúcar (Empresa)\n• Molde desmontable (Alumno)",
                "$12000",
                R.drawable.imagenpromocionalinicio1,
                R.drawable.ic_presencial,
                R.drawable.camipollo,
                "Laura Dulcetti",
                "Pastelera profesional y profesora de cocina dulce.",
                Arrays.asList("Sede Palermo", "Sede Caballito", "Sede Devoto")
        ));

        cursos.add(new cursoFront(
                "Curso de Cocina Vegetariana",
                "Recetas prácticas y nutritivas sin carne.",
                "$8000",
                "Virtual",
                "Sábado",
                "15:30 hs",
                "05/06/2025",
                "31/12/2025",
                "Incorporar platos vegetarianos balanceados.",
                "• Proteínas vegetales\n• Sustitutos caseros\n• Recetas rápidas",
                "• Lista de compras sugerida en PDF",
                "$7000",
                R.drawable.imagenpromocionalinicio2,
                R.drawable.ic_online,
                R.drawable.fotopromocionaliniciocursos,
                "Martín Verde",
                "Chef especializado en cocina vegetariana y saludable.",
                Arrays.asList("Sede Caballito", "Sede Barrio Mitre")
        ));

        cursos.add(new cursoFront(
                "Curso de Sushi en Casa",
                "Aprendé a hacer rolls y nigiris desde cero.",
                "$10000",
                "Presencial",
                "Viernes",
                "20:00 hs",
                "14/06/2025",
                "21/06/2025",
                "Dominar técnicas base de sushi casero.",
                "• Arroz perfecto\n• Rolls tradicionales\n• Tempura y nigiris",
                "• Arroz para sushi (Empresa)\n• Esterilla (Alumno)\n• Pescados frescos (Empresa)",
                "$8500",
                R.drawable.imagenpromocionalinicio1,
                R.drawable.ic_presencial,
                R.drawable.cocinafacil,
                "Kenji Yamato",
                "Sushiman profesional con formación en Japón.",
                Arrays.asList("Sede Microcentro", "Sede Retiro")
        ));

        cursos.add(new cursoFront(
                "Panadería Artesanal",
                "Fermentos, masa madre y panes caseros.",
                "$9000",
                "Virtual",
                "Sábado",
                "10:00 hs",
                "01/06/2025",
                "31/12/2025",
                "Aprender a preparar pan artesanal en casa.",
                "• Masa madre\n• Fermentación lenta\n• Técnicas de cocción",
                "• Lista de insumos en PDF\n• Video paso a paso",
                "9000",
                R.drawable.imagenpromocionalinicio2,
                R.drawable.ic_online,
                R.drawable.cocinandoando,
                "Sofía Leudante",
                "Panadera artesanal especialista en fermentación natural.",
                Arrays.asList("Sede Palermo", "Sede Devoto", "Sede Barrio Mitre")
        ));



        // Agregar mas cursos aca

        for (cursoFront curso : cursos) {
            if (curso.sedes != null && curso.sedes.contains(sedeSeleccionada.nombre)) {
                agregarCursoAlBody(curso);
            }
        }
    }

    private void agregarCursoAlBody(cursoFront curso) {
        View item = getLayoutInflater().inflate(R.layout.item_curso, contenedorCursos, false);

        TextView tvTitulo = item.findViewById(R.id.tvTituloCurso);
        TextView tvDescripcion = item.findViewById(R.id.tvDescripcionCurso);
        TextView tvPrecio = item.findViewById(R.id.tvPrecioCurso);
        TextView tvDescuento = item.findViewById(R.id.tvDescuentoCurso);
        TextView tvFecha = item.findViewById(R.id.tvFechaCurso);
        TextView tvModalidad = item.findViewById(R.id.tvModalidad);
        ImageView imgCurso = item.findViewById(R.id.imgCurso);
        ImageView icModalidad = item.findViewById(R.id.icModalidad);

        tvTitulo.setText(curso.nombre);
        tvDescripcion.setText(curso.descripcion);
        tvPrecio.setText(curso.precio);
        tvFecha.setText(curso.horario);
        tvModalidad.setText(curso.modalidad);
        tvDescuento.setText(""); // opcional
        imgCurso.setImageResource(curso.imagenResId);
        icModalidad.setImageResource(curso.iconoModalidadResId);

        // Si tiene Sede Palermo, calcular 30% de descuento
        if (curso.sedes != null && curso.sedes.contains("Sede Palermo")) {
            try {
                // Parsear el precio quitando el '$'
                int precio = Integer.parseInt(curso.precio.replace("$", "").replace(".", ""));
                int precioConDescuento = precio - (precio * 30 / 100);
                tvDescuento.setText("(Descuento en Sede Palermo: $" + precioConDescuento + ")");
            } catch (NumberFormatException e) {
                tvDescuento.setText(""); // si hay algún error, no se muestra descuento
            }
        } else {
            tvDescuento.setText("");
        }

        // ver detalle curso
        item.setOnClickListener(v -> {
            Intent intent = new Intent(this, DetalleCursosInscripcionActivity.class);
            intent.putExtra("curso", curso);
            startActivity(intent);
        });

        contenedorCursos.addView(item);
    }
}
