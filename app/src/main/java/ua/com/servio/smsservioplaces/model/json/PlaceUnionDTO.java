package ua.com.servio.smsservioplaces.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceUnionDTO {

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("PlaceGroups")
    private List<PlaceGroupDTO> placeGroups;

    public PlaceUnionDTO(String name, List<PlaceGroupDTO> placeGroups) {
        this.name = name;
        this.placeGroups = placeGroups;
    }

    public String getName() {
        return name;
    }

    public List<PlaceGroupDTO> getPlaceGroups() {
        return placeGroups;
    }
}
