package edu.unisabana.tyvs.tdd.domain.service;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import edu.unisabana.tyvs.tdd.domain.model.Gender;
import edu.unisabana.tyvs.tdd.domain.model.Person;
import edu.unisabana.tyvs.tdd.domain.model.RegisterResult;

public class RegistryTest {

    private Registry registry;

    @Before
    public void setUp() {
        registry = new Registry();
    }

    @Test
    public void shouldRegisterValidPerson() {
        // Arrange
        Person person = new Person("Ana", 1, 30, Gender.FEMALE, true);
        // Act
        RegisterResult result = registry.registerVoter(person);
        // Assert
        assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldRejectDeadPerson() {
        // Arrange
        Person dead = new Person("Carlos", 2, 40, Gender.MALE, false);
        // Act
        RegisterResult result = registry.registerVoter(dead);
        // Assert
        assertEquals(RegisterResult.DEAD, result);
    }

    @Test
    public void shouldRejectInvalidNegativeAge() {
        // Arrange
        Person person = new Person("Pedro", 3, -5, Gender.MALE, true);
        // Act
        RegisterResult result = registry.registerVoter(person);
        // Assert
        assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldRejectOlderThan120() {
        // Arrange
        Person person = new Person("Isabela", 4, 121, Gender.FEMALE, true);
        // Act
        RegisterResult result = registry.registerVoter(person);
        // Assert
        assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldRejectUnderageAt17() {
        // Arrange
        Person person = new Person("Ana", 5, 17, Gender.FEMALE, true);
        // Act
        RegisterResult result = registry.registerVoter(person);
        // Assert
        assertEquals(RegisterResult.UNDERAGE, result);
    }

    @Test
    public void shouldAcceptAdultAt18() {
        // Arrange
        Person person = new Person("Luis", 6, 18, Gender.MALE, true);
        // Act
        RegisterResult result = registry.registerVoter(person);
        // Assert
        assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldRejectInvalidAgeOver120() {
        // Arrange
        Person person = new Person("Sofia", 7, 121, Gender.FEMALE, true);
        // Act
        RegisterResult result = registry.registerVoter(person);
        // Assert
        assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldRejectDuplicatedPerson() {
        // Arrange
        Person person1 = new Person("Carlos", 8, 25, Gender.MALE, true);
        Person person2 = new Person("Carlos", 8, 25, Gender.MALE, true);
        registry.registerVoter(person1);
        // Act
        RegisterResult result = registry.registerVoter(person2);
        // Assert
        assertEquals(RegisterResult.DUPLICATED, result);
    }

    @Test
    public void shouldRejectInvalidId() {
        // Arrange
        Person person = new Person("Laura", 0, 30, Gender.FEMALE, true);
        // Act
        RegisterResult result = registry.registerVoter(person);
        // Assert
        assertEquals(RegisterResult.INVALID, result);
    }
}
