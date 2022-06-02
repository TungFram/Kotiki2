package ru.itmo.kotiki2.model;

import java.time.LocalDate;
import java.util.List;


import ru.itmo.kotiki2.enums.CatColor;
import ru.itmo.kotiki2.enums.CatType;
import javax.persistence.*;
import lombok.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Value
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Builder(builderClassName = "CatBuilder",
        builderMethodName = "createBuilder",
        toBuilder = true,
        access = AccessLevel.PUBLIC,
        setterPrefix = "with")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Cat")
public class ModelCat {

    @Id
    @SequenceGenerator(name = "pet_seq_gen", sequenceName = "pet_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq_gen")
    @Column(name = "CatID", nullable = false)
    int id;

    @EqualsAndHashCode.Exclude
    @Column(name = "Name", length = 64)
    String name;

    @Column(name = "Date_birth")
    LocalDate dateOfBirth;

    @Column(name = "Type", length = 32)
    @Enumerated(EnumType.STRING)
    CatType type;

    @Column(name = "Color", length = 32)
    @Enumerated(EnumType.STRING)
    CatColor color;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "Affiliation",
            joinColumns = @JoinColumn(name = "Id_of_cat", referencedColumnName = "CatID"),
            inverseJoinColumns = @JoinColumn(name = "Id_of_owner", referencedColumnName = "OwnerID"))
    ModelOwner owner;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Friendship",
            joinColumns = @JoinColumn(name = "id_of_first_cat", referencedColumnName = "CatID"),
            inverseJoinColumns = @JoinColumn(name = "id_of_second_cat", referencedColumnName = "CatID"))
    @Singular List<ModelCat> friends;
}
