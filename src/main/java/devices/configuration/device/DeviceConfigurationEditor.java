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
}
