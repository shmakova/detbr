package ru.shmakova.detbr.data.wot_network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WotResponse {
    private static final int GOOD_REPUTATION = 50;

    @SerializedName("0")
    @Expose
    private final List<Integer> trustworthiness = new ArrayList<>();
    @SerializedName("4")
    @Expose
    private final List<Integer> childSafety = new ArrayList<>();

    public boolean isSafe() {
        return !childSafety.isEmpty() && childSafety.get(0) >= GOOD_REPUTATION &&
                !trustworthiness.isEmpty() && trustworthiness.get(0) >= GOOD_REPUTATION ||
                trustworthiness.isEmpty() && childSafety.isEmpty();
    }
}
