package secondGUI;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class secondGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSearchForRecipe;
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
					secondGUI frame = new secondGUI();
					frame.setVisible(true);
					recipeListUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		}

	@SuppressWarnings({ "unchecked", "rawtypes" }) secondGUI() {
		setTitle("RecipeManager");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(320, 180, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
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
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				String tbl = model.getValueAt(table.getSelectedRow(), 0).toString();
				
				table_1.setModel(new DefaultTableModel(null, new String[] {"Ingredient", "Amount", "Unit"} ));
				
				txtEnterRecipeName.setText(tbl);
				
				DefaultTableModel model1 = (DefaultTableModel) table.getModel();
				if(table.getSelectedRow() != -1) {
				int column = 0;
				int row = table.getSelectedRow();
				String nameForLoad = table.getModel().getValueAt(row, column).toString() + ".txt";
				
		        try	(Reader myReader = new BufferedReader(new FileReader(nameForLoad))) {
		        		txtrWriteRecipeHere.read(myReader, null);
		        	}
		        catch(FileNotFoundException e1) {
		        	System.out.println("Keine Zutaten angelegt");
		        }
		        catch(IOException e2) {
		        	e2.printStackTrace();	
		        }
				
				File folder = new File("src/Zutaten");
				
				
				String ztt = txtEnterRecipeName.getText() + "Zutaten.txt";
				

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
				} 
				catch (FileNotFoundException e1) {
					System.out.println("Keine Zutaten angelegt");
				}
				
				
				}
			}
		});
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
		
		txtSearchForRecipe = new JTextField();
		txtSearchForRecipe.setText("Search for recipe name");
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
		
		JButton btnNewButton = new JButton("Add recipe");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			    try {
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {txtEnterRecipeName.getText()});
				
				String recipeText = txtrWriteRecipeHere.getText();
				
				File file = new File(txtEnterRecipeName.getText() + ".txt");
		        file.setWritable(true);
		        file.setReadable(true);
				
			    FileWriter fw = new FileWriter(file);
			    System.out.println(file.getAbsoluteFile());
			    
			    String recipeFullText = recipeText;
			    fw.write(recipeFullText);
	    	
			    	JOptionPane.showMessageDialog(null, "Recipe successfully added");
					fw.close();
				} 
			    catch (IOException e) {
					e.printStackTrace();
				}
			    
			    table_1.setModel(new DefaultTableModel(null, new String[] {"Ingredient", "Amount", "Unit"} ));
			    
			    txtEnterRecipeName.setText("Enter recipe name");
			    txtrWriteRecipeHere.setText("Write recipe here");
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
		
		JButton btnNewButton_1 = new JButton("Delete recipe");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if(table.getSelectedRow() != -1) {
				int column = 0;
				int row = table.getSelectedRow();
				String nameForDelete = table.getModel().getValueAt(row, column).toString() + ".txt";
				
		        try {
		            Files.deleteIfExists(Paths.get(nameForDelete));
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
		
		JButton btnNewButton_3 = new JButton("Show distribution");
		panel_main_ingredients_diagram.add(btnNewButton_3, "name_39671767661000");
		
		JPanel panel_ingredient_add = new JPanel();
		panel_ingredient_add.setBounds(780, 520, 70, 50);
		panel.add(panel_ingredient_add);
		panel_ingredient_add.setLayout(new CardLayout(0, 0));
		
		JButton btnNewButton_4 = new JButton("add");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel model2 = (DefaultTableModel) table_1.getModel();
				model2.addRow(new Object[] {txtEnterIngredientName.getText(), txtEnterIngredientAmount.getText(), comboBox.getSelectedItem() });
				
				String tbl2 = txtEnterRecipeName.getText();
				
				File folder = new File("src/Zutaten");
				folder.mkdir();
				
				String zt = txtEnterRecipeName.getText() + "Zutaten.txt";
				
				File file3 = new File(folder, zt);

				
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
		
		JButton btnNewButton_5 = new JButton("delete");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel model2 = (DefaultTableModel) table_1.getModel();
				int rowindex2 = table_1.getSelectedRow();
				
				if(table_1.getSelectedRow() != -1) {
				model2.removeRow(rowindex2);
				
				txtEnterIngredientName.setText("Enter ingredient name here");
				txtEnterIngredientAmount.setText("Enter ingredient amount here");
				
				}
				
				
			}
		});
		panel_ingredient_delete.add(btnNewButton_5);
		
		JPanel panel_change = new JPanel();
		panel_change.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_change.setBounds(900, 274, 300, 50);
		panel.add(panel_change);
		panel_change.setLayout(new CardLayout(0, 0));
		
		JButton btnChangeInputs = new JButton("Change inputs");
		btnChangeInputs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if(table.getSelectedRowCount() == 1) {
					String recipename = txtEnterRecipeName.getText();
					
					model.setValueAt(recipename, table.getSelectedRow(), 0);
					txtEnterRecipeName.setText("Enter recipe name");
				}
				
				
			}
		});
		
		panel_change.add(btnChangeInputs, "name_178572106133400");
	}
		
		
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
		         System.out.println(fileNameWithExtention);
		         int pos = fileNameWithExtention.lastIndexOf(".");
		         if (pos > 0 && pos < (fileNameWithExtention.length() - 1)) { // If '.' is not the first or last character.
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
}	

