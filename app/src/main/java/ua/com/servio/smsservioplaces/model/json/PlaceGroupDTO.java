package ua.com.servio.smsservioplaces.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceGroupDTO {

    @Expose
    @SerializedName("Name")
    private String name;

    @Expose
    @SerializedName("PlaceGroupSchemas")
    private List<PlaceGroupSchemaDTO> placeGroupSchemas;

    public PlaceGroupDTO(String name, List<PlaceGroupSchemaDTO> placeGroupSchemas) {
        this.name = name;
        this.placeGroupSchemas = placeGroupSchemas;
    }

    public String getName() {
        return name;
    }

    public List<PlaceGroupSchemaDTO> getPlaceGroupSchemas() {
        return placeGroupSchemas;
    }
}
