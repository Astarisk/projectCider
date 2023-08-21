import java.net.DatagramPacket;

public interface AuthIntf {
    DatagramPacket getDatagramPacket();
    void setDataGramPacket(final DatagramPacket packet);
}
