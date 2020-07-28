package Rezeptbuch;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;


public class Rezeptbuch extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEnterRecipeName;
	private JTextField txtEnterIngredientName;
	private JTextField txtEnterIngredientAmount;
	private JTextArea txtrWriteRecipeHere;
	private static JTable table;
	private JTable table_1;

	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Rezeptbuch();
					recipeListUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	// layout mit funktionen
	Rezeptbuch() {
		JFrame frame = new JFrame();
		frame.setTitle("Rezeptbuch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(320, 180, 1280, 720);
		frame.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(250, 240, 230));
		panel.setBorder(new MatteBorder(7, 7, 7, 7, (Color) new Color(0, 0, 128)));
		panel.setBounds(10, 11, 1244, 659);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_list = new JPanel();
		panel_list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_list.setBounds(30, 91, 300, 539);
		panel.add(panel_list);
		panel_list.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_list.add(scrollPane);
		
		
		
		// tabelle für rezept verwaltung
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//beim klicken des rezeptnamens werden allle gespeicherten daten aufgerufen
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String tbl = model.getValueAt(table.getSelectedRow(), 0).toString();
				
				table_1.setModel(new DefaultTableModel(null, new String[] {"Ingredient", "Amount", "Unit"} ));
				
				txtEnterRecipeName.setText(tbl);
				
				if(table.getSelectedRow() != -1) {
				int column = 0;
				int row = table.getSelectedRow();
				String nameForLoad = table.getModel().getValueAt(row, column).toString() + ".txt";
				
				
				//Der Freitext wird aus der Datei gelesen und im Freitextfeld eingefügt
		        try	(
		        		Reader myReader = new BufferedReader(new FileReader(nameForLoad))) {
		        		txtrWriteRecipeHere.read(myReader, null);
		        	}
		        
		        catch(FileNotFoundException e1) {
		        	JOptionPane.showMessageDialog(null, "no ingredients were created");
		        }
		        
		        catch(IOException e2) {
		        	e2.printStackTrace();	
		        }
				
				File folder = new File("src/Zutaten");
							
				String ztt = txtEnterRecipeName.getText() + "Zutaten.txt";
				
				
				//Die Datei mit den Zutaten wird aufgerufen und in die Tabelle eingefügt
				try {
					
					File file4 = new File(folder, ztt);
					FileReader fr2 = new FileReader(file4);
					BufferedReader br = new BufferedReader(fr2);
					
					DefaultTableModel model4 = (DefaultTableModel) table_1.getModel();
					Object[] lines = br.lines().toArray();
					
					for(int i = 0; i < lines.length; i++) {
						String[] row2 = lines[i].toString().split(" ");
						model4.addRow(row2);
					}
					
					if(file4.length() == 0) {
						JOptionPane.showMessageDialog(null, "no ingredients were created");
					}
					fr2.close();
					br.close();
					
				} 
				catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null, "no ingredients were created");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				
				txtEnterIngredientName.setText("Enter Ingredient name");
				txtEnterIngredientAmount.setText("Enter Ingredient amount");
				
				}
			}
		});
		
		// standard parameter für die tabelle
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name"
			}
		) {
			
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		scrollPane.setViewportView(table);
		
		JPanel panel_search = new JPanel();
		panel_search.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_search.setBounds(30, 30, 300, 50);
		panel.add(panel_search);
		panel_search.setLayout(new GridLayout(1, 0, 0, 0));
		
		// dem textfeld wird die suchfunktion (methode am ende) hinzugefügt
		JTextField txtSearchForRecipe = RowFilterUtil.createRowFilter(table);
		txtSearchForRecipe.setText("");
		txtSearchForRecipe.setToolTipText("");
		panel_search.add(txtSearchForRecipe);
		txtSearchForRecipe.setColumns(10);
		
		JPanel panel_name = new JPanel();
		panel_name.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_name.setBounds(470, 30, 300, 50);
		panel.add(panel_name);
		panel_name.setLayout(new GridLayout(1, 0, 0, 0));
		
		txtEnterRecipeName = new JTextField();
		txtEnterRecipeName.setText("Enter recipe name");
		txtEnterRecipeName.setToolTipText("");
		panel_name.add(txtEnterRecipeName);
		txtEnterRecipeName.setColumns(10);
		
		JPanel panel_add_recipe = new JPanel();
		panel_add_recipe.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_add_recipe.setBounds(900, 91, 300, 50);
		panel.add(panel_add_recipe);
		panel_add_recipe.setLayout(new CardLayout(0, 0));
		
		JPanel panel_freetext = new JPanel();
		panel_freetext.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_freetext.setBounds(470, 91, 300, 290);
		panel.add(panel_freetext);
		panel_freetext.setLayout(new GridLayout(0, 1, 0, 0));
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_freetext.add(scrollPane_2);
		
		txtrWriteRecipeHere = new JTextArea();
		txtrWriteRecipeHere.setText("Write recipe here");
		txtrWriteRecipeHere.setToolTipText("");
		scrollPane_2.setViewportView(txtrWriteRecipeHere);
		
		JPanel panel_ingredient_unit = new JPanel();
		panel_ingredient_unit.setBounds(715, 461, 55, 50);
		panel.add(panel_ingredient_unit);
		panel_ingredient_unit.setLayout(new CardLayout(0, 0));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"unit", "kg", "g", "l", "ml", "other"}));
		panel_ingredient_unit.add(comboBox, "name_39842455498200");
		
		
		
		// wenn der button add recipe gedrückt wird, wird das rezept in die "datenbank" hinzugefügt
		JButton btnNewButton = new JButton("Add recipe");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			    try {

				String recipeText = txtrWriteRecipeHere.getText();
				
				File file = new File(txtEnterRecipeName.getText() + ".txt");
		        file.setWritable(true);
		        file.setReadable(true);
				
		        
		        //Der Freitext wird in eine Datei gespeichert, falls es eine Datei mit dem Rezeptnamen nicht schon gibt
		        if(file.exists() == false) {
			    FileWriter fw = new FileWriter(file);
			    System.out.println(file.getAbsoluteFile());
			    
			    String recipeFullText = recipeText;
			    fw.write(recipeFullText);
			    fw.close();
			    
			    
			    //Rezeptname wird in die Tabelle eingefügt
	    		DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {txtEnterRecipeName.getText()});
				
			    JOptionPane.showMessageDialog(null, "Recipe successfully added");
		        }
		        
		        else {
		        	JOptionPane.showMessageDialog(null, "Recipe with this name already exists");
		        }
				}
			    catch (IOException e) {
					e.printStackTrace();
				}
			    
			    
				//Verzeichnis Zutaten wird erstellt
				File folder = new File("src/Zutaten");
				folder.mkdir();
				
				String zt = txtEnterRecipeName.getText() + "Zutaten.txt";
				
				File file3 = new File(folder, zt);

				
				//Die Zutaten werden im Verzeichnis Zutaten, in einer Datei gespeichert
				try {
										
					FileWriter fw = new FileWriter(file3);
					BufferedWriter bw = new BufferedWriter(fw);
					
					for(int i = 0; i < table_1.getRowCount(); i++) {
						for(int j = 0; j < table_1.getColumnCount(); j++) {
							
							bw.write(table_1.getValueAt(i, j).toString() + " ");
							
						}
						bw.newLine();
					}
					
					bw.close();
					fw.close();
									
					
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			    
				// und die eingabefelder in die standard einstellung zurückgesetzt
			    txtEnterRecipeName.setText("Enter recipe name");
			    txtrWriteRecipeHere.setText("Write recipe here");
				txtEnterIngredientName.setText("Enter Ingredient name");
				txtEnterIngredientAmount.setText("Enter Ingredient amount");
			    table_1.setModel(new DefaultTableModel(null, new String[] {"Ingredient", "Amount", "Unit"} ));					
				}
			
			}
		);
		panel_add_recipe.add(btnNewButton, "name_39649647792600");
		
		JPanel panel_ingredient = new JPanel();
		panel_ingredient.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_ingredient.setBounds(470, 400, 300, 50);
		panel.add(panel_ingredient);
		panel_ingredient.setLayout(new GridLayout(1, 0, 0, 0));
		
		txtEnterIngredientName = new JTextField();
		txtEnterIngredientName.setText("Enter ingredient name here");
		txtEnterIngredientName.setToolTipText("");
		panel_ingredient.add(txtEnterIngredientName);
		txtEnterIngredientName.setColumns(10);
		
		JPanel panel_ingredient_amount = new JPanel();
		panel_ingredient_amount.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_ingredient_amount.setBounds(470, 461, 235, 50);
		panel.add(panel_ingredient_amount);
		panel_ingredient_amount.setLayout(new GridLayout(1, 0, 0, 0));
		
		txtEnterIngredientAmount = new JTextField();
		txtEnterIngredientAmount.setText("Enter ingredient amount here");
		txtEnterIngredientAmount.setToolTipText("");
		panel_ingredient_amount.add(txtEnterIngredientAmount);
		txtEnterIngredientAmount.setColumns(10);
		
		JPanel panel_change_recipe = new JPanel();
		panel_change_recipe.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_change_recipe.setBounds(900, 152, 300, 50);
		panel.add(panel_change_recipe);
		panel_change_recipe.setLayout(new CardLayout(0, 0));
		
		
		
		// das aus der tabelle ausgewählte rezept wird gelöscht
		JButton btnNewButton_1 = new JButton("Delete recipe");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if(table.getSelectedRow() != -1) {
				int column = 0;
				int row = table.getSelectedRow();
				String nameForDelete = table.getModel().getValueAt(row, column).toString() + ".txt";
				
				File folder = new File("src/Zutaten");
				String zt = table.getModel().getValueAt(row, column).toString() + "Zutaten.txt";
				File file5 = new File(folder, zt);

				
				//Die gespeicherten daten werden gelöscht
		        try {
		            Files.deleteIfExists(Paths.get(nameForDelete));
		            file5.delete();
		            JOptionPane.showMessageDialog(null, "Recipe successfully deleted");
		        } 
		        catch(NoSuchFileException e) 
		        { 
		        	JOptionPane.showMessageDialog(null, "No such file/directory exists");		            
		        } 
		        catch(DirectoryNotEmptyException e) 
		        { 
		        	JOptionPane.showMessageDialog(null, "Directory is not empty.");	
		        } 
		        catch(IOException e) 
		        { 
		        	JOptionPane.showMessageDialog(null, "Invalid permissions.");	
		        }
				
				int rowindex = table.getSelectedRow();
				model.removeRow(rowindex);
				txtEnterRecipeName.setText("Enter recipe name");
				txtrWriteRecipeHere.setText("Write recipe here");
				table_1.setModel(new DefaultTableModel(null, new String[] {"Ingredient", "Amount", "Unit"} ));
				}
			}
		});
		panel_change_recipe.add(btnNewButton_1, "name_39659656090600");
		
		JPanel panel_delete_recipe = new JPanel();
		panel_delete_recipe.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_delete_recipe.setBounds(900, 213, 300, 50);
		panel.add(panel_delete_recipe);
		panel_delete_recipe.setLayout(new CardLayout(0, 0));
		
		JButton btnNewButton_2 = new JButton("Empty inputs");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//Jedes Eingabefeld wird zurückgesetzt
				txtrWriteRecipeHere.setText("");
				txtEnterRecipeName.setText("");
				txtEnterIngredientName.setText("");
				txtEnterIngredientAmount.setText("");
				table_1.setModel(new DefaultTableModel(null, new String[] {"Ingredient", "Amount", "Unit"} ));	
				
			}
		});
		panel_delete_recipe.add(btnNewButton_2, "name_39669647657600");
		
		JPanel panel_main_ingredients_diagram = new JPanel();
		panel_main_ingredients_diagram.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_main_ingredients_diagram.setBounds(900, 335, 300, 50);
		panel.add(panel_main_ingredients_diagram);
		panel_main_ingredients_diagram.setLayout(new CardLayout(0, 0));
		
		JFrame secondFrame = new JFrame("Distribution");
		secondFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		secondFrame.setBounds(320, 180, 900, 480);
		secondFrame.setLocationRelativeTo(panel);	

		JPanel secondPanel = new JPanel();
		secondPanel.setBackground(new Color(250, 240, 230));
		secondPanel.setBorder(new MatteBorder(7, 7, 7, 7, (Color) new Color(0, 0, 128)));
		secondPanel.setBounds(10, 11, 900, 480);
		contentPane.add(panel);
		secondPanel.setLayout(null);
		
		
		
		// eine grafische verteilung der am häufigsten auftretenden bestandteile wird hier dargestellt
		JButton btnNewButton_3 = new JButton("Show distribution");
		btnNewButton_3.addActionListener(new ActionListener() {
			private BufferedReader br;

			
			// aus der zutaten datei werden die zutaten rausgelesen
			public void actionPerformed(ActionEvent e){
				
				
				// läd den frame neu
				frame.dispose();
				new Rezeptbuch();
				recipeListUpdate();
				
				try {

		        File dir = new File("src/Zutaten"); 

		        PrintWriter pw = new PrintWriter("src/output.txt"); 

		        String[] fileNames = dir.list(); 
		  
		        for (String fileName : fileNames) { 

		            File f = new File(dir, fileName); 
		  
		            br = new BufferedReader(new FileReader(f)); 

		            String line = br.readLine(); 
		            while (line != null) { 
		            	
		            	String value = line.toLowerCase();
		            	String words1 = firstWords(value, 1);
		                pw.println(words1); 
		                line = br.readLine(); 
		            } 
		            pw.flush();   
		        } 
		        pw.close();
		        br.close();

		        
		        
		        // aus dieser rausgelesenen datei werden die gleichen einträge zusammengezählt 
		        Map<String, Long> dupes = new HashMap<String, Long>();
		        dupes = Files.lines(Paths.get("src/output.txt"))
		                .collect(Collectors.groupingBy(Function.identity(), 
		                     Collectors.counting()));

		        LinkedHashMap<String, Long> reverseMap = new LinkedHashMap<>();
		         
		        
		        // und in absteigender reihenfolge sortiert
		        dupes.entrySet()
		            .stream()
		            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
		            .forEachOrdered(x -> reverseMap.put(x.getKey(), x.getValue()));
		      
				Long[] valuesLong = reverseMap.values().toArray(new Long[dupes.size()]);
			    String[] names = reverseMap.keySet().toArray(new String[dupes.size()]);
			    
			    double[] values = new double[0];
			    values = convertLongToDoubles(valuesLong);
			    
			    
			    // dannach werden die werte in die zeichenfunktion übergeben
			    try {
			    	values[0] = values[0]; 
			    	names[0] = names[0] + ": " + valuesLong[0]; 


			    	values[1] =  values[1]; 
			    	names[1] = names[1] + ": " + valuesLong[1]; 


			    	values[2] = values[2]; 
			    	names[2] = names[2] + ": " + valuesLong[2]; 


			    	values[3] = values[3]; 
			    	names[3] = names[3] + ": " + valuesLong[3]; 

			    	values[4] = values[4]; 
				    names[4] = names[4] + ": " + valuesLong[4]; 
			    }
			    
				catch(ArrayIndexOutOfBoundsException e1) {
					System.out.println("");
				}
			    
			    ChartPanel drawPanel = new ChartPanel(values, names, "Ingredients used in most recipes");
			    secondFrame.add(drawPanel);
		        
				}
				catch (IOException x) {
					x.printStackTrace();
				}
				
			    secondFrame.setVisible(true);

			}
		});
		panel_main_ingredients_diagram.add(btnNewButton_3, "name_39671767661000");
		
		JPanel panel_ingredient_add = new JPanel();
		panel_ingredient_add.setBounds(780, 520, 70, 50);
		panel.add(panel_ingredient_add);
		panel_ingredient_add.setLayout(new CardLayout(0, 0));
		
		
		// button um zutaten hinzuzufügen
		JButton btnNewButton_4 = new JButton("add");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				
				//Zutaten werden in einer Tabelle eingefügt
				DefaultTableModel model2 = (DefaultTableModel) table_1.getModel();
				model2.addRow(new Object[] {txtEnterIngredientName.getText(), txtEnterIngredientAmount.getText(), comboBox.getSelectedItem() });				
				
				JOptionPane.showMessageDialog(null, "Ingredients successfully added");
				
				txtEnterIngredientName.setText("Enter Ingredient name");
				txtEnterIngredientAmount.setText("Enter Ingredient amount");
			}
		});
		panel_ingredient_add.add(btnNewButton_4, "name_40440830809200");
		
		
		JPanel panel_ingredient_list = new JPanel();
		panel_ingredient_list.setBounds(470, 522, 300, 108);
		panel.add(panel_ingredient_list);
		panel_ingredient_list.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_ingredient_list.add(scrollPane_1);
		
		// die zweite tabelle für die zutaten
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ingredient", "Amount", "Unit"
			}
		) {
			
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		scrollPane_1.setViewportView(table_1);
		
		JPanel panel_ingredient_delete = new JPanel();
		panel_ingredient_delete.setBounds(780, 580, 70, 50);
		panel.add(panel_ingredient_delete);
		panel_ingredient_delete.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		//Die ausgewählten zutaten werden wieder gelöscht
		JButton btnNewButton_5 = new JButton("delete");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel model2 = (DefaultTableModel) table_1.getModel();
				int rowindex2 = table_1.getSelectedRow();
				
				
				//Die Zutaten werden aus der Tabelle entfernt
				if(table_1.getSelectedRow() != -1) {
				model2.removeRow(rowindex2);
				
				txtEnterIngredientName.setText("Enter ingredient name here");
				txtEnterIngredientAmount.setText("Enter ingredient amount here");
				
				}
				
				File folder = new File("src/Zutaten");				
				String zt = txtEnterRecipeName.getText() + "Zutaten.txt";				
				File file3 = new File(folder, zt);

				
				//Die Datei mit den Zutaten wird nun umgeschrieben mit den neuen Zutaten
				try {
					FileWriter fw = new FileWriter(file3);
					BufferedWriter bw = new BufferedWriter(fw);
					
					for(int i = 0; i < table_1.getRowCount(); i++) {
						for(int j = 0; j < table_1.getColumnCount(); j++) {
							
							bw.write(table_1.getValueAt(i, j).toString() + " ");
							
						}
						bw.newLine();
					}
					bw.close();
					fw.close();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}			
			}
		});
		panel_ingredient_delete.add(btnNewButton_5);
		
		JPanel panel_change = new JPanel();
		panel_change.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_change.setBounds(900, 274, 300, 50);
		panel.add(panel_change);
		panel_change.setLayout(new CardLayout(0, 0));
		
		
		// eine änderung durch den chagne button speichern
		JButton btnChangeInputs = new JButton("Change/save recipe");
		btnChangeInputs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String oldrecipename = model.getValueAt(table.getSelectedRow(), 0).toString() + ".txt";
				String oldingredientsname = model.getValueAt(table.getSelectedRow(), 0).toString() + "Zutaten.txt";
				
				
				//Der neue Rezeptname wird in der tabelle umgechrieben
				if(table.getSelectedRowCount() == 1) {
					String recipename = txtEnterRecipeName.getText();
					
					model.setValueAt(recipename, table.getSelectedRow(), 0);					
				}
				
				
				//Die Datei mit dem Rezeptnamen wird geändert
				File file = new File(oldrecipename);
				
				String newname = txtEnterRecipeName.getText() + ".txt";
				File newfile = new File(newname);			
				file.renameTo(newfile);
				
				
				//Die Datei mit dem Zutaten wird geändert
				File folder = new File("src/Zutaten");				
				File file3 = new File(folder, oldingredientsname);
				String ztt = txtEnterRecipeName.getText() + "Zutaten.txt";		
				File newzt = new File(folder, ztt);
				file3.renameTo(newzt);
				
				
				//Der Freitext wird geändert
				try {
					
					String recipeText = txtrWriteRecipeHere.getText();
									
					FileWriter fw = new FileWriter(newfile);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(recipeText);
					bw.close();
					fw.close();
			
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				

				//Die Zutaten Datei wird neu geschrieben und somit verändert
				try {
										
					FileWriter fw = new FileWriter(newzt);
					BufferedWriter bw = new BufferedWriter(fw);
					
					for(int i = 0; i < table_1.getRowCount(); i++) {
						for(int j = 0; j < table_1.getColumnCount(); j++) {
							bw.write(table_1.getValueAt(i, j).toString() + " ");
						}
						bw.newLine();
					}
					bw.close();
					fw.close();		
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
						
				txtEnterRecipeName.setText("Enter recipe name");
				txtrWriteRecipeHere.setText("Write Recipe Here");
				txtEnterIngredientName.setText("Enter Ingredient name");
				txtEnterIngredientAmount.setText("Enter Ingredient amount");
				table_1.setModel(new DefaultTableModel(null, new String[] {"Ingredient", "Amount", "Unit"} ));
			}
		});
		panel_change.add(btnChangeInputs, "name_178572106133400");
	}
		
	
		//Methode um die gespeicherten Rezeptnamen beim Start des Programms aufzurufen
		//Wird in der Main Methode aufgerufen
		public static void recipeListUpdate() {
				Path currentRelativePath = Paths.get("");
				String s = currentRelativePath.toAbsolutePath().toString();
				
				File directoryPath = new File(s);
			      FilenameFilter textFilefilter = new FilenameFilter(){
			         public boolean accept(File dir, String name) {
			            String lowercaseName = name.toLowerCase();
			            if (lowercaseName.endsWith(".txt")) {
			               return true;
			            } else {
			               return false;
			            }
			         }
			      };
			  String textFilesList[] = directoryPath.list(textFilefilter);
		      DefaultTableModel model3 = (DefaultTableModel) table.getModel(); 
		      for(String fileNameWithExtention : textFilesList) {
		         int pos = fileNameWithExtention.lastIndexOf(".");
		         if (pos > 0 && pos < (fileNameWithExtention.length() - 1)) { 
		             String fileName = fileNameWithExtention.substring(0, pos);
		             model3.addRow(new String[] {fileName});
		         }
		         else if (fileNameWithExtention.indexOf(".") > 0) {
		        	   String fileName = fileNameWithExtention.substring(0, fileNameWithExtention.lastIndexOf("."));
						 model3.addRow(new String[] {fileName});
		        	} else {
		        	   String fileName =  fileNameWithExtention;
						 model3.addRow(new String[] {fileName});
		        	}
		      }
		}
		
		
		// methode um die zutaten aus der zutaten liste raus zu lesen
		public static String firstWords(String input, int words) {
	        for (int i = 0; i < input.length(); i++) {
	            if (input.charAt(i) == ' ') {
	                words--;
	            }
	            if (words == 0) {
	                return input.substring(0, i);
	            }
	        }
	        return "";
	    }
		
		
		// methode um die anzahl der gleichen zutaten " weiter verrechenbar" zu machen
		public static double[] convertLongToDoubles(Long[] valuesLong)
		{
		    if (valuesLong == null)
		    {
		        return null; 
		    }
		    double[] output = new double[valuesLong.length];
		    for (int i = 0; i < valuesLong.length; i++)
		    {
		        output[i] = valuesLong[i];
		    }
		    return output;
		}
}


//Nach rezeptnamen suchen durch eingabe (und filter) in das textfeld
class RowFilterUtil {
  public static JTextField createRowFilter(JTable table) {
      RowSorter<? extends TableModel> rs = table.getRowSorter();
      if (rs == null) {
          table.setAutoCreateRowSorter(true);
          rs = table.getRowSorter();
      }
      TableRowSorter<? extends TableModel> rowSorter =
              (rs instanceof TableRowSorter) ? (TableRowSorter<? extends TableModel>) rs : null;
      if (rowSorter == null) {
          throw new RuntimeException("Cannot find appropriate rowSorter: " + rs);
      }
      final JTextField tf = new JTextField(15);
      tf.getDocument().addDocumentListener(new DocumentListener() {
          @Override
          public void insertUpdate(DocumentEvent e) {
              update(e);
          }
          @Override
          public void removeUpdate(DocumentEvent e) {
              update(e);
          }
          @Override
          public void changedUpdate(DocumentEvent e) {
              update(e);
          }
          private void update(DocumentEvent e) {
              String text = tf.getText();
              if (text.trim().length() == 0) {
                  rowSorter.setRowFilter(null);
              } else {
                  rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
              }
          }
      });
      return tf;
  }
}


//methode um das balkendiagramm zu zeichnen
class ChartPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private double[] values;
	private String[] names;
	private String title;

	  public ChartPanel(double[] v, String[] n, String t) {
	    names = n;
	    values = v;
	    title = t;
	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (values == null || values.length == 0)
	      return;
	    
	    double minValue = 0;
	    double maxValue = 0;
	    
	    for (int i = 0; i < values.length; i++) {
	    	
	      if (minValue > values[i])
	        minValue = values[i];
	      
	      if (maxValue < values[i])
	        maxValue = values[i];
	    }

	    Dimension d = getSize();
	    int clientWidth = d.width;
	    int clientHeight = d.height;
	    int barWidth = (clientWidth / values.length);

	    Font titleFont = new Font("SansSerif", Font.BOLD, 18);
	    FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
	    
	    Font labelFont = new Font("SansSerif", Font.PLAIN, 14);
	    FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

	    int titleWidth = titleFontMetrics.stringWidth(title);
	    int y = titleFontMetrics.getAscent();
	    int x = (clientWidth - titleWidth) / 2;
	    g.setFont(titleFont);
	    g.drawString(title, x, y);

	    int top = titleFontMetrics.getHeight();
	    int bottom = labelFontMetrics.getHeight();
	    if (maxValue == minValue)
	      return;
	    
	    double scale = (clientHeight - top - bottom) / (maxValue - minValue);
	    y = clientHeight - labelFontMetrics.getDescent();
	    g.setFont(labelFont);

	    for (int i = 0; i < values.length; i++) {
	      int valueX = i * barWidth + 1;
	      int valueY = top;
	      int height = (int) (values[i] * scale);
	      if (values[i] >= 0)
	        valueY += (int) ((maxValue - values[i]) * scale);
	      else {
	        valueY += (int) (maxValue * scale);
	        height = -height;
	      }
	      
	      g.setColor(new Color(51, 153, 255));
	      g.fillRect(valueX, valueY, barWidth - 2, height);
	      g.setColor(Color.black);
	      g.drawRect(valueX, valueY, barWidth - 2, height);
	      int labelWidth = labelFontMetrics.stringWidth(names[i]);
	      x = i * barWidth + (barWidth - labelWidth) / 2;
	      g.drawString(names[i], x, y);
	    }
	  }
}

