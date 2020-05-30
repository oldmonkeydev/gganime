package blackmafia.gogoanime;

import java.util.ArrayList;

public class episode_class {

    private String name;
    private String epLink;

    private String AnimeName;

    public episode_class(){

    }
    public episode_class(String name, String epLink, String AnimeName){
        this.AnimeName = AnimeName;
        this.name = name;
        this.epLink = epLink;

    }

    public String getAnimeName() {
        return AnimeName;
    }

    public void setAnimeName(String animeName) {
        AnimeName = animeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEpLink() {
        return epLink;
    }

    public void setEpLink(String epLink) {
        this.epLink = epLink;
    }


}
