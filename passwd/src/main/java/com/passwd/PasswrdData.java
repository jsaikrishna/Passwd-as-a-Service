package com.passwd;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.net.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONObject;

public class PasswrdData {

    public PasswrdData(){}

    public Passwrd passwrdObj = new Passwrd();

    public static PasswrdData instance = null;

    public static PasswrdData getInstance(){
        if(instance == null)
            instance = new PasswrdData();
        return instance;
    }

    public List<Passwrd> getData() {
        List<Passwrd> inputList = new ArrayList<>();
        try {
            inputList = new ArrayList<>();
            Path path = Paths.get("/private/etc/passwd");
            List<String> lines = Files.readAllLines(path);

            for(String input_line : lines) {

                String[] input_line_array = input_line.split(":");

                if (input_line_array.length == 7) {
                    Passwrd p = new Passwrd(input_line_array[0], Integer.parseInt(input_line_array[2]), Integer.parseInt(input_line_array[3]), input_line_array[4], input_line_array[5], input_line_array[6]);
                    inputList.add(p);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return inputList;
    }

    public HashSet<Passwrd> getUsers(){
        List<Passwrd> inputList = getData();
        HashSet<Passwrd> usersList = new HashSet<>();
        for(Passwrd p : inputList) {
            usersList.add(p);
        }
        return usersList;
    }


    public  HashSet<Passwrd> getOptionalUsers(String name, Integer uid, Integer gid, String comment, String home, String shell){
        List<Passwrd> inputList = getData();


        HashSet<Passwrd> setByName = new HashSet<>();
        if(name != null) {
            for (Passwrd p : inputList) {
                if (p.getName().equals(name)) {
                    if (!setByName.contains(p)) {
                        setByName.add(p);
                    }

                }
            }
        }
        else
            setByName = new HashSet<>(inputList);

        HashSet<Passwrd> setByUid = new HashSet<>();
        if(uid != null) {
            for (Passwrd p : setByName) {
                if (p.getUid() == uid) {
                    if (!setByUid.contains(p)) {
                        setByUid.add(p);
                    }

                }
            }
        }
        else
            setByUid = setByName;

        HashSet<Passwrd> setByGid = new HashSet<>();
        if(gid != null) {
            for (Passwrd p : setByUid) {
                if (p.getGid() == gid) {
                    if (!setByGid.contains(p)) {
                        setByGid.add(p);
                    }

                }
            }
        }
        else
            setByGid = setByUid;



        HashSet<Passwrd> setByComment = new HashSet<>();
        if(comment != null) {
            for (Passwrd p : setByGid) {
                if (p.getComment().equals(comment)) {
                    if (!setByComment.contains(p)) {
                        setByComment.add(p);
                    }

                }
            }
        }

        else{
            setByComment = setByGid;
        }

        HashSet<Passwrd> setByHome = new HashSet<>();
        if(home != null){
            for(Passwrd p : setByComment) {
                if (p.getHome().equals(home)){
                    if(!setByHome.contains(p)){
                        setByHome.add(p);
                    }

                }

            }
        }
        else{
            setByHome = setByComment;
        }
        HashSet<Passwrd> setByShell = new HashSet<>();
        if(shell != null){
            for(Passwrd p : setByHome){
                if(p.getShell().equals(shell)){
                    if(!setByShell.contains(p)){
                        setByShell.add(p);
                    }

                }
            }
        }
        else{
            setByShell = setByHome;
        }

        HashSet<Passwrd> optionalUsersSet = new HashSet<>();
        for(Passwrd p : setByShell){
            if(!optionalUsersSet.contains(p))
                optionalUsersSet.add(p);
        }
        return optionalUsersSet;
    }

    public  HashSet<Passwrd> getShellQuery(String sh){
        List<Passwrd> inputList = getData();
        HashSet<Passwrd> shellList = new HashSet<>();
        for(Passwrd p : inputList){
            if(p.getShell().equals(sh)){
                if(!shellList.contains(p))
                    shellList.add(p);
            }
        }
        return shellList;
    }

    public  Passwrd getUidQuery(int id){
        List<Passwrd> inputList = getData();
        Passwrd resultObject = null;
        for(Passwrd p : inputList){
            if(p.getUid() == id) {
                resultObject = p;
                break;
            }
        }
        return resultObject;
    }
}
