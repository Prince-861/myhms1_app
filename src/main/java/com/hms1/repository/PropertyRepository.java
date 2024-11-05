package com.hms1.repository;

import com.hms1.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
//    Property entity class is somewhat related with the city class. How it is related? --> It has common column, but we will not give
//  column name here, we are writing the HQL query we are writing.

//    @Query annotation can help us to write the queries very similar to SQL.
//    Below we are writing p.city because the city variable is present in the Property class
//    @Query("Select p from Property p JOIN p.city c JOIN p.country co where c.name =:city or co.name=:country")
//    List<Property> searchHotels(
//            @Param("city") String cityName,
//            @Param("country") String countryName
//            );//so what this will do @Param("city") --> wherever :city is written down in our query, in the place of :city, this value(cityName) will be substituted dynamically.

//  Other way of doing above thing is first take the cityName, go to the city table("Select id from city where name= ) this will give us the id no. , now take the id no.
//    and we will go to  the property table("Select * from property where city_id = 1).So, we can write two different queries and simplify joins here.

//    We don' require two variables here in the above as we have mentioned @Param two times.
    @Query("Select p from Property p JOIN p.city c JOIN p.country co where c.name=:name or co.name=:name")
     List<Property> searchHotels(
         @Param("name") String name
    );
}