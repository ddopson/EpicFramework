package java.lang;


public class _Integer {
	private static Integer[] cache = new Integer[256];

	public static Integer valueOf(int i)
	{
		return (i >= -128) && (i <= 127) ? cache[(i & 0xFF)] : new Integer(i);
	}

	public static int signum(int i) {
		return -i >>> 31 | i >> 31;
	}

	public static int highestOneBit(int i)
	{
		i |= i >> 1;
		i |= i >> 2;
		i |= i >> 4;
		i |= i >> 8;
		i |= i >> 16;
		return i ^ i >>> 1;
	}

	public static int lowestOneBit(int i) {
		return i & -i;
	}

	static
	{
		for (int i = 0; i < cache.length; i++) cache[i] = new Integer((byte)i);
	}
}
