package com.szatkowskiartur.user;

import com.szatkowskiartur.portfolio.Portfolio;
import com.szatkowskiartur.role.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private LocalDate birthday;

    @Column (nullable = false)
    @ToString.Exclude
    private String password;

//    @OneToOne (fetch = FetchType.LAZY, mappedBy = "owner")
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    @ToString.Exclude
//    private Portfolio portfolio;

    @Column (nullable = false)
    private Boolean active;

    @ManyToMany (fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

}
