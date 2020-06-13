public enum Modality {

    INDIVIDUAL(1),
    GROUP(6);

    private final int maximum;

    Modality(int maximum) {
        this.maximum = maximum;
    }

    public boolean doesNotAllow(int numberOfStudents) {
        return numberOfStudents > this.maximum;
    }

}
