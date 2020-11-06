package in.falconfour.sahpathi.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.falconfour.sahpathi.R;
import in.falconfour.sahpathi.Subject;

public class ResourcesFragmentAdapter extends RecyclerView.Adapter<ResourcesFragmentAdapter.ResourcesFragmentViewHolder> {
    private Context mContext;
    private ArrayList<Subject> mSubjectList;
    private ResourcesFragmentAdapter.resourcesCardClickInterface resourcesCardClickInterface;
    public ResourcesFragmentAdapter(Context context){
        mContext = context;
    }
    public void setAdapterSettings(ArrayList<Subject> subjects, ResourcesFragmentAdapter.resourcesCardClickInterface resourcesCardClickInterface){
        mSubjectList = subjects;
        this.resourcesCardClickInterface = resourcesCardClickInterface;
    }
    @NonNull
    @Override
    public ResourcesFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.resources_item_card,parent,false);
        return new ResourcesFragmentViewHolder(v,resourcesCardClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourcesFragmentViewHolder holder, int position) {
        Subject currentSubject = mSubjectList.get(position);
        holder.subjectTv.setText(currentSubject.getSubjectName());
    }

    @Override
    public int getItemCount() {
        if(mSubjectList != null){
            return mSubjectList.size();
        }
        return 0;
    }

    public static class ResourcesFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView subjectTv;
        private ImageView resourcesCardIv;
        private ImageView resourcesCardBackgroundIv;
        private resourcesCardClickInterface resourcesCardClickInterface;
        public ResourcesFragmentViewHolder(@NonNull View itemView,resourcesCardClickInterface resourcesCardClickInterface) {
            super(itemView);
            subjectTv = itemView.findViewById(R.id.resources_item_card_subject_tv);
            resourcesCardIv = itemView.findViewById(R.id.resources_item_card_iv);
            resourcesCardBackgroundIv = itemView.findViewById(R.id.resource_card_background_iv);
            this.resourcesCardClickInterface = resourcesCardClickInterface;
        }

        @Override
        public void onClick(View v) {
            resourcesCardClickInterface.onClickResourcesSubjectCard(getAdapterPosition());
        }
    }

    public interface resourcesCardClickInterface{
        void onClickResourcesSubjectCard(int position);
    }
}
