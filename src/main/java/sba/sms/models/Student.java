package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "student")

public class Student {
    @Id
    @Column(length = 50, unique = true, nullable = false)
    String email;

    @NonNull
    @Column(length = 50, nullable = false)
    String name;

    @NonNull
    @Column(length = 50, nullable = false)
    String password;

    //@ManyToMany(mappedBy = "student_email", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    @ToString.Exclude
    //orphanRemoval = true
    @ManyToMany(cascade =
            {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinTable(name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    List<Course> courses = new ArrayList<>();

    public Student(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }

}
