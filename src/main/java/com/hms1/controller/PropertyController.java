package com.hms1.controller;

import com.hms1.entity.Property;
import com.hms1.repository.PropertyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
    private PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
//    @GetMapping("/search-hotels")
//    public List<Property> searchHotels(@RequestParam(required = false) String cityName, @RequestParam(required = false) String countryName){//By writing required=false we are making the parameters optional in the RequestParam(means if we give only one parameters while GET in the Postman, then also it will work or we just request like this (http://localhost:8080/api/v1/properties/search-hotels?countryName=India) then also it will work.//if we want to search cityName, or countryName or locationName then we will have to add more @RequestParam String countryName
//        List<Property> properties = propertyRepository.searchHotels(cityName, countryName);
//        return properties;
//    }
    @GetMapping("search-hotels")
    public List<Property>searchHotels(@RequestParam String name){
        List<Property> properties = propertyRepository.searchHotels(name);
        return properties;
    }

}
