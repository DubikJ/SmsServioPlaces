package ua.com.servio.smsservioplaces.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillDTO {
    @Expose
    @SerializedName("ID")
    private int id;

    @Expose
    @SerializedName("Number")
    private int number;

    @Expose
    @SerializedName("Opened")
    private String opened;

    @Expose
    @SerializedName("Total")
    private double total;

    @Expose
    @SerializedName("OpenUser")
    private String openUser;

    public BillDTO(int id, int number, String opened, double total, String openUser) {
        this.id = id;
        this.number = number;
        this.opened = opened;
        this.total = total;
        this.openUser = openUser;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getOpened() {
        return opened;
    }

    public double getTotal() {
        return total;
    }

    public String getOpenUser() {
        return openUser;
    }
}
