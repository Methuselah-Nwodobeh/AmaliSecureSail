package com.amalitech.securesail.amalisecuresail.user_management.models;

import com.amalitech.securesail.amalisecuresail.global.BaseEntity;
import com.amalitech.securesail.amalisecuresail.user_management.constants.Gender;
import com.amalitech.securesail.amalisecuresail.user_management.constants.MaritalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User extends BaseEntity {
    @NotNull(message = "first firstName cannot be null")
    private String firstName;
    @NotNull(message = "last firstName cannot be null")
    private String lastName;

    private String otherName;
    private String position;
    @Email
    @NotBlank(message = "Employee Email is required")
    private String employeeEmail;
    @Email
    @NotBlank(message = "Personal Email is required")
    private String personalEmail;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$\n", message = "Invalid phone number")
    private String phoneNumber;
    @NotBlank(message = "Employee ID is required")
    private String employeeId;
    @NotNull(message = "Password is required")
    @Length(min = 8, message = "Password must be at least 8 characters")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$\n", message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    private String password;
    @Past
    private LocalDate dateOfBirth;
    @NotNull
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private String about;
    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull
    private Role role;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @URL(message = "Invalid image url")
    private String profilePicture;
    @NotBlank
    private String homeAddress;
    @NotBlank
    private String city;
    @NotBlank
    private String zipCode;
    @NotBlank
    private String country;
    @NotBlank
    private String regionOrState;
    private boolean isVerified;
    private boolean isAdmin;
    private boolean isBlocked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(otherName, user.otherName) && Objects.equals(employeeEmail, user.employeeEmail) && Objects.equals(employeeId, user.employeeId) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, otherName, employeeEmail, employeeId, role);
    }
}
