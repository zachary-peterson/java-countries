package com.countries.com.countries.controllers;

import com.countries.com.countries.models.Country;
import com.countries.com.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    @Autowired
    private CountryRepository countrepos;

    // http://localhost:2019/names/all

    private ArrayList<Country> findCountries(ArrayList<Country> myList, CheckCountry tester)
    {
        ArrayList<Country> tempList = new ArrayList<>();
        for (Country c : myList)
        {
            if (tester.test(c))
            {
                tempList.add(c);
            }
        }
        return tempList;
    }

    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries()
    {
        List<Country> myList = new ArrayList<>();

        countrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        for (Country c : myList)
        {
            System.out.println(c);
        }
        return new ResponseEntity<>( myList, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/u

    @GetMapping(value = "/names/start/{letter}",
        produces = "application/json")
    public ResponseEntity<?> listAllByFirstName(@PathVariable char letter)
    {
        ArrayList<Country> myList = new ArrayList<>();
        countrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Country> rtnList = findCountries(myList, c -> c.getName().toLowerCase().charAt(0) == Character.toLowerCase(letter));

        for (Country c : rtnList)
        {
            System.out.println(c);
        }

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total

    @GetMapping(value = "/population/total", produces = {"application/json"})
    public ResponseEntity<?> displayPopulation()
    {
        List<Country> myList = new ArrayList<>();

        countrepos.findAll().iterator().forEachRemaining(myList::add);

        double total = 0.0;

        for (Country c : myList)
        {
            total = total + c.getPopulation();
        }
        System.out.println("The Total Population Is " + total);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/population/min

    @GetMapping(value = "/population/min", produces = {"application/json"})
    public ResponseEntity<?> displayPopulationMin()
    {
        ArrayList<Country> myList = new ArrayList<>();

        countrepos.findAll().iterator().forEachRemaining(myList::add);
        
        myList.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));

        Country minPop = myList.get(0);

        return new ResponseEntity<>(minPop, HttpStatus.OK);
    }
    
    // http://localhost:2019/population/max

    @GetMapping(value = "/population/max", produces = {"application/json"})
    public ResponseEntity<?> displayPopulationMax()
    {
        ArrayList<Country> myList = new ArrayList<>();

        countrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int) (c2.getPopulation() - c1.getPopulation()));

        Country maxPop = myList.get(0);

        return new ResponseEntity<>(maxPop, HttpStatus.OK);
    }

    //Stretch
    // http://localhost:2019/population/median
    //

    @GetMapping(value = "/population/median", produces = {"application/json"})
    public ResponseEntity<?> displayPopulationMedian()
    {
        ArrayList<Country> myList = new ArrayList<>();

        countrepos.findAll().iterator().forEachRemaining(myList::add);

        myList.sort((c1, c2) -> (int) (c1.getPopulation() - c2.getPopulation()));

        int medianMod = (myList.size() % 2 );

        int medianNum = ((myList.size() / 2) + medianMod );

        Country medianPop = myList.get(medianNum);

        return new ResponseEntity<>(medianPop, HttpStatus.OK);
    }

}
