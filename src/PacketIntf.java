import java.net.DatagramPacket;

public abstract class PacketIntf implements AuthIntf {
    final byte[] data;
    DatagramPacket datagramPacket = null;

    public PacketIntf(DatagramPacket datagramPacket) {
        this.data = datagramPacket.getData();
        this.datagramPacket = datagramPacket;
    }

    public PacketIntf(byte[] data, DatagramPacket datagramPacket) {
        this.data = data;
        this.datagramPacket = datagramPacket;
    }

    public abstract void addInt(int num);
    public abstract int getInt();
    public abstract byte[] getNext(int num);
    public abstract byte[] getRemaining();

    abstract PacketIntf fromDatagramPacket(DatagramPacket datagramPacket);
}
