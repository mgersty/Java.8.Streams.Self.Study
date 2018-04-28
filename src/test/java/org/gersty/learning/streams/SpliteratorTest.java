package org.gersty.learning.streams;

import org.gersty.learning.streams.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.List;
import java.util.ArrayList;


import org.junit.jupiter.api.Test;

public class SpliteratorTest{

    @Test
    public void exampleOfSpliterator(){
        List<Person> expectedPeople = new ArrayList<Person>();
        expectedPeople.add(new Person("Alice", 52, "New York"));
        expectedPeople.add(new Person("Brian", 25, "Chicago"));
        expectedPeople.add(new Person("Chelsea", 19, "London"));
        expectedPeople.add(new Person("David", 44, "Paris"));
        expectedPeople.add(new Person("Erica", 32, "Berlin"));
        expectedPeople.add(new Person("Francisco", 64, "Mexico"));

        Path path = Paths.get("files/people.txt");
        

        try{
           Stream<String> lines = Files.lines(path);
           Spliterator<String> lineSpliterator = lines.spliterator();
           Spliterator<Person> peopleSpliterator = new PersonSpliterator(lineSpliterator);
           Stream<Person> people = StreamSupport.stream(peopleSpliterator, false);
           
           assertEquals(expectedPeople, people.collect(Collectors.toList())); 
           lines.close();
            
        }catch(IOException e){
            e.printStackTrace();

        }
    }


}
