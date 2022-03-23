package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "BASIC_ALBUM")
@DiscriminatorValue("A")
public class Album  extends Item{

    private String artist;


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
