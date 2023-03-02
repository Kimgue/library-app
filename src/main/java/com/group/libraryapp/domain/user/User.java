package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id //이 필드를 primary key로 간주한다
    @GeneratedValue(strategy = GenerationType.IDENTITY) //primary key 는 자동생성되는 값이다 mysql 는 IDENTITY와 매핑된다.
    private Long id = null;

    //@Column : 객체의 필드와 table 의 필드를 매핑한다.
    @Column(nullable = false, length = 20, name = "name")
    private  String name;

    private  Integer age;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLoanHistory> userLoanHistory = new ArrayList<>();

    //JPA를 사용하기위해서는 기본 생성자가 꼭 필요하다!
    protected User(){}

    public User(String name, Integer age) {
        if (name == null || name.isBlank()) {

            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void updateName(String name) {
        this.name =name;
    }

    public Long getId() {
        return id;
    }
    public void loanBook(String bookName){
        this.userLoanHistory.add(new UserLoanHistory(this, bookName));
    }
    public void returnBook(String bookName){
        UserLoanHistory targetHistory = this.userLoanHistory.stream()
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow();
        targetHistory.doReturn();
    }


}
