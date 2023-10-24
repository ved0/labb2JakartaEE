    package com.example.demo1.entities.categories;

    import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

    @JsonDeserialize(using = CategoryDeserializer.class)
    public enum Category {
        NON_EXISTENT,
        KITCHEN,
        BATHROOM,
        BEDROOM,
        HALLWAY;

        public static boolean isValidCategory(String category) {
            for (Category c : Category.values()) {
                if (c.name().equalsIgnoreCase(category)) {
                    return true;
                }
            }
            return false;
        }
    }

