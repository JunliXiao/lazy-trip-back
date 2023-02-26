package friend.model;

public class Chatroom {

    private Integer id;
    private String name;
    private Long createdAtUnix;

    public Chatroom() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedAtUnix() {
        return createdAtUnix;
    }

    public void setCreatedAtUnix(Long createdAtUnix) {
        this.createdAtUnix = createdAtUnix;
    }
}
