package blackmafia.gogoanime;

public class FavItem {

    int id;
    String AnimeName;
    String favStatus;
    String imgUrl;
    String linkSummary;

    public FavItem() {
    }

    public FavItem(int id, String animeName, String favStatus,String imgUrl, String linkSummary) {
        this.id = id;
        this.AnimeName = animeName;
        this.favStatus = favStatus;
        this.imgUrl = imgUrl;
        this.linkSummary = linkSummary;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnimeName() {
        return AnimeName;
    }

    public void setAnimeName(String animeName) {
        AnimeName = animeName;
    }

    public String getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(String favStatus) {
        this.favStatus = favStatus;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLinkSummary() {
        return linkSummary;
    }

    public void setLinkSummary(String linkSummary) {
        this.linkSummary = linkSummary;
    }
}
