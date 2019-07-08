package com.example.test;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("serial")
public class Recipe implements Serializable {
        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("images")
        @Expose
        private List<String> images = null;
        @SerializedName("lastUpdated")
        @Expose
        private int lastUpdated;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("instructions")
        @Expose
        private String instructions;
        @SerializedName("difficulty")
        @Expose
        private int difficulty;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public int getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(int lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getInstructions() {
            return instructions;
        }

        public void setInstructions(String instructions) {
                this.instructions = instructions;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }
}
