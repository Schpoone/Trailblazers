
public class ColorIdentifier {
	public static final int
			BLACK = 0,
			WHITE = 1,
			YELLOW = 2,
			GRAY = 3,
			GREEN = 4,
			RED = 5,
			BLUE = 6
		;
	public static final int[][] colors = {
		{  0,  0,  0},
		{255,255,255},
		{255,216,  0},
		{160,160,160},
		{ 59,152, 95},
		{127,  0,  0},
		{  0, 38,255}
	};
	
	public static int identify(int r,int g,int b) {
		int[] lsr = new int[7];
		int min = -1,minlsr = 255*255*3;
		int dr,dg,db,ds;
		for (int i=0; i<lsr.length; i++) {
			dr = colors[i][0] - r;
			dg = colors[i][1] - g;
			db = colors[i][2] - b;
			ds = dr*dr + dg*dg + db*db;
			if (min==-1 || ds<minlsr) {
				min = i;
				minlsr = ds;
			}
		}
		return min;
	}
}
