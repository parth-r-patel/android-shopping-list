package mobile.project.ogshopper;

/**
 * Created by Ryan Lildhar on 11/25/2015.
 */

// Simple structure for Item data in user list
public class Item {
    private long id;
    private String name;

    // check and price are added for future implementation and are provided for futureproofing
    private int check ;
    private String price;

    public Item(String name, int check, String price) {
        this.id = -1; // must be updated after the object is created
        this.name = name;
        this.check = check;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCheck() {
        return check;
    }

    public String getPrice() {
        return price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
