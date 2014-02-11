package com.github.hubin0011.rest.spring.controller;

import com.github.hubin0011.rest.spring.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserRestController {

    private List<User> users = new ArrayList<User>();

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus create(@RequestBody User user){
        users.add(user);
        return HttpStatus.CREATED;
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable("id")String id){
        if(id == null) {
            return null;
        }

        for(User user : users){
            if(user.getId().equals(id)){
                return user;
            }
        }

        return null;
    }

    @RequestMapping(value="{id}",method = RequestMethod.PUT)
    @ResponseBody
    public HttpStatus update(@PathVariable("id")String id, @RequestBody User user){
        for(User aUser : users){
            if(aUser.getId().equals(id)){
                aUser.setEmail(user.getEmail());
                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value="{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public HttpStatus delete(@PathVariable("id")String id){
        for(User aUser : users){
            if(aUser.getId().equals(id)){
                users.remove(aUser);
                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

    @RequestMapping(value="list",method = RequestMethod.GET)
    @ResponseBody
    public List<User> list(){
        return users;
    }
}
