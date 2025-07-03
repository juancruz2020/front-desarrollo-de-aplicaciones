package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.dto.CursoDTO;

import java.util.ArrayList;
import java.util.List;

public class CursosActivity extends AppCompatActivity {

    TextView tabDescubrir, tabMisCursos;
    LinearLayout contenedorDescubrir, contenedorMisCursos;
    View lineaSelector;
    LinearLayout tabOptions;

    List<CursoDTO> cursos = new ArrayList<>();  // Lista de cursos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        // Vinculamos views
        tabDescubrir = findViewById(R.id.tabDescubrir);
        tabMisCursos = findViewById(R.id.tabMisCursos);
        contenedorDescubrir = findViewById(R.id.contenedorCursosDescubrir);
        contenedorMisCursos = findViewById(R.id.contenedorMisCursos);
        lineaSelector = findViewById(R.id.lineaSelector);
        tabOptions = findViewById(R.id.tabOptions);

        // Ajustamos ancho de lineaSelector según la mitad del ancho del tabOptions
        tabOptions.post(() -> {
            int anchoMitad = tabOptions.getWidth() / 2;
            LayoutParams params = lineaSelector.getLayoutParams();
            params.width = anchoMitad;
            lineaSelector.setLayoutParams(params);
            lineaSelector.setTranslationX(0);
        });

        // Configuramos comportamiento de los tabs
        tabDescubrir.setOnClickListener(v -> {
            contenedorDescubrir.setVisibility(View.VISIBLE);
            contenedorMisCursos.setVisibility(View.GONE);
            tabDescubrir.setTextColor(Color.parseColor("#FFBA61"));
            tabMisCursos.setTextColor(Color.parseColor("#FFFFFF"));
            lineaSelector.animate().translationX(0).setDuration(300).start();
        });

        tabMisCursos.setOnClickListener(v -> {
            contenedorDescubrir.setVisibility(View.GONE);
            contenedorMisCursos.setVisibility(View.VISIBLE);
            tabDescubrir.setTextColor(Color.parseColor("#FFFFFF"));
            tabMisCursos.setTextColor(Color.parseColor("#FFBA61"));
            int anchoMitad = tabOptions.getWidth() / 2;
            lineaSelector.animate().translationX(anchoMitad).setDuration(300).start();
        });

        // Inicializamos con Descubrir visible
        contenedorDescubrir.setVisibility(View.VISIBLE);
        contenedorMisCursos.setVisibility(View.GONE);
        tabDescubrir.setTextColor(Color.parseColor("#FFBA61"));
        tabMisCursos.setTextColor(Color.parseColor("#FFFFFF"));

        cargarCursosDescubrir();

    }

    private void cargarCursosDescubrir() {
        cursos.clear(); // por si se recarga

        cursos.add(new CursoDTO(
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
                "Maestro pizzero napolitano con 20 años de experiencia."
        ));

        cursos.add(new CursoDTO(
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
                "Pastelera profesional y profesora de cocina dulce."
        ));

        cursos.add(new CursoDTO(
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
                "Chef especializado en cocina vegetariana y saludable."
        ));

        cursos.add(new CursoDTO(
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
                "Sushiman profesional con formación en Japón."
        ));

        cursos.add(new CursoDTO(
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
                "Panadera artesanal especialista en fermentación natural."
        ));



        // Agregar mas cursos aca

        for (CursoDTO curso : cursos) {
            agregarCursoAlBody(curso);
        }
    }

    private void agregarCursoAlBody(CursoDTO curso) {
        View item = getLayoutInflater().inflate(R.layout.item_curso, contenedorDescubrir, false);

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

        item.setOnClickListener(v -> {
            Intent intent = new Intent(CursosActivity.this, DetalleCursosInscripcionActivity.class);
            intent.putExtra("curso", curso);
            startActivity(intent);
        });

        contenedorDescubrir.addView(item);
    }



}
