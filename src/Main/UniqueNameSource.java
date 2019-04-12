package Main;

public class UniqueNameSource
{
	private static int uniqueInt = -1;
	private static String $var = "$var";

	public static String getUniqueName()
	{
		uniqueInt++;
		return $var + uniqueInt;
	}
}
