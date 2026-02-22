# Maven directory structure & IntelliJ setup

## Correct Maven structure

Your project must look like this (folder names matter):

```
JAVA/
├── pom.xml
└── src/
    ├── main/
    │   └── java/          ← one folder named "java"
    │       ├── client/    ← package client
    │       │   └── GameClient.java
    │       └── server/    ← package server
    │           ├── ClientHandler.java
    │           ├── GameLogic.java
    │           ├── GameServer.java
    │           └── GameSession.java
    └── test/
        └── java/          ← one folder named "java", NOT "java.server"
            └── server/    ← package server
                ├── FixedRandom.java
                └── GameLogicTest.java
```

Important:

- **Two separate folders:** `java` then `server` → path is `.../java/server/`.
- **Not** one folder named `java.server` → that breaks “package name does not correspond to file path”.

So the path for test sources must be:

`JAVA/src/test/java/server/`  
(i.e. `java` + `server` as two levels), not `JAVA/src/test/java.server/`.

## If you have `java.server` as one folder

1. Rename `src/test/java.server` to something temporary, e.g. `src/test/java_server_backup`.
2. Create folder `src/test/java`.
3. Move the contents of `java_server_backup` into `src/test/java/server/` (create `server` if your package is `server`).
4. Delete `java_server_backup`.
5. In every file under that path, set the first line to `package server;` (no `java.`).

So: one folder per package segment. `package server` → path must end with `.../server/`.

## IntelliJ: make it recognize the project

1. **Reimport Maven**
   - Right‑click `pom.xml` → **Maven** → **Reload Project**
   - Or open the Maven tool window and click the reload (🔄) button.

2. **Check source roots**
   - `src/main/java` must be blue (Sources).
   - `src/test/java` must be green (Test Sources).
   - If not: right‑click the folder → **Mark Directory as** → **Sources Root** or **Test Sources Root**.

3. **Open the right root**
   - Open the **JAVA** folder (where `pom.xml` is) as the project root in IntelliJ, not the parent. **File** → **Open** → select the `JAVA` folder.

## Resolving “cannot resolve” (GameLogic, JUnit, etc.)

- **GameLogic / server classes:** Fix the structure and package paths as above, then **Maven → Reload Project**. Main and test sources will then see each other.
- **JUnit:** This project’s tests are written without JUnit (no `@Test`). If you add JUnit later, add to `pom.xml`:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

Then run **Maven → Reload Project** again.

## Quick checklist

- [ ] Path is `src/test/java/server/...`, not `src/test/java.server/...`
- [ ] Every file has `package server;` or `package client;` matching its path under `.../java/`.
- [ ] Opened project root = folder that contains `pom.xml` (JAVA).
- [ ] Maven reload done after any `pom.xml` or folder change.
- [ ] `src/main/java` = Sources, `src/test/java` = Test Sources.
