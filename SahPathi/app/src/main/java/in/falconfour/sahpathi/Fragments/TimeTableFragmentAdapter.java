package in.falconfour.sahpathi.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.falconfour.sahpathi.R;
import in.falconfour.sahpathi.Subject;

public class TimeTableFragmentAdapter extends RecyclerView.Adapter<TimeTableFragmentAdapter.TimeTableCardViewHolder> {

    private ArrayList<Subject> mSubjectList;
    public JoiningLinkClickListener joiningLinkClickListener;
    private Context mContext;

    public TimeTableFragmentAdapter(Context context, JoiningLinkClickListener joiningLinkClickListener){
        mContext = context;
        this.joiningLinkClickListener = joiningLinkClickListener;
    }

    public void setAdapterSettings(ArrayList<Subject> Subjects) {
        mSubjectList = Subjects;
        notifyDataSetChanged();
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
        holder.startTimeTv.setText(subject.getStartTime());
        holder.endTimeTv.setText(subject.getEndTime());
        holder.subjectTv.setText(subject.getSubjectName());
        holder.teacherTv.setText(subject.getTeacherName());
    }

    @Override
    public int getItemCount() {
        if(mSubjectList.size() != 0){
            return mSubjectList.size();
        }
        return 0;
    }

    public static class TimeTableCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView subjectTv;
        private TextView startTimeTv;
        private TextView endTimeTv;
        private TextView teacherTv;
        private Button linkJoinButton;
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
