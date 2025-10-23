## Text-Based RPG Combat System
- Java-based RPG demonstrating OOP principles, design patterns, and MySQL database integration.

## What It Does
- Players create characters with customizable stats and equipment, manage inventories, and fight AI opponents in turn-based combat with temporary buffs/debuffs and status effects.

## Technologies Used
- Design Patterns: Singleton (database), Factory (item creation), Strategy (sorting algorithms)
- OOP: Abstract classes, inheritance hierarchies, polymorphism, interfaces, composition/aggregation
- Database: MySQL with JDBC, JSON serialization, CRUD operations, normalized schema with foreign keys
- Data Structures: HashMaps, ArrayLists, Iterators, custom comparators
- Technologies: Java 21, MySQL, Jackson, Maven

## Key Components
- Stats.java: Dynamic stat system with permanent/temporary modifiers
- Inventory.java: Equipment slots, multiple sorting methods, item management
- Service.java: Combat calculations, item effects, turn resolution
- DB_*.java: Database layer with table managers for Items, Stats, Inventory, NPCs

## Running
- Create MySQL database proiectjava
- Run JDBCTest.java to create tables
- Run Project.java to start


