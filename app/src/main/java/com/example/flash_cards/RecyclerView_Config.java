package com.example.flash_cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class RecyclerView_Config {
    private Context mcontext;
    private SubjectAdapter mSubjectsadapter;

    public void setConfig(RecyclerView recyclerView,Context context,ArrayList<Subject>subjects,ArrayList<String>keys)
    {
        mcontext=context;
        mSubjectsadapter=new SubjectAdapter(subjects,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mSubjectsadapter);
    }


    class SubjectItemView extends RecyclerView.ViewHolder//inflate layout and populate view objects
    {
        private TextView mSubjectName;


        private String key;//hold the key of the subject record

        public SubjectItemView(ViewGroup parent) {
            super(LayoutInflater.from(mcontext).inflate(R.layout.subject_list_item, parent, false));
            mSubjectName = (TextView) itemView.findViewById(R.id.subjectname);
        }

        public void bind(Subject subject, String key) {
            mSubjectName.setText(subject.getMath());
            this.key = key;

        }
    }

        class SubjectAdapter extends RecyclerView.Adapter<SubjectItemView>//responsible for creating SubjectItemView
        {
            private ArrayList<Subject> mSubjectList;
            private ArrayList<String>mKeys;

            public SubjectAdapter(ArrayList<Subject> mSubjectList, ArrayList<String> mKeys) {
                this.mSubjectList = mSubjectList;
                this.mKeys = mKeys;
            }

            @NonNull
            @Override
            public SubjectItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new SubjectItemView(parent);
            }

            @Override
            public void onBindViewHolder(@NonNull SubjectItemView holder, int position) {
                holder.bind(mSubjectList.get(position),mKeys.get(position));

            }

            @Override
            public int getItemCount() {
                return mSubjectList.size();
            }
        }


    }

