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
    private fun calcularPropina() {
        // Validar campos vacíos
        val montoStr = etMontoTotal.text.toString()
        val personasStr = etNumPersonas.text.toString()

        if (montoStr.isBlank()) {
            etMontoTotal.error = "Ingrese el monto total"
            return
        }
        if (personasStr.isBlank()) {
            etNumPersonas.error = "Ingrese el número de personas"
            return
        }

        val monto = montoStr.toDoubleOrNull()
        val personas = personasStr.toIntOrNull()

        if (monto == null || monto <= 0) {
            etMontoTotal.error = "Monto inválido"
            return
        }
        if (personas == null || personas <= 0) {
            etNumPersonas.error = "Debe ser mayor a cero"
            return
        }

        // Obtener porcentaje de propina
        val propinaPorcentaje = when (rgPropina.checkedRadioButtonId) {
            R.id.rb10 -> 10.0
            R.id.rb15 -> 15.0
            R.id.rb20 -> 20.0
            R.id.rbOtro -> {
                val personalizadaStr = etPropinaPersonalizada.text.toString()
                if (personalizadaStr.isBlank()) {
                    etPropinaPersonalizada.error = "Ingrese un valor"
                    return
                }
                val personalizada = personalizadaStr.toDoubleOrNull()
                if (personalizada == null || personalizada < 0) {
                    etPropinaPersonalizada.error = "Valor inválido"
                    return
                }
                personalizada
            }
            else -> {
                Toast.makeText(this, "Seleccione un porcentaje de propina", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Cálculos
        val propina = monto * propinaPorcentaje / 100
        val iva = if (switchIVA.isChecked) monto * 0.16 else 0.0
        val total = monto + propina + iva
        val porPersona = total / personas

        // Mostrar resultados
        val resultado = """
            Propina: $%.2f
            IVA: $%.2f
            Total a pagar: $%.2f
            Total por persona: $%.2f
        """.trimIndent().format(propina, iva, total, porPersona)

        tvResultado.text = resultado
    }

}