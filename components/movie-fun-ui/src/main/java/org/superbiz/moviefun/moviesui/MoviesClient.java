package org.superbiz.moviefun.moviesui;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpMethod.GET;

import java.util.List;

public class MoviesClient {
    private final RestOperations restOperations;
    private final String moviesURL;

    private static ParameterizedTypeReference<List<MovieInfo>> movieListType = new ParameterizedTypeReference<List<MovieInfo>>() {
    };

    public MoviesClient(String moviesURL, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.moviesURL = moviesURL;
    }


    /*public MovieInfo find(Long id) {
        //Call movie-service GET request with id
        MovieInfo mi = restOperations.getForObject(moviesURL.concat(Long.toString(id)), MovieInfo.class);
        return mi;
    }
*/

    public void addMovie(MovieInfo movie) {
        //Call movie-service add POST request
        System.out.println(moviesURL);
        restOperations.postForObject(moviesURL, movie, MovieInfo.class);
    }

/*
    public void updateMovie(MovieInfo movie) {
        //Call movie-service update PUT request
        restOperations.put(moviesURL, movie, MovieInfo.class);
    }


    public void deleteMovie(MovieInfo movie) {
        //Call movie-service DELETE request
        restOperations.delete(moviesURL, movie, MovieInfo.class);
    }
*/

    public void deleteMovieId(long id) {
        //Call movie-service DELETE request
        restOperations.delete(moviesURL.concat("/").concat(Long.toString(id)));

    }

    public List<MovieInfo> getMovies() {
        //Call movie-service GET request no id
        //return restOperations.exchange(moviesUrl, GET, null, movieListType).getBody(); --Pivotal solution
        //Not sure how excheange works


        List<MovieInfo> mi = restOperations.getForObject(moviesURL, List.class);
        return mi;
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {
        //Call movie-service GET request with record count range
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesURL)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).getBody();
    }

    public int countAll() {
        return restOperations.getForObject(moviesURL.concat("/count"), Integer.class);
    }

    public int count(String field, String searchTerm) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesURL.concat("/count"))
                .queryParam("field", field)
                .queryParam("key", searchTerm);

        return restOperations.getForObject(builder.toUriString(), Integer.class);
    }


    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(moviesURL)
                .queryParam("field", field)
                .queryParam("key", searchTerm)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults);

        return restOperations.exchange(builder.toUriString(), GET, null, movieListType).getBody();
    }
}