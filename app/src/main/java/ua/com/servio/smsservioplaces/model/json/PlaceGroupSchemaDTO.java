package ua.com.servio.smsservioplaces.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceGroupSchemaDTO {

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("Places")
    private List<PlaceDTO> places;

    public PlaceGroupSchemaDTO(String name, List<PlaceDTO> places) {
        this.name = name;
        this.places = places;
    }

    public String getName() {
        return name;
    }

    public List<PlaceDTO> getPlaces() {
        return places;
    }
}
