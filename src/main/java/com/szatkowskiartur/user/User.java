package com.szatkowskiartur.user;

import com.szatkowskiartur.portfolio.Portfolio;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String surname;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private Date birthday;

    @Column (nullable = false)
    @ToString.Exclude
    private String password;

    @OneToOne (fetch = FetchType.LAZY, mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ToString.Exclude
    private Portfolio portfolio;


}
