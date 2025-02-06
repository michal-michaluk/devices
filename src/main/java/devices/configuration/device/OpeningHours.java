package devices.configuration.device;

public record OpeningHours(boolean alwaysOpen) {

    public static OpeningHours alwaysOpened() {
        return new OpeningHours(true);
    }
}
