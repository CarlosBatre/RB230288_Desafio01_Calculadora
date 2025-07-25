package udb.edu.sv.desafio01_calculadora

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var etMontoTotal: EditText
    private lateinit var etNumPersonas: EditText
    private lateinit var rgPropina: RadioGroup
    private lateinit var etPropinaPersonalizada: EditText
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchIVA: Switch
    private lateinit var btnCalcular: Button
    private lateinit var btnLimpiar: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etMontoTotal = findViewById(R.id.etMontoTotal)
        etNumPersonas = findViewById(R.id.etNumPersonas)
        rgPropina = findViewById(R.id.rgPropina)
        etPropinaPersonalizada = findViewById(R.id.etPropinaPersonalizada)
        switchIVA = findViewById(R.id.switchIVA)
        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        tvResultado = findViewById(R.id.tvResultado)

        rgPropina.setOnCheckedChangeListener { _, checkedId ->
            etPropinaPersonalizada.visibility =
                if (checkedId == R.id.rbOtro) EditText.VISIBLE else EditText.GONE
        }

        // Botón Calcular
        btnCalcular.setOnClickListener {
            calcularPropina()
        }

        // Botón Limpiar
        btnLimpiar.setOnClickListener {
            limpiarCampos()
        }
    }
}