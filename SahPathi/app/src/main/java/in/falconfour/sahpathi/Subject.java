package in.falconfour.sahpathi;

public class Subject {
    private String SUBJECT;
    private String LINK;
    private String START_TIME;
    private String END_TIME;
    private String POSITION;

    //private String teacherName;

    /*public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }*/

    public Subject() {
    }

    public String getPOSITION() {
        return POSITION;
    }

    public void setPOSITION(String POSITION) {
        this.POSITION = POSITION;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public String getSTART_TIME() {
        return START_TIME;
    }

    public String getEND_TIME() {
        return END_TIME;
    }

    public String getLINK() {
        return LINK;
    }

    public void setLINK(String LINK) {
        this.LINK = LINK;
    }

    public void setSTART_TIME(String START_TIME) {
        this.START_TIME = START_TIME;
    }

    public void setEND_TIME(String END_TIME) {
        this.END_TIME = END_TIME;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }
}
