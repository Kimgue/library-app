package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.User.request.UserCreateRequest;
import com.group.libraryapp.dto.User.request.UserUpdateRequest;
import com.group.libraryapp.dto.User.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceV1 {


    private final UserJdbcRepository userRepository;

    public UserServiceV1(UserJdbcRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserResponse> getUsers(){
        return userRepository.getUsers();
    }

    public void saveUser(UserCreateRequest request){
        userRepository.saveUser(request.getName() , request.getAge());

    }

    public void updateUser(UserUpdateRequest request) {

        if (userRepository.isUserNotExist(request.getId())) {
            throw new IllegalArgumentException();
        }
        userRepository.updateUserName( request.getName(), request.getId());

    }


    public void deleteUser(String name){

        if(userRepository.isUserNotExist(name)){
            throw  new IllegalArgumentException();
        }

        userRepository.deleteUser(name);
    }
}
