package com.example.flash_cards;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;



public class FireBaseHelperSubjects {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceSubjects;
    private ArrayList<Subject> subjects=new ArrayList<>();
   FirebaseAuth firebaseAuth;
   FirebaseUser user;


    public interface DataStatus//8:05
    {
        void DataIsLoaded(ArrayList<Subject>subjects,ArrayList<String>keys);
        void DataIsUpdated();
        void DataIsDeleted();
        void DataIsInserted();
    }

    public FireBaseHelperSubjects() {
        mDatabase=FirebaseDatabase.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();

        String userid=user.getUid();
        mReferenceSubjects=mDatabase.getReference("users").child(userid).child("subjects");

    }

    public void ReadSubjects(final DataStatus dataStatus)
    {

        mReferenceSubjects.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                subjects.clear();
                ArrayList<String>keys=new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren())
                {

                    keys.add(keyNode.getKey());
                    Subject _subject=keyNode.getValue(Subject.class);

                    subjects.add(_subject);

                }
                dataStatus.DataIsLoaded(subjects,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
