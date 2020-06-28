
package beans;

import java.sql.Date;


public class Facture {
   
    private int id_fact ; 
    private int id_clt ;
    private Date date_fact ;

    public Facture() {
    }

    public Facture(int id_fact, int id_clt, Date date_fact) {
        this.id_fact = id_fact;
        this.id_clt = id_clt;
        this.date_fact = date_fact;
    }

    public int getId_fact() {
        return id_fact;
    }

    public void setId_fact(int id_fact) {
        this.id_fact = id_fact;
    }

    public int getId_clt() {
        return id_clt;
    }

    public void setId_clt(int id_clt) {
        this.id_clt = id_clt;
    }

    public Date getDate_fact() {
        return date_fact;
    }

    public void setDate_fact(Date date_fact) {
        this.date_fact = date_fact;
    }

    @Override
    public String toString() {
        return "Facture{" + "id_fact=" + id_fact + ", id_clt=" + id_clt + ", date_fact=" + date_fact + '}';
    }
   
   
}
