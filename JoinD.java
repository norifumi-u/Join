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
import loop.Join.Join.roles.D;
import loop.Join.Join.statechans.D.EndSocket;
import loop.Join.Join.statechans.D.Join_D_1;
import loop.Join.Join.statechans.D.Join_D_1_Cases;
import loop.Join.Join.statechans.D.Join_D_2;
import loop.Join.Join.statechans.D.Join_D_3;

public class JoinD {

	public static void main(String[] args) throws Exception {
		try (ScribServerSocket ss = new SocketChannelServer(7777)) {
			run(ss);
		}
	}

	public static void run(ScribServerSocket ss) throws Exception {
		while (true) {
			Join join = new Join();
			try (MPSTEndpoint<Join, D> serverD = new MPSTEndpoint<>(join, D, new ObjectStreamFormatter())) {
				serverD.accept(ss, C);
				new JoinD().run(new Join_D_1(serverD));
			} catch (ScribRuntimeException | IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void run(Join_D_1 d1) throws Exception {
		Buf<Integer> x = new Buf<>();
		Buf<Integer> y = new Buf<>();
		while (true) {
			Join_D_1_Cases cases = d1.branch(C);
			switch (cases.op) {
			case left:
				Join_D_2 d2 = cases.receive(left, x, y);
				d1 = d2.send(C, Res, x.val + y.val);
				break;
			case right:
				Join_D_3 d3 = cases.receive(right, x, y);
				@SuppressWarnings("unused")
				EndSocket end = d3.send(C, Res, x.val + y.val);
				return;
			}
		}
	}
}
