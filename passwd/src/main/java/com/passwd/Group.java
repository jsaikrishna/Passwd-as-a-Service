package com.passwd;
import java.util.*;

public class Group {

    private String name;
    private int gid;
    private List<String> members;

    public Group(){}

    public Group(String name, int gid, List<String> members){
        this.setName(name);
        this.setGid(gid);
        this.setMembers(members);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    @Override
    public String toString(){
        String open = "{";
        String namePrint = "name";
        String gidPrint = "gid";
        String membersPrint = "members";
        String colonPrint = ":";
        String commaPrint = ",";
        String close = "}";

        return open + namePrint + colonPrint + name + commaPrint +gidPrint + colonPrint + gid + commaPrint + membersPrint + colonPrint + members + close;
    }
}
