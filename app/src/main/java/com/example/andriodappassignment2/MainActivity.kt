package com.example.andriodappassignment2

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import android.graphics.Color


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // declaring my layout variables:
        val welcomeScreenText: TextView = findViewById(R.id.wecomeScreenText)
        val startQuizButton: Button = findViewById(R.id.startQuizButton)
        val userScoreTextBox: TextView = findViewById((R.id.userScoreTextBox))
        val questionsTextBox: TextView = findViewById(R.id.questionsTextBox)
        val answerTrueButton: Button = findViewById(R.id.answerTrueButton)
        val answerFalseButton: Button = findViewById(R.id.answerFalseButton)
        val answerDisplayMessage: TextView = findViewById(R.id.answerDisplayMessage)
        answerDisplayMessage.visibility = View.INVISIBLE

        // delcaring our global user answer variables
        var userHasAnsweredQuestion: Boolean = false
        var usersAnswer: Boolean = false

        // declaring out 2 parllel arrays
        // questionsArray = 5 true/false type questions
        val questionsArray: Array<String> = arrayOf(
            "The star sign Aquarius is represented by a tiger",
            "The Battle Of Hastings took place in 1066",
            "H&M stands for Hennes & Mauritz",
            "K is worth four points in Scrabble",
            "In a deck of cards, the king has a moustache"
        )

        // answersArray = 5 corresponsing answers
        val answersArray: Array<Boolean> = arrayOf(
            false,
            true,
            true,
            true,
            false
        )

        fun showQuizSummaryFunction(){

        }

        fun showCorrectAnswerPopUpMessage(){
            // first we unhide our answerDisplayMessage text box
            answerDisplayMessage.visibility = View.VISIBLE
            // we give our answer message a green background :)
            answerDisplayMessage.setBackgroundColor(Color.GREEN)
            // then we set our text content to "Correct!"
            answerDisplayMessage.setText("Correct!")
        }

        fun showIncorrectAnswerPopUpMessage(){
            // show our answer pop up
            answerDisplayMessage.visibility = View.VISIBLE
            // set our background to red
            answerDisplayMessage.setBackgroundColor(Color.RED)
            // give our text content "Incorrect"
            answerDisplayMessage.setText("Incorrect")
        }

        // Flashcard questions: add rerencing for questions (https://www.cosmopolitan.com/uk/entertainment/a32612392/best-true-false-quiz-questions/)

        fun showFlashCards(){
            // display our initial score text with our inital score count
            var indexCounter: Int = 0
            var scoreCounter: Int = 0
            var initialUserScore = "Score: ${scoreCounter}/5"
            userScoreTextBox.setText(initialUserScore)

            // show our first question in our parallel array in our questions text box
            questionsTextBox.setText(questionsArray[indexCounter]);

            // if our user has clicked on one of the buttons then we can check their answer
            if (userHasAnsweredQuestion){
                // here we can access our correct answer to the question
                // by using the same indexCounter from our questionsArray for our answersArray
                // due to the arrays being parallel
                var answerToQuestion = answersArray[indexCounter]

                if(answerToQuestion == usersAnswer){
                    // if our users answer matches the correct answer
                    // then we increase our scoreCounter to update our user score
                    scoreCounter++
                    // and we can then show our "Correct!" message
                    showCorrectAnswerPopUpMessage()

                } else{
                    // else the user got the question wrong
                    // so we leave our scoreCounter and show our "Incorrect" message
                    showIncorrectAnswerPopUpMessage()
                }

                // here we increment our indexCounter to move to the next question
                indexCounter++

                // lastly we need to call our function again to continue our loop
                // but only up until the last question in our array
                // so while we are within our array size we recall our function
                if (indexCounter < questionsArray.size){
                    showFlashCards()

                } else {
                    // else we call our showQuizSummaryFunction
                    showQuizSummaryFunction()
                }
            }
        }

        // giving our true or false answer buttons onclick event listeners to set
        // the userHasAnsweredQuestion to true and usersAnswer to true or false
        answerTrueButton.setOnClickListener(){
            // if they click true then we set our usersAnswer to true
            usersAnswer = true
            // set our user has answered question boolean to true to continue to our next question
            userHasAnsweredQuestion = true
        }

        answerFalseButton.setOnClickListener(){
            // else if they click on false then we set our usersAnswer to false
            usersAnswer = false
            userHasAnsweredQuestion = true
        }


        // Welcome screen
        // when the user clicks "Start" we then load the flashcard question screen
        // once the start button is clicked we start the quiz
        startQuizButton.setOnClickListener(){
            // first we need to hide our welcome title and start button
            welcomeScreenText.visibility = View.INVISIBLE
            startQuizButton.visibility = View.INVISIBLE

            // here we show our score text box we are going to display our score int
            // show our text box which we are going to use to load our questions inside of
            // and show our true or false answer buttons for the quiz
            userScoreTextBox.visibility = View.VISIBLE
            questionsTextBox.visibility = View.VISIBLE
            answerTrueButton.visibility = View.VISIBLE
            answerFalseButton.visibility = View.VISIBLE
            // show our true or false answer buttons
            // then call our function to show our flashcard question section
            showFlashCards()
        }



        // 3. Score screen logic
        // after final question score screen loaded
        // shows total score
        // display feedback based on the total score:
        // scores of 3 or more = "Great Job!"
        // scores of less than 3 = "Keep Practising!"
        // "Review" button:
        // -> displays screen with all questions and correct/incorrect answers

    }
}