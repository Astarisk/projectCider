import java.net.DatagramPacket;

public class Frame extends FrameIntf {
    private DatagramPacket datagramPacket = null;;
    public Frame(int cmd, int action, byte[] frameData, DatagramPacket datagramPacket) {
        super(cmd, action, frameData, datagramPacket);
    }

    @Override
    public DatagramPacket getDatagramPacket() {
        return datagramPacket;
    }

    @Override
    public void setDataGramPacket(DatagramPacket packet) {
        this.datagramPacket = packet;
    }
}
