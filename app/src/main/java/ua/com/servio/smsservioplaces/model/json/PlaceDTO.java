package ua.com.servio.smsservioplaces.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceDTO {

    @Expose
    @SerializedName("ID")
    private int id;

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("Code")
    private String code;

    @Expose
    @SerializedName("Left")
    private int left;

    @Expose
    @SerializedName("Top")
    private int top;

    @Expose
    @SerializedName("Bills")
    private List<BillDTO> bills;

    public PlaceDTO(int id, String name, String code, int left, int top, List<BillDTO> bills) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.left = left;
        this.top = top;
        this.bills = bills;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public List<BillDTO> getBills() {
        return bills;
    }
}
