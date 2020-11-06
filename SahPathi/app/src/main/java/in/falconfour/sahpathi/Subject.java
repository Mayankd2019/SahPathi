package in.falconfour.sahpathi;

public class Subject {
    private String subjectName;
    private String meetLink;
    private String startTime;
    private String endTime;
    private String teacherName;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Subject(String subjectName, String meetLink) {
        this.subjectName = subjectName;
        this.meetLink = meetLink;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getMeetLink() {
        return meetLink;
    }

    public void setMeetLink(String meetLink) {
        this.meetLink = meetLink;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
