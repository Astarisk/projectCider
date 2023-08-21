import java.net.DatagramPacket;

public abstract class FrameIntf implements AuthIntf {
    final int cmd;
    final int action;
    final byte[] frameData;

    public FrameIntf(int cmd, int action, byte[] frameData, DatagramPacket datagramPacket) {
        this.cmd = cmd;
        this.action = action;
        this.frameData = frameData;

        System.out.println("FrameIntf.FrameIntf: cmd=" + cmd + ", action=" + action + ", frameData=" + frameData);
    }

    public FrameIntf fromPacketIntf(PacketIntf packetIntf) {
        int cmd = packetIntf.getInt();
        int packetIntfInt = packetIntf.getInt();

        System.out.println("FrameIntf.fromPacketIntf: cmd=" + cmd + ", packetIntfInt=" + packetIntfInt);
        return null;
    }

    public int getCmd() {
        return cmd;
    }

    public int getAction() {
        return action;
    }

    public byte[] getFrameData() {
        return frameData;
    }
}
