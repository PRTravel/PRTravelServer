package data;

public class Attraction {

    private int id;
    private String name;
    private String location;
    private String description;
    private String image;
    //private Comment[] comments;
    //private Service[] services;

    public Attraction(int id, String name, String location, String description, String image){
        this.id = id;
        this.name = name;
        this.location = location;
        this.description = description;
        this.image = image;
    }

    public String toString(){
        return "{\"id\":\"" + getId() + "\",\"name\":\"" + getName() + "\"}";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
