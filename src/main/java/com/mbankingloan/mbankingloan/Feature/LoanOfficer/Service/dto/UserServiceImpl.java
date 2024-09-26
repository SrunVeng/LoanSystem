package com.mbankingloan.mbankingloan.Feature.LoanOfficer.Service.dto;


import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public Integer findUserIdByStaffId(String StaffId) {
        User user = userRepository.findByStaffId(StaffId);
        return user.getId();
    }
}
