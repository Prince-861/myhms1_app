package com.hms1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
//    Here all the variables are non-static variables.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "no_of_guest", nullable = false)
    private Integer no_of_guest;

    @Column(name = "no_of_bedrooms", nullable = false)
    private Integer no_of_bedrooms;

    @Column(name = "no_of_beds", nullable = false)
    private Integer no_of_beds;

    @Column(name = "no_of_bathrooms", nullable = false)
    private Integer no_of_bathrooms;

//    here One means the below variable(country) and the Many is the class name in which we are writing(that is Property class here)
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;//From this city variable, can we tell that Property object has the memory address of the city object.
                      //because property object has reference variable of city.

//    since the foreign key is in the property table now, so we will have to go to JpaBuddy and do the associations from there.
//    after writing above code we will have three tables created in the database now.

}