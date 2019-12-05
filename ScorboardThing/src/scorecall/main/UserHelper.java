package main;

public class UserHelper implements Comparable<UserHelper> {

    public String userID;
    public double value;

    public UserHelper(String userID, double value) {
        this.userID = userID;
        this.value = value;

    }


    @Override
    public int compareTo(UserHelper o) {
        return (int) (o.value -this.value);
    }
}
