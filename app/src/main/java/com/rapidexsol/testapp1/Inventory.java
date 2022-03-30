package com.rapidexsol.testapp1;

public class Inventory {
    public int id;
    public String name;
    public String barcode;
    public int quantity,multiplier;

    //constructor


    public Inventory(int id, String name, String barcode, int quantity, int multiplier) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.quantity = quantity;
        this.multiplier = multiplier;
    }


    public Inventory() {
    }

// toSting necessary to print all the contents of a class



//    @Override
//    public String toString() {
//        return "Inventory{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", barcode='" + barcode + '\'' +
//                ", quantity=" + quantity +
//                ", multiplier=" + multiplier +
//                '}';
//    }


    @Override
    public String toString() {
        return "" +
                "ID: " + id +
                ",  Name: " + name +
                ",  Barcode: " + barcode +
                ",  Quantity: " + quantity +
                ",  Multiplier: " + multiplier;

    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
