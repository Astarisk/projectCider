import java.net.DatagramPacket;
import java.nio.ByteBuffer;

public class MsgBuf extends PacketIntf {
    int ptr = 0;

    public MsgBuf(byte[] data, DatagramPacket datagramPacket) {
        super(data, datagramPacket);
    }

    @Override
    public void addInt(int num) {
        System.out.println("Adding int: " + num);
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(num);
        byte[] buf = b.array();
        for(int i = 0; i < 4; i++) {
            data[ptr++] = buf[i];
        }
    }

    @Override
    public int getInt() {
        ByteBuffer b = ByteBuffer.wrap(data, ptr, 4);
        ptr += 4;
        return b.getInt();
    }

    @Override
    public byte[] getNext(int num) {
        byte[] buf = new byte[num];
        for(int i = 0; i < num; i++) {
            buf[i] = data[ptr++];
        }
        return buf;
    }

    @Override
    public byte[] getRemaining() {
        byte[] buf = new byte[data.length - ptr];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = data[ptr++];
        }
        return buf;
    }

    @Override
    PacketIntf fromDatagramPacket(DatagramPacket datagramPacket) {
        return null;
    }

    @Override
    public DatagramPacket getDatagramPacket() {
        return this.datagramPacket;
    }

    @Override
    public void setDataGramPacket(DatagramPacket packet) {
        this.datagramPacket = packet;
    }
}
