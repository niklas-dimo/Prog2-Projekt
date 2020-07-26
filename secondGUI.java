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
