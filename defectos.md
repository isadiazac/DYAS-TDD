# Registro de Defectos

Este documento recopila los defectos encontrados durante la ejecución de las pruebas unitarias del proyecto **Registraduría**.  
Cada defecto se documenta con su caso de prueba, entrada, resultado esperado y obtenido, causa probable y estado actual.

---

## Formato 1: Lista detallada (narrativa)

### Defecto 01

- **Caso de prueba**: Persona muerta.
- **Entrada**: `Person(name="Carlos", id=2, age=40, gender=MALE, alive=false)`
- **Resultado esperado**: `DEAD`
- **Resultado obtenido**: `VALID`
- **Causa probable**: No se evalúa la condición `alive=false` en `Registry.registerVoter`.
- **Estado**: Resuelto ✅

---

### Defecto 02

- **Caso de prueba**: Edad negativa.
- **Entrada**: `Person(name="Pedro", id=3, age=-5, gender=MALE, alive=true)`
- **Resultado esperado**: `INVALID_AGE`
- **Resultado obtenido**: `VALID`
- **Causa probable**: No se valida que la edad sea mayor o igual a cero.
- **Estado**: Resuelto ✅

---

### Defecto 03

- **Caso de prueba**: Edad mayor al límite (121 años).
- **Entrada**: `Person(name="Isabela", id=4, age=121, gender=FEMALE, alive=true)`
- **Resultado esperado**: `INVALID_AGE`
- **Resultado obtenido**: `VALID`
- **Causa probable**: Falta de validación del límite máximo de edad.
- **Estado**: Resuelto ✅

---

### Defecto 04

- **Caso de prueba**: Persona menor de edad (17 años).
- **Entrada**: `Person(name="Ana", id=5, age=17, gender=FEMALE, alive=true)`
- **Resultado esperado**: `UNDERAGE`
- **Resultado obtenido**: `VALID`
- **Causa probable**: No se verifica si la edad es menor que 18 años.
- **Estado**: Resuelto ✅

---

### Defecto 05

- **Caso de prueba**: Registro duplicado (mismo ID).
- **Entradas**:
  - Persona 1: `Person(name="Carlos", id=8, age=25, gender=MALE, alive=true)`
  - Persona 2: `Person(name="Carlos", id=8, age=25, gender=MALE, alive=true)`
- **Resultado esperado**:
  - Persona 1 → `VALID`
  - Persona 2 → `DUPLICATED`
- **Resultado obtenido**:
  - Persona 1 → `VALID`
  - Persona 2 → `VALID`
- **Causa probable**: Falta de control sobre IDs previamente registrados.
- **Estado**: Resuelto ✅

---

### Defecto 06

- **Caso de prueba**: ID inválido (0 o menor).
- **Entrada**: `Person(name="Laura", id=0, age=30, gender=FEMALE, alive=true)`
- **Resultado esperado**: `INVALID`
- **Resultado obtenido**: `VALID`
- **Causa probable**: No se valida que el ID sea mayor que 0.
- **Estado**: Resuelto ✅

---

## Formato 2: Tabla de defectos (bug tracking)

| ID  | Caso de Prueba          | Entrada       | Resultado Esperado | Resultado Obtenido | Causa Probable                       | Estado   |
| --- | ----------------------- | ------------- | ------------------ | ------------------ | ------------------------------------ | -------- |
| 01  | Persona muerta          | `alive=false` | `DEAD`             | `VALID`            | No se valida condición `alive=false` | Resuelto |
| 02  | Edad negativa           | `age=-5`      | `INVALID_AGE`      | `VALID`            | Falta validación de edad negativa    | Resuelto |
| 03  | Edad mayor a 120 años   | `age=121`     | `INVALID_AGE`      | `VALID`            | No se valida límite superior de edad | Resuelto |
| 04  | Menor de edad (17 años) | `age=17`      | `UNDERAGE`         | `VALID`            | No se verifica edad mínima           | Resuelto |
| 05  | Registro duplicado      | `id` repetido | `DUPLICATED`       | `VALID`            | No hay verificación de ID duplicado  | Resuelto |
| 06  | ID inválido (0)         | `id=0`        | `INVALID`          | `VALID`            | Falta validación de ID > 0           | Resuelto |

---

## Convenciones de Estado

- **Abierto** → El defecto aún no se corrige.
- **En progreso** → El defecto está siendo trabajado.
- **Resuelto** → El defecto fue corregido y validado con pruebas unitarias.

---

## Observaciones

- Todos los defectos fueron detectados inicialmente durante el desarrollo con TDD (fase _Red_).
- Cada uno fue corregido en iteraciones posteriores, pasando a _Green_ y posteriormente _Refactor_.
- Se recomienda mantener este registro actualizado con cada cambio en la clase `Registry` o las pruebas.
