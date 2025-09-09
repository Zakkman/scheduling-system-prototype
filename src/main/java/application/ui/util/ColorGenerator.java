// In a new file, e.g., application.ui.util.ColorGenerator.java

package application.ui.util;

public class ColorGenerator {

    private static final String[] COLORS = {
        "#E57373", // Red 300
        "#F06292", // Pink 300
        "#BA68C8", // Purple 300
        "#9575CD", // Deep Purple 300
        "#7986CB", // Indigo 300
        "#64B5F6", // Blue 300
        "#4FC3F7", // Light Blue 300
        "#4DD0E1", // Cyan 300
        "#4DB6AC", // Teal 300
        "#81C784", // Green 300
        "#AED581", // Light Green 300
        "#DCE775", // Lime 300
        "#FFF176", // Yellow 300
        "#FFD54F", // Amber 300
        "#FFB74D", // Orange 300
        "#FF8A65", // Deep Orange 300
        "#A1887F", // Brown 300
        "#E0E0E0", // Grey 300
        "#90A4AE", // Blue Grey 300

        "#EF9A9A", // Red 200
        "#F48FB1", // Pink 200
        "#CE93D8", // Purple 200
        "#B39DDB", // Deep Purple 200
        "#9FA8DA", // Indigo 200
        "#90CAF9", // Blue 200
        "#81D4FA", // Light Blue 200
        "#80DEEA", // Cyan 200
        "#80CBC4", // Teal 200
        "#A5D6A7", // Green 200
        "#C5E1A5", // Light Green 200
        "#E6EE9C", // Lime 200
        "#FFF59D", // Yellow 200
        "#FFE082", // Amber 200
        "#FFCC80", // Orange 200
        "#FFAB91", // Deep Orange 200
        "#BCAAA4", // Brown 200
        "#EEEEEE", // Grey 200
        "#B0BEC5"  // Blue Grey 200
    };

    public static String generateColorFromName(String inputString) {
        if (inputString == null || inputString.trim().isEmpty()) {
            return "#F5F5F5"; // Default light gray for empty names
        }
        int hash = inputString.hashCode();
        int index = Math.abs(hash % COLORS.length);
        return COLORS[index];
    }
}