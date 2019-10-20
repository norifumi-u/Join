package loop.Join;

public class JoinB{
    
    public static void main(String[] args) throws Exception {
	try (ScribServerSocket ss = new SocketChannelServer(8888)) {
	    while (true) {
		Join join = new Join();
		try (MPSTEndpoint<Join, B> serverB) = new MPSTEndpoint<>(Join, B, new ObjectStreamFormatter()){
			serverB.accept(ss, A);
			new JoinB().run(new Join_B_1(serverB));
		    } catch (ScribbleRuntimeException | IOException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
		try (MPSTEndpoint<Join, B> bob = new MPSTEndpoint<>(Join, B, new ObjectStreamFormatter())){
		    bob.connect(D, SocketChannelEndpoint::new, "localhost", 7777);
		    System.out.println()
		}
	    }
	}
    }

    private void run(Join_B_1 b1) throws Exception {
	Buf<Integer> a = new Buf<>(0);
	Buf<Integer> x = new Buf<>(0);
	Buf<Integer> y = new Buf<>(0);
	while (true) {
	    Join_B_1_Cases cases = b1.branch(A);
	    switch (cases.op) {
	    case left: cases.receive(left, x);
		a.val = a.val + x.val;
		break;
	    case right: cases.receive(right, y);
		a.val = a.val + y.val;
		return;
	    }
	}
	b1.send(D, join, a);
    }
}
