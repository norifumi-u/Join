package loop.Join;

public class JoinC{
    
    public static void main(String[] args) throws Exception{
	Join join = new Join();
	try (MPSTEndpoint<Join, C> chris = new MPSTEndpoint<>(Join, C, new ObjectStreamFormatter())) {
	    chris.connect(D, SocketChannelEndpoint::new, "localhost", 7777);
	    int n = 3;
	    System.out.println("sum = " + addC(new Join_C_1(chris), n));
	}
    }
    
    private static int addC(Join_C_1 c1, int n) throws Exception {
	Buf<Integer> x = new Buf<>(10);
	Buf<Integer> y = new Buf<>(1);
	Buf<Integer> i = new Buf<>(1);
	while (i.val < n) {
	    c1.send(D, left, x.val);
	    i.val = n+1;
	}
	c1.send(D, right, y.val);
    }
    
}
