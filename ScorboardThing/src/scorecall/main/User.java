package main;

public class User {

    public String userID;
    public double score;
    public double wins;
    public double kills;
    public double timePlayed;

    public User(String userID, double score, double wins, double kills, double timePlayed) {
        this.userID = userID;
        this.score = score;
        this.wins = wins;
        this.kills = kills;
        this.timePlayed = timePlayed;
    }

    public User(String username) {
        this.userID = username;
    }

}

