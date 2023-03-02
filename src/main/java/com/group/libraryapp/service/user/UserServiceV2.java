package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.User.request.UserCreateRequest;
import com.group.libraryapp.dto.User.request.UserUpdateRequest;
import com.group.libraryapp.dto.User.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //아래에 있는 함수가 시작될 때 start transaction을 해준다(트랜잭션을 시작!)
    //함수가 예외없이 잘 끝나면 commit;
    //문제가 있다면 rollback;
    @Transactional
    public void saveUser(UserCreateRequest request){
        userRepository.save( new User(request.getName(), request.getAge()));

    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(){
       return userRepository.findAll().stream()
               .map(UserResponse::new)
               .collect(Collectors.toList());

    }
    @Transactional
    public void updateUser(UserUpdateRequest request){
       User user =  userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

       user.updateName(request.getName());
       userRepository.save(user); //영속성컨텍스트 기능으로 변경감지해서 save 를 하지않아도 자동 자정된다
    }
    @Transactional
    public void deleteUser(String name){
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }
}
