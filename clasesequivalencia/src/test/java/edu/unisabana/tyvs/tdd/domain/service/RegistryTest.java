package edu.unisabana.tyvs.tdd.domain.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;

import edu.unisabana.tyvs.tdd.domain.model.Gender;
import edu.unisabana.tyvs.tdd.domain.model.Person;
import edu.unisabana.tyvs.tdd.domain.model.RegisterResult;


public class RegistryTest {
    @Test
    public void shouldRegisterValidPerson() {
        // Arrange: preparar los datos y el objeto a probar
        Registry registry = new Registry();
        Person person = new Person("Ana", 1, 30, Gender.FEMALE, true);

        // Act: ejecutar la acción que queremos probar
        RegisterResult result = registry.registerVoter(person);

        // Assert: verificar el resultado esperado
        Assert.assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldRejectDeadPerson() {
        // Arrange
        Registry registry = new Registry();
        Person dead = new Person("Carlos", 2, 40, Gender.MALE, false);

        // Act
        RegisterResult result = registry.registerVoter(dead);

        // Assert
        Assert.assertEquals(RegisterResult.DEAD, result);
    }

    @Test
    public void shouldRejectInvalidNegativeAge() {
        Registry registry = new Registry();
        Person person = new Person("Pedro", 3, -5, Gender.MALE, true);
        RegisterResult result = registry.registerVoter(person);
        assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldRejectOlderThan120() {
        Registry registry = new Registry();
        Person person = new Person("Isabela", 4, 121, Gender.FEMALE, true);
        RegisterResult result = registry.registerVoter(person);
        assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldRejectUnderageAt17() {
        Registry registry = new Registry();
        Person person = new Person("Ana", 5, 17, Gender.FEMALE, true);
        RegisterResult result = registry.registerVoter(person);
        assertEquals(RegisterResult.UNDERAGE, result);
    }

    @Test
    public void shouldAcceptAdultAt18() {
        Registry registry = new Registry();
        Person person = new Person("Luis", 6, 18, Gender.MALE, true);
        RegisterResult result = registry.registerVoter(person);
        assertEquals(RegisterResult.VALID, result);
    }

    @Test
    public void shouldRejectInvalidAgeOver120() {
        Registry registry = new Registry();
        Person person = new Person("Sofia", 7, 121, Gender.FEMALE, true);
        RegisterResult result = registry.registerVoter(person);
        assertEquals(RegisterResult.INVALID_AGE, result);
    }

    @Test
    public void shouldRejectDuplicatedPerson() {
        Registry registry = new Registry();
        Person person1 = new Person("Carlos", 8, 25, Gender.MALE, true);
        Person person2 = new Person("Carlos", 8, 25, Gender.MALE, true);
        registry.registerVoter(person1);
        RegisterResult result = registry.registerVoter(person2);
        assertEquals(RegisterResult.DUPLICATED, result);
    }

    @Test
    public void shouldRejectInvalidId() {
        Registry registry = new Registry();
        Person person = new Person("Laura", 0, 30, Gender.FEMALE, true);
        RegisterResult result = registry.registerVoter(person);
        assertEquals(RegisterResult.INVALID, result);
    }

    

}
