import org.bson.types.ObjectId;

public class User {
    private ObjectId id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public User(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
        this.id = null;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
