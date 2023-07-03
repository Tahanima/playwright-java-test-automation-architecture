# Playwright Java Test Automation Architecture

Ready-to-use UI Test Automation Architecture using Java and Playwright.

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

allure serve allure-results -h localhost
```

## Languages and Frameworks

The project uses the following:
- *[Java 11](https://openjdk.java.net/projects/jdk/11/)* as the programming language.
- *[Playwright](https://playwright.dev/)* as the web browser automation framework using the Java binding.
- *[Univocity Parsers](https://www.univocity.com/pages/univocity_parsers_tutorial)* to parse and handle CSV files.
- *[JUnit 5](https://junit.org/junit5/)* as the testing framework.
- *[Lombok](https://projectlombok.org/)* to generate getters.
- *[Owner](http://owner.aeonbits.org/)* to minimize the code to handle properties file.
- *[Allure Report](https://qameta.io/allure-report/)* as the test reporting strategy.
- *[Gradle](https://gradle.org/)* as the Java build tool.
- *[IntelliJ IDEA](https://www.jetbrains.com/idea/)* as the IDE.

## Project Structure

The project is structured as follows:

```bash
📦 playwright-java-test-automation-architecture
├─ .github
│  ├─ FUNDING.yml
│  └─ workflows
│     └─ test-execution.yml
├─ .gitignore
├─ LICENSE
├─ README.md
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
   │  └─ java
   │     └─ io
   │        └─ github
   │           └─ tahanima
   │              ├─ config
   │              │  ├─ Configuration.java
   │              │  └─ ConfigurationManager.java
   │              ├─ data
   │              │  ├─ BaseData.java
   │              │  ├─ LoginData.java
   │              │  └─ ProductsData.java
   │              ├─ factory
   │              │  ├─ BasePageFactory.java
   │              │  └─ BrowserFactory.java
   │              ├─ ui
   │              │  ├─ component
   │              │  │  ├─ BaseComponent.java
   │              │  │  ├─ Header.java
   │              │  │  └─ SideNavMenu.java
   │              │  └─ page
   │              │     ├─ BasePage.java
   │              │     ├─ LoginPage.java
   │              │     └─ ProductsPage.java
   │              └─ util
   │                 └─ BrowserManager.java
   └─ test
      ├─ java
      │  └─ io
      │     └─ github
      │        └─ tahanima
      │           ├─ annotation
      │           │  ├─ DataSource.java
      │           │  ├─ Regression.java
      │           │  ├─ Smoke.java
      │           │  └─ Validation.java
      │           ├─ e2e
      │           │  ├─ BaseE2ETest.java
      │           │  ├─ LoginE2ETest.java
      │           │  └─ ProductsE2ETest.java
      │           └─ util
      │              ├─ CsvToPOJOMapper.java
      │              └─ DataArgumentsProvider.java
      └─ resources
         ├─ allure.properties
         ├─ config.properties
         ├─ junit-platform.properties
         └─ testdata
            ├─ login.csv
            └─ products.csv
```

## Basic Usage

- ### Configuration
  The project uses a [*config.properties*](./src/test/resources/config.properties) file to manage global configurations such as browser type and base url.
  
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

  To add configurations for new test data, add a new Java bean in the [*data*](./src/main/java/io/github/tahanima/data) package. For example, let's say I want to add test data for a `User` with the attributes `First Name` and `Last Name`. The code for this is as follows:
     
   ```java
   package io.github.tahanima.data;

   import com.univocity.parsers.annotations.Parsed;

   import lombok.Getter;
   import lombok.ToString;

   @Getter
   @ToString(callSuper = true)
   public class UserData extends BaseData {

       @Parsed(field = "First Name", defaultNullRead = "")
       private String firstName;

       @Parsed(field = "Last Name", defaultNullRead = "")
       private String lastName;
   }
   ```
   Note that the class extends from BaseData and thus, inherits the attribute `Test Case ID`.

   Now, in the [*testdata*](./src/test/resources/testdata) folder you can add a csv file `user.csv` for `User` with the below contents and use it in your tests.
   ```
   Test Case ID,First Name,Last Name
   TC-1,Tahanima,Chowdhury
   ```
   For reference, check [this](./src/main/java/io/github/tahanima/data/LoginData.java), [this](./src/test/resources/testdata/login.csv) and [this](./src/test/java/io/github/tahanima/e2e/LoginE2ETest.java).

- ### Page Objects and Page Component Objects
  The project uses [*Page Objects* and *Page Component Objects*](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) to capture the relevant behaviors of a web page. Check the [*ui*](./src/main/java/io/github/tahanima/ui) package for reference.

- ### Tests
  The project uses *TestNG* as the test runner. Check [this implementation](./src/test/java/io/github/tahanima/e2e/LoginE2ETest.java) for reference.
