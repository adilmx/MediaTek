package beans;

public class Type {
    private int id_type;
    private String lib_type ;

    public Type() {
    }

    public Type(int id_type, String lib_type) {
        this.id_type = id_type;
        this.lib_type = lib_type;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getLib_type() {
        return lib_type;
    }

    public void setLib_type(String lib_type) {
        this.lib_type = lib_type;
    }

    @Override
    public String toString() {
        return "Type{" + "id_type=" + id_type + ", lib_type=" + lib_type + '}';
    }
    
    
}
