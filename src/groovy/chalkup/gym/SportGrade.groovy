package chalkup.gym

class SportGrade implements Comparable<SportGrade> {

    double value;

    /** required by Hibernate */
    SportGrade() {}

    public SportGrade(double value) {
        assert 0.0 <= value && value < 1.0
        this.value = value
    }

    @Override
    int compareTo(SportGrade o) {
        return this.value <=> o.value
    }

    final static def UIAA_GRADES = ['2', '3-', '3', '3+', '4-', '4', '4+', '5-', '5', '5+', '6-', '6', '6+', '7-', '7',
                                    '7+', '8-', '8', '8+', '9-', '9', '9+', '10-', '10', '10+', '11-', '11', '11+',
                                    '12-', '12', '12+']

    public String toUiaaScale() {
        double segment = 1.0 / UIAA_GRADES.size()
        return UIAA_GRADES[(int) Math.floor(value / segment)]

    }

    public static SportGrade fromUiaaScale(String uiaa) {
        int i = UIAA_GRADES.indexOf(uiaa.toUpperCase())
        assert i != -1
        double segment = 1.0 / UIAA_GRADES.size()

        return new SportGrade(i * segment + (segment / 2))
    }

    public static boolean uiaaNeighbours(SportGrade g1, SportGrade g2) {
        int uiaaGradePos1 = UIAA_GRADES.indexOf(g1.toUiaaScale())
        int uiaaGradePos2 = UIAA_GRADES.indexOf(g2.toUiaaScale())
        return Math.abs(uiaaGradePos2 - uiaaGradePos1) == 1;
    }

    public static boolean isUiaaScaleGrade(String uiaa) {
        return UIAA_GRADES.contains(uiaa.toUpperCase())
    }

    public static SportGrade between(SportGrade g1, SportGrade g2) {
        assert g1 <= g2
        return g1 + (g2 - g1) / 2
    }

    /** Can be used for null values. Lower than regular lowest() */
    public static SportGrade zero() {
        return new SportGrade(0.0);
    }

    public static SportGrade lowest() {
        return fromUiaaScale(UIAA_GRADES[0])
    }

    public static SportGrade highest() {
        return fromUiaaScale(UIAA_GRADES[UIAA_GRADES.size() - 1])
    }

    public static double oneUiaaGradeDifference() {
        return SportGrade.fromUiaaScale('7').value - SportGrade.fromUiaaScale('7+').value;
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof SportGrade)) return false

        SportGrade grade = (SportGrade) o

        if (Double.compare(grade.value, value) != 0) return false

        return true
    }

    int hashCode() {
        long temp = value != +0.0d ? Double.doubleToLongBits(value) : 0L
        return (int) (temp ^ (temp >>> 32))
    }

    @Override
    public String toString() {
        return "${String.format('%.3f', value)} (${toUiaaScale()})"
    }
}
