package in.falconfour.sahpathi;

import com.google.firebase.Timestamp;

public class User {
    private String BRANCH;
    private String COLLEGE;
    private String token;
    private Timestamp createdAt;

    public User(){

    }

    public String getBRANCH() {
        return BRANCH;
    }

    public void setBRANCH(String BRANCH) {
        this.BRANCH = BRANCH;
    }

    public String getCOLLEGE() {
        return COLLEGE;
    }

    public void setCOLLEGE(String COLLEGE) {
        this.COLLEGE = COLLEGE;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
