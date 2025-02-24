# Dependency Injection and Loose Coupling in Java

## Overview
This project demonstrates dependency injection and loose coupling in Java using three approaches:
1. **Static Instantiation**
2. **Dynamic Instantiation**
3. **Using the Spring Framework**
   - XML-based configuration
   - Annotation-based configuration

## Part 1: Implementation Steps

### 1. Create the `IDao` Interface
Define an interface `IDao` with a method `getData()`.

```java
public interface IDao {
    double getData();
}
```

### 2. Implement `IDao`
Create a class `DaoImpl` that implements `IDao`.

```java
@Component("dao")
public class IDaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Version base de donnée");
        return 32;
    }
}
```

### 3. Create the `IMetier` Interface
Define an interface `IMetier` with a method `calcul()`.

```java
public interface IMetier {
    public double calcul();
}
```

### 4. Implement `IMetier` with Loose Coupling
Create a class `MetierImpl` that depends on `IDao` but uses dependency injection.

```java
@Component
public class IMetierImpl implements IMetier {


    @Autowired
    public IDao dao;
    @Override
    public double calcul() {
        double valeur = dao.getData();
        double result = valeur * 12;
        return result;
    }

    public IMetierImpl(IDao dao) {
        this.dao = dao;
    }

    /*
        pour injecter dans la variable dao
        un objet de la classe qui impléments
        l'interface IDao
    */
    public void setDao(IDao dao) {

        this.dao = dao;
    }
}
```

## Part 2: Dependency Injection Methods

### a. Static Instantiation
Manually create instances and inject the dependency in the `main` method.

```java
public class PresStatic {
    public static void main(String[] args) {

        /*
        Injection de dépendance par
        instanciation statique ==> new
         */
        IDao dao = new IDaoImpl();
        IMetierImpl metier1 = new IMetierImpl(dao); // injection via le constructeur

//        IMetierImpl metier = new IMetierImpl();
//        metier.setDao(dao); // Injection via le setter

        System.out.println("Resultats = "+metier1.calcul());
    }
}
```

### b. Dynamic Instantiation
Use `Class.forName()` to instantiate objects dynamically.

```java
public class PresDynamique {
    public static void main(String[] args) throws Exception {

        try {
            Scanner sc = new Scanner(new File("Config.txt"));
            /*
            Injection de dépendance par
            instanciation dynamique
            */

            // les trois premiére ligne équivalente à "IDao dao = new IDaoImpl();"
            String daoClassName = sc.nextLine();
            Class cDao = Class.forName(daoClassName);
            IDao dao = (IDao) cDao.getConstructor().newInstance();
            System.out.println("Résultat = "+dao.getData());

            // les trois premiére ligne équivalente à "IMetierImpl metier1 = new IMetierImpl(dao);"
            String metierClassName = sc.nextLine();
            Class cMetier = Class.forName(metierClassName);
            // instanciation via le constructeur
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

            System.out.println("Résultats = "+metier.calcul());

            // instanciation via le setter équivalente à "metier.setDao(dao);"
            //Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
            //setDao.invoke(metier, dao);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```

Configuration file `config.txt`:
```
dao.IDaoImpl
metier.IMetierImpl
```

### c. Using Spring Framework

#### XML-Based Configuration
Spring configuration file `spring-config.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="d" class="dao.IDaoImpl"></bean>
    <bean id="m" class="metier.IMetierImpl">
        <property name="dao" ref="d"></property>
    </bean>
</beans>
```

Main class:

```java
public class PresSpringXml {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("Résultats = "+metier.calcul());
    }
}
```

#### Annotation-Based Configuration
Update `DaoImpl` and `MetierImpl` with Spring annotations 

Main class:

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainSpringAnnotations {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("package.containing.classes");
        IMetier metier = context.getBean(IMetier.class);
        System.out.println("Result: " + metier.calcul());
    }
}
```

## Technologies Used
- Java
- Spring Framework
- XML Configuration
- Dependency Injection

## Author
- **Mohamed Amine Ghazoui**

