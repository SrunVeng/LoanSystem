package com.mbankingloan.mbankingloan.Util;


import com.mbankingloan.mbankingloan.Domain.*;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {
        LoanTypeInit();
        CollateralInit();
        LoanProductInit();
        RoleInit();
        BranchInit();
        UserInit();

    }

    private void LoanTypeInit() {

        LoanType loanType1 = LoanType.builder()
                .name("TermLoan")
                .build();

        LoanType loanType2 = LoanType.builder()
                .name("OverDaft")
                .build();

        LoanType loanType3 = LoanType.builder()
                .name("HomeLoan")
                .build();
        loanTypeRepository.saveAll(List.of(loanType1, loanType2, loanType3));
    }

    private void CollateralInit() {

        CollateralType collateralType1 = CollateralType.builder()
                .title("Hard-Title-Deed")
                .moa(BigDecimal.valueOf(0.8))
                .build();
        CollateralType collateralType2 = CollateralType.builder()
                .title("Soft-Title-Deed")
                .moa(BigDecimal.valueOf(0.5))
                .build();
        CollateralType collateralType3 = CollateralType.builder()
                .title("Fixed-Deposit-Certificate")
                .moa(BigDecimal.valueOf(1.0))
                .build();
        CollateralType collateralType4 = CollateralType.builder()
                .title("Income-Statement")
                .moa(BigDecimal.valueOf(1.0))
                .build();
        collateralTypeRepository.saveAll(List.of(collateralType1, collateralType2, collateralType3, collateralType4));

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
                .collateralTypes(allCollateralTypes)
                .build();

        Loan microBusinessLoan = Loan.builder()
                .name("Micro-BusinessLoan")
                .amountLimit(BigDecimal.valueOf(50000))
                .interestRate(BigDecimal.valueOf(17.50))
                .loantype(termLoan)
                .collateralTypes(hardSoftFixedCollateralTypes)
                .build();

        Loan smallBusinessLoan = Loan.builder()
                .name("Small-BusinessLoan")
                .amountLimit(BigDecimal.valueOf(100000))
                .interestRate(BigDecimal.valueOf(15.85))
                .loantype(termLoan)
                .collateralTypes(hardFixedCollateralTypes)
                .build();

        Loan mediumBusinessLoan = Loan.builder()
                .name("Medium-BusinessLoan")
                .amountLimit(BigDecimal.valueOf(500000))
                .interestRate(BigDecimal.valueOf(13.85))
                .loantype(termLoan)
                .collateralTypes(hardFixedCollateralTypes)
                .build();

        Loan largeBusinessLoan = Loan.builder()
                .name("Large-BusinessLoan")
                .amountLimit(BigDecimal.valueOf(2000000))
                .interestRate(BigDecimal.valueOf(13.85))
                .loantype(termLoan)
                .collateralTypes(hardFixedCollateralTypes)
                .build();

        Loan commercialBusinessLoan = Loan.builder()
                .name("Commercial-BusinessLoan")
                .amountLimit(BigDecimal.valueOf(10000000))
                .interestRate(BigDecimal.valueOf(13.85))
                .loantype(termLoan)
                .collateralTypes(hardFixedCollateralTypes)
                .build();

        loanRepository.saveAll(List.of(personalLoan, microBusinessLoan, smallBusinessLoan,mediumBusinessLoan,largeBusinessLoan,commercialBusinessLoan));

    }

    private void BranchInit(){

        Branch headOffice = Branch.builder()
                .name("Head-Office")
                .code("001")
                .build();

        Branch branch1 = Branch.builder()
                .name("Preah-Monivong")
                .code("002")
                .build();
        Branch branch2 = Branch.builder()
                .name("Pshar-Tmey")
                .code("003")
                .build();
        Branch branch3 = Branch.builder()
                .name("Pshar-DermThkov")
                .code("004")
                .build();
        Branch branch4 = Branch.builder()
                .name("Pshar-DeyHouy")
                .code("005")
                .build();
        Branch branch5 = Branch.builder()
                .name("Chrouy-Chongva")
                .code("006")
                .build();
        Branch branch6 = Branch.builder()
                .name("Independent-Monument")
                .code("006")
                .build();

        branchRepository.saveAll(List.of(headOffice, branch1, branch2, branch3, branch4, branch5, branch6));

    }

    private void RoleInit(){

        Role role1 = Role.builder()
                .name("ADMIN")
                .build();
        Role role2 = Role.builder()
                .name("TOP-MANAGEMENT")
                .build();
        Role role3 = Role.builder()
                .name("MANAGEMENT")
                .build();
        Role role4 = Role.builder()
                .name("MANAGER")
                .build();
        Role role5 = Role.builder()
                .name("LOAN-OFFICER")
                .build();
        Role role6 = Role.builder()
                .name("CSA-OFFICER")
                .build();
        Role role7 = Role.builder()
                .name("CUSTOMER")
                .build();
        roleRepository.saveAll(List.of(role1,role2,role3,role4,role5,role6,role7));

    }


    private void UserInit(){
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(1).orElseThrow());
        User admin = User.builder()
                .email("vengsrun2017new@gmail.com")
                .birthDate(LocalDate.of(1989,12,1))
                .branchCode(branchRepository.findById(1).orElseThrow())
                .firstName("Veng")
                .lastName("Srun")
                .hireDate(LocalDate.of(2018,1,1))
                .createdAt(LocalDate.now())
                .staffId("2114")
                .position("Software Engineer")
                .phoneNumber("086252502")
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
                .roles(roles)
                .build();
        userRepository.saveAll(List.of(admin));
    }

}
