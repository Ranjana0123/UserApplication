package com.microservice.user.services;

import com.microservice.user.entity.DepartmentDto;
import com.microservice.user.entity.ResponseDto;
import com.microservice.user.entity.User;
import com.microservice.user.entity.UserDto;
import com.microservice.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
        @Autowired
        private WebClient.Builder webClientBuilder;

    public User saveUser(User department) {
        return userRepository.save(department);
    }

    @Override
    public ResponseDto getUser(Long id) {

    ResponseDto responseDto=new ResponseDto();
      User user =userRepository.findById(id).get();
        UserDto userDto =mapToUser(user);
       /* ResponseEntity<DepartmentEntity> responseEntity = restTemplate
                .getForEntity("http://localhost:8080/api/departments/" + user.getDepartmentId(),
                        DepartmentEntity.class);
     */
        DepartmentDto dept = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/departments/" + user.getDepartmentId())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();
        responseDto.setUser(userDto);
        responseDto.setDepartment(dept);

        return responseDto;
    }
    private UserDto mapToUser(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserAddress(user.getFirstName());
        userDto.setUserName(user.getLastName());
        userDto.setUseCode(user.getEmail());
        return userDto;
    }
}
