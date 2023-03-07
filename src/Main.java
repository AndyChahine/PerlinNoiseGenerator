import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

public class Main {
	
	public static final int S = 1;
	public static final double P = 1d;
	public static final double F = 0.5d;
	public static final double A = 1.0d;
	public static final int O = 6;
	
	private static BufferedImage map;
	private static JLabel label_persistence;
	private static JLabel label_frequency;
	private static JLabel label_amplitude;
	private static JLabel label_octaves;
	
	private static int w = 128;
	private static int h = 128;
	
	public static void main(String[] args) {
		PerlinNoise noise = new PerlinNoise(S, P, F, A, O);
		map = noise.generateNoiseMap(w, h);
		
//		PerlinNoise elevation = new PerlinNoise(1, 1d, 0.01d, 1d, 6);
//		PerlinNoise moisture = new PerlinNoise(879, 1d, 0.005d, 1d, 6);
//		
//		map = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
//		for(int r = 0; r < map.getHeight(); r++) {
//			for(int c = 0; c < map.getWidth(); c++) {
//				double noiseVal = elevation.getHeight(c, r);
//				if(noiseVal >= 1d) { noiseVal = 1d; }
//				if(noiseVal <= -1d) { noiseVal = -1d; }
//				double elevationColor = (noiseVal * 0.5d) + 0.5d;
//				
//				double nx = 2d * c / map.getWidth() - 1d;
//				double ny = 2d * r / map.getHeight() - 1d;
//				
//				double dist = 1d - (1d - (Math.pow(nx, 2d))) * (1d - (Math.pow(ny, 2d)));
//				
//				elevationColor = (elevationColor + (1d - dist)) / 2d;
//				
//				double noiseVal2 = moisture.getHeight(c, r);
//				if(noiseVal2 >= 1d) { noiseVal2 = 1d; }
//				if(noiseVal2 <= -1d) { noiseVal2 = -1d; }
//				double moistureColor = (noiseVal2 * 0.5d) + 0.5d;
//				
//				Color col = getColor(elevationColor, moistureColor);
//				
//				map.setRGB(c, r, col.getRGB());
//			}
//		}
		
		JPanel panel = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(map, 0, 0, 512, 512, null);
			}
		};
		panel.setPreferredSize(new Dimension(512, 512));
		
		JFrame frame = new JFrame("Perlin Noise");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(384, 512));
		frame.getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 50, 200, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 50, 50, 50, 50, 50, 50, 50, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel_5 = new JLabel("Elevation Map");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 2;
		gbc_lblNewLabel_5.gridy = 0;
		panel_1.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JLabel lblNewLabel_3 = new JLabel("Width");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 1;
		panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JSpinner spinner_width = new JSpinner();
		spinner_width.setModel(new SpinnerNumberModel(w, 2, 512, 1));
		GridBagConstraints gbc_spinner_width = new GridBagConstraints();
		gbc_spinner_width.anchor = GridBagConstraints.WEST;
		gbc_spinner_width.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_width.gridx = 2;
		gbc_spinner_width.gridy = 1;
		spinner_width.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				w = (Integer)spinner_width.getValue();
				map = noise.generateNoiseMap(w, h);
				panel.repaint();
			}
		});
		panel_1.add(spinner_width, gbc_spinner_width);
		
		JLabel lblNewLabel_4 = new JLabel("Height");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 2;
		panel_1.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JSpinner spinner_height = new JSpinner();
		spinner_height.setModel(new SpinnerNumberModel(h, 2, 512, 1));
		GridBagConstraints gbc_spinner_height = new GridBagConstraints();
		gbc_spinner_height.anchor = GridBagConstraints.WEST;
		gbc_spinner_height.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_height.gridx = 2;
		gbc_spinner_height.gridy = 2;
		spinner_height.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				h = (Integer)spinner_height.getValue();
				map = noise.generateNoiseMap(w, h);
				panel.repaint();
			}
		});
		panel_1.add(spinner_height, gbc_spinner_height);
		
		JLabel lblNewLabel_2 = new JLabel("Seed");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		panel_1.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JSlider slider_persistence = new JSlider();
		slider_persistence.setMaximum(200);
		slider_persistence.setValue(100);
		
		JSpinner spinner_seed = new JSpinner();
		spinner_seed.setModel(new SpinnerNumberModel(noise.getSeed(), 0, 9999, 1));
		GridBagConstraints gbc_spinner_seed = new GridBagConstraints();
		gbc_spinner_seed.anchor = GridBagConstraints.WEST;
		gbc_spinner_seed.insets = new Insets(0, 0, 5, 5);
		gbc_spinner_seed.gridx = 2;
		gbc_spinner_seed.gridy = 3;
		spinner_seed.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				noise.setSeed((Integer)spinner_seed.getValue());
				map = noise.generateNoiseMap(w, h);
				panel.repaint();
			}
		});
		panel_1.add(spinner_seed, gbc_spinner_seed);
		
		JLabel lblNewLabel = new JLabel("Persistence");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 4;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		
		GridBagConstraints gbc_slider_persistence = new GridBagConstraints();
		gbc_slider_persistence.fill = GridBagConstraints.BOTH;
		gbc_slider_persistence.insets = new Insets(0, 0, 5, 5);
		gbc_slider_persistence.gridx = 2;
		gbc_slider_persistence.gridy = 4;
		panel_1.add(slider_persistence, gbc_slider_persistence);
		
		label_persistence = new JLabel(Double.toString(noise.getPersistence()));
		
		slider_persistence.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				noise.setPersistence(slider_persistence.getValue() / 100d);
				map = noise.generateNoiseMap(w, h);
				label_persistence.setText(Double.toString(slider_persistence.getValue() / 100d));
				panel.repaint();
			}
		});
		
		
		GridBagConstraints gbc_label_persistence = new GridBagConstraints();
		gbc_label_persistence.insets = new Insets(0, 0, 5, 0);
		gbc_label_persistence.fill = GridBagConstraints.BOTH;
		gbc_label_persistence.gridx = 3;
		gbc_label_persistence.gridy = 4;
		panel_1.add(label_persistence, gbc_label_persistence);
		
		JLabel lblNewLabel_1 = new JLabel("Frequency");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 5;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		label_frequency = new JLabel(Double.toString(noise.getFrequency()));
		
		JSlider slider_frequency = new JSlider();
		slider_frequency.setMaximum(1000);
		slider_frequency.setValue((int)(noise.getFrequency() * 1000d));
		GridBagConstraints gbc_slider_frequency = new GridBagConstraints();
		gbc_slider_frequency.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider_frequency.insets = new Insets(0, 0, 5, 5);
		gbc_slider_frequency.gridx = 2;
		gbc_slider_frequency.gridy = 5;
		slider_frequency.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				noise.setFrequency(slider_frequency.getValue() / 1000d);
				map = noise.generateNoiseMap(w, h);
				label_frequency.setText(Double.toString(noise.getFrequency()));
				panel.repaint();
			}
		});
		panel_1.add(slider_frequency, gbc_slider_frequency);
		
		
		GridBagConstraints gbc_label_frequency = new GridBagConstraints();
		gbc_label_frequency.anchor = GridBagConstraints.WEST;
		gbc_label_frequency.insets = new Insets(0, 0, 5, 0);
		gbc_label_frequency.gridx = 3;
		gbc_label_frequency.gridy = 5;
		panel_1.add(label_frequency, gbc_label_frequency);
		
		JLabel lblNewLabel_6 = new JLabel("Amplitude");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 1;
		gbc_lblNewLabel_6.gridy = 6;
		panel_1.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		label_amplitude = new JLabel(Double.toString(noise.getAmplitude()));
		
		JSlider slider_amplitude = new JSlider();
		slider_amplitude.setMinimum(-100);
		slider_amplitude.setValue((int)(noise.getAmplitude() * 10d));
		GridBagConstraints gbc_slider_amplitude = new GridBagConstraints();
		gbc_slider_amplitude.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider_amplitude.insets = new Insets(0, 0, 5, 5);
		gbc_slider_amplitude.gridx = 2;
		gbc_slider_amplitude.gridy = 6;
		slider_amplitude.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				noise.setAmplitude(slider_amplitude.getValue() / 10d);
				map = noise.generateNoiseMap(w, h);
				label_amplitude.setText(Double.toString(noise.getAmplitude()));
				panel.repaint();
			}
		});
		panel_1.add(slider_amplitude, gbc_slider_amplitude);
		
		GridBagConstraints gbc_label_amplitude = new GridBagConstraints();
		gbc_label_amplitude.anchor = GridBagConstraints.WEST;
		gbc_label_amplitude.insets = new Insets(0, 0, 5, 0);
		gbc_label_amplitude.gridx = 3;
		gbc_label_amplitude.gridy = 6;
		panel_1.add(label_amplitude, gbc_label_amplitude);
		
		JLabel lblNewLabel_7 = new JLabel("Octaves");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 1;
		gbc_lblNewLabel_7.gridy = 7;
		panel_1.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		label_octaves = new JLabel(Integer.toString(noise.getOctaves()));
		
		JSlider slider_octaves = new JSlider();
		slider_octaves.setValue(noise.getOctaves());
		slider_octaves.setMaximum(12);
		GridBagConstraints gbc_slider_octaves = new GridBagConstraints();
		gbc_slider_octaves.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider_octaves.insets = new Insets(0, 0, 5, 5);
		gbc_slider_octaves.gridx = 2;
		gbc_slider_octaves.gridy = 7;
		slider_octaves.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				noise.setOctaves(slider_octaves.getValue());
				map = noise.generateNoiseMap(w, h);
				label_octaves.setText(Integer.toString(noise.getOctaves()));
				panel.repaint();
			}
		});
		panel_1.add(slider_octaves, gbc_slider_octaves);
		
		GridBagConstraints gbc_label_octaves = new GridBagConstraints();
		gbc_label_octaves.insets = new Insets(0, 0, 5, 0);
		gbc_label_octaves.anchor = GridBagConstraints.WEST;
		gbc_label_octaves.gridx = 3;
		gbc_label_octaves.gridy = 7;
		panel_1.add(label_octaves, gbc_label_octaves);
		
		JButton exportButton = new JButton("Export");
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int res = fc.showSaveDialog(frame);
				if(res == JFileChooser.APPROVE_OPTION) {
					File outputFile = fc.getSelectedFile();
					String filename = outputFile.getName();
					if(filename.contains(".")) {
						filename = filename.substring(0, filename.lastIndexOf('.'));
					}
					filename += "." + "png";
					outputFile = new File(filename);
					try {
						ImageIO.write(map, "png", outputFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_exportButton = new GridBagConstraints();
		gbc_exportButton.insets = new Insets(0, 0, 0, 5);
		gbc_exportButton.gridx = 1;
		gbc_exportButton.gridy = 12;
		panel_1.add(exportButton, gbc_exportButton);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
//	public static Color getColor(double e, double m) {
//		
//		if(e < 0.1) {
//			return new Color(0, 0, 1f); // OCEAN
//		}
//		if(e < 0.12) {
//			return new Color(203, 175, 146); // BEACH
//		}
//		
//		if(e > 0.8) {
//			if(m < 0.1) {
//				return new Color(68,64,61); // SCORCHED
//			}
//			if(m < 0.2) {
//				return new Color(103,105,101); // BARE
//			}
//			if(m < 0.5) {
//				return new Color(124, 145, 156); // TUNDRA
//			}
//			
//			return new Color(255, 255, 255); // SNOW
//		}
//		
//		if(e > 0.6) {
//			if(m < 0.33) {
//				return new Color(224, 232, 166); // TEMPERATE DESERT
//			}
//			
//			if(m < 0.66) {
//				return new Color(143, 181, 164); // SHRUBLAND
//			}
//			
//			return new Color(155, 184, 125); // BOREAL FOREST
//		}
//		
//		if(e > 0.3) {
//			if(m < 0.16) {
//				return new Color(224, 232, 166); // TEMPERATE DESERT
//			}
//			
//			if(m < 0.50) {
//				return new Color(22,184,90); // GRASSLAND
//			}
//			
//			if(m < 0.83) {
//				return new Color(156, 176, 65); // TEMPERATE DECIDUOUS FOREST
//			}
//			
//			return new Color(62, 168, 112); // TEMPERATE RAIN FOREST
//		}
//		
//		if(m < 0.16) {
//			return new Color(250, 213, 165); // SUBTROPICAL DESERT
//		}
//		
//		if(m < 0.33) {
//			return new Color(22,184,90); // GRASSLAND
//		}
//		
//		if(m < 0.66) {
//			return new Color(0, 159, 125); // TROPICAL SEASONAL FOREST
//		}
//		
//		return new Color(0, 111, 117); // TROPICAL RAIN FOREST
//	}
}
