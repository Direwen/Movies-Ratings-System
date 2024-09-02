package com.movieproject.managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

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
    void formatGenres_validInput()
    {
        assertEquals("Action|Drama|Comedy", manager.formatGenres("comedy|action|drama"));
    }

    @Test
    void formatGenres_duplicatesInput()
    {
        assertEquals("Action|Comedy", manager.formatGenres("comedy|action|Comedy"));
    }

    @Test
    void formatGenres_mixedCaseInput()
    {
        assertEquals("Action|Drama|Comedy", manager.formatGenres("CoMeDy|AcTiOn|dRaMa"));
    }

    @Test
    void formatGenres_trailingSpacesInput()
    {
        assertEquals("Action|Drama|Comedy", manager.formatGenres("  Comedy  | Action | Drama  "));
    }

}