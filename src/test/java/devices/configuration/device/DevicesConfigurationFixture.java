package devices.configuration.device;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class DevicesConfigurationFixture {
    @NotNull
    public static Location someLocationInCity() {
        return new Location(
                "Rakietowa",
                "1A",
                "Wrocław",
                "54-621",
                "POL",
                new Location.Coordinates(
                        new BigDecimal("16.931752852309156"),
                        new BigDecimal("51.09836221719513")
                )
        );
    }

    public static Settings.SettingsBuilder someSettings() {
        return Settings.builder()
                .publicAccess(true)
                .showOnMap(true);
    }
}
