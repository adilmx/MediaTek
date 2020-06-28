package beans;

import java.sql.Date;

public class Journale {
    private int id_journale  ;
    private int id_pro ;
    private Date date_journale  ;
    private  int   stock ;

    public Journale() {
    }

    public int getId_journale() {
        return id_journale;
    }

    public void setId_journale(int id_journale) {
        this.id_journale = id_journale;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public Date getDate_journale() {
        return date_journale;
    }

    public void setDate_journale(Date date_journale) {
        this.date_journale = date_journale;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Journale(int id_journale, int id_pro, Date date_journale, int stock) {
        this.id_journale = id_journale;
        this.id_pro = id_pro;
        this.date_journale = date_journale;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Journale{" + "id_journale=" + id_journale + ", id_pro=" + id_pro + ", date_journale=" + date_journale + ", stock=" + stock + '}';
    }

    
}
