//package com.MattyDubs.MovieProject;
//
//import com.MattyDubs.MovieProject.dao.MovieDAO;
//import com.MattyDubs.MovieProject.entity.CustomUser;
//import com.MattyDubs.MovieProject.entity.Movie;
//import com.MattyDubs.MovieProject.service.MovieService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataJpaTest
//public class MovieTest {
//
//    @Autowired
//    MovieDAO movieService;
//
//    @Autowired
//    TestEntityManager testEntityManager;
//
//
//
//    @Test
//    public void testSave(){
//        CustomUser user = new CustomUser("Matt");
//        testEntityManager.persist(user);
//        Movie movie = new Movie();
//        movie.setTitle("Matrix");
//        movie.setYear("1999");
//        movie.setUser(user);
//        testEntityManager.persist(movie);
//
//        List<Movie> movies = movieService.findByTitleYear("Matrix", "1999");
//        assertThat(movies.size()).isEqualTo(1);
//        assertThat(movies.get(0).getTitle()).isEqualTo("Matrix");
//    }
//}
