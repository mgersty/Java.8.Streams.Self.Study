package org.gersty.learning.streams;

import java.util.stream.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;



public class FlatMapExamplesTest{


    Stream<String> stream1;
    Stream<String> stream2;
    Stream<String> stream3;
    Stream<String> stream4;
 

    @BeforeEach
    public void setup() throws IOException {
        stream1 = Files.lines(Paths.get("files/TomSawyer_01.txt")) ;
        stream2 = Files.lines(Paths.get("files/TomSawyer_02.txt")) ;
        stream3 = Files.lines(Paths.get("files/TomSawyer_03.txt")) ;
        stream4 = Files.lines(Paths.get("files/TomSawyer_04.txt")) ;
    }

    @Test 
    public void canCreateAStreamofStreamsContainingTheStory(){
        Stream<Stream<String>> streamOfStreams = 
            Stream.of(stream1, stream2, stream3, stream4);
        
            assertEquals(4, (int) streamOfStreams.count(), "Expecting to verify the count of streams after using Stream.of is equal to 4");
     }





    @Test
    public void canProduceASingleStreamWithAllTheLinesOfTheStory() throws IOException{
        Stream<Stream<String>> streamOfStreams = 
        Stream.of(stream1, stream2, stream3, stream4);

              Stream<String> streamOfLines =
                streamOfStreams.flatMap(Function.identity());
            
                assertEquals(8449, (int) streamOfLines.count(), "Expecting to verify the count of streams after using Stream.flatMap is equal to 8449");
 }


    @Test
    public void canProduceASingleStreamWithAllTheWordsOfTheStory(){
        

        Stream<Stream<String>> streamOfStreams1 = 
        Stream.of(stream1, stream2, stream3, stream4);

              Stream<String> streamOfLines1 =
                streamOfStreams1.flatMap(Function.identity());
    
    
        
        Function<String, Stream<String>> lineSplitter = 
            line -> Pattern.compile(" ").splitAsStream(line);

            Stream<String> streamOfWords = 
                streamOfLines1.flatMap(lineSplitter)
                             .map(word -> word.toLowerCase())
                             .filter(word -> word.length() == 4)
                             .distinct();
                             
        assertEquals(1094, streamOfWords.count(), "Expecting to verify the count of words with a length smaller than 4 is equal to 1094");
    }



    @AfterEach
    public void teardown(){
        
        stream1.close();
        stream2.close();
        stream3.close();
        stream4.close();
    }


}