# Coding style guide
## Naming conventions
Standard Java conventions. It is used in all Java libraries so we should follow it.
| Type      | Rule                 | Example                         |
| --------- | -------------------- | ------------------------------- |
| Classes   | UpperCamelCase       | `public class MovieBrowser { }` |
| Methods   | lowerCamelCase       | `boolean buyTicket() { }`       |
| Variables | lowerCamelCase       | `int ticketNumber;`             |
| Constants | SCREAMING_SNAKE_CASE | `final int MAX_TICKETS = 100;`  |

## Documentation
Java has a tool called [Javadoc](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html) which can automatically create an API document for your class (like [this one](https://developer.android.com/reference/android/os/Message)). This may come in handy when eventually we have tens of classes in our project and you need to interact with some class another one of us wrote. It would also be a nice thing to present in our project.

And we have to document anyway, so why not.

For it to work you need to follow these rules when documenting:

### Comment blocks
Javadoc only works on this kind of comments (starts with 2 *'s):
```java
/**
 * Comment block
 * @tag description
 */ 
```
Tags are created automatically in IntelliJ/Eclipse, you just fill the description.
### Classes
Brief description and @author tag:
```java
/**
 * Defines a ticket for a movie.
 *
 * @author Yuval Razieli
 */
public class Ticket {
    ...
}
```

### Methods
Brief description, and @param, @return, @throws depending on the method:
```java
/**
 * Creates a list of movies from the given movie file.
 *
 * @param movieList A file with movies.
 * @return List<Movie> List of movies from the file.
 * @throws FileNotFoundException If file was not found.
 */
 public List<Movie> getMovieList(File movieList) throws FileNotFoundException {
     ...
 }
```