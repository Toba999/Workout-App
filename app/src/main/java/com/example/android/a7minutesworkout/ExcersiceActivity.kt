package com.example.android.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.a7minutesworkout.databinding.ActivityExcersiceBinding
import com.example.android.a7minutesworkout.databinding.DialogCustomBackConfirmationBinding
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExcersiceActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

   private var restTimer:CountDownTimer?=null
    private var restProgressBar=0
    private var restProgressDuration:Long=10

    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgressBar=0
    private var exerciseProgressDuration:Long=30

    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1

    private var tts:TextToSpeech?=null
    private var player :MediaPlayer?=null
    private var exerciseAdapter:ExerciseStatusAdapter?=null


    private lateinit var binding: ActivityExcersiceBinding
    private lateinit var dialogBinding: DialogCustomBackConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExcersiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarExerciseActivity)
        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarExerciseActivity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts=TextToSpeech(this,this)

        exerciseList=Constants.defaultExerciseList()
        setupRestView()

        setupExerciseStatusRecycleView()

    }


    override fun onDestroy() {
        if (restTimer!=null){
            restTimer!!.cancel()
            restProgressBar=0
        }

        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgressBar=0
        }

        if (tts!=null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        if (player!=null){
            player!!.stop()
        }

        super.onDestroy()
    }

    private fun setRestProgressBar(){
        binding.progressBar.progress=restProgressBar
        restTimer=object:CountDownTimer(restProgressDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgressBar++
                binding.progressBar.progress=restProgressDuration.toInt()-restProgressBar
                binding.tvTimer.text=(restProgressDuration.toInt()-restProgressBar).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        binding.progressBar.progress=exerciseProgressBar
        exerciseTimer=object:CountDownTimer(exerciseProgressDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgressBar++
                binding.progressBarExercise.progress=exerciseProgressDuration.toInt()-exerciseProgressBar
                binding.tvExerciseTimer.text=(exerciseProgressDuration.toInt()-exerciseProgressBar).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition<exerciseList?.size!!-1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                   finish()
                    val intent=Intent(this@ExcersiceActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    private fun setupExerciseView(){
        binding.llRestView.visibility= View.GONE
        binding.llExerciseView.visibility= View.VISIBLE

        if (exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgressBar=0
        }
        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()
        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text=exerciseList!![currentExercisePosition].getName()
    }

    private fun setupRestView(){

        try {
            player=MediaPlayer.create(applicationContext,R.raw.press_start)
            player!!.isLooping=false
            player!!.start()
        }catch (e:Exception){
            e.printStackTrace()
        }


        binding.llRestView.visibility= View.VISIBLE
        binding.llExerciseView.visibility= View.GONE

        if (restTimer!=null){
            restTimer!!.cancel()
            restProgressBar=0
        }
        binding.restExerciseName.text=exerciseList!![currentExercisePosition+1].getName()
        setRestProgressBar()
    }

    override fun onInit(status: Int) {
        if (status==TextToSpeech.SUCCESS){
            var result=tts!!.setLanguage(Locale.US)
            if (result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","the language specified is not supported")
            }
        }else{
            Log.e("TTS","Initialization failed!")
        }

    }

    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseStatusRecycleView(){
        binding.rvExerciseStatus.layoutManager=
                LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!,this)
        binding.rvExerciseStatus.adapter=exerciseAdapter
    }

    private fun customDialogForBackButton(){
        val customDialog=Dialog(this)
        dialogBinding= DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        dialogBinding.tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

}