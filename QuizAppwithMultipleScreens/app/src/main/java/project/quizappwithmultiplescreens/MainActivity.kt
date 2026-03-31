package project.quizappwithmultiplescreens

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var score = 0
    private var currentQuestionIndex = 0
    private var timeLeftInMillis: Long = 30000 
    private lateinit var countDownTimer: CountDownTimer

    private lateinit var tvTimer: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var btnOpt1: Button
    private lateinit var btnOpt2: Button
    private lateinit var btnOpt3: Button

    private var randomizedQuestions: List<Question> = emptyList()

    private val allQuestions = listOf(
        // Tech & Android
        Question("Which company developed the Android OS?", listOf("Apple", "Google", "Microsoft"), 1),
        Question("What is the primary language for Android development?", listOf("Java", "Kotlin", "Swift"), 1),
        Question("Which file handles the app's configuration?", listOf("build.gradle", "MainActivity", "Manifest"), 2),
        
        // General Knowledge
        Question("Which planet is known as the Red Planet?", listOf("Mars", "Jupiter", "Venus"), 0),
        Question("What is the largest ocean on Earth?", listOf("Atlantic", "Indian", "Pacific"), 2),
        Question("Who wrote 'Romeo and Juliet'?", listOf("Charles Dickens", "William Shakespeare", "Mark Twain"), 1),
        
        // Science & Math
        Question("What is the chemical symbol for Gold?", listOf("Ag", "Au", "Fe"), 1),
        Question("What is 15 multiplied by 4?", listOf("50", "60", "70"), 1),
        Question("How many legs does a spider have?", listOf("6", "8", "10"), 1),
        
        // Sports & Fun
        Question("Which sport is played at Wimbledon?", listOf("Golf", "Tennis", "Cricket"), 1),
        Question("What is the color of an emerald?", listOf("Red", "Blue", "Green"), 2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTimer = findViewById(R.id.tvTimer)
        tvQuestion = findViewById(R.id.tvQuestion)
        btnOpt1 = findViewById(R.id.btnOpt1)
        btnOpt2 = findViewById(R.id.btnOpt2)
        btnOpt3 = findViewById(R.id.btnOpt3)

        btnOpt1.setOnClickListener { checkAnswer(0) }
        btnOpt2.setOnClickListener { checkAnswer(1) }
        btnOpt3.setOnClickListener { checkAnswer(2) }

        // Increase variety by shuffling and picking 6 questions
        randomizedQuestions = allQuestions.shuffled().take(6)

        displayQuestion()
        startTimer()
    }

    private fun displayQuestion() {
        val currentQuestion = randomizedQuestions[currentQuestionIndex]
        tvQuestion.text = currentQuestion.text
        btnOpt1.text = currentQuestion.options[0]
        btnOpt2.text = currentQuestion.options[1]
        btnOpt3.text = currentQuestion.options[2]
    }

    private fun startTimer() {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }
            override fun onFinish() {
                navigateToResult()
            }
        }.start()
    }

    private fun updateTimerText() {
        val seconds = (timeLeftInMillis / 1000).toInt()
        tvTimer.text = String.format("00:%02d", seconds)
    }

    private fun checkAnswer(selectedIndex: Int) {
        if (selectedIndex == randomizedQuestions[currentQuestionIndex].correctAnswerIndex) {
            score++
        }

        if (currentQuestionIndex < randomizedQuestions.size - 1) {
            currentQuestionIndex++
            timeLeftInMillis = 30000 
            displayQuestion()
            startTimer()
        } else {
            navigateToResult()
        }
    }

    private fun navigateToResult() {
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("FINAL_SCORE", score)
        intent.putExtra("TOTAL_QUESTIONS", randomizedQuestions.size)
        startActivity(intent)
        finish() 
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
    }
}