package in.falconfour.sahpathi.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import in.falconfour.sahpathi.MainActivity;
import in.falconfour.sahpathi.R;
import in.falconfour.sahpathi.SignupActivity;
import in.falconfour.sahpathi.Subject;
import in.falconfour.sahpathi.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment implements TimeTableFragmentAdapter.JoiningLinkClickListener {
    private ArrayList<Subject> mSubjectList ;


    private RecyclerView timeTableFragmentRv;
    private LinearLayoutManager layoutManager;
    private TimeTableFragmentAdapter adapter;
    private static String dayOfTheWeek;
    private static String email;
    static String branch;
    static String college;

    private Button monday_btn;
    private Button tuesday_btn;
    private Button wednesday_btn;
    private Button thursday_btn;
    private Button friday_btn;
    private Button saturday_btn;
    private Button sunday_btn;
    private TextView dayDisplayTv;



    private FirebaseFirestore db ;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    public TimeTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_time_table, container, false);
        initializingViews(v);



        setOnClickListeners();

        return v;
    }


    private void initializingViews(View v) {
        timeTableFragmentRv = v.findViewById(R.id.time_table_fragment_rv);
        layoutManager = new LinearLayoutManager(v.getContext());
        adapter = new TimeTableFragmentAdapter(getContext(),this);
        timeTableFragmentRv.setLayoutManager(layoutManager);
        timeTableFragmentRv.setAdapter(adapter);
        mSubjectList = new ArrayList<>();

        monday_btn= v.findViewById(R.id.mon_button);
        tuesday_btn= v.findViewById(R.id.tue_button);
        wednesday_btn= v.findViewById(R.id.wed_button);
        thursday_btn= v.findViewById(R.id.thu_button);
        friday_btn= v.findViewById(R.id.fri_button);
        saturday_btn= v.findViewById(R.id.sat_button);
        sunday_btn= v.findViewById(R.id.sun_button);
        dayDisplayTv = v.findViewById(R.id.day_display_tv);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if(mUser != null) {
            email = mUser.getEmail();
        }
        db.collection("users").document(email).get()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        if(user != null) {
                            college = user.getCOLLEGE();
                            branch = user.getBRANCH();
                            Toast.makeText(getContext(),"branch " + branch,Toast.LENGTH_SHORT).show();
                            fetchDayData("Tue");
                        }
                    }
                });
    }

    private void setOnClickListeners() {
        monday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Mon");
            }
        });
        tuesday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Tue");
            }
        });

        wednesday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Wed");
            }
        });
        thursday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Thu");
            }
        });
        friday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Fri");
            }
        });
        saturday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Sat");
            }
        });
        sunday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Sun");
            }
        });
    }

    private void fetchDayData(String dayOfTheWeek) {
        Log.d("subject doc","we reached here");
        dayDisplayTv.setText(dayOfTheWeek);
        mSubjectList.clear();
        Toast.makeText(getContext(),"Subject added"+mSubjectList.size(),Toast.LENGTH_SHORT).show();
            db.collection("timetable").document(branch)
                    .collection(dayOfTheWeek)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("subject doc","we reached here");
                                    Subject newSubject = document.toObject(Subject.class);
                                    mSubjectList.add(newSubject);
                                    Toast.makeText(getContext(),"Subject added"+mSubjectList.size(),Toast.LENGTH_SHORT).show();
                                    //adapter.addSubject(newSubject);
                                }
                                adapter.changeData(mSubjectList);
                            } else {
                                Toast.makeText(getContext(),"getting timetable not successful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }

    @Override
    public void onClickIcon(int position) {
        String subjectLink = mSubjectList.get(position).getLINK();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(subjectLink));
        startActivity(browserIntent);
    }




}

