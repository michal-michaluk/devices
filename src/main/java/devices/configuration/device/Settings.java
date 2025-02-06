package devices.configuration.device;

import lombok.Builder;

@Builder
public record Settings(
        Boolean autoStart,
        Boolean remoteControl,
        Boolean billing,
        Boolean reimbursement,
        Boolean showOnMap,
        Boolean publicAccess) {

    public static Settings defaultSetting() {
        return Settings.builder()
                .autoStart(false)
                .remoteControl(false)
                .billing(false)
                .reimbursement(false)
                .showOnMap(false)
                .publicAccess(false)
                .build();
    }
}
