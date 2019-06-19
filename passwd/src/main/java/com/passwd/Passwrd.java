package com.passwd;

public class Passwrd {

    private String name;
    private int uid;
    private int gid;
    private String comment;
    private String home;
    private String shell;

    public Passwrd() {
    }

    public Passwrd(String name, int uid, int gid, String comment, String home, String shell) {
        this.setName(name);
        this.setUid(uid);
        this.setGid(gid);
        this.setComment(comment);
        this.setHome(home);
        this.setShell(shell);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getShell() {
        return shell;
    }

    public void setShell(String shell) {
        this.shell = shell;
    }

    @Override
    public String toString() {
        String open = "{";
        String namePrint = "name";
        String uidPrint = "uid";
        String gidPrint = "gid";
        String commentPrint = "comment";
        String homePrint = "home";
        String shellPrint = "shell";
        String colonPrint = ":";
        String commaPrint = " , ";
        String close = "}";

        return open + namePrint + colonPrint + name + commaPrint + uidPrint + colonPrint + uid + commaPrint + gidPrint + colonPrint + gid + commaPrint + commentPrint + colonPrint + comment + commaPrint + homePrint + colonPrint + home + commaPrint + shellPrint + colonPrint + shellPrint + close;
    }
}
