package org.superbiz.moviefun.movies;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    private final MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }


    @DeleteMapping("/{moviedId}")
    public void deleteMovie(@PathVariable long movieId) {
        moviesRepository.deleteMovieId(movieId);
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie) {

        moviesRepository.addMovie(movie);
    }

    @GetMapping
    public List<Movie> find(
            @RequestParam(name = "field", required = false) String field,
            @RequestParam(name = "searchTerm", required = false) String searchTerm,
            @RequestParam(name = "firstResult", required = false) Integer firstResult,
            @RequestParam(name = "maxResults", required = false) Integer maxResults) {
        System.out.println("test");
        if (field != null && searchTerm !=null ) {
            return moviesRepository.findRange(field, searchTerm, firstResult, maxResults);
        } else if (firstResult != null && maxResults != null){
            return moviesRepository.findAll(firstResult, maxResults);
        } else {
            return moviesRepository.getMovies();
        }
    }

    @GetMapping("/count")
    public  int count(
            @RequestParam(name = "field", required = false) String field,
            @RequestParam(name = "searchTerm", required = false) String searchTerm){
        if (field != null && searchTerm !=null ){
            return moviesRepository.count(field, searchTerm);
        } else {
            return moviesRepository.countAll();
        }
    }
}
