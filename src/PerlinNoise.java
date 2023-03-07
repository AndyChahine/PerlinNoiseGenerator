import java.awt.Color;
import java.awt.image.BufferedImage;

public class PerlinNoise {
	
	private int octaves;
	private double amplitude;
	private double frequency;
	private double persistence;
	private int seed;
	
	public PerlinNoise(int seed, double persistence, double frequency, double amplitude, int octaves) {
		set(seed, persistence, frequency, amplitude, octaves);
	}

	public PerlinNoise() {
		set(0,0,0,0,0);
	}
	
	public double getHeight(double x, double y) {
		return amplitude * total(x,y);
	}
	
	public int getSeed() {
		return seed;
	}
	
	public int getOctaves() {
		return octaves;
	}
	
	public double getAmplitude() {
		return amplitude;
	}
	
	public double getFrequency() {
		return frequency;
	}
	
	public double getPersistence() {
		return persistence;
	}
	
	public final void set(int seed, double persistence, double frequency, double amplitude, int octaves) {
		this.seed = 2 + seed * seed;
		this.octaves = octaves;
		this.amplitude = amplitude;
		this.frequency = frequency;
		this.persistence = persistence;
	}
	
	public void setSeed(int seed) {
		this.seed = 2 + seed * seed;
	}
	
	public void setOctaves(int octaves) {
		this.octaves = octaves;
	}
	
	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}
	
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	
	public void setPersistence(double persistence) {
		this.persistence = persistence;
	}
	
	public BufferedImage generateNoiseMap(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for(int r = 0; r < image.getHeight(); r++) {
			for(int c = 0; c < image.getWidth(); c++) {
				double val = getHeight(c, r);
				if(val >= 1d) { val = 1d; }
				if(val <= -1d) { val = -1d; }
				double noiseVal = (val * 0.5d) + 0.5d;
				
				image.setRGB(c, r, new Color((float)noiseVal, (float)noiseVal, (float)noiseVal).getRGB());
			}
		}
		
		return image;
	}

	private double total(double x, double y) {
		double t = 0.0;
		double amp = 1;
		double freq = frequency;
		
		for(int k = 0; k < octaves; k++) {
	        t += getValue(y * freq + seed, x * freq + seed) * amp;
	        amp *= persistence;
	        freq *= 2;
	    }

	    return t;
	}

	private double getValue(double x, double y) {
		int Xint = (int) x;
		int Yint = (int) y;
		double Xfrac = x - Xint;
		double Yfrac = y - Yint;

		double n01 = noise(Xint - 1, Yint - 1);
		double n02 = noise(Xint + 1, Yint - 1);
		double n03 = noise(Xint - 1, Yint + 1);
		double n04 = noise(Xint + 1, Yint + 1);
		double n05 = noise(Xint - 1, Yint);
		double n06 = noise(Xint + 1, Yint);
		double n07 = noise(Xint, Yint - 1);
		double n08 = noise(Xint, Yint + 1);
		double n09 = noise(Xint, Yint);

		double n12 = noise(Xint + 2, Yint - 1);
		double n14 = noise(Xint + 2, Yint + 1);
		double n16 = noise(Xint + 2, Yint);

		double n23 = noise(Xint - 1, Yint + 2);
		double n24 = noise(Xint + 1, Yint + 2);
		double n28 = noise(Xint, Yint + 2);

		double n34 = noise(Xint + 2, Yint + 2);

		double x0y0 = 0.0625 * (n01 + n02 + n03 + n04) + 0.125 * (n05 + n06 + n07 + n08) + 0.25 * (n09);
		double x1y0 = 0.0625 * (n07 + n12 + n08 + n14) + 0.125 * (n09 + n16 + n02 + n04) + 0.25 * (n06);
		double x0y1 = 0.0625 * (n05 + n06 + n23 + n24) + 0.125 * (n03 + n04 + n09 + n28) + 0.25 * (n08);
		double x1y1 = 0.0625 * (n09 + n16 + n28 + n34) + 0.125 * (n08 + n14 + n06 + n24) + 0.25 * (n04);

		double v1 = interpolate(x0y0, x1y0, Xfrac);
		                                            
		double v2 = interpolate(x0y1, x1y1, Xfrac);
		                                            
		double fin = interpolate(v1, v2, Yfrac);

		return fin;
	}

	private double interpolate(double x, double y, double a) {
		double negA = 1.0 - a;
		double negASqr = negA * negA;
		double fac1 = 3.0 * (negASqr) - 2.0 * (negASqr * negA);
		double aSqr = a * a;
		double fac2 = 3.0 * aSqr - 2.0 * (aSqr * a);

		return x * fac1 + y * fac2;
	}

	private double noise(int x, int y) {
		int n = x + y * 57;
		n = (n << 13) ^ n;
		int t = (n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff;
		return 1.0 - (double) (t) * 0.931322574615478515625e-9;
	}
}
