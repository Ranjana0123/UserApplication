package com.microservice.user;

import com.microservice.user.entity.ResponseDto;
import com.microservice.user.entity.User;
import com.microservice.user.repository.UserRepository;
import com.microservice.user.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    WebClient webClient;

    @Mock
    WebClient.Builder webClientBuilder;

    @BeforeEach
    void setup() {
        User userSetup = new User();
        userSetup.setId(1L);
        userSetup.setFirstName("John");
        userSetup.setEmail("john@example.com");
        when(webClient.get().uri(anyString(), Optional.ofNullable(any())).retrieve().bodyToMono(User.class))
                .thenReturn(Mono.just(userSetup));

    }


    @Test
    void createUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setEmail("john@example.com");
        // when - action or the behaviour that we are going test
        when(userRepository.save(any(User.class))).thenReturn(user);
        User created = userService.saveUser(user);

        assertEquals("John",created.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository).save(any(User.class));
                
    }
    @Test
    void getUser(){
       /* User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setFirstName("john");
        when(userRepository.findById(1L))
                .thenReturn(Optional.of((mockUser)));
        // Act
        ResponseDto result = userService.getUser(1L);
        // Assert
        assertEquals(1L, result.getUser().getId());
        assertEquals("john", result.getUser().getUserName());
        verify(userRepository, times(1)).findById(1L);

*/
    }
}
