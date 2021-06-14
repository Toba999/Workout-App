package com.example.android.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.android.a7minutesworkout.databinding.ActivityBMIBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    val metricUnitsView="Metric_Units_View"
    val uSUnitsView="US_Units_View"
    var currentVisibleView:String = metricUnitsView


    private lateinit var binding:ActivityBMIBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBMIBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarBmiActivity)

        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title="CALCULATE BMI"
        }

        binding.toolbarBmiActivity.setOnClickListener {
            onBackPressed()
        }
        binding.btnCalculateUnit.setOnClickListener {
            calculateBMI()
        }

        binding.rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnits()
            }else if (checkedId==R.id.rbUSUnits){
                makeVisibleUSUnits()
            }
        }

    }

    private fun makeVisibleMetricUnits(){
        binding.llMetricUnits.visibility=View.VISIBLE
        binding.llUSUnits.visibility=View.GONE
        binding.etUSUnitHeightFeet.text!!.clear()
        binding.etUSUnitHeightInch.text!!.clear()
        binding.etUSUnitWeight.text!!.clear()
        currentVisibleView=metricUnitsView
    }

    private fun makeVisibleUSUnits(){
        binding.llMetricUnits.visibility=View.GONE
        binding.llUSUnits.visibility=View.VISIBLE
        binding.etMetricUnitHeight.text!!.clear()
        binding.etMetricUnitWeight.text!!.clear()
        currentVisibleView=uSUnitsView
    }

    private fun validateMetricUnits():Boolean {
        var isValid = true
        val  weightValue=binding.etMetricUnitWeight.text.toString()
        val heightValue=binding.etMetricUnitHeight.text.toString()

        if (weightValue.isEmpty()) {
            isValid = false
        } else if (heightValue.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun validateUsUnits():Boolean {
        var isValid = true
        val  weightValue=binding.etUSUnitWeight.text.toString()
        val heightValueInch=binding.etUSUnitHeightInch.text.toString()
        val heightValueFeet=binding.etUSUnitHeightFeet.text.toString()

        when {
            weightValue.isEmpty() -> {
                isValid = false
            }
            heightValueInch.isEmpty() -> {
                isValid = false
            }
            heightValueFeet.isEmpty() -> {
                isValid = false

            }
        }
        return isValid
    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0)  {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        binding.llDisplayBMI.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.tvBMIValue.text = bmiValue // Value is set to TextView
        binding.tvBMIType.text = bmiLabel // Label is set to TextView
        binding.tvBMIDescription.text = bmiDescription // Description is set to TextView
    }

    private fun calculateBMI(){
        if (currentVisibleView == metricUnitsView){
            if (validateMetricUnits()){
                val weightValue =(binding.etMetricUnitWeight.text.toString().toFloat())
                val heightValue=binding.etMetricUnitHeight.text.toString().toFloat()/100
                val result= (weightValue/(heightValue*heightValue))
                displayBMIResult(result)
            }else {
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }

        }else if (currentVisibleView ==uSUnitsView) {
            if (validateUsUnits()) {
                val usWeightValue = (binding.etUSUnitWeight.text.toString().toFloat())
                val usHeightValueFeet = binding.etUSUnitHeightFeet.text.toString().toFloat()
                val usHeightValueInch = binding.etUSUnitHeightInch.text.toString().toFloat()
                val usHeightValue = usHeightValueInch + (usHeightValueFeet * 12)
                val result = 703 * (usWeightValue / (usHeightValue * usHeightValue))
                displayBMIResult(result)
            } else {
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }



}