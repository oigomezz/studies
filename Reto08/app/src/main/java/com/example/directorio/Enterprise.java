package com.example.directorio;

public class Enterprise {

    public long id;
    public String name;
    public String url;
    public String phone;
    public String email;
    public String products;
    public String classification;

    public Enterprise(long id, String name, String url, String phone, String email, String products, String classification) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.phone = phone;
        this.email = email;
        this.products = products;
        this.classification = classification;
    }

    public Enterprise() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public String toString() {

        //affichage des classification
        String[] tabClassif = classification.split(";");
        String stringClassif ="";
        for(int i =0; i<tabClassif.length;i++)
            stringClassif+=tabClassif[i] + " | ";

        //affichage des produits et services
        String[] tabProducts = products.split(" ");
        String stringProducts = "";
        for(int j=0; j<tabProducts.length;j++)
            stringProducts+="     - " + tabProducts[j] + "\n";
        return "Empresa " +
                id + "\n" +
                "  Nombre : " + name + "\n" +
                "  Url : " + url + "\n" +
                "  Teléfono : " + phone + "\n" +
                "  Email : " + email + "\n" +
                "  Productos y servicios : \n" + stringProducts +
                "  Clasificacion : | " + stringClassif;
    }
}
