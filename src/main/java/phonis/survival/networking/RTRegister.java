package phonis.survival.networking;

public class RTRegister implements RTPacket {

    public final int protocolVersion;

    public RTRegister(int protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

}
