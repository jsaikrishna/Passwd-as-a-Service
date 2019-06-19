package com.passwd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.io.*;
import java.net.*;
import com.google.*;

@RestController
public class MainController {

    PasswrdData passwrdData = PasswrdData.getInstance();

    @GetMapping("/users")
    public HashSet<Passwrd> findUsers(){
        return passwrdData.getUsers();
    }

    @RequestMapping(value = "/users/query", method = RequestMethod.GET)
    public HashSet<Passwrd> optionalUsers(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "uid", required = false) Integer uid,
                                     @RequestParam(value = "gid", required = false) Integer gid,
                                     @RequestParam(value = "comment", required = false) String comment,
                                     @RequestParam(value = "home", required = false) String home,
                                     @RequestParam(value = "shell", required = false) String shell){

        HashSet<Passwrd> optionalUsersSet = passwrdData.getOptionalUsers(name, uid, gid, comment, home, shell);
        return optionalUsersSet;
    }

    @RequestMapping(value = "/users/query", method = RequestMethod.GET, params = {"shell"})
    public HashSet<Passwrd> shellQuery(@RequestParam("shell") String sh){
        HashSet<Passwrd> shellQueryList = passwrdData.getShellQuery(sh);
        return shellQueryList;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Passwrd uidQuery(@PathVariable int id){
        Passwrd result = passwrdData.getUidQuery(id);
        return result;
    }


    // Groups
    GroupData groupData = GroupData.getInstance();

    @GetMapping("/users/{id}/groups")
    public HashSet<Group> getIdGroups(@PathVariable int id){
        HashSet<Group> userGroupList = groupData.getUserGroups(id);

        return userGroupList;
    }

    @GetMapping("/groups")
    public HashSet<Group> getGroups(){
        HashSet<Group> groupsList = groupData.getGroups();
        return groupsList;
    }


    @RequestMapping(value = "/groups/query", method = RequestMethod.GET)
    public HashSet<Group> optionalGroupUsers(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "gid", required = false) Integer gid,
                                          @RequestParam(value = "member", required = false) List<String> members
                                          ){
        HashSet<Group> optionalGroupSet = groupData.getOptionalGroup(name, gid, members);

        return optionalGroupSet;
    }


    @GetMapping("/groups/{id}")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Group getGidGroup(@PathVariable int id){

        Group groupGid = groupData.getGroupGid(id);

        return groupGid;
    }
}
