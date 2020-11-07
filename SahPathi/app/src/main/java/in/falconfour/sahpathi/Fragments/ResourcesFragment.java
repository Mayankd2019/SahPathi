package in.falconfour.sahpathi.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

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
    private View view;
    public ResourcesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_resources, container, false);
        return view;
    }

    @Override
    public void onClickResourcesSubjectCard(int position) {
        NavController navController = Navigation.findNavController(view);
        Bundle bundle = new Bundle();
        bundle.putString(SubjectFragment.SUBJECT_NAME,mSubjectList.get(position).toString());
        navController.navigate(R.id.action_resources_to_subjectFragment,bundle);
    }
}
