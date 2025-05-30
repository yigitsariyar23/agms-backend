package com.agms.backend.service.impl;

import com.agms.backend.dto.UserProfileResponse;
import com.agms.backend.model.users.Role;
import com.agms.backend.model.users.Student;
import com.agms.backend.model.users.User;
import com.agms.backend.model.users.Advisor;
import com.agms.backend.model.users.DepartmentSecretary;
import com.agms.backend.model.users.DeanOfficer;
import com.agms.backend.model.users.StudentAffairs;
import com.agms.backend.repository.StudentRepository;
import com.agms.backend.repository.UserRepository;
import com.agms.backend.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(String firstName, String lastName, String email, String password, Role role) {
        // This method should now be implemented differently based on the role
        // For now, throwing an exception as creation should go through
        // AuthenticationService
        throw new UnsupportedOperationException("User creation should go through AuthenticationService");
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserProfileResponse getUserProfile(String email) {
        User user = findByEmail(email);
        UserProfileResponse.UserProfileResponseBuilder profileBuilder = UserProfileResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .role(user.getRole());

        // Set institute number based on user role
        String instituteNumber = getInstituteNumber(user);
        if (instituteNumber != null) {
            profileBuilder.instituteNumber(instituteNumber);
        }

        return profileBuilder.build();
    }

    /**
     * Get the institute number for a user based on their role
     * 
     * @param user The user
     * @return Student number for students, employee ID for employees
     */
    private String getInstituteNumber(User user) {
        switch (user.getRole()) {
            case STUDENT:
                if (user instanceof Student) {
                    return ((Student) user).getStudentNumber();
                }
                break;
            case ADVISOR:
                if (user instanceof Advisor) {
                    return ((Advisor) user).getEmpId();
                }
                break;
            case DEPARTMENT_SECRETARY:
                if (user instanceof DepartmentSecretary) {
                    return ((DepartmentSecretary) user).getEmpId();
                }
                break;
            case DEAN_OFFICER:
                if (user instanceof DeanOfficer) {
                    return ((DeanOfficer) user).getEmpId();
                }
                break;
            case STUDENT_AFFAIRS:
                if (user instanceof StudentAffairs) {
                    return ((StudentAffairs) user).getEmpId();
                }
                break;
        }
        return null;
    }
}