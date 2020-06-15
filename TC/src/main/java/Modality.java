public enum Modality {

    INDIVIDUAL(1),
    GROUP(6);

    private final int maximum;

    Modality(int maximum) {
        this.maximum = maximum;
    }

    public int maximumAllowed() {
        return this.maximum;
    }

}
