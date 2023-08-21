import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CiderServer extends Thread {
    private static final int PORT = 10001;

    public static ServiceBase serviceBase = new ServiceBase();

    public DatagramPacket grabPacket(DatagramSocket socket) throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        System.out.println("=> (rcv) " + Arrays.toString(buffer));
        return packet;
    }

    /// TODO This is kinda important as its where I initialize the first two abstract classes to populate the data
    private Optional<PacketIntf> acceptPacket(DatagramPacket datagramPacket) {
        return Optional.of(new MsgBuf(datagramPacket.getData(), datagramPacket));
    }

    private ReplyBuf acceptFrame(FrameIntf frameIntf) {
        //ReplyBuf reply = CiderServer.serviceBase.acceptFrame(frameIntf);
        System.out.println("CiderServer.acceptFrame:");
        ReplyBuf buf =  CiderServer.serviceBase.acceptFrame(frameIntf);
        System.out.println("CiderServer.acceptFrame: " + buf);
        return buf;
    }

    private FrameIntf packetToFrame(PacketIntf packetIntf) {
        return new Frame(packetIntf.getInt(), packetIntf.getInt(), packetIntf.getRemaining(), packetIntf.getDatagramPacket());
    }

    private DatagramPacket sendReply(ReplyBuf replyBuf) {
        System.out.println(Arrays.toString(replyBuf.data));
        byte[] data = replyBuf.data;

        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, replyBuf.getDatagramPacket().getAddress(), replyBuf.getDatagramPacket().getPort());
        return datagramPacket;
    }

    public void startServer() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {

            Supplier<DatagramPacket> packetSupplier = () -> {
                try {
                    return grabPacket(socket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            };

            Stream.generate(packetSupplier)
            .takeWhile(Objects::nonNull)
            .map(this::acceptPacket)
            .flatMap(Optional::stream)
                    .map((e) -> { FrameIntf f = this.packetToFrame(e); f.setDataGramPacket(e.getDatagramPacket()); return f;})
                    .map((e1) -> { ReplyBuf f = this.acceptFrame(e1); f.setDataGramPacket(e1.getDatagramPacket()); return f;})
                    .map((e2) -> { DatagramPacket f = this.sendReply(e2); return f; })
//                    .map(this::sendReply)
                    .forEach((e) -> {
                        System.out.println(e);
                        try {
                            socket.send(e);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
