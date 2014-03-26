package chalkup.gym

import java.awt.*

public enum RouteColor {

    GREEN("13A300"), YELLOW("DBE300"), RED("D90000"), BLUE("3D3DFF"), BLACK("0A0A0A"), GRAY("696969"),
    WHITE("FCFCFC"), PINK("FF42F9"), ORANGE("E87400"), BROWN("8A4500"), PURPLE("8200ED"), BLUE_BLACK("0068DE",
            "0A0A0A"), YELLOW_BLACK("DBE300", "0A0A0A"), BLUE_WHITE_RED("3D3DFF", "FCFCFC", "D90000"), STONE("858585");

    public final Color primaryColor
    public final Color secondaryColor
    public final Color ternaryColor

    RouteColor(String primaryColor) {
        this.primaryColor = c(primaryColor)
        this.secondaryColor = null
        this.ternaryColor = null
    }

    RouteColor(String primaryColor, String secondaryColor) {
        this.primaryColor = c(primaryColor)
        this.secondaryColor = c(secondaryColor)
        this.ternaryColor = null
    }

    RouteColor(String primaryColor, String secondaryColor, String ternaryColor) {
        this.primaryColor = c(primaryColor)
        this.secondaryColor = c(secondaryColor)
        this.ternaryColor = c(ternaryColor)
    }

    private static Color c(String hex) {
        return new Color(Integer.parseInt(hex.substring(0, 2), 16), Integer.parseInt(hex.substring(2, 4), 16),
                Integer.parseInt(hex.substring(4, 6), 16))
    }

    public static String asRgb(Color color) {
        return "rgb($color.red, $color.green, $color.blue)"
    }

}