package com.food_delivery.user_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.food_delivery.user_service.annotations.UserRoleValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Audited    // This Audit all the field where you define the @Audited
//@ToString(exclude = {"addresses", "favorites", "orders"})
public class User extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name of the User cannot be blank")
    @Size(min = 3, max = 30, message = "Number of characters in name should be in the range: [3, 10]")
    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email of the User cannot be blank")
    @Email(message = "Email should be valid email")
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
//    @Pattern(regexp = "^(ADMIN | CUSTOMER | DELIVERY_BOY | RESTAURANT_OWNER)$")
    @UserRoleValidation
    private Role role;

    @NotAudited  // If we define NotAudited then It will not keep track the changes down in the database to that particular field
    private boolean isAvailable = true;



/*

    @NotAudited
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurant> favorites = new ArrayList<>();

    @NotAudited
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer")  // inverse side
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

//    @OneToOne(mappedBy = "user")
//    private Restaurant restaurant;

    @NotAudited
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();

*/



    @PrePersist
    void beforeSave() {

    }

    @PreUpdate
    void beforeUpdate() {

    }
    @PreRemove
    void beforeDelete() {

    }

    // currently we are not seen which thing is updated and when was updated
}
