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
        val continueQuizButton: Button = findViewById(R.id.continueQuizButton)
        val summaryPage: TextView = findViewById(R.id.summaryPage)
        val restartQuiz: Button = findViewById(R.id.restartQuiz)
        val reviewButton: Button = findViewById(R.id.reviewButton)
        val backToSummaryButton: Button = findViewById(R.id.backToSummaryButton)

        // setting our answer pop up for "Correct!" or "Incorrect" and continue button as hidden
        answerDisplayMessage.visibility = View.INVISIBLE
        continueQuizButton.visibility = View.INVISIBLE

        // setting our summaryPage, retake quiz, review, and back to summary buttons as hidden initially
        summaryPage.visibility = View.INVISIBLE
        restartQuiz.visibility = View.INVISIBLE
        reviewButton.visibility = View.INVISIBLE
        backToSummaryButton.visibility = View.INVISIBLE


        // keeping an index count of which question we are on
        var questionIndex: Int = 0
        // keeping track of our user's score with another counter
        var scoreCounter: Int = 0

        // declaring our global user answer variable
        var usersAnswer: Boolean = false

        // declaring out 2 parallel arrays
        // questionsArray = 5 true/false type questions
        // Flashcard questions: add rerencing for questions (https://www.cosmopolitan.com/uk/entertainment/a32612392/best-true-false-quiz-questions/)
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

        // SECTION FOR OUR FUNCTIONS

        // Score screen
        // after final question score screen loaded
        // shows total score
        fun showSummaryPage(){
            // here we should show all the questions and correct and incorrect answers
            // show our summaryPage pop up
            summaryPage.visibility = View.VISIBLE
            summaryPage.setBackgroundColor(Color.WHITE)
            restartQuiz.visibility = View.VISIBLE
            reviewButton.visibility = View.VISIBLE

            // first lets show our overall score
            var summaryTextContent = "Overall Score: ${scoreCounter}/5\n"

            // display feedback based on the total score:
            if(scoreCounter >= 3){
                // scores of 3 or more = "Great Job!"
                // here we display our message on a new line
                summaryTextContent += "\n Great Job!"

            } else{
                // scores of less than 3 = "Keep Practising!"
                summaryTextContent += "\n Keep Practising!"
            }

            // then we give our summary page our summary text content
            summaryPage.setText(summaryTextContent)
            // along with some center text styling and large font size :)
            summaryPage.textAlignment = View.TEXT_ALIGNMENT_CENTER
            summaryPage.textSize = 32f
        }

        // our function to display each question in our array and keep track of the score
        fun showFlashCards(indexCounter: Int){
            // here we need to make sure the indexCounter is withing our range of questions
            if(indexCounter < questionsArray.size){
                // if it is then we show our question using our indexCounter

                // 1. display our initial score text with our inital score count
                var initialUserScore = "Score: ${scoreCounter}/5"
                userScoreTextBox.setText(initialUserScore)

                // 2. show the question in the question text box using our index counter
                var questionToShow = questionsArray[indexCounter]
                questionsTextBox.setText((questionToShow))

                // 3. set out questionIndex to our indexCounter so that we can access the next question
                // in the array the next time the function is called
                questionIndex = indexCounter

            } else{
                // else we have shown all of our questions and it's time to show our summary page
                showSummaryPage()
            }

        }

        fun showCorrectAnswerPopUpMessage(){
            // first we unhide our answerDisplayMessage text box and continue button
            answerDisplayMessage.visibility = View.VISIBLE
            continueQuizButton.visibility = View.VISIBLE
            // we give our answer message a green background :)
            answerDisplayMessage.setBackgroundColor(Color.GREEN)
            // then we set our text content to "Correct!"
            answerDisplayMessage.setText("Correct!")
            // give our message a large font and make the text in the center of the pop up
            answerDisplayMessage.textSize = 32f
            answerDisplayMessage.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        fun showIncorrectAnswerPopUpMessage(){
            // first we unhide our answerDisplayMessage text box and continue button
            answerDisplayMessage.visibility = View.VISIBLE
            continueQuizButton.visibility = View.VISIBLE
            // we give our answer message a red background
            answerDisplayMessage.setBackgroundColor(Color.RED)
            // then we set our text content to "Incorrect"
            answerDisplayMessage.setText("Incorrect")
            // give our message a large font and make the text in the center of the pop up
            answerDisplayMessage.textSize = 32f
            answerDisplayMessage.textAlignment = View.TEXT_ALIGNMENT_CENTER
        }


        // SECTION FOR OUR BUTTON ON CLICK FUNCTIONS:
        backToSummaryButton.setOnClickListener(){
            // hide our back to summary button before reshowing our summary
            backToSummaryButton.visibility = View.INVISIBLE
            showSummaryPage()
        }

        reviewButton.setOnClickListener(){
            // hide our reviewButton and show back to summary button
            reviewButton.visibility = View.INVISIBLE
            backToSummaryButton.visibility = View.VISIBLE
            //displays screen with all questions and correct/incorrect answers
            // first we clear the the summary page text content
            summaryPage.setText("")
            // set our summary page font size smaller
            summaryPage.textSize = 18f

            // lets then declare a string to hold all of our questions and answers
            var questionsAndAnswersReview = ""
            // then we loop through all of the questions
            var summaryCounter = 0
            for(question in questionsArray){
                // using our summaryCounter we can show the answer next to the question
                // first we show our question number by plussing one to our 0 based index
                var questionNumber = summaryCounter + 1

                // get our answer by using the summaryCounter
                var answerToCurrentQuestion = answersArray[summaryCounter]
                // place our answer in string to display alongside the current question
                var correctAnswer = "Answer: $answerToCurrentQuestion"

                // display each of our questions on a new line
                // alongside the corresponsing correct answer by accessing our parallel answers array using our index counter
                questionsAndAnswersReview += "\nQ$questionNumber $question \n$correctAnswer \n"

                // then we increment the summary counter to access the next answer from our answers array
                // for our next question
                summaryCounter++
            }

            // lastly we display the review on our summary page
            summaryPage.setText(questionsAndAnswersReview)
            // and we make our text content justified to align-start for better readability
            summaryPage.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        }

        restartQuiz.setOnClickListener(){
            // hide our summary page and buttons
            summaryPage.visibility = View.INVISIBLE
            restartQuiz.visibility = View.INVISIBLE
            reviewButton.visibility = View.INVISIBLE
            backToSummaryButton.visibility = View.INVISIBLE

            // reset our question counter
            questionIndex = 0
            // reset our score counter
            scoreCounter = 0

            // call our showFlashCards function with the reset question Index
            showFlashCards(questionIndex)
        }

        // here we add a onclick event listerner continue to the next question in the quiz
        continueQuizButton.setOnClickListener {
            // hide the answer pop up message and the continue button
            answerDisplayMessage.visibility = View.INVISIBLE
            continueQuizButton.visibility = View.INVISIBLE

            // continue quiz by incrementing the question index
            // and then supplying the questionIndex back to our showFlashCards function
            // to show the next set of questions
            questionIndex++
            showFlashCards(questionIndex)
        }

        // give our true button an onclick event listener to set the user's answer to true
        // and then check the user's answer against the correct answer
        answerTrueButton.setOnClickListener(){
            // if they click true then we set our usersAnswer to true
            usersAnswer = true

            var correctAnswer = answersArray[questionIndex]

            if(usersAnswer == correctAnswer){
                // if they got the answer correct then we increase the score
                scoreCounter++
                // then we show the correct pop up
                showCorrectAnswerPopUpMessage()

            } else{
                // else we don't increase the score
                // and show them the incorrect pop up message
                showIncorrectAnswerPopUpMessage()
            }
        }

        // give our false button an onclick event listener to set the user's answer to false
        // and then check the user's answer against the correct answer
        answerFalseButton.setOnClickListener(){
            // else if they click on false then we set our usersAnswer to false
            usersAnswer = false

            var correctAnswer = answersArray[questionIndex]

            if(usersAnswer == correctAnswer){
                // if they got the answer correct then we increase the score
                scoreCounter++
                // then we show the correct pop up
                showCorrectAnswerPopUpMessage()

            } else{
                // else we don't increase the score
                // and show them the incorrect pop up message
                showIncorrectAnswerPopUpMessage()
            }
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
            // using our index of 0 to show our first question
            var indexCounter: Int = 0
            showFlashCards(indexCounter)
        }
    }
}