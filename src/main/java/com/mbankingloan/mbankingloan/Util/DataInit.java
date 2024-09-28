package com.mbankingloan.mbankingloan.Util;


import com.mbankingloan.mbankingloan.Domain.*;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.*;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DataInit {

    private final LoanTypeRepository loanTypeRepository;
    private final LoanRepository loanRepository;
    private final CollateralTypeRepository collateralTypeRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenerateCustomerCIFNumber generateCustomerCIFNumber;



    @PostConstruct
    void init() {
        LoanTypeInit();
        CollateralInit();
        LoanProductInit();
        RoleInit();
        BranchInit();
        UserInit();
        CustomerInit();
    }

    private void LoanTypeInit() {

        LoanType loanType1 = LoanType.builder()
                .name("TermLoan")
                .isDeleted(false)
                .build();

        LoanType loanType2 = LoanType.builder()
                .name("OverDaft")
                .isDeleted(false)
                .build();

        loanTypeRepository.saveAll(List.of(loanType1, loanType2));
    }

    private void CollateralInit() {

        CollateralType collateralType1 = CollateralType.builder()
                .title("Hard-Title-Deed")
                .moa(BigDecimal.valueOf(0.8))
                .isDeleted(false)
                .build();
        CollateralType collateralType2 = CollateralType.builder()
                .title("Soft-Title-Deed")
                .moa(BigDecimal.valueOf(0.5))
                .isDeleted(false)
                .build();
        CollateralType collateralType3 = CollateralType.builder()
                .title("Fixed-Deposit-Certificate")
                .moa(BigDecimal.valueOf(1.0))
                .isDeleted(false)
                .build();
        collateralTypeRepository.saveAll(List.of(collateralType1, collateralType2, collateralType3));

    }


    private void LoanProductInit() {


            LoanType termLoan = loanTypeRepository.findById(1).orElseThrow();
            List<CollateralType> allCollateralTypes = collateralTypeRepository.findAll();

            List<CollateralType> hardSoftFixedCollateralTypes = new ArrayList<>();
            hardSoftFixedCollateralTypes.add(collateralTypeRepository.findById(2).orElseThrow());
            hardSoftFixedCollateralTypes.add(collateralTypeRepository.findById(3).orElseThrow());

            List<CollateralType> hardFixedCollateralTypes = new ArrayList<>();
            hardFixedCollateralTypes.add(collateralTypeRepository.findById(1).orElseThrow());
            hardFixedCollateralTypes.add(collateralTypeRepository.findById(3).orElseThrow());



            Loan personalLoan = Loan.builder()
                    .name("PersonalLoan")
                    .amountLimit(BigDecimal.valueOf(20000))
                    .interestRate(BigDecimal.valueOf(18.00))
                    .loantype(termLoan)
                    .tenure(120)
                    .isDeleted(false)
                    .collateralTypes(allCollateralTypes)
                    .build();

            Loan microBusinessLoan = Loan.builder()
                    .name("Micro-BusinessLoan")
                    .amountLimit(BigDecimal.valueOf(50000))
                    .interestRate(BigDecimal.valueOf(17.50))
                    .loantype(termLoan)
                    .tenure(120)
                    .isDeleted(false)
                    .collateralTypes(hardSoftFixedCollateralTypes)
                    .build();

            Loan smallBusinessLoan = Loan.builder()
                    .name("Small-BusinessLoan")
                    .amountLimit(BigDecimal.valueOf(100000))
                    .interestRate(BigDecimal.valueOf(15.85))
                    .loantype(termLoan)
                    .tenure(120)
                    .isDeleted(false)
                    .collateralTypes(hardFixedCollateralTypes)
                    .build();

            Loan mediumBusinessLoan = Loan.builder()
                    .name("Medium-BusinessLoan")
                    .amountLimit(BigDecimal.valueOf(500000))
                    .interestRate(BigDecimal.valueOf(13.85))
                    .loantype(termLoan)
                    .tenure(120)
                    .isDeleted(false)
                    .collateralTypes(hardFixedCollateralTypes)
                    .build();

            Loan largeBusinessLoan = Loan.builder()
                    .name("Large-BusinessLoan")
                    .amountLimit(BigDecimal.valueOf(2000000))
                    .interestRate(BigDecimal.valueOf(13.85))
                    .loantype(termLoan)
                    .tenure(120)
                    .isDeleted(false)
                    .collateralTypes(hardFixedCollateralTypes)
                    .build();

            Loan commercialBusinessLoan = Loan.builder()
                    .name("Commercial-BusinessLoan")
                    .amountLimit(BigDecimal.valueOf(10000000))
                    .interestRate(BigDecimal.valueOf(9.5))
                    .loantype(termLoan)
                    .tenure(120)
                    .isDeleted(false)
                    .collateralTypes(hardFixedCollateralTypes)
                    .build();



            loanRepository.saveAll(List.of(personalLoan, microBusinessLoan, smallBusinessLoan,mediumBusinessLoan,largeBusinessLoan,commercialBusinessLoan));

    }

    private void BranchInit(){

        Branch headOffice = Branch.builder()
                .name("Head-Office")
                .code("001")
                .isDeleted(false)
                .build();

        Branch branch1 = Branch.builder()
                .name("Preah-Monivong")
                .code("002")
                .isDeleted(false)
                .build();
        Branch branch2 = Branch.builder()
                .name("Pshar-Tmey")
                .code("003")
                .isDeleted(false)
                .build();
        Branch branch3 = Branch.builder()
                .name("Pshar-DermThkov")
                .code("004")
                .isDeleted(false)
                .build();
        Branch branch4 = Branch.builder()
                .name("Pshar-DeyHouy")
                .code("005")
                .isDeleted(false)
                .build();
        Branch branch5 = Branch.builder()
                .name("Chrouy-Chongva")
                .code("006")
                .isDeleted(false)
                .build();
        Branch branch6 = Branch.builder()
                .name("Independent-Monument")
                .code("006")
                .isDeleted(false)
                .build();

        branchRepository.saveAll(List.of(headOffice, branch1, branch2, branch3, branch4, branch5, branch6));

    }

    private void RoleInit(){

        Role role1 = Role.builder()
                .name("ADMIN")
                .build();
        Role role2 = Role.builder()
                .name("MANAGER")
                .build();
        Role role3 = Role.builder()
                .name("LOAN-OFFICER")
                .build();
        Role role4 = Role.builder()
                .name("CSA-OFFICER")
                .build();
        Role role5 = Role.builder()
                .name("CUSTOMER")
                .build();
        roleRepository.saveAll(List.of(role1,role2,role3,role4,role5));

    }


    private void UserInit(){
        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(roleRepository.findById(1).orElseThrow());

        List<Role> managerRoles = new ArrayList<>();
        managerRoles.add(roleRepository.findById(2).orElseThrow());

        List<Role> loanOfficerRoles = new ArrayList<>();
        loanOfficerRoles.add(roleRepository.findById(3).orElseThrow());

        List<Role> csaRoles = new ArrayList<>();
        csaRoles.add(roleRepository.findById(4).orElseThrow());

        User admin = User.builder()
                .email("fake@gmail.com")
                .birthDate(LocalDate.of(1989,12,1))
                .branchCode(branchRepository.findById(1).orElseThrow())
                .firstName("testing")
                .lastName("user")
                .hireDate(LocalDate.of(2018,1,1))
                .createdAt(LocalDate.now())
                .staffId("1")
                .position("Software Engineer")
                .phoneNumber("087276512")
                .KhanDistrict("7Makara")
                .village("VealVong")
                .provinceCity("PhnomPenh")
                .country("Cambodia")
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isDeleted(false)
                .isAccountNonLocked(true)
                .isVerified(true)
                .isBlock(false)
                .password(passwordEncoder.encode("Srun@123"))
                .roles(adminRoles)
                .build();

        User manager = User.builder()
                .email("fake2@gmail.com")
                .birthDate(LocalDate.of(1989,12,1))
                .branchCode(branchRepository.findById(1).orElseThrow())
                .firstName("testing")
                .lastName("user")
                .hireDate(LocalDate.of(2018,1,1))
                .createdAt(LocalDate.now())
                .staffId("2")
                .position("Head of Loan Branch Manager")
                .phoneNumber("086276512")
                .KhanDistrict("7Makara")
                .village("VealVong")
                .provinceCity("PhnomPenh")
                .country("Cambodia")
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isDeleted(false)
                .isAccountNonLocked(true)
                .isVerified(true)
                .isBlock(false)
                .password(passwordEncoder.encode("Srun@123"))
                .roles(managerRoles)
                .build();


        User loanOfficer = User.builder()
                .email("fake3@gmail.com")
                .birthDate(LocalDate.of(1989,12,1))
                .branchCode(branchRepository.findById(1).orElseThrow())
                .firstName("testing")
                .lastName("user")
                .hireDate(LocalDate.of(2018,1,1))
                .createdAt(LocalDate.now())
                .staffId("3")
                .position("Loan officer")
                .phoneNumber("086276518")
                .KhanDistrict("7Makara")
                .village("VealVong")
                .provinceCity("PhnomPenh")
                .country("Cambodia")
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isDeleted(false)
                .isAccountNonLocked(true)
                .isVerified(true)
                .isBlock(false)
                .password(passwordEncoder.encode("Srun@123"))
                .roles(loanOfficerRoles)
                .build();

        User csaOfficer = User.builder()
                .email("fake4@gmail.com")
                .birthDate(LocalDate.of(1989,12,1))
                .branchCode(branchRepository.findById(1).orElseThrow())
                .firstName("testing")
                .lastName("user")
                .hireDate(LocalDate.of(2018,1,1))
                .createdAt(LocalDate.now())
                .staffId("4")
                .position("Loan officer")
                .phoneNumber("086276912")
                .KhanDistrict("7Makara")
                .village("VealVong")
                .provinceCity("PhnomPenh")
                .country("Cambodia")
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isDeleted(false)
                .isAccountNonLocked(true)
                .isVerified(true)
                .isBlock(false)
                .password(passwordEncoder.encode("Srun@123"))
                .roles(csaRoles)
                .build();

        userRepository.saveAll(List.of(admin, manager, csaOfficer, loanOfficer));
    }

    private void CustomerInit(){

        Customer customer1 = Customer.builder()
                .email("vengsrun2019kh@gmail.com")
                .pin(passwordEncoder.encode("1234"))
                .password(passwordEncoder.encode("123456"))
                .customerCIFNumber(generateCustomerCIFNumber.generateCustomerCIFNumber())
                .isVerified(false)
                .phoneNumber("086252502")
                .nationalCardId("fakeNID")
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .isDeleted(false)
                .isBlock(false)
                .isAccountNonLocked(true)
                .createdAt(LocalDate.now())
                .gender("Male")
                .firstName("Testing")
                .lastName("Customer")
                .roles(roleRepository.findById(5).orElseThrow())
                .companyName("asd")
                .build();
        customerRepository.saveAll(List.of(customer1));
    }


}
