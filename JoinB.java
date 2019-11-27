package loop.Join;

import static loop.Join.Join.Join.*;

import java.io.IOException;

import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import loop.Join.Join.Join;
import loop.Join.Join.roles.B;
import loop.Join.Join.statechans.B.Join_B_1;
import loop.Join.Join.statechans.B.Join_B_1_Cases;

public class JoinB {

	public static void main(String[] args) throws Exception {
		try (ScribServerSocket ss = new SocketChannelServer(8888)) {
			run(ss);
		}
	}

	public static void run(ScribServerSocket ss) throws Exception {
		while (true) {
			Join join = new Join();
			try (MPSTEndpoint<Join, B> serverB = new MPSTEndpoint<>(join, B, new ObjectStreamFormatter())) {
				serverB.accept(ss, A);
				new JoinB().run(new Join_B_1(serverB));
			} catch (ScribRuntimeException | IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void run(Join_B_1 b1) throws Exception {
		Buf<Integer> x = new Buf<>();
		Buf<Integer> y = new Buf<>();
		while (true) {
			Join_B_1_Cases cases = b1.branch(A);
			switch (cases.op) {
			case left:
				b1 = cases.receive(left, x, y).send(A, Res, x.val + y.val);
				break;
			case right:
				cases.receive(right, x, y).send(A, Res, x.val + y.val);
				return;
			}
		}
	}
}
