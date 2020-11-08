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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import in.falconfour.sahpathi.R;
import in.falconfour.sahpathi.SignupActivity;
import in.falconfour.sahpathi.Subject;
import in.falconfour.sahpathi.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment implements TimeTableFragmentAdapter.JoiningLinkClickListener {
    private ArrayList<Subject> mSubjectList ;
    //private ArrayList<HashMap<String,Object>> mSubjectList2;
    //private HashMap<String,Object> subjectHashMap = new HashMap<>();
    //private Subject subject = new Subject();


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



    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public TimeTableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_time_table, container, false);
        timeTableFragmentRv = v.findViewById(R.id.time_table_fragment_rv);
        layoutManager = new LinearLayoutManager(v.getContext());
        adapter = new TimeTableFragmentAdapter(getContext(),this);
        timeTableFragmentRv.setLayoutManager(layoutManager);
        timeTableFragmentRv.setAdapter(adapter);
        mSubjectList = new ArrayList<>();

        adapter.setAdapterSettings(mSubjectList);

        dayOfTheWeek = getActivity().getPreferences(Context.MODE_PRIVATE).getString("DAY_OF_THE_WEEK","Mon");
        fetchDayData(dayOfTheWeek);

        monday_btn=(Button)v.findViewById(R.id.mon_button);
        tuesday_btn=(Button)v.findViewById(R.id.tue_button);
        wednesday_btn=(Button)v.findViewById(R.id.wed_button);
        thursday_btn=(Button)v.findViewById(R.id.thu_button);
        friday_btn=(Button)v.findViewById(R.id.fri_button);
        saturday_btn=(Button)v.findViewById(R.id.sat_button);
        sunday_btn=(Button)v.findViewById(R.id.sun_button);

        monday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Mon");
            }
        });
        tuesday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Mon");
            }
        });

        wednesday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        thursday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Mon");
            }
        });
        friday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Mon");
            }
        });
        saturday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Mon");
            }
        });
        sunday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDayData("Mon");
            }
        });



        return v;
    }

    private void fetchDayData(String dayOfTheWeek) {
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        /*DocumentReference documentReference = db.collection(getString(R.string.collection_user)).document(email);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if(user != null) {
                    branch = user.getBRANCH();
                    college = user.getCOLLEGE();
                    Log.d("branch","and college done");
                } else {
                    Log.d("failed for user","failed for user");
                }
            }
        });*/


        db.collection("timetable").document("ICE")
                .collection("Mon")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("doc fetching", document.getId() + " => " + document.getData());
                        Subject subject = document.toObject(Subject.class);
                        if(subject!=null) {
                            mSubjectList.add(subject);
                        }

                    }

                } else {
                    Log.d("Error", "Error getting documents: ", task.getException());
                }
            }
        });


        if(adapter!=null) {
            if(mSubjectList==null){
                Log.d("subject list","null how come");
            }
            adapter.setAdapterSettings(mSubjectList);

        } else {
            Log.d("adapter error","adapter hi bc null ho gaya!");
        }
    }

    @Override
    public void onClickIcon(int position) {
        String subjectLink = mSubjectList.get(position).getLINK();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(subjectLink));
        startActivity(browserIntent);
    }




}

