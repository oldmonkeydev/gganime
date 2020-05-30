package blackmafia.gogoanime;

public class ParseItem {
    private String imgUrl;
    private String title;
    private String episode;
    private String urlAnime;


    public ParseItem() {
    }

    public ParseItem(String imgUrl, String title, String detailUrl, String animeUrl) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.episode = detailUrl;
        this.urlAnime = animeUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailUrl() {
        return episode;
    }

    public void setDetailUrl(String detailUrl) {
        this.episode = detailUrl;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getUrlAnime() {
        return urlAnime;
    }

    public void setUrlAnime(String urlAnime) {
        this.urlAnime = urlAnime;
    }
}
