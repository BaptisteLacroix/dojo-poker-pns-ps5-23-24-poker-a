package com.seinksansdoozebank.fr.controller;

import com.seinksansdoozebank.fr.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RefereeTest {

    private Referee referee;
    private Hand hand1;
    private Hand hand2;
    private Victory victory;

    @BeforeEach
    public void setUp() {
        referee = new Referee();
        Hand.resetIdCounter();
    }

    @Test
    void compareHandsDraw() {
        hand1 = new Hand(new ArrayList<>(List.of("A")));
        hand2 = new Hand(new ArrayList<>(List.of("A")));
        victory = referee.compareHands(hand1, hand2);
        assertNull(victory);
    }
    @Test
    void compareHandsFirstWin() {
        hand1 = new Hand(new ArrayList<>(List.of("A")));
        hand2 = new Hand(new ArrayList<>(List.of("2")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand1);
    }

    @Test
    void straighVictoryTest() {
        hand1 = new Hand(new ArrayList<>(List.of("2","3","4","5","6")));
        hand2 = new Hand(new ArrayList<>(List.of("2","3","4","5","6")));
        victory = referee.compareHands(hand1, hand2);
        assertNull(victory);

        hand1 = new Hand(new ArrayList<>(List.of("2","3","4","5","6")));
        hand2 = new Hand(new ArrayList<>(List.of("3","4","5","6","7")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand2);

        hand1 = new Hand(new ArrayList<>(List.of("A","2","3","4","5")));
        hand2 = new Hand(new ArrayList<>(List.of("2","3","4","5","6")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand2);

        hand1 = new Hand(new ArrayList<>(List.of("9","10","R","D","V")));
        hand2 = new Hand(new ArrayList<>(List.of("10","R","D","V","A")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand1);

        hand1 = new Hand(new ArrayList<>(List.of("4","5","3","2","A")));
        hand2 = new Hand(new ArrayList<>(List.of("10","R","D","V","A")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand1);
    }

    @Test
    void straighCombinaisonTest() {
        hand1 = new Hand(new ArrayList<>(List.of("2","3","4","5","6")));
        hand2 = new Hand(new ArrayList<>(List.of("2","3","4","5","6")));
        assertEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand1).getCombinaison());
        assertEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand2).getCombinaison());

        hand1 = new Hand(new ArrayList<>(List.of("2","3","4","5","6")));
        hand2 = new Hand(new ArrayList<>(List.of("3","4","5","6","7")));
        assertEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand1).getCombinaison());
        assertEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand2).getCombinaison());

        hand1 = new Hand(new ArrayList<>(List.of("A","2","3","4","5")));
        hand2 = new Hand(new ArrayList<>(List.of("2","3","4","5","6")));
        assertEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand1).getCombinaison());
        assertEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand2).getCombinaison());

        hand1 = new Hand(new ArrayList<>(List.of("9","10","R","D","V")));
        hand2 = new Hand(new ArrayList<>(List.of("10","R","D","V","A")));
        assertEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand1).getCombinaison());
        assertNotEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand2).getCombinaison());

        hand1 = new Hand(new ArrayList<>(List.of("4","5","3","2","A")));
        hand2 = new Hand(new ArrayList<>(List.of("10","R","D","V","A")));
        assertEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand1).getCombinaison());
        assertNotEquals(Combinaison.STRAIGHT, referee.getBestCombinaison(hand2).getCombinaison());
    }

    @Test
    void compareHandsSecondWin() {
        hand1 = new Hand(new ArrayList<>(List.of("2")));
        hand2 = new Hand(new ArrayList<>(List.of("A")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand2);
    }

    @Test
    void getBestCombinaisonForHighestCard(){
        hand1 = new Hand(new ArrayList<>(List.of("2","D","7")));
        CombinaisonValue cv = new CombinaisonValue(Combinaison.HIGHEST_CARD, hand1);
        assertEquals(cv.toString(),referee.getBestCombinaison(hand1).toString());
    }

    @Test
    void threeOfAKindCombinaisonTest(){

        /* we check if the method searchThreeOfAKind works */
       Hand  hand1= new Hand(new ArrayList<>(List.of("2","2","3","4","2")));
       Hand hand2= new Hand(new ArrayList<>(List.of("2","A","3","4","2")));
       assertEquals(Combinaison.THREE_OF_A_KIND, referee.getBestCombinaison(hand1).getCombinaison());
       assertNotEquals(Combinaison.THREE_OF_A_KIND, referee.getBestCombinaison(hand2).getCombinaison());
    }

    @Test
    void threeOfAKindVictoryTest(){

        /* case with same threeOfAKinds*/
        hand1 = new Hand(new ArrayList<>(List.of("8","3","8","5","8")));
        hand2 = new Hand(new ArrayList<>(List.of("8","3","8","5","8")));
        victory = referee.compareHands(hand1, hand2);
        assertNull(victory);

        /* case with threeOfAKind combination and highest card combination*/
        hand1 = new Hand(new ArrayList<>(List.of("8","3","8","5","8")));
        hand2 = new Hand(new ArrayList<>(List.of("3","2","5","8","7")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand1);

        /* case with two different threeOfAKind  */
        hand1 = new Hand(new ArrayList<>(List.of("8","3","8","5","8")));
        hand2 = new Hand(new ArrayList<>(List.of("A","3","A","5","A")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand2);

        /* case with the same threeOfAKind but the highest card of each hand is different */
        hand1 = new Hand(new ArrayList<>(List.of("8","3","8","5","8")));
        hand2 = new Hand(new ArrayList<>(List.of("8","3","8","7","8")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand2);

        /* We test if the straight is stronger than the threeOfAKind*/
        hand1 = new Hand(new ArrayList<>(List.of("2","3","4","5","6")));
        hand2 = new Hand(new ArrayList<>(List.of("2","3","4","3","3")));
        victory = referee.compareHands(hand1, hand2);
        assertEquals(victory.getHand(), hand1);
    }
}
