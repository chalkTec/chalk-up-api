package chalkup.gym

class BoulderGrade implements Comparable<BoulderGrade> {

    double value;

    /** required by Hibernate */
    BoulderGrade() {}

    public BoulderGrade(double value) {
        assert 0.0 <= value && value < 1.0
        this.value = value
    }

    @Override
    int compareTo(BoulderGrade o) {
        return this.value <=> o.value
    }

    final static
    def FONT_GRADES = ['1A', '1B', '1C', '2A', '2B', '2C', '3A', '3B', '3C', '4A', '4B', '4C', '5A', '5B', '5C',
                       '6A', '6A+', '6B', '6B+', '6C', '6C+', '7A', '7A+', '7B', '7B+', '7C', '7C+', '8A', '8A+', '8B', '8B+',
                       '8C', '8C+']

    public String toFontScale() {
        double segment = 1.0 / FONT_GRADES.size()
        return FONT_GRADES[(int) Math.floor(value / segment)]
    }

    public static BoulderGrade fromFontScale(String font) {
        int i = FONT_GRADES.indexOf(font.toUpperCase())
        assert i != -1
        double segment = 1.0 / FONT_GRADES.size()

        return new BoulderGrade(i * segment + (segment / 2))
    }

    public static boolean fontNeighbours(BoulderGrade g1, BoulderGrade g2) {
        int fontGradePos1 = FONT_GRADES.indexOf(g1.toFontScale())
        int fontGradePos2 = FONT_GRADES.indexOf(g2.toFontScale())
        return Math.abs(fontGradePos2 - fontGradePos1) == 1;
    }

    public static boolean isFontScaleGrade(String font) {
        return FONT_GRADES.contains(font.toUpperCase())
    }

    public static BoulderGrade between(BoulderGrade g1, BoulderGrade g2) {
        assert g1 <= g2
        return g1 + (g2 - g1) / 2
    }

    /** Can be used for null values. Lower than regular lowest() */
    public static BoulderGrade zero() {
        return new BoulderGrade(0.0);
    }

    public static BoulderGrade lowest() {
        return fromFontScale(FONT_GRADES[0])
    }

    public static BoulderGrade highest() {
        return fromFontScale(FONT_GRADES[FONT_GRADES.size() - 1])
    }

    public static double oneFontGradeDifference() {
        return BoulderGrade.fromFontScale('7a+').value - BoulderGrade.fromFontScale('7a').value;
    }


    public BoulderGrade plus(double value) {
        return new BoulderGrade(this.value + value)
    }

    public double minus(BoulderGrade g) {
        return this.value - g.value
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof BoulderGrade)) return false

        BoulderGrade grade = (BoulderGrade) o

        if (Double.compare(grade.value, value) != 0) return false

        return true
    }

    int hashCode() {
        long temp = value != +0.0d ? Double.doubleToLongBits(value) : 0L
        return (int) (temp ^ (temp >>> 32))
    }

    @Override
    public String toString() {
        return "${String.format('%.3f', value)} (${toFontScale()})"
    }
}
