# Taller de TDD - Pruebas Unitarias: Registraduría

Integrantes:

- Isabela Díaz Acosta(isabeladiac@unisabana.edu.co - 0000310594)

## Cómo compilar y ejecutar pruebas

```bash
mvn clean test
mvn jacoco:report
```

Abrir reporte JaCoCo: target/site/jacoco/index.html

## Dominio y reglas validadas

Breve: servicio Registry registra votantes. Reglas principales:

- Persona nula → INVALID

- id ≤ 0 → INVALID

- alive = false → DEAD

- edad < 0 o > 120 → INVALID_AGE

- edad < 18 → UNDERAGE

- id duplicado → DUPLICATED

- Si pasa todo → VALID

## TDD y AAA aplicado

Se siguió ciclo Red → Green → Refactor en múltiples iteraciones. Cada test está escrito con el patrón AAA:

- // Arrange → preparar

- // Act → ejecutar

- // Assert → verificar

## Historia TDD (ejemplos de 3 iteraciones)

1. test: add shouldRejectDeadPerson (RED)
   feat: return DEAD when !alive (GREEN)
   refactor: add null-check and constants (REFACTOR)

2. test: add underage at 17 (RED)
   feat: return UNDERAGE when age < 18 (GREEN)
   refactor: extract MIN_AGE constant (REFACTOR)

3. test: add duplicated id (RED)
   feat: track registeredIds and return DUPLICATED (GREEN)
   refactor: move registeredIds to field and add documentation (REFACTOR)

- Caso 1: Registrar persona válida
| Fase        | Descripción                                                                           | Commit                                                   | Resultado Esperado            |
| ----------- | ------------------------------------------------------------------------------------- | -------------------------------------------------------- | ----------------------------- |
| 🔴 RED      | Crear prueba `shouldRegisterValidPerson` con persona viva, id válido y edad adecuada. | `test: add shouldRegisterValidPerson (RED)`              | Falla inicialmente.           |
| 🟢 GREEN    | Implementar registro válido en `registerVoter`.                                       | `feat: implement valid registration logic (GREEN)`       | La prueba pasa correctamente. |
| 🟡 REFACTOR | Optimizar estructura del método y constantes.                                         | `refactor: improve registerVoter readability (REFACTOR)` | Código limpio y mantenible.   |

- Caso 2: Rechazar persona fallecida
  | Fase        | Descripción                                  | Commit                                                              | Resultado Esperado            |
| ----------- | -------------------------------------------- | ------------------------------------------------------------------- | ----------------------------- |
| 🔴 RED      | Crear prueba `shouldRejectDeadPerson`.       | `test: add shouldRejectDeadPerson (RED)`                            | La prueba falla (sin lógica). |
| 🟢 GREEN    | Devolver `DEAD` cuando `isAlive` es `false`. | `feat: return DEAD when person is not alive (GREEN)`                | Prueba exitosa.               |
| 🟡 REFACTOR | Agregar verificación de nulos y constantes.  | `refactor: add null check and constants MIN_AGE/MAX_AGE (REFACTOR)` | Código limpio.                |

- Caso 3: Edad negativa o fuera del rango
  | Fase        | Descripción                                    | Commit                                            | Resultado Esperado          |
| ----------- | ---------------------------------------------- | ------------------------------------------------- | --------------------------- |
| 🔴 RED      | Crear prueba `shouldRejectInvalidNegativeAge`. | `test: add shouldRejectInvalidNegativeAge (RED)`  | Falla al no estar validado. |
| 🟢 GREEN    | Validar edad `< 0` o `> 120`.                  | `feat: validate negative or too high age (GREEN)` | Pasa correctamente.         |
| 🟡 REFACTOR | Centralizar validación en método `isValidAge`. | `refactor: extract isValidAge method (REFACTOR)`  | Código más ordenado.        |

- Caso 4: Menor de edad (17 años)
  | Fase        | Descripción                              | Commit                                         | Resultado Esperado  |
| ----------- | ---------------------------------------- | ---------------------------------------------- | ------------------- |
| 🔴 RED      | Crear prueba `shouldRejectUnderageAt17`. | `test: add shouldRejectUnderageAt17 (RED)`     | Falla.              |
| 🟢 GREEN    | Validar edad `< 18`.                     | `feat: reject person under 18 (GREEN)`         | Pasa correctamente. |
| 🟡 REFACTOR | Reutilizar constante `MIN_AGE = 18`.     | `refactor: define MIN_AGE constant (REFACTOR)` | Mejora legibilidad. |

- Caso 5: Adulto de 18 años
  | Fase        | Descripción                           | Commit                                         | Resultado Esperado  |
| ----------- | ------------------------------------- | ---------------------------------------------- | ------------------- |
| 🔴 RED      | Crear prueba `shouldAcceptAdultAt18`. | `test: add shouldAcceptAdultAt18 (RED)`        | Falla.              |
| 🟢 GREEN    | Validar que `18` sea permitido.       | `feat: accept person with age 18 (GREEN)`      | Pasa correctamente. |
| 🟡 REFACTOR | Reutilizar método `isValidAge`.       | `refactor: reuse isValidAge method (REFACTOR)` | Código limpio.      |

- Caso 6: Edad mayor a 120 años
  | Fase        | Descripción                                   | Commit                                          | Resultado Esperado  |
| ----------- | --------------------------------------------- | ----------------------------------------------- | ------------------- |
| 🔴 RED      | Crear prueba `shouldRejectInvalidAgeOver120`. | `test: add shouldRejectInvalidAgeOver120 (RED)` | Falla.              |
| 🟢 GREEN    | Validar `age > MAX_AGE`.                      | `feat: reject person older than 120 (GREEN)`    | Pasa correctamente. |
| 🟡 REFACTOR | Centralizar constantes.                       | `refactor: move MAX_AGE constant (REFACTOR)`    | Código más limpio.  |

- Caso 7: Persona duplicada
  | Fase        | Descripción                                      | Commit                                              | Resultado Esperado                 |
| ----------- | ------------------------------------------------ | --------------------------------------------------- | ---------------------------------- |
| 🔴 RED      | Crear prueba `shouldRejectDuplicatedPerson`.     | `test: add shouldRejectDuplicatedPerson (RED)`      | Falla (sin control de duplicados). |
| 🟢 GREEN    | Validar si el `id` ya existe en `registeredIds`. | `feat: reject duplicated person (GREEN)`            | Pasa correctamente.                |
| 🟡 REFACTOR | Reutilizar estructura de `Set<Integer>`.         | `refactor: clean up registeredIds logic (REFACTOR)` | Código ordenado.                   |

- Caso 8: ID inválido (cero o negativo)
  | Fase        | Descripción                           | Commit                                              | Resultado Esperado            |
| ----------- | ------------------------------------- | --------------------------------------------------- | ----------------------------- |
| 🔴 RED      | Crear prueba `shouldRejectInvalidId`. | `test: add shouldRejectInvalidId (RED)`             | Falla (sin validación de ID). |
| 🟢 GREEN    | Validar `id <= 0`.                    | `feat: reject invalid id (GREEN)`                   | Pasa correctamente.           |
| 🟡 REFACTOR | Extraer método `isValidId()`.         | `refactor: extract id validation method (REFACTOR)` | Código más legible.           |


## Matriz de pruebas (Clases de Equivalencia y Valores Límite)

| **Test (método)**                | **Entrada (edad, alive, id)**               | **Resultado esperado** |
| -------------------------------- | ------------------------------------------- | ---------------------- |
| `shouldRegisterValidPerson`      | edad = 30, alive = true, id = 1             | `VALID`                |
| `shouldRejectDeadPerson`         | edad = 40, alive = false, id = 2            | `DEAD`                 |
| `shouldRejectInvalidNegativeAge` | edad = -5, alive = true, id = 3             | `INVALID_AGE`          |
| `shouldRejectUnderageAt17`       | edad = 17, alive = true, id = 4             | `UNDERAGE`             |
| `shouldAcceptAdultAt18`          | edad = 18, alive = true, id = 5             | `VALID`                |
| `shouldRejectInvalidAgeOver120`  | edad = 121, alive = true, id = 6            | `INVALID_AGE`          |
| `shouldRejectDuplicatedPerson`   | edad = 25, alive = true, id = 7 (dos veces) | `DUPLICATED`           |
| `shouldRejectInvalidId`          | edad = 30, alive = true, id = 0             | `INVALID`              |

## Cobertura

Ejecutar mvn jacoco:report y anexar target/site/jacoco/index.html.

## Reflexión

- Escenarios no cubiertos: p.ej. formato de ID no numérico (si aplica), test de null person.

- Defectos detectados: ejemplo en defectos.md.

- Mejoras: separar validadores, inyectar repositorio de IDs (para test y producción), usar constantes y límites.
