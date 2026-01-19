# Prueba técnica Inditex

Esta es una pequeña demo como prueba técnica para Inditex. Aquí se detallan los requisitos para ejecutar el código y
explicaciones sobre la arquitectura, principios utilizados y mecanismos de resiliencia implementados.

## Arquitectura de Capas

El microservicio sigue un modelo de capas:

- **Controller → Facade → Service → Repository (Port) → JPA Repository (Adapter)**

**Beneficios:**

- **Separación de responsabilidades:** cada capa cumple un rol único y bien definido.
- **Arquitectura Hexagonal:** los ports desacoplan el dominio de la persistencia.
- **Principios SOLID:** alto acoplamiento con interfaces y bajo acoplamiento con implementaciones.
- **Testabilidad:** cada capa se puede testear de manera independiente.
- **Flexibilidad:** cambios en persistencia o reglas de negocio no afectan otras capas.
- **Reactividad con WebFlux:** tanto el `Controller` como el `Repository` exponen y consumen flujos reactivos (`Mono`/`Flux`), permitiendo manejar peticiones concurrentes de forma eficiente y sin bloquear hilos de Netty.

Este modelo asegura un microservicio limpio, mantenible y escalable, alineado con buenas prácticas de desarrollo backend.

## Resiliencia y Protección del Microservicio

El microservicio implementa mecanismos de resiliencia para garantizar disponibilidad y estabilidad frente a fallos, tráfico elevado o problemas transitorios en la base de datos, utilizando **Resilience4j** integrado en el flujo reactivo (`Mono`) de WebFlux:

- **Circuit Breaker**  
  - Protege el servicio frente a fallos repetidos.  
  - Si un porcentaje de errores supera el umbral, se abre el circuito y se bloquean temporalmente nuevas llamadas.  
  - Evita saturar la base de datos o recursos cuando hay fallos continuos.  

- **Rate Limiter**  
  - Limita el número de peticiones por unidad de tiempo (`limitForPeriod`).  
  - Evita sobrecarga por picos de tráfico y protege recursos críticos.  
  - Las llamadas que exceden el límite pueden esperar un tiempo (`timeoutDuration`) o fallar directamente.  

- **Retry**  
  - Reintenta automáticamente operaciones que fallan de forma transitoria.  
  - Mejora la resiliencia frente a errores temporales sin afectar al usuario final.  

- **Bulkhead (Aislamiento de concurrencia)**  
  - Limita el número de llamadas concurrentes al repositorio.  
  - Evita que demasiadas solicitudes simultáneas bloqueen el servicio.  

- **Timeout**  
  - Cada operación crítica tiene un tiempo máximo de espera (`2 segundos` en este ejemplo).  
  - Si no se completa en ese tiempo, se lanza un error controlado.  

**Beneficio global:**  
Estas técnicas combinadas permiten que el microservicio mantenga su disponibilidad y rendimiento bajo condiciones de tráfico intenso, fallos de base de datos o errores transitorios, garantizando una experiencia consistente y predecible para el consumidor del API.

## Testing

- Se incluyen pruebas unitarias para cada capa (`Controller`, `Facade`, `Service`, `Repository`) utilizando `WebTestClient` para endpoints reactivos.
- Esto permite verificar tanto el flujo de negocio como la respuesta JSON del API.

## Resumen

Con esta arquitectura y los mecanismos de resiliencia:

- El servicio es **reactivo**, **modular**, **resiliente** y **testable**.  
- Se siguen principios de **buena ingeniería**: SOLID, Hexagonal Architecture y patrones de resiliencia modernos.  
- Listo para integrarse en entornos de producción donde la disponibilidad y la escalabilidad son críticas.
