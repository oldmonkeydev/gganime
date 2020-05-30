package blackmafia.gogoanime;

public class model_categories_list {

    private String name;
    private String imgLink;

    public model_categories_list(String name, String imgLink) {
        this.name = name;
        this.imgLink = imgLink;
    }

    public model_categories_list() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
