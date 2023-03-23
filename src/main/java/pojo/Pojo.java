package pojo;

import java.io.Serial;
import java.io.Serializable;

public class Pojo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private int id;

    public Pojo() {
        this.id = -1;
    }

    public Pojo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "pojo.Pojo{" +
                "id=" + id +
                '}';
    }
}
