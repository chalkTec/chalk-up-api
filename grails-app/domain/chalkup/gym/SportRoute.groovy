package chalkup.gym

class SportRoute extends Route<SportGrade> {

    public final static String DISCRIMINATOR = "sport-route"

    static mapping = {
        discriminator DISCRIMINATOR
    }

    @Override
    protected SportGrade gradeForValue(double value) {
        return new SportGrade(value)
    }

    @Override
    String getReadableInitialGrade() {
        switch(getInitialGradeCertainty()) {
            case GradeCertainty.UNKNOWN:
                return "?"
            case GradeCertainty.ASSIGNED:
                return new SportGrade(initialGradeLow).toUiaaScale()
            case GradeCertainty.RANGE:
                if(SportGrade.uiaaNeighbours(getGradeRangeLow(), getGradeRangeHigh())) {
                    return "${getGradeRangeLow().toUiaaScale()}/${getGradeRangeHigh().toUiaaScale()}"
                }
                else {
                    return "${getGradeRangeLow().toUiaaScale()} – ${getGradeRangeHigh().toUiaaScale()}"
                }
        }
    }
}
