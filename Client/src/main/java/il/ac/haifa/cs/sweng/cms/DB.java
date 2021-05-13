package il.ac.haifa.cs.sweng.cms;
import java.util.List;
import java.util.Random;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import static java.lang.Math.round;

public class DB {
    private static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
// Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Movie.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void generate() throws Exception {
        String titanicSum="A story about a ship.";
        String titanicPoster = "https://upload.wikimedia.org/wikipedia/en/1/19/Titanic_%28Official_Film_Poster%29.png";
        Movie titanic=new Movie("Titanic", "טיטאניק",194,0, titanicPoster);
        session.save(titanic);


        String ShrekSum="A story about a swamp monster.";
        String ShrekPoster="https://images.moviesanywhere.com/5948f139cd669fb5984d2c782e7678be/99cedd1f-ae78-4026-a3e8-b79840b71cbc.jpg";
        Movie shrek=new Movie("Shrek", "שרק",90,0, ShrekPoster);
        session.save(shrek);

        String interstellarSum="A story about a airship.";
        String interstellarPoster="https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg";
        Movie interstellar=new Movie("interstellar", "בין הכוכבים",169,13, interstellarPoster);
        session.save(interstellar);
        session.flush();

//        String titanicSum="A story about a ship.";
//    String titanicPoster="../Images/Titanic.jpg";
//    Movie titanic=new Movie(1,"Titanic", "טיטאניק","James Cameron",titanicSum, titanicPoster);
////    titanic.addToCast("Leonardo DiCaprio");
////    titanic.addToCast("Kate Winslet");
//
//    String interstellarSum="A story about a airship.";
//    String interstellarPoster="../Images/interstellar.jpg";
//    Movie interstellar=new Movie(2,"interstellar", "בין הכוכבים","Christopher Nolan",interstellarSum, interstellarPoster);
////    titanic.addToCast("Matthew McConaughey");
////    titanic.addToCast("Anne Hathaway");
//
//
//    String ShrekSum="A story about a swamp monster.";
//    String ShrekPoster="../Images/Shrek.jpg";
//    Movie shrek=new Movie(3,"Shrek", "שרק","Andrew Adamson",ShrekSum, ShrekPoster);
////    titanic.addToCast("Mike Myers");
////    titanic.addToCast("Eddie Murphy");
//
//    movies.add(this.titanic);
//    movies.add(this.interstellar);
//    movies.add(this.shrek);

    }

    private static void printAllDB() throws Exception{
        List<Movie> movies=getAllMovies();

        System.out.println("\nMovies:");
        for (Movie movie:movies) {
            System.out.format("Id: %d,\tEnglish name: %s,\tHebrew name: %s,\tlength %d,\tAge Restriction %d,\tPoster Url %s",
                    movie.getId(),
                    movie.getEngName(),
                    movie.getHebName(),
                    movie.getLength(),
                    movie.getAgeRestriction(),
                    movie.getPosterUrl()
            );

            System.out.print("\n");
        }

    }

    public static List<Movie> getAllMovies() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
        query.from(Movie.class);
        List<Movie> data = session.createQuery(query).getResultList();
        return data;
    }

    public static <Movie> List<Movie> getAll(Class<Movie> object) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Movie> criteriaQuery = builder.createQuery(object);
        Root<Movie> rootEntry = criteriaQuery.from(object);
        CriteriaQuery<Movie> allCriteriaQuery = criteriaQuery.select(rootEntry);

        TypedQuery<Movie> allQuery = session.createQuery(allCriteriaQuery);
        return allQuery.getResultList();
    }

    public static void main(String[] args) {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();
            generate();
            printAllDB();
            session.getTransaction().commit(); // Save everything.
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occured, changes have been rolled back.");
            exception.printStackTrace();
        } finally {
            if (session!=null)
                session.close();

        }
    }
}
