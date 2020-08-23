package info.androidhive.bottomnavigation;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class Subject {

    public String Name ;
    public String Price;
    public int ID;
    public boolean isChecked;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean getCheckedState(){

        return this.isChecked;
    }

    public String getSubName() {

        return Name;

    }
    public void setSubName(String code) {

        this.Name = code;

    }
    public String getPrice(){
        return this.Price;
    }
    public String getSubFullForm() {

        return getIndianRupee(Price);

    }
    public void setSubFullForm(String name) {

        this.Price = name;

    }

    @Override
    public String toString(){
        return this.Name;
    }

    public String getIndianRupee(String value) {
        Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        return format.format(new BigDecimal(value));
    }
}