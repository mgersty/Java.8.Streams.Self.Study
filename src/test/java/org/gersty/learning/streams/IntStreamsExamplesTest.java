package org.gersty.learning.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntStreamsExamplesTest{

    Set<String> shakespeareWords;
    Set<String> scrabbleWords;

    final int [] scrabbleENScore = {
        // a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p,  q, r, s, t, u, v, w, x, y,  z
           1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10} ;
           

    @BeforeEach
    public void setup() throws IOException {
        shakespeareWords = Files.lines(Paths.get("files/words.shakespeare.txt"))
                    .map(word -> word.toLowerCase())
                    .collect(Collectors.toSet());

        scrabbleWords = Files.lines(Paths.get("files/ospd.txt"))
                    .map(word -> word.toLowerCase())
                    .collect(Collectors.toSet());

                                               
   }

   @Test
   public void verifyScoreIsCalculatedCorrectly() {

        ToIntFunction<String> intScore = 
            word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

          assertEquals(14, intScore.applyAsInt("java"), "Score of hello: ");
   }


   @Test
   public void verifyBestWordCanBeDetermined(){

    Function<String, Integer> score = 
    word -> word.chars().map(letter -> scrabbleENScore[letter - 'a']).sum();

       String bestWord = 
       shakespeareWords.stream()
                        .filter(word -> scrabbleWords.contains(word))
                        .max(Comparator.comparing(score)) // iterate through the collection utlizing the Score Function as comparison
                        .get();
    
   
    assertEquals("whizzing", bestWord, "Best word: ");
   }

}
