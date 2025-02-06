package devices.configuration.device;

import devices.configuration.JsonAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeviceConfigurationEditorTest {
    private final String deviceId = "ALF-98262561";

    @Test
    void createNewDevice() {
        // given - prepare state of system before test

        // when - execute some functionality under test
        DeviceConfigurationEditor editor = DeviceConfigurationEditor.createNewDevice(deviceId);

        // then - verify state of the system after test
        JsonAssert.assertThat(editor)
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
        Assertions.fail("Not Implemented");
    }

    @Test
    void setLocation() {
        Assertions.fail("Not Implemented");
    }

    @Test
    void changeSettings() {
        Assertions.fail("Not Implemented");
    }

    @Test
    void fullyConfiguredDevice() {
        // given
        DeviceConfigurationEditor editor = DeviceConfigurationEditor.createNewDevice(deviceId);

        // when
        // change Ownership
        // set Location
        // change some Settings

        // then
        JsonAssert.assertThat(editor)
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
                            "showOnMap": false,
                            "publicAccess": false
                          }
                        }
                        """);
    }
}
