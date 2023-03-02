package com.group.libraryapp.domain.user.loanhistory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory,Long> {

    //select * from user_loan_history where book_name = ? nad is_reutnr = ?
    boolean existsByBookNameAndIsReturn(String name,boolean isReturn);


}
