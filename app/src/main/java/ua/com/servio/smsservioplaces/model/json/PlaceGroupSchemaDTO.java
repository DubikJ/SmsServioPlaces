package ua.com.servio.smsservioplaces.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceGroupSchemaDTO {

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("Places")
    private ArrayList<PlaceDTO> places;

    public PlaceGroupSchemaDTO(String name, ArrayList<PlaceDTO> places) {
        this.name = name;
        this.places = places;
    }

    public String getName() {
        return name;
    }

    public ArrayList<PlaceDTO> getPlaces() {
        return places;
    }
}
