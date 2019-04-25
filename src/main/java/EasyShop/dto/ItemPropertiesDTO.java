package EasyShop.dto;

public class ItemPropertiesDTO {

    private int id;
    private int itemId;
    private String name;
    private String description;

    public ItemPropertiesDTO() {
    }

    public ItemPropertiesDTO(int id, int itemId, String name, String description) {
        this.id = id;
        this.itemId = itemId;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
