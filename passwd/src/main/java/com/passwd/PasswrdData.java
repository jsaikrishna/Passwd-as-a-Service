package com.passwd;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PasswrdData {

    public PasswrdData() {
    }

    public Passwrd passwrdObj = new Passwrd();

    public static PasswrdData instance = null;

    public static PasswrdData getInstance() {
        if (instance == null)
            instance = new PasswrdData();
        return instance;
    }

    public List<Passwrd> getData() {
        List<Passwrd> inputList = new ArrayList<>();
        try {
            Path path = Paths.get("/private/etc/passwd");
            List<String> lines = Files.readAllLines(path);

            inputList = lines.stream()
                    .filter(line -> line.contains(":"))
                    .map(this::getPasswrd)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return inputList;
    }

    private Passwrd getPasswrd(String input_line) {
        String[] input_line_array = input_line.split(":");
        if (input_line_array.length < 7)
            return null;

        if (input_line_array[0].isEmpty()
                || input_line_array[1].isEmpty()
                || input_line_array[2].isEmpty()
                || input_line_array[3].isEmpty()
                || input_line_array[4].isEmpty()
                || input_line_array[5].isEmpty()
                || input_line_array[6].isEmpty())
            return null;


        return input_line_array.length == 7 ? new Passwrd(input_line_array[0], Integer.parseInt(input_line_array[2]), Integer.parseInt(input_line_array[3]), input_line_array[4], input_line_array[5], input_line_array[6]) : null;
    }

    public HashSet<Passwrd> getUsers() {
        return new HashSet<>(getData());
    }


    public HashSet<Passwrd> getOptionalUsers(String name, Integer uid, Integer gid, String comment, String home, String shell) {
        List<Passwrd> inputList = getData();
        HashSet<Passwrd> setByName = getName(name, inputList);
        HashSet<Passwrd> setByUid = getUid(uid, setByName);
        HashSet<Passwrd> setByGid = getGid(gid, setByUid);
        HashSet<Passwrd> setComment = getComment(comment, setByGid);
        HashSet<Passwrd> setHome = getHome(home, setComment);
        HashSet<Passwrd> setShell = getShell(shell, setHome);
        return new HashSet<>(setShell);
    }

    private HashSet<Passwrd> getName(String name, List<Passwrd> inputList) {
        if (name != null) {
            return inputList.stream()
                    .filter(passwrd -> passwrd.getName().equals(name))
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return new HashSet<>(inputList);
    }

    private HashSet<Passwrd> getUid(Integer uid, HashSet<Passwrd> setByName) {
        if (uid != null) {
            return setByName.stream()
                    .filter(passwrd -> passwrd.getUid() == uid)
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return setByName;
    }

    private HashSet<Passwrd> getGid(Integer gid, HashSet<Passwrd> setByUid) {
        if (gid != null) {
            return setByUid.stream()
                    .filter(passwrd -> passwrd.getGid() == gid)
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return setByUid;
    }

    private HashSet<Passwrd> getComment(String comment, HashSet<Passwrd> setByGid) {
        if (comment != null) {
            return setByGid.stream()
                    .filter(passwrd -> passwrd.getComment().equals(comment))
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return setByGid;
    }

    private HashSet<Passwrd> getHome(String home, HashSet<Passwrd> setByComment) {
        if (home != null) {
            return setByComment.stream()
                    .filter(passwrd -> passwrd.getHome().equals(home))
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return setByComment;
    }

    private HashSet<Passwrd> getShell(String shell, HashSet<Passwrd> setByHome) {
        if (shell != null) {
            return setByHome.stream()
                    .filter(passwrd -> passwrd.getShell().equals(shell))
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return setByHome;
    }

    public HashSet<Passwrd> getShellQuery(String sh) {
        List<Passwrd> inputList = getData();
        HashSet<Passwrd> shellList = new HashSet<>();
        return inputList.stream()
                .filter(passwrd -> passwrd.getShell().equals(sh))
                .collect(Collectors.toCollection(HashSet::new));
    }

    public Passwrd getUidQuery(int id) {
        List<Passwrd> inputList = getData();
        Passwrd resultObject = null;
        for (Passwrd p : inputList) {
            if (p.getUid() == id) {
                resultObject = p;
                break;
            }
        }
        return resultObject;
    }
}
