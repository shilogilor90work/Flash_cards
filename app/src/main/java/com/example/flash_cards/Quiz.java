package com.example.flash_cards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Quiz extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference root_database;
    FirebaseUser user;

    Map allQuestionsAndAnswers;
    ArrayList<String> QandA;

    private int score = 0;
    private int questionIndex = 0;
    private Random rand;
    TextView scoreView;
    TextView questionView;
    String question;
    String correctAnswer;
    Button choice1;
    Button choice2;
    Button choice3;
    Button quit;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle("Quiz");

        firebaseAuth = FirebaseAuth.getInstance();
        root_database = FirebaseDatabase.getInstance().getReference().child("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        rand=new Random();
        allQuestionsAndAnswers = new HashMap<Integer,ArrayList<String>>();
        questionView = (TextView) findViewById(R.id.question_id);
        scoreView = (TextView) findViewById(R.id.score);
        choice1 = (Button)findViewById(R.id.choice1);
        choice2 = (Button)findViewById(R.id.choice2);
        choice3 = (Button)findViewById(R.id.choice3);
        quit = (Button)findViewById(R.id.quit);
        subject=getIntent().getStringExtra("subject");

        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(subject).child("definitions").addValueEventListener(new ValueEventListener(){
               @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allQuestionsAndAnswers.clear();
                int i=0;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    QandA = new ArrayList<>();
                    QandA.add(snapshot.getKey());
                    QandA.add(snapshot.getValue().toString());
                    allQuestionsAndAnswers.put(i,QandA);
                    i++;
                }
                if(i<3){
                    goToNotEnoughDefinitions();
                }
                List<Map.Entry<Integer, ArrayList<String>>> QaA2 = new ArrayList<Map.Entry<Integer, ArrayList<String>>>(allQuestionsAndAnswers.entrySet());
                updateQuestion(questionIndex);
                questionIndex++;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correctAnswer.equals(choice1.getText())){
                    score++;
                    updateScore(score);
                }
                updateQuestion(questionIndex);
                questionIndex++;
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correctAnswer.equals(choice2.getText())){
                    score++;
                    updateScore(score);
                }
                updateQuestion(questionIndex);
                questionIndex++;
            }
        });

        choice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correctAnswer.equals(choice3.getText())){
                    score++;
                    updateScore(score);
                }
                updateQuestion(questionIndex);
                questionIndex++;
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFinish();
            }
        });
    }

    private void updateQuestion(int i){
        if (i<allQuestionsAndAnswers.size()) {
            List<Map.Entry<Integer, ArrayList<String>>> QaA = new ArrayList<Map.Entry<Integer, ArrayList<String>>>(allQuestionsAndAnswers.entrySet());
            question = QaA.get(i).getValue().get(0) + " ?";
            correctAnswer = QaA.get(i).getValue().get(1);
            questionView.setText(question);
            List<Map.Entry<Integer, ArrayList<String>>> tmpQaA=QaA;
            shuffleAnswersWithCorrectAnswer(tmpQaA, correctAnswer);
        }
        else{
            goToFinish();
        }
    }

    private void shuffleAnswersWithCorrectAnswer(List<Map.Entry<Integer, ArrayList<String>>> QaA, String ca){
        Collections.shuffle(QaA);
        choice1.setText(QaA.get(0).getValue().get(1));
        choice2.setText(QaA.get(1).getValue().get(1));
        choice3.setText(QaA.get(2).getValue().get(1));

        int correctChoice = rand.nextInt(3);
        switch (correctChoice+1){
            case 1:
                if(choice2.getText().toString().equals(ca)){
                    choice2.setText(choice1.getText());
                }
                else if(choice3.getText().toString().equals(ca)){
                    choice3.setText(choice1.getText());
                }
                choice1.setText(ca);
                break;

            case 2:
                if(choice1.getText().toString().equals(ca)){
                    choice1.setText(choice2.getText());
                }
                else if(choice3.getText().toString().equals(ca)){
                    choice3.setText(choice2.getText());
                }
                choice2.setText(ca);
                break;

            case 3:
                if(choice1.getText().toString().equals(ca)){
                    choice1.setText(choice3.getText());
                }
                else if(choice2.getText().toString().equals(ca)){
                    choice2.setText(choice3.getText());
                }
                choice3.setText(ca);
                break;
        }
    }

    private void updateScore(int s){
        scoreView.setText(""+s);
    }

    private void goToFinish() {
        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(subject).child("score").setValue(score + "/" + allQuestionsAndAnswers.size());
        root_database.child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("subjects").child(subject).child("teacher").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            root_database.child(dataSnapshot.getValue().toString().substring(0, dataSnapshot.getValue().toString().indexOf("@"))).child("subjects").child(subject).child("students").child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).setValue(score + "/" + allQuestionsAndAnswers.size());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
        questionIndex=0;
        Intent i = new Intent(getApplicationContext(),FinalScore.class);
        i.putExtra("int_final_score",score);
        i.putExtra("int_num_questions",allQuestionsAndAnswers.size());
        startActivity(i);
    }

    private void goToNotEnoughDefinitions(){
        startActivity(new Intent(getApplicationContext(),NotEnoughDefinitions.class));
    }
}