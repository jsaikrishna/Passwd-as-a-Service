package com.passwd;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.net.*;


public class GroupData {

    public GroupData(){};

    public Group groupObj = new Group();

    public static GroupData instance = null;
    public static GroupData getInstance(){
        if(instance == null)
            instance = new GroupData();
        return instance;
    }

    public List<Group> getData(){
        List<Group> inputList = new ArrayList<>();
        try {

            inputList = new ArrayList<>();
            Path path = Paths.get("/private/etc/group");
            List<String> lines = Files.readAllLines(path);

            for (String input_line : lines) {
                if (input_line.contains(":")) {
                    String[] input_line_array = input_line.split(":");
                    if (input_line_array.length == 4) {
                        List<String> memberGroups = new ArrayList<String>();
                        String[] members = input_line_array[3].split(",");
                        for (String mem : members)
                            memberGroups.add(mem);
                        Group gp = new Group(input_line_array[0], Integer.parseInt(input_line_array[2]), memberGroups);
                        inputList.add(gp);
                    } else {
                        Group gp = new Group(input_line_array[0], Integer.parseInt(input_line_array[2]), new ArrayList<>());
                        inputList.add(gp);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return inputList;
    }


    public HashSet<Group> getUserGroups(int id){
        List<Group> inputList1 = getData();
        HashSet<Group> userGroupsList = new HashSet<>();
        for(Group g: inputList1){
            if(g.getGid() == id){
                if(!userGroupsList.contains(g))
                    userGroupsList.add(g);
            }
        }
        return userGroupsList;
    }

    public HashSet<Group> getGroups(){
        List<Group> inputList2 = getData();
        HashSet<Group> groupsList = new HashSet<>();

        for(Group g: inputList2){
            if(!groupsList.contains(g))
                groupsList.add(g);
        }
        return groupsList;
    }


    public HashSet<Group> getOptionalGroup(String name, Integer gid, List<String> members){
        List<Group> inputList3 = getData();


        HashSet<Group> nameSet = new HashSet<>();
        if(name != null){
            for(Group g: inputList3){
                if(g.getName().equals(name)){
                    if(!nameSet.contains(g))
                        nameSet.add(g);
                }
            }
        }
        else{
            nameSet = new HashSet<>(inputList3);
        }

        HashSet<Group> gidSet = new HashSet<>();
        if((Integer)gid != null){
            for(Group g: nameSet){
                if(g.getGid() == gid){
                    if(!gidSet.contains(g))
                        gidSet.add(g);
                }
            }
        }
        else{
            gidSet = nameSet;
        }

        HashSet<Group> membersSet = new HashSet<>();
        boolean find = false;
        if(members != null) {
            for (String mem : members) {
                if (mem != null) {
                    for (Group g : gidSet) {
                        if (g.getMembers().contains(mem)) {
                            find = true;
                            if(!membersSet.contains(g)){
                                membersSet.add(g);
                            }
                        }
                    }
                }
            }
        }

        Iterator<Group> itr = membersSet.iterator();
        while (itr.hasNext()){
            Group gm = itr.next();
            for(String mem: members){
                if(!gm.toString().contains(mem)){
                    itr.remove();
                }
            }
        }

        if(find == false){
            membersSet = gidSet;
        }

        HashSet<Group> optionalGroupSet = new HashSet<>();
        for(Group g: membersSet){
            if(!optionalGroupSet.contains(g))
                optionalGroupSet.add(g);
        }
        return optionalGroupSet;
    }

    public Group getGroupGid(int id){
        List<Group> inputList1 = getData();
        Group result = null;
        for(Group g: inputList1){
            if(g.getGid() == id) {
                result = g;
            }
        }
        return result;
    }
}
