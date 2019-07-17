package com.cubesoft.oleksandr.havryliuk.yspeh

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    var boolean = false
    lateinit var editText: EditText
    lateinit var text10: TextView
    lateinit var text15: TextView
    lateinit var text75: TextView
    lateinit var plusBtn: ImageView
    lateinit var diffBtn: ImageView
    lateinit var counterText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_text)
        text10 = findViewById(R.id.sum_text_10)
        text15 = findViewById(R.id.sum_text_15)
        text75 = findViewById(R.id.sum_text_75)
        plusBtn = findViewById(R.id.plus_button)
        diffBtn = findViewById(R.id.diff_button)
        counterText = findViewById(R.id.count_text_view)

        text10.setOnLongClickListener {
            inverse()
            update()
        }
        text15.setOnLongClickListener {
            inverse()
            update()
        }
        text75.setOnLongClickListener {
            inverse()
            update()
        }

        diffBtn.setOnClickListener {
            val count = counterText.text.toString().toInt()
            if (count > 1) {
                counterText.text = count.minus(1).toString()
                update()
            }
        }

        plusBtn.setOnClickListener {
            val count = counterText.text.toString().toInt()
            if (count < 20) {
                counterText.text = count.plus(1).toString()
                update()
            }
        }

        editText.setSelection(editText.text.length)

        editText.afterTextChanged {
            update()
        }
    }

    fun inverse(): Boolean {
        boolean = boolean.not()

        if (boolean) {
            Toast.makeText(this, "round to one", Toast.LENGTH_SHORT).show()
        }
        if (!boolean) {
            Toast.makeText(this, "round to ten", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    private fun update(): Boolean {
        var sum = editText.text.toString().toDoubleOrNull() ?: 0.0
        sum = sum.div(counterText.text.toString().toInt())

        text10.text = sum.times(0.10).roundToTen(boolean).toString()
        text15.text = sum.times(0.15).roundToTen(boolean).toString()
        text75.text = sum.times(0.75).roundToTen(boolean).toString()

        return true
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}


fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

fun Double.roundToTen(boolean: Boolean): Int {
    val integer = this.roundToInt()

    if (!boolean) {
        return integer
    }

    return if (integer.rem(10) > 4) {
        integer - integer.rem(10) + 10
    } else {
        integer - integer.rem(10)
    }
}