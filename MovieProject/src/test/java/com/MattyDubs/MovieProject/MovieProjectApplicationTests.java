package com.MattyDubs.MovieProject;

import com.MattyDubs.MovieProject.data.TopMovieModel;
import com.MattyDubs.MovieProject.entity.Movie;
import com.MattyDubs.MovieProject.entity.Post;
import com.MattyDubs.MovieProject.util.MovieUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
class MovieProjectApplicationTests {


	@Test
	void contextLoads() {
	}


	@Test
	public void testTopMovieCopy(){
		MovieUtil movieUtil = new MovieUtil();
		TopMovieModel topMovie = new TopMovieModel();
		topMovie.setYear("1999");
		topMovie.setTitle("Matrix");

		Movie movie = movieUtil.mapFromTopMovieModel(topMovie);
		assertEquals("1999", movie.getYear());
		assertEquals("Matrix", movie.getTitle());
	}

	// post upvotes was NOT thread safe.
	@Test
	public void testPostRaceCondition() throws InterruptedException {
		Post post = new Post("dummy", "dummy");
		final int THREAD_COUNT = 10;
		final int ITERATIONS = 2000;
		Thread[] threads = new Thread[THREAD_COUNT];

		for(int i = 0; i < THREAD_COUNT; i++) {
			threads[i] = new Thread(() -> {
				for(int j = 0; j < ITERATIONS; j++){
					post.addVote();
				}
			});
		}
		for(Thread thread : threads){
			thread.start();
		}
		for(Thread thread : threads){
			thread.join();
		}
		assertEquals(THREAD_COUNT * ITERATIONS, post.getUpvotes());
	}

}
