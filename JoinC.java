package loop.Join;

import static loop.Join.Join.Join.*;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.net.SocketChannelServer;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import loop.Join.Join.Join;
import loop.Join.Join.roles.C;
import loop.Join.Join.statechans.C.Join_C_1;
import loop.Join.Join.statechans.C.Join_C_2;
import loop.Join.Join.statechans.C.Join_C_3;
import loop.Join.Join.statechans.C.Join_C_4;
import loop.Join.Join.statechans.C.Join_C_5;

public class JoinC {

	public static void main(String[] args) throws Exception {
		try (ScribServerSocket ss = new SocketChannelServer(6666)) {
			run(ss);
		}
	}

	public static void run(ScribServerSocket ss) throws Exception {
		Join join = new Join();
		try (MPSTEndpoint<Join, C> chris = new MPSTEndpoint<>(join, C, new ObjectStreamFormatter())) {
			chris.request(D, SocketChannelEndpoint::new, "localhost", 7777);
			chris.accept(ss, A);
			int n = 3;
			System.out.println("sum of C = " + addC(new Join_C_1(chris), n));
		}
	}

	private static int addC(Join_C_1 c1, int n) throws Exception {
		var x = new Buf<>(10);
		var y = new Buf<>(1);
		var z = new Buf<>(0);
		var last = new Buf<Integer>();
		var i = new Buf<>(0);

		while (i.val < n) {
			Join_C_2 c2 = c1.send(D, left, x.val, z.val);
			c1 = c2.receive(D, Res, z);
			i.val += 1;
		}
		Join_C_3 c3 = c1.send(D, right, y.val, z.val);
		Join_C_4 c4 = c3.receive(D, Res, z);
		Join_C_5 c5 = c4.receive(A, join, last);
		c5.send(A, Res, z.val + last.val);
		return z.val;
	}
}
