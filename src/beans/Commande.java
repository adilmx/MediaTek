package beans;


public class Commande {
    
    private int id_pro;
    private int id_fact;
    private int qte_com ;

    public Commande() {
    }

    public Commande(int id_pro, int id_fact, int qte_com) {
        this.id_pro = id_pro;
        this.id_fact = id_fact;
        this.qte_com = qte_com;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public int getId_fact() {
        return id_fact;
    }

    public void setId_fact(int id_fact) {
        this.id_fact = id_fact;
    }

    public int getQte_com() {
        return qte_com;
    }

    public void setQte_com(int qte_com) {
        this.qte_com = qte_com;
    }

    @Override
    public String toString() {
        return "Commande{" + "id_pro=" + id_pro + ", id_fact=" + id_fact + ", qte_com=" + qte_com + '}';
    }
    
    
}
