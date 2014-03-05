package com.github.hubin0011.rest.rw.controller;

import com.github.hubin0011.rest.rw.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserRestController {

    private List<User> users = new ArrayList<User>();
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus create(@RequestBody User user){
        System.out.println("in Request Post ");
        users.add(user);
        return HttpStatus.CREATED;
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable("id")String id){
        System.out.println("in Request Get ");
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
        System.out.println("in Request Put ");

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
        System.out.println("in Request Delete ");
        for(User aUser : users){
            if(aUser.getId().equals(id)){
                users.remove(aUser);
                return HttpStatus.OK;
            }
        }

        return HttpStatus.BAD_REQUEST;
    }

}
