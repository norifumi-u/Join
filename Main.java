package loop.Join;

import java.io.IOException;

import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;

public class Main {

	public static abstract class MyRunnable implements Runnable {
		abstract public void myrun() throws Exception;

		public void run() {
			try {
				myrun();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		try (
				ScribServerSocket sb = new SocketChannelServer(8888);
				ScribServerSocket sd = new SocketChannelServer(7777);
				ScribServerSocket sc = new SocketChannelServer(6666)) {
			var a = new Thread(new MyRunnable() {
				@Override
				public void myrun() throws Exception {
					JoinA.run();
				}
			});
			var b = new Thread(new MyRunnable() {
				@Override
				public void myrun() throws Exception {
					JoinB.run(sb);
				}
			});
			var c = new Thread(new MyRunnable() {
				@Override
				public void myrun() throws Exception {
					JoinC.run(sc);
				}
			});
			var d = new Thread(new MyRunnable() {
				@Override
				public void myrun() throws Exception {
					JoinD.run(sd);
				}
			});
			b.start();
			d.start();
			Thread.sleep(1000);
			c.start();
			Thread.sleep(1000);
			a.start();
			a.join();
			b.join();
			c.join();
			d.join();
		}
	}

}
