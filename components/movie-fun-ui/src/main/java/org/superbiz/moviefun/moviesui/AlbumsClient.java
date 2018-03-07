package org.superbiz.moviefun.moviesui;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class AlbumsClient {
    private final RestOperations restOperations;
    private final String albumsURL;

    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };

    public AlbumsClient(String albumsURL, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.albumsURL = albumsURL;
    }

    public void addAlbum(AlbumInfo album) {
        restOperations.postForObject(albumsURL, album, AlbumInfo.class);
    }

    public AlbumInfo find(long id) {
        return restOperations.getForObject(albumsURL.concat("/").concat(Long.toString(id)), AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsURL, GET, null, albumListType).getBody();
    }

    public void deleteAlbum(AlbumInfo album) {
        restOperations.delete(albumsURL, album, AlbumInfo.class);

    }

    public void updateAlbum(AlbumInfo album) {
        restOperations.put(albumsURL, album);

    }
}
