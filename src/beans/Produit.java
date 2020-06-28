package beans;


public class Produit {
    private int id_pro ; //id de produit
    private String lib_pro; //nom de produit
    private double prix_unit_pro; //prix unitaire de produit
    private int qte_dispo ; //quantite dspoonible de produit
    private int type_pro ; //type de produit
    private String desc_pro; //description de produit
    private String pic_src_pro; //image src de produit

    public Produit() {
    }

    public Produit(int id_pro, String lib_pro, double prix_unit_pro, int qte_dispo, int type_pro, String desc_pro, String pic_src_pro) {
        this.id_pro = id_pro;
        this.lib_pro = lib_pro;
        this.prix_unit_pro = prix_unit_pro;
        this.qte_dispo = qte_dispo;
        this.type_pro = type_pro;
        this.desc_pro = desc_pro;
        this.pic_src_pro = pic_src_pro;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getLib_pro() {
        return lib_pro;
    }

    public void setLib_pro(String lib_pro) {
        this.lib_pro = lib_pro;
    }

    public double getPrix_unit_pro() {
        return prix_unit_pro;
    }

    public void setPrix_unit_pro(double prix_unit_pro) {
        this.prix_unit_pro = prix_unit_pro;
    }

    public int getQte_dispo() {
        return qte_dispo;
    }

    public void setQte_dispo(int qte_dispo) {
        this.qte_dispo = qte_dispo;
    }

    public int getType_pro() {
        return type_pro;
    }

    public void setType_pro(int type_pro) {
        this.type_pro = type_pro;
    }

    public String getDesc_pro() {
        return desc_pro;
    }

    public void setDesc_pro(String desc_pro) {
        this.desc_pro = desc_pro;
    }

    public String getPic_src_pro() {
        return pic_src_pro;
    }

    public void setPic_src_pro(String pic_src_pro) {
        this.pic_src_pro = pic_src_pro;
    }

    @Override
    public String toString() {
        return "Produit{" + "id_pro=" + id_pro + ", lib_pro=" + lib_pro + ", prix_unit_pro=" + prix_unit_pro + ", qte_dispo=" + qte_dispo + ", type_pro=" + type_pro + ", desc_pro=" + desc_pro + ", pic_src_pro=" + pic_src_pro + '}';
    }

    
}
