package in.falconfour.sahpathi.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import in.falconfour.sahpathi.R;
import in.falconfour.sahpathi.Subject;

public class TimeTableFragmentAdapter extends RecyclerView.Adapter<TimeTableFragmentAdapter.TimeTableCardViewHolder> {

    public ArrayList<Subject> mSubjectList ;
    public JoiningLinkClickListener joiningLinkClickListener;
    private Context mContext;

    public TimeTableFragmentAdapter(Context context, JoiningLinkClickListener joiningLinkClickListener){
        mContext = context;
        mSubjectList = new ArrayList<>();
        this.joiningLinkClickListener = joiningLinkClickListener;
    }


    public void changeData(ArrayList<Subject> subjects){
        if(subjects != null) {
            mSubjectList = subjects;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public TimeTableCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.time_table_item_card,parent,false);
        TimeTableCardViewHolder holder = new TimeTableCardViewHolder(v,joiningLinkClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableCardViewHolder holder, int position) {
        Subject subject = mSubjectList.get(position);
        holder.startTimeTv.setText(subject.getSTART_TIME());
        holder.endTimeTv.setText(subject.getEND_TIME());
        holder.subjectTv.setText(subject.getSUBJECT());
    }

    @Override
    public int getItemCount() {
        if(mSubjectList != null){
            return mSubjectList.size();
        }
        return 0;
    }

    public static class TimeTableCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView subjectTv;
        private TextView startTimeTv;
        private TextView endTimeTv;
        private TextView teacherTv;
        private ImageButton linkJoinButton;
        private JoiningLinkClickListener joiningLinkClickListener;

        public TimeTableCardViewHolder(@NonNull View itemView, JoiningLinkClickListener joiningLinkClickListener) {
            super(itemView);
            subjectTv =  itemView.findViewById(R.id.subject_tv);
            startTimeTv = itemView.findViewById(R.id.start_time_tv);
            endTimeTv = itemView.findViewById(R.id.end_time_tv);
            teacherTv = itemView.findViewById(R.id.teacher_tv);
            linkJoinButton = itemView.findViewById(R.id.link_button);
            this.joiningLinkClickListener = joiningLinkClickListener;
            linkJoinButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            joiningLinkClickListener.onClickIcon(getAdapterPosition());
        }
    }

    public interface JoiningLinkClickListener{
        void onClickIcon(int position);
    }
}
