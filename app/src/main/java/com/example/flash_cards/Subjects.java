package com.example.flash_cards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class Subjects extends AppCompatActivity {
    //ListView subject_list;
    //ArrayList<String>subjects;
    //ArrayAdapter<String> adapter;
    //SubjectAdapter ad;
    Set<String> _subjects;
    ArrayList<String> Subjectlist;

    //ArrayList<Flash_Card>_flash_cards;//each s

    //EditText search_words;
    Button btn_search;

    Button definitions;
    Button friends;
    //FirebaseAuth firebaseAuth;


    ////////////////
   // DatabaseReference root_database;
   // DatabaseReference ref_database;

   // FirebaseUser user;
    ///////////////////////




    //////////changes


    private RecyclerView mRecyclerview;




    ////changes....



//    public interface Datastatus
//    {
//            void DataIsLoaded();
//            void DataIsUpdated();
//            void DataIsDeleted();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        setTitle("Subjects");

        mRecyclerview=(RecyclerView)findViewById(R.id.recyclerview_subjects);
        new FireBaseHelperSubjects().ReadSubjects(new FireBaseHelperSubjects.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Subject> subjects, ArrayList<String> keys) {
                new RecyclerView_Config().setConfig(mRecyclerview,Subjects.this,subjects,keys);
            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }

            @Override
            public void DataIsInserted() {

            }
        });
        //subject_list=(ListView)findViewById(R.id.subject_list);
        //subjects = new ArrayList<>();
        //adapter = new ArrayAdapter<String>(Subjects.this, android.R.layout.simple_list_item_1, subjects);
        //subject_list.setAdapter(adapter);
       // search_words = (EditText) findViewById(R.id.subjects_search);
       // btn_search = (Button) findViewById(R.id.subjects_btn);
       // root_database = FirebaseDatabase.getInstance().getReference();
      //  user = FirebaseAuth.getInstance().getCurrentUser();


        //ref_database.addValueEventListener(change)
                       //fetching all user subjects from database
        /////
        //root_database.
        /////


        //ref_database=FirebaseDatabase.getInstance().getReference("/users");


                //if(DataSnapshot data)

                //click_button();

//        definitions = findViewById(R.id.Definitions2);
//
//        definitions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Subjects.this,Definitions.class);
//                startActivity(i);
//            }
//        });
//        friends = findViewById(R.id.go_to_friends);
//
//        friends.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Subjects.this,Friends.class);
//                startActivity(i);
//            }
//        });


    }


//    public void click_button() {
//        btn_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               if( !check_for_duplicates(subjects,search_words.getText().toString()))
//               {
//
//                   subjects.add(search_words.getText().toString());
//
//                   adapter.notifyDataSetChanged();
//               }
//               else
//               {
//                   Toast.makeText(Subjects.this, "please re check for possible duplicate", Toast.LENGTH_SHORT).show();
//               }
//            }
//        });
//    }

    /**
     * used for checking possible duplicates for subjects
     * set has one instance of each element. we insert from list to set
     * if size is different meaning it has duplicate
     * algorithm 1 and Algorithm 1 is considered a duplicate
     * @param __subjects
     * @return true in case of non duplicate subjects
     */
    public boolean check_for_duplicates(ArrayList<String>__subjects,String str)
    {
        Set<String>s= new HashSet<String>();
        //_subjects.add(str);
        for(int i=0;i<__subjects.size();i++)
        {
         String temp=__subjects.get(i).toLowerCase();
         s.add(temp);
        }
        int temp=s.size();
        s.add(str.toLowerCase());
        if(s.size()==temp)
            return true;//duplicates exist
        return false;//non duplicate list of subjects

    }
//    public void check_for_sub_enteries(DataSnapshot dataSnapshot)//check if any subjects exist for user.
//            //if there isnt any subject we skip the fetch of subjects from db. else we fetch data from db and store it locally
//    {
//        if(dataSnapshot.hasChildren())
//        {
//            for(DataSnapshot dsp:dataSnapshot.getChildren())//iterate over users subjects and fetch one by one to store them locally
//            {
//                subjects.add(String.valueOf(dsp.getValue()));
//            }
//            subject_list.setAdapter(adapter);
//        }
//        else// no need to fetch data from db because no subjects yet entered
//        {
//
//        }
//    }




}