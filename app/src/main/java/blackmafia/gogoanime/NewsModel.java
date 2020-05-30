package blackmafia.gogoanime;

public class NewsModel {

    private String Title;
    private String info;
    private String link;
    private String date;

    public NewsModel(String title, String info, String link, String date) {
        Title = title;
        this.info = info;
        this.link = link;
        this.date = date;
    }

    public NewsModel() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
