import java.net.DatagramPacket;

public class ReplyBuf extends MsgBuf {
    public ReplyBuf(byte[] data, DatagramPacket datagramPacket) {
        super(data, datagramPacket);
    }
}
