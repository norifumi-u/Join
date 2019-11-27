package loop.Join;

import static loop.Join.Join.Join.*;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import loop.Join.Join.Join;
import loop.Join.Join.roles.A;
import loop.Join.Join.statechans.A.Join_A_1;
import loop.Join.Join.statechans.A.Join_A_2;
import loop.Join.Join.statechans.A.Join_A_3;
import loop.Join.Join.statechans.A.Join_A_4;
import loop.Join.Join.statechans.A.Join_A_5;

public class JoinA {

	public static void main(String[] args) throws Exception {
		run();
	}

	public static void run() throws Exception {
		Join join = new Join();
		try (MPSTEndpoint<Join, A> alice = new MPSTEndpoint<>(join, A, new ObjectStreamFormatter())) {
			alice.request(B, SocketChannelEndpoint::new, "localhost", 8888);
			alice.request(C, SocketChannelEndpoint::new, "localhost", 6666);
			int n = 5;
			System.out.println("sum of all = " + addA(new Join_A_1(alice), n));
		}
	}

	private static int addA(Join_A_1 a1, int n) throws Exception {
    	var x = new Buf<>(1000);
    	var y = new Buf<>(100);
    	var z = new Buf<>(0);
    	var last = new Buf<Integer>();
    	var i = new Buf<>(0);

    	while(i.val < n) {
    		Join_A_2 a2 = a1.send(B, left, x.val, z.val);
        	a1 = a2.receive(B, Res, z);
        	i.val += 1;
    	}
		Join_A_3 a3 = a1.send(B, right, y.val, z.val);
		Join_A_4 a4 = a3.receive(B, Res, z);
		System.out.println("sum of A = " + z.val);
		Join_A_5 a5 = a4.send(C, join, z.val);
		a5.receive(C, Res, last);
		return last.val;
    }

}
