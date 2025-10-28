package edu.unisabana.tyvs.tdd.domain.service;


import java.util.HashSet;
import java.util.Set;

import edu.unisabana.tyvs.tdd.domain.model.Person;
import edu.unisabana.tyvs.tdd.domain.model.RegisterResult;

public class Registry {
    
    private Set<Integer> registeredIds = new HashSet<>();

    public RegisterResult registerVoter(Person p) {

        // Regla 1: id inválido
        if (p.getId() <= 0) {
            return RegisterResult.INVALID;
        }

        // Regla 2: persona muerta
        if (!p.isAlive()) {
            return RegisterResult.DEAD;
        }

        // Regla 3: edad negativa o mayor a 120
        if (p.getAge() < 0 || p.getAge() > 120) {
            return RegisterResult.INVALID_AGE;
        }

        // Regla 4: menor de edad
        if (p.getAge() < 18) {
            return RegisterResult.UNDERAGE;
        }

        // Regla 5: persona duplicada
        if (registeredIds.contains(p.getId())) {
            return RegisterResult.DUPLICATED;
        }

        
        registeredIds.add(p.getId());
        return RegisterResult.VALID;
    }


}
