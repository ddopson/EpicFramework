package java.lang;


public class _Boolean {
	private static final Boolean _true = new Boolean(true);
	private static final Boolean _false = new Boolean(false);
	
	public static Boolean valueOf(boolean b)
	{
		return b ? _true : _false;
	}
}
