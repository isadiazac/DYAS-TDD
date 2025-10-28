package edu.unisabana.tyvs.tdd.domain.service;

import edu.unisabana.tyvs.tdd.domain.model.Person;
import edu.unisabana.tyvs.tdd.domain.model.RegisterResult;

import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa el registro de votantes.
 * Aplica las reglas de validación del dominio de la Registraduría.
 */
public class Registry {

    private static final int MIN_ID = 1;
    private static final int MIN_AGE = 0;
    private static final int MIN_VOTER_AGE = 18;
    private static final int MAX_AGE = 120;

    private final Set<Integer> registeredIds = new HashSet<>();

    /**
     * Registra una persona como votante si cumple todas las reglas de validación.
     *
     * @param p persona a registrar
     * @return resultado del registro según las reglas de negocio
     */
    public RegisterResult registerVoter(Person p) {

        // Regla 1: ID inválido
        if (p.getId() < MIN_ID) {
            return RegisterResult.INVALID;
        }

        // Regla 2: persona muerta
        if (!p.isAlive()) {
            return RegisterResult.DEAD;
        }

        // Regla 3: edad fuera de rango
        if (p.getAge() < MIN_AGE || p.getAge() > MAX_AGE) {
            return RegisterResult.INVALID_AGE;
        }

        // Regla 4: menor de edad
        if (p.getAge() < MIN_VOTER_AGE) {
            return RegisterResult.UNDERAGE;
        }

        // Regla 5: persona duplicada
        if (registeredIds.contains(p.getId())) {
            return RegisterResult.DUPLICATED;
        }

        // Registro exitoso
        registeredIds.add(p.getId());
        return RegisterResult.VALID;
    }
}
