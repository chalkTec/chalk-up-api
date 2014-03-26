package chalkup.gym

import javax.imageio.ImageIO
import java.awt.*

class Boulder extends Route {

    static mapping = {
        discriminator "boulder"
    }

    static constraints = {
        // Limit upload file size to 5MB
        photoAsJpg maxSize: 1024 * 1024 * 5, nullable: true
    }

    static embedded = ['initialGradeRangeLow', 'initialGradeRangeHigh']

    GradeCertainty initialGradeCertainty
    BoulderGrade initialGradeRangeLow, initialGradeRangeHigh

    byte[] photoAsJpg


    static transients = ['photo']

    public Boulder() {
        this.initialGradeRangeLow = BoulderGrade.zero()
        this.initialGradeRangeHigh = BoulderGrade.zero()
    }


    public void removePhoto() {
        photoAsJpg = null
    }

    public void setPhoto(Image image) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "JPG", out)
        photoAsJpg = out.toByteArray()
    }

    public Image setPhotoFromInputStream(InputStream input) {
        Image image = ImageIO.read(input)
        setPhoto(image)
    }

    public boolean hasPhoto() {
        return photoAsJpg != null
    }

    public Image getPhoto() {
        return ImageIO.read(getPhotoAsInputStream())
    }

    public InputStream getPhotoAsInputStream() {
        return new ByteArrayInputStream(photoAsJpg)
    }

    public void assignedGrade(BoulderGrade grade) {
        initialGradeCertainty = GradeCertainty.ASSIGNED
        initialGradeRangeLow = grade
        initialGradeRangeHigh = BoulderGrade.zero()
    }

    public boolean hasAssignedGrade() {
        return initialGradeCertainty == GradeCertainty.ASSIGNED

    }

    public BoulderGrade getAssignedGrade() {
        assert hasAssignedGrade()
        return initialGradeRangeLow
    }

    public void gradeRange(BoulderGrade rangeLow, BoulderGrade rangeHigh) {
        initialGradeCertainty = GradeCertainty.RANGE
        initialGradeRangeLow = rangeLow
        initialGradeRangeHigh = rangeHigh
    }

    public boolean hasGradeRange() {
        return initialGradeCertainty == GradeCertainty.RANGE;
    }

    public BoulderGrade getGradeRangeLow() {
        assert hasGradeRange()
        return initialGradeRangeLow
    }

    public BoulderGrade getGradeRangeHigh() {
        assert hasGradeRange()
        return initialGradeRangeHigh
    }

    public void unknownGrade() {
        initialGradeCertainty = GradeCertainty.UNKNOWN;
        initialGradeRangeLow = BoulderGrade.lowest()
        initialGradeRangeHigh = BoulderGrade.highest()
    }

    public String getInitialGrade() {
        switch (initialGradeCertainty) {
            case GradeCertainty.ASSIGNED:
                return getAssignedGrade().toFontScale()
            case GradeCertainty.RANGE:
                return getGradeRangeLow().toFontScale() + " – " + getGradeRangeHigh().toFontScale()
            case GradeCertainty.UNKNOWN:
                return "unbekannt"
        }
    }

    public boolean hasUnknownGrade() {
        return initialGradeCertainty == GradeCertainty.UNKNOWN;
    }

}
