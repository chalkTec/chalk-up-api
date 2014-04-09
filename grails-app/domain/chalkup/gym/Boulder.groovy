package chalkup.gym

class Boulder extends Route<BoulderGrade> {

    public final static String DISCRIMINATOR = "boulder"


    static mapping = {
        discriminator DISCRIMINATOR
    }

    @Override
    protected BoulderGrade gradeForValue(double value) {
        return new BoulderGrade(value)
    }

    @Override
    String getReadableInitialGrade() {
        switch(getInitialGradeCertainty()) {
            case GradeCertainty.UNKNOWN:
                return "?"
            case GradeCertainty.ASSIGNED:
                return new BoulderGrade(initialGradeLow).toFontScale()
            case GradeCertainty.RANGE:
                if(BoulderGrade.fontNeighbours(getGradeRangeLow(), getGradeRangeHigh())) {
                    return "${getGradeRangeLow().toFontScale()}/${getGradeRangeHigh().toFontScale()}"
                }
                else {
                    return "${getGradeRangeLow().toFontScale()} – ${getGradeRangeHigh().toFontScale()}"
                }
        }
    }

}
