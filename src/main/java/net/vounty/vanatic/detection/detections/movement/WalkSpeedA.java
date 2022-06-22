package net.vounty.vanatic.detection.detections.movement;

import io.github.retrooper.packetevents.packetwrappers.NMSPacket;
import io.github.retrooper.packetevents.packetwrappers.WrappedPacket;
import net.vounty.vanatic.detection.VanaticDetection;
import net.vounty.vanatic.user.User;
import net.vounty.vanatic.utils.annotations.DetectionInformation;
import net.vounty.vanatic.utils.enums.*;

@DetectionInformation(
        name = "WalkSpeed",
        type = DetectionType.A,
        category = DetectionCategory.MOVEMENT,
        status = DetectionStatus.TEST,
        banAmount = 10,
        kickAmount = 5
)
public class WalkSpeedA extends VanaticDetection {

    @Override
    public Boolean onPacket(User user, NMSPacket nmsPacket, WrappedPacket wrappedPacket, ProtocolMode mode, ProtocolType type) {

        if (user == null)
            return false;



        return false;
    }

}