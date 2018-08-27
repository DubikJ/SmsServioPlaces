package ua.com.servio.smsservioplaces.model.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadResponse {

    @Expose
    @SerializedName("Error")
    private String error;
    @Expose
    @SerializedName("PlaceUnions")
    private List<PlaceUnionDTO> placeUnions;

    public DownloadResponse(String error, List<PlaceUnionDTO> placeUnions) {
        this.error = error;
        this.placeUnions = placeUnions;
    }

    public String getError() {
        return error;
    }

    public List<PlaceUnionDTO> getPlaceUnions() {
        return placeUnions;
    }
}
