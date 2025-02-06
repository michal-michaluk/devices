package devices.configuration.device;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeviceConfigurationEditor {
    private final String deviceId;
    private Ownership ownership;
    private Location location;
    private OpeningHours openingHours;
    private Settings settings;

    public static DeviceConfigurationEditor createNewDevice(String deviceId) {
        return new DeviceConfigurationEditor(
                deviceId,
                Ownership.unowned(),
                null,
                OpeningHours.alwaysOpened(),
                Settings.defaultSetting()
        );
    }

    public void assignTo(Ownership ownership) {
        this.ownership = ownership;

        if (this.ownership.isUnowned()) {
            resetToDefault();
        }
    }

    private void resetToDefault() {
        setLocation(null);
        this.openingHours = OpeningHours.alwaysOpened();
        setSettings(Settings.defaultSetting());
    }

    public void setSettings(Settings settings) {
        this.settings = this.settings.merge(settings);
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Violations checkViolations() {
        return Violations.builder()
                .operatorNotAssigned(ownership.operator() == null)
                .providerNotAssigned(ownership.provider() == null)
                .locationMissing(location == null)
                .showOnMapButMissingLocation(settings.showOnMap() && location == null)
                .showOnMapButNoPublicAccess(settings.showOnMap() && !settings.publicAccess())
                .build();
    }

    public DeviceConfiguration toDeviceConfiguration() {
        Violations violations = checkViolations();
        return new DeviceConfiguration(
                deviceId,
                ownership,
                location,
                openingHours,
                settings,
                violations
        );
    }
}
