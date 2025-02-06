package devices.configuration.device;

import devices.configuration.JsonAssert;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static devices.configuration.JsonAssert.assertThat;
import static devices.configuration.device.DevicesConfigurationFixture.someLocationInCity;
import static devices.configuration.device.DevicesConfigurationFixture.someSettings;

public class DeviceConfigurationEditorTest {
    private final String deviceId = "ALF-98262561";

    @Test
    void createNewDevice() {
        // given - prepare state of system before test

        // when - execute some functionality under test
        DeviceConfigurationEditor editor = DeviceConfigurationEditor.createNewDevice(deviceId);

        // then - verify state of the system after test
        assertThat(editor)
                .isExactlyLike("""
                        {
                          "deviceId": "ALF-98262561",
                          "ownership": {
                            "operator": null,
                            "provider": null
                          },
                          "location": null,
                          "openingHours": {
                            "alwaysOpen": true
                          },
                          "settings": {
                            "autoStart": false,
                            "remoteControl": false,
                            "billing": false,
                            "reimbursement": false,
                            "showOnMap": false,
                            "publicAccess": false
                          }
                        }
                        """);
    }

    @Test
    void changeOwnership() {
        // given new not configured device
        var device = DeviceConfigurationEditor.createNewDevice(deviceId);

        // when change ownership is executed

        device.assignTo(new Ownership("operator", "provider"));

        // then we expect ownership to be set in configuration

        assertThat(device)
                .isExactlyLike("""
                        {
                          "deviceId": "ALF-98262561",
                          "ownership": {
                            "operator": "operator",
                            "provider": "provider"
                          },
                          "location": null,
                          "openingHours": {
                            "alwaysOpen": true
                          },
                          "settings": {
                            "autoStart": false,
                            "remoteControl": false,
                            "billing": false,
                            "reimbursement": false,
                            "showOnMap": false,
                            "publicAccess": false
                          }
                        }
                        """);
    }

    @Test
    void changeOwnershipToUnowned() {
        // given fully configured device
        var device = DeviceConfigurationEditor.createNewDevice(deviceId);

        // when
        device.assignTo(new Ownership(
                "Devicex.nl",
                "public-devices"
        ));

        device.setLocation(someLocationInCity());

        device.setSettings(
                someSettings()
                        .billing(true)
                        .build());


        // when change ownership unowned

        device.assignTo(Ownership.unowned());

        // then we expect device to be reset to new device state

        assertThat(device)
                .isExactlyLike("""
                        {
                          "deviceId": "ALF-98262561",
                          "ownership": {
                            "operator": null,
                            "provider": null
                          },
                          "location": null,
                          "openingHours": {
                            "alwaysOpen": true
                          },
                          "settings": {
                            "autoStart": false,
                            "remoteControl": false,
                            "billing": false,
                            "reimbursement": false,
                            "showOnMap": false,
                            "publicAccess": false
                          }
                        }
                        """);
    }

    @Test
    void setLocation() {
        var device = DeviceConfigurationEditor.createNewDevice(deviceId);

        device.setLocation(someLocationInCity());

        assertThat(device)
                .isExactlyLike("""
                        {
                          "deviceId": "ALF-98262561",
                          "ownership": {
                            "operator": null,
                            "provider": null
                          },
                          "location": {
                            "street": "Rakietowa",
                            "houseNumber": "1A",
                            "city": "Wrocław",
                            "postalCode": "54-621",
                            "country": "POL",
                            "coordinates": {
                              "longitude": 16.931752852309156,
                              "latitude": 51.09836221719513
                            }
                          },
                          "openingHours": {
                            "alwaysOpen": true
                          },
                          "settings": {
                            "autoStart": false,
                            "remoteControl": false,
                            "billing": false,
                            "reimbursement": false,
                            "showOnMap": false,
                            "publicAccess": false
                          }
                        }
                        """);
    }

    @Test
    void changeSettings() {
        // given new not configured device
        var device = DeviceConfigurationEditor.createNewDevice(deviceId);

        device.setSettings(Settings.builder()
                .publicAccess(true)
                .showOnMap(true)
                .build());

        // then - verify state of the system after test
        assertThat(device)
                .isExactlyLike("""
                        {
                          "deviceId": "ALF-98262561",
                          "ownership": {
                            "operator": null,
                            "provider": null
                          },
                          "location": null,
                          "openingHours": {
                            "alwaysOpen": true
                          },
                          "settings": {
                            "autoStart": false,
                            "remoteControl": false,
                            "billing": false,
                            "reimbursement": false,
                            "showOnMap": true,
                            "publicAccess": true
                          }
                        }
                        """);
    }

    @Test
    void fullyConfiguredDevice() {
        // given
        DeviceConfigurationEditor device = DeviceConfigurationEditor.createNewDevice(deviceId);

        // when
        device.assignTo(new Ownership(
                "Devicex.nl",
                "public-devices"
        ));

        device.setLocation(someLocationInCity());
        device.setSettings(Settings.builder()
                .publicAccess(true)
                .showOnMap(true)
                .build());
        // then
        assertThat(device)
                .isExactlyLike("""
                        {
                          "deviceId": "ALF-98262561",
                          "ownership": {
                            "operator": "Devicex.nl",
                            "provider": "public-devices"
                          },
                          "location": {
                            "street": "Rakietowa",
                            "houseNumber": "1A",
                            "city": "Wrocław",
                            "postalCode": "54-621",
                            "country": "POL",
                            "coordinates": {
                              "longitude": 16.931752852309156,
                              "latitude": 51.09836221719513
                            }
                          },
                          "openingHours": {
                            "alwaysOpen": true
                          },
                          "settings": {
                            "autoStart": false,
                            "remoteControl": false,
                            "billing": false,
                            "reimbursement": false,
                            "showOnMap": true,
                            "publicAccess": true
                          }
                        }
                        """);
    }

    @Test
    void testCheckViolations() {
        // given new not configured device
        var device = DeviceConfigurationEditor.createNewDevice(deviceId);

        // when - checking for violations
        var violations = device.checkViolations();

        // then we expect ownership to be set in configuration
        assertNotNull(violations);
    }
}
