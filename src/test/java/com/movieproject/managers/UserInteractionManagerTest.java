package com.movieproject.managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserInteractionManagerTest {

    private static UserInteractionManager manager;

    @BeforeAll
    static void init()
    {
        manager = new UserInteractionManager(new Scanner(System.in));
    }

    @Test
    void parseInteger_validInput()
    {
        assertEquals(123, manager.parseInteger("123"));
    }

    @Test
    void parseInteger_invalidInput()
    {
        assertEquals(-1, manager.parseInteger("abc"));  // Should return -1 for invalid input
    }

    @Test
    void parseFloat_validInput()
    {
        assertEquals(3.5f, manager.parseFloat("3.5"));
    }

    @Test
    void parseFloat_invalidInput()
    {
        assertEquals(-1, manager.parseFloat("abc"));  // Should return -1 for invalid input
    }

    @Test
    void isGenresInputValid_validInput()
    {
        assertTrue(manager.isGenresInputValid("Comedy|Action|Drama"));
    }

    @Test
    void isGenresInputValid_invalidInput()
    {
        assertFalse(manager.isGenresInputValid("Comedy|123|Action"));
    }

    @Test
    void isGenresInputValid_singleGenre()
    {
        assertTrue(manager.isGenresInputValid("Comedy"));
    }

    @Test
    void isGenresInputValid_emptyInput()
    {
        assertFalse(manager.isGenresInputValid(""));
    }

    @Test
    void isGenreInputValid_doubleDelimeter()
    {
        assertFalse(manager.isGenresInputValid("Comedy|||Action||Drama"));
    }

    @Test
    void isGenresInputValid_multipleDashes()
    {
        assertFalse(manager.isGenresInputValid("Action|Drama|Sci--Fi"));
    }

    @Test
    void formatGenres_validInput() {
        assertTrue(Set.of("Action", "Drama", "Comedy").equals(Set.of(manager.formatGenres("comedy|action|drama").split("\\|"))));
    }

    @Test
    void formatGenres_duplicatesInput() {
        assertTrue(Set.of("Action", "Comedy").equals(Set.of(manager.formatGenres("comedy|action|comedy").split("\\|"))));
    }

    @Test
    void formatGenres_mixedCaseInput() {
        assertTrue(Set.of("Action", "Drama", "Comedy").equals(Set.of(manager.formatGenres("CoMeDy|AcTiOn|dRaMa").split("\\|"))));
    }

    @Test
    void formatGenres_trailingSpacesInput() {
        assertTrue(Set.of("Action", "Drama", "Comedy").equals(Set.of(manager.formatGenres("  Comedy  | Action | Drama  ").split("\\|"))));
    }

    @Test
    void formatGenres_singleDash() {
        assertTrue(Set.of("Sci-Fi", "Action", "Drama").equals(Set.of(manager.formatGenres(" sci-fi | action | Drama ").split("\\|"))));
    }

}