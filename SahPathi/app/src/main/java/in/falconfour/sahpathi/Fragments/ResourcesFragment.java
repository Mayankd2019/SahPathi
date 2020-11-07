package in.falconfour.sahpathi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.falconfour.sahpathi.R;
import in.falconfour.sahpathi.Subject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourcesFragment extends Fragment implements ResourcesFragmentAdapter.resourcesCardClickInterface{
    private ArrayList<Subject> mSubjectList;
    public ResourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resources, container, false);
    }

    @Override
    public void onClickResourcesSubjectCard(int position) {

    }
}
