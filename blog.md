## Task 1

### a

**Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.**

In both Mercenary and ZombieToast classes, there's a movement behavior when the enemey has an InvincibilityPotion. This portion of the movement logic is identical in both classes:

The onDestroy method in both Enemy and ZombieToastSpawner classes are the same.

**What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.**

The Strategy Pattern can be a good fit here. The strategy pattern is used when we have multiple algorithms (or strategies) for a specific task, and we can select one of these algorithms depending on the situation. Here, the movement behavior of enemies can be seen as a strategy that can change.

Relating the code to the strategy design we observe that different movement behaviors of enemies are the algorithms. The behavior can be encapsulated in separate classes. Depending on the situation (like the type of potion the player has), a suitable movement behavior can be chosen.