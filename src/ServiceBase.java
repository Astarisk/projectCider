public class ServiceBase implements ServiceIntf {



    private ReplyBuf doLoginRequest(PacketIntf packetIntf) {
        byte[] username = packetIntf.getNext(256);
        byte[] password = packetIntf.getNext(256);

        String usernameStr = new String(username).trim();
        String passWordStr = new String(password).trim();

        System.out.println("ServiceBase.doLoginRequest: username=" + usernameStr + ", password=" + passWordStr);

        if("RAWRZ".equals(usernameStr) && "OTTERZRULEZ".equals(passWordStr)) {
            System.out.println("ServiceBase.doLoginRequest: SUCCESS");

            ReplyBuf replyBuf = new ReplyBuf(new byte[1024], packetIntf.getDatagramPacket());

            replyBuf.addInt(3);
            replyBuf.addInt(5);
            return replyBuf;
        }
        return null;
    }

    @Override
    public ReplyBuf acceptFrame(FrameIntf frame) {
        System.out.println("ServiceBase.acceptFrame");

        int cmd = frame.getCmd();
        int action = frame.getAction();

        // AUTHENTICATE 2
        // - LOGIN 3
        if(cmd == 2) {
            if(action == 3) {
                PacketIntf packetIntf = new MsgBuf(frame.getFrameData(), frame.getDatagramPacket());
                return doLoginRequest(packetIntf);
            }
        }

        System.out.println("ServiceBase.acceptFrame: cmd=" + cmd + ", action=" + action);
        return null;
    }
}
