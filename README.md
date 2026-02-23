# Playwright Java Test Automation Architecture

Ready-to-use UI Test Automation Architecture using Java and Playwright.

## Features

- Configuration-based architecture
- Utilizes Page Objects and Page Component Objects
- Data-Driven
- Captures screenshot on test failure
- Records video of test execution
- Provides detailed test report
- Supports parallel test execution
- Supports gemini-cli for AI-assisted coding
---

## Installation Steps

In order to use the framework:

1. [Fork](https://github.com/Tahanima/playwright-java-test-automation-architecture/fork) the repository.
2. Clone, i.e, download your copy of the repository to your local machine using
```
git clone https://github.com/[your_username]/playwright-java-test-automation-architecture.git
```
3. Import the project in [IntelliJ IDEA](https://www.jetbrains.com/idea/download/).
4. Make your desired changes.
5. Use IntelliJ IDEA to run your desired tests. Alternatively, you can use the terminal to run the tests, for example `./gradlew test -Dbrowser=firefox -Dheadless=false` to run all the tests using the firefox browser in headed mode.
6. Build and browse the allure report using
```
./gradlew allureServe
```
---

## Languages and Frameworks

The project uses the following:
- *[Java](https://openjdk.java.net/projects/jdk/)* as the programming language.
- *[Playwright](https://playwright.dev/)* as the web browser automation framework using the Java binding.
- *[Univocity Parsers](https://www.univocity.com/pages/univocity_parsers_tutorial)* to parse and handle CSV files.
- *[JUnit 5](https://junit.org/junit5/)* as the testing framework.
- *[Lombok](https://projectlombok.org/)* to generate getters.
- *[Owner](http://owner.aeonbits.org/)* to minimize the code to handle properties file.
- *[Allure Report](https://qameta.io/allure-report/)* as the test reporting strategy.
- *[Gradle](https://gradle.org/)* as the Java build tool.
- *[IntelliJ IDEA](https://www.jetbrains.com/idea/)* as the IDE.
- *[Gemini CLI](https://github.com/google-gemini/gemini-cli)* as an AI collaborator for code reviews, locator healing, and test generation.
---

## Project Structure

The project is structured as follows:

```bash
📦 playwright-java-test-automation-architecture
├─ .gemini
│  └─ skills
│     ├─ code-reviewer
│     │  ├─ review-rules.md
│     │  └─ SKILL.md
│     └─ healing-agent
│        ├─ locator-healer.md
│        └─ SKILL.md
├─ .github
│  ├─ FUNDING.yml
│  ├─ dependabot.yml
│  └─ workflows
│     └─ test-execution.yml
├─ .gitignore
├─ LICENSE
├─ README.md
├─ GEMINI.md
├─ build.gradle
├─ gradle
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
├─ settings.gradle
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ io
   │  │     └─ github
   │  │        └─ tahanima
   │  │           ├─ config
   │  │           │  ├─ Configuration.java
   │  │           │  └─ ConfigurationManager.java
   │  │           ├─ factory
   │  │           │  ├─ BasePageFactory.java
   │  │           │  └─ BrowserFactory.java
   │  │           ├─ testData
   │  │           │  ├─ BaseTestData.java
   │  │           │  ├─ LoginTestData.java
   │  │           │  └─ ProductsTestData.java
   │  │           ├─ report
   │  │           │  └─ AllureManager.java
   │  │           ├─ ui
   │  │           │  ├─ component
   │  │           │  │  ├─ BaseComponent.java
   │  │           │  │  ├─ Header.java
   │  │           │  │  └─ SideNavMenu.java
   │  │           │  └─ page
   │  │           │     ├─ BasePage.java
   │  │           │     ├─ LoginPage.java
   │  │           │     └─ ProductsPage.java
   │  │           └─ util
   │  │              └─ BrowserManager.java
   │  └─ resources
   │     ├─ allure.properties
   │     └─ config.properties
   └─ test
      ├─ java
      │  └─ io
      │     └─ github
      │        └─ tahanima
      │           ├─ annotation
      │           │  ├─ Regression.java
      │           │  ├─ Smoke.java
      │           │  ├─ TestDataSource.java
      │           │  └─ Validation.java
      │           ├─ e2e
      │           │  ├─ BaseTest.java
      │           │  ├─ LoginTest.java
      │           │  └─ ProductsTest.java
      │           └─ util
      │              ├─ TestDataArgumentsProvider.java
      │              └─ CsvLoader.java
      └─ resources
         ├─ junit-platform.properties
         └─ testdata
            ├─ login.csv
            └─ products.csv
```
---

## Basic Usage

- ### Configuration
  The project uses a [*config.properties*](./src/main/resources/config.properties) file to manage global configurations such as browser type and base url.
  
  1. To add a new property, register a new entry in this file.
      ```
      key=value
      ```
    
      Then, add a method in the [*Configuration*](./src/main/java/io/github/tahanima/config/Configuration.java) interface in the below format.
      ```java
      @Key("key")
      dataType key();
      ```
    
      For example, let's say I want to add a new property named `context` with the value `dev`. In the `config.properties` file, I'll add:
      ```
      context=dev
      ```
    
      In the `Configuration` interface, I'll add:
      ```java
      @Key("context")
      String context();
      ```
    
      To use your newly created property, you need to use the below import statement.
      ```java
      import static io.github.tahanima.config.ConfigurationManager.config;
      ```
    
      Then, you can call `config().key()` to retrieve the value of your newly created property. For the example I've provided, I need to call `config().context()`.

  2. You can supply the properties present in the `config.properties` file as system properties in your test via gradle.
      ```bash
      ./gradlew test -Dkey1=value1 -Dkey2=value2
      ```
      
- ### Test Data
  The project uses *csv* file to store test data and [*univocity-parsers*](https://github.com/uniVocity/univocity-parsers) to retrieve the data and map it to a Java bean.

  To add configurations for new test data, add a new Java bean in the [*testdata*](./src/main/java/io/github/tahanima/testdata) package. For example, let's say I want to add test data for a `User` with the attributes `First Name` and `Last Name`. The code for this is as follows:

   ```java
   package io.github.tahanima.testdata;

   import com.univocity.parsers.annotations.Parsed;

   import lombok.Getter;
   import lombok.ToString;

   @Getter
   @ToString(callSuper = true)
   public class UserTestData extends BaseTestData {

       @Parsed(field = "First Name", defaultNullRead = "")
       private String firstName;

       @Parsed(field = "Last Name", defaultNullRead = "")
       private String lastName;
   }
   ```
   Note that the class extends from BaseTestData and thus, inherits the attribute `Test Case ID`.

   Now, in the [*testdata*](./src/test/resources/testdata) folder you can add a csv file `user.csv` for `User` with the below contents and use it in your tests.
   ```
   Test Case ID,First Name,Last Name
   TC-1,Tahanima,Chowdhury
   ```
   For reference, check [this](./src/main/java/io/github/tahanima/testdata/LoginTestData.java), [this](./src/test/resources/testdata/login.csv) and [this](./src/test/java/io/github/tahanima/e2e/LoginTest.java).

- ### Page Objects and Page Component Objects
  The project uses [*Page Objects* and *Page Component Objects*](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) to capture the relevant behaviors of a web page. Check the [*ui*](./src/main/java/io/github/tahanima/ui) package for reference.

- ### Tests
  The project uses *JUnit 5* as the test runner. Check [this implementation](./src/test/java/io/github/tahanima/e2e/LoginTest.java) for reference.
---

## AI-Augmented Workflow

This project is optimized for use with **Gemini CLI**, providing a specialized AI agent that understands the architecture.

### Prerequisites
- Gemini CLI: -[Installed and authenticated](https://github.com/google-gemini/gemini-cli) via your Google Account.
- Project Root: Ensure you run the CLI from the project root to allow it to parse the .gemini/ configuration.

### Project Context (GEMINI.md)
The project includes a `GEMINI.md` file which serves as the AI's "Source of Truth." This ensures that any code generated or reviewed by the AI adheres to:
- **BasePageFactory** initialization patterns.
- **Layer Separation** (Pages vs. TestData POJOs vs. E2E Tests).
- **Playwright Best Practices** (Accessibility-first locators over brittle XPaths).

### Specialized AI Skills
The project contains custom **Skills** in the `.gemini/skills/` directory to automate common QA tasks:

- **`code-reviewer`**: Automatically audits new Page Objects, POJOs, and Tests to ensure they follow the project's layer separation and naming conventions.
- **`healing-agent`**: Diagnoses test failures (like `TimeoutError`) and suggests robust, accessibility-first Playwright locators (e.g., `getByRole`) to replace brittle XPaths.

### How to Use
Simply run `gemini` in the project root. You can trigger skills manually or let the AI suggest them:
```bash
# To perform a code review
gemini "Review this new test class: @src/test/java/io/github/tahanima/e2e/NewFeatureTest.java"

# To fix a failing locator
gemini "The login button locator is failing. Use the healing-agent to suggest a fix for @src/main/java/io/github/tahanima/ui/page/LoginPage.java"
