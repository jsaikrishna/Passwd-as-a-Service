package com.passwd;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GroupData {

    public GroupData() {
    }


    public static GroupData instance = null;

    public static GroupData getInstance() {
        if (instance == null)
            instance = new GroupData();
        return instance;
    }

    public List<Group> getData() {
        List<Group> inputLines = new ArrayList<>();
        try {
            Path path = Paths.get("/private/etc/group");
            List<String> lines = Files.readAllLines(path);

            return lines.stream()
                    .filter(line -> line.contains(":"))
                    .map(this::createGroup)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.out.println(e);
            return null;

        }
    }

    private Group createGroup(String line) {


        String[] input_line_array = line.split(":");

        if (input_line_array[0].isEmpty()
                || input_line_array[1].isEmpty()
                || input_line_array[2].isEmpty()
                )
            return null;



        List<String> memberGroups = input_line_array.length == 4  ? getMembers(input_line_array[3]) : new ArrayList<>();
        return new Group(input_line_array[0], Integer.parseInt(input_line_array[2]), memberGroups);
    }

    private List<String> getMembers(String s) {
        return Stream.of(s.split(",")).collect(Collectors.toList());
    }


    public HashSet<Group> getUserGroups(int id) {
        List<Group> groups = getData();
        return groups.stream()
                .filter(group -> group.getGid() == id)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public HashSet<Group> getGroups() {
        return new HashSet<>(getData());

    }


    public HashSet<Group> getOptionalGroup(String name, Integer gid, List<String> members) {
        List<Group> inputList3 = getData();
        HashSet<Group> nameSet = getName(name, inputList3);
        HashSet<Group> gidSet = getGid(gid, nameSet);
        return getMembers(members, gidSet);
    }

    private HashSet<Group> getName(String name, List<Group> inputList) {
        if (name != null) {
            return inputList.stream()
                    .filter(group -> group.getName().equals(name))
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return new HashSet<>(inputList);
    }

    private HashSet<Group> getGid(Integer gid, HashSet<Group> nameSet) {
        if (gid != null) {
            return nameSet.stream()
                    .filter(group -> group.getGid() == gid)
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return nameSet;
    }

    private HashSet<Group> getMembers(List<String> members, HashSet<Group> gidSet) {
        if (members == null) return gidSet;
        List<String> filteredMembers = members.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        HashSet<Group> membersSet = gidSet.stream()
                .filter(group -> filteredMembers.stream().anyMatch(member -> group.getMembers().contains(member)))
                .collect(Collectors.toCollection(HashSet::new));

        if (membersSet.size() == 0) return gidSet;


        Iterator<Group> itr = membersSet.iterator();
        while (itr.hasNext()) {
            Group gm = itr.next();
            for (String mem : members) {
                if (!gm.toString().contains(mem)) {
                    itr.remove();
                }
            }
        }
        return membersSet;
    }

    public Group getGroupGid(int id) {
        List<Group> inputList1 = getData();
        Group resultObject = null;
        for (Group g : inputList1) {
            if (g.getGid() == id) {
                resultObject = g;
                break;
            }
        }
        return resultObject;
    }
}
