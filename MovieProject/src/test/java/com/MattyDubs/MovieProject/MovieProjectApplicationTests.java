package com.MattyDubs.MovieProject;

import com.MattyDubs.MovieProject.data.TopMovieModel;
import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.util.MovieUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MovieProjectApplicationTests {

    @Autowired
    private MovieUtil movieUtil;

	@Test
	void contextLoads() {
	}

//	@Test
//	public void testCopy(){
//		Movie movie = new Movie();
//		movie.setYear("1999");
//		movie.setTitle("Matrix");
//
//		Movie copiedMovie = movieUtil.copyMovie(movie);
//		assertEquals("Matrix", copiedMovie.getTitle());
//		assertEquals("1999", copiedMovie.getYear());
//	}

	@Test
	public void testTopMovieCopy(){
		TopMovieModel topMovie = new TopMovieModel();
		topMovie.setYear("1999");
		topMovie.setTitle("Matrix");

		Movie movie = movieUtil.mapFromTopMovieModel(topMovie);
		assertEquals("1999", movie.getYear());
		assertEquals("Matrix", movie.getTitle());
	}

}
