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


}
