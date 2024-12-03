# Bus Route System

The **Bus Route System** simulates a network of bus routes and stops, ensuring thread-safe interactions between buses, passengers, and stops. Each bus follows its own route, stopping at designated stops where passengers can transfer between buses or wait for the next one.

## System Features
- **Bus Stops**: Multiple buses can stop at the same bus stop, but the total number of buses at a stop is limited.
- **Passenger Interaction**: Passengers can transfer between buses or remain at the stop while waiting for their destination.
- **Independent Routes**: Each bus operates independently, following its assigned route.

---

## Development Requirements

### Synchronization
- Use synchronization utilities provided by `java.util.concurrent` and `java.util.concurrent.locks`.
- Avoid using:
    - `synchronized`
    - `volatile`
    - `BlockingQueue`
    - Other collections with limited thread-safety.

### Code Structure
- Organize classes and entities into meaningful packages.
- Use clear and descriptive names that reflect their functionality.

### Design Patterns
- Implement the **State** pattern for objects with more than two states.

### Multithreading
- Use `Callable` for creating threads wherever possible.
- Replace `Thread.sleep` with `TimeUnit` utilities for managing delays.

### Initialization
- Load object initialization data from a file.

### Singleton
- Ensure the application includes a thread-safe **Singleton**.

### Logging
- Use **Log4J2** for logging system behavior.

### Output
- Utilize the `main` method to demonstrate the functionality of threads if necessary.

---

## Implementation Notes
This system models real-world bus route scenarios with careful attention to thread safety, modularity, and scalability, leveraging modern Java libraries and design principles.
