package rs.ac.uns.quiz.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false,unique = true)
    String username;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    Role role;

    @Column(nullable = false)
    String country;

    public Person(String username, String password, String name, String email, Role role, String country) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
        this.country = country;
    }

    public Person(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Person && ((Person) obj).username == this.username);

    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
