package in.falconfour.sahpathi.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import in.falconfour.sahpathi.R;
import in.falconfour.sahpathi.Subject;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTableFragment extends Fragment implements TimeTableFragmentAdapter.JoiningLinkClickListener {
    private ArrayList<Subject> mSubjectList;
    private RecyclerView timeTableFragmentRv;
    private LinearLayoutManager layoutManager;
    private TimeTableFragmentAdapter adapter;

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
        adapter.setAdapterSettings(mSubjectList);
        return v;
    }

    @Override
    public void onClickIcon(int position) {
        String subjectLink = mSubjectList.get(position).getMeetLink();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(subjectLink));
        startActivity(browserIntent);
    }
}
