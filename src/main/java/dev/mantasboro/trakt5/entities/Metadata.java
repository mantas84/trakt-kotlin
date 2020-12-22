package dev.mantasboro.trakt5.entities;

import com.google.gson.annotations.SerializedName;
import dev.mantasboro.trakt5.enums.*;

public class Metadata {

    public MediaType media_type;
    public Resolution resolution;
    public Hdr hdr;
    public Audio audio;
    public AudioChannels audio_channels;
    @SerializedName("3d")
    public Boolean is3d;

}
