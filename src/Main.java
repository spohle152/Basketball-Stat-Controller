import java.awt.Color; //Import Libraries
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main { //Setup the file browser to get file paths
	public static String OutputCSVFilePath;
	public static String StorageCSVFilePath;
	public static ArrayList<String> numberName = new ArrayList<String>();
	public static ArrayList<String> names = new ArrayList<String>();
	public static ArrayList<String> number = new ArrayList<String>();
	public static ArrayList<String> team = new ArrayList<String>();
	public static ArrayList<Integer> make2 = new ArrayList<Integer>();
	public static ArrayList<Integer> total2 = new ArrayList<Integer>();
	public static ArrayList<Integer> make3 = new ArrayList<Integer>();
	public static ArrayList<Integer> total3 = new ArrayList<Integer>();
	public static ArrayList<Integer> makeFoul = new ArrayList<Integer>();
	public static ArrayList<Integer> totalFoul = new ArrayList<Integer>();
	public static void main (String[] args) throws IOException {
		//Create an image to use as the logo
		ImageIcon icon = new ImageIcon("JCD LIVE LOGO 1 .png");
		Image logo = icon.getImage();
		
		//Create a JFrame to Select a File location
		JFrame selectFile = new JFrame ();
		selectFile.setTitle("Stat Controller: Select File");
		selectFile.setSize(500, 300);
		selectFile.setIconImage(logo);
		selectFile.setLayout(null);
		selectFile.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create Components for the Select File Frame
		JLabel storageInstructions = new JLabel ("Please Enter the File path of where the .csv storage file is located:");
		storageInstructions.setBounds(20, 20, 500, 15);
		selectFile.add(storageInstructions);
		JTextField storageFilePath = new JTextField ("C:/Users/LiveStream/Desktop/fullstats.csv");
		storageFilePath.setBounds(20, 40, 300, 20);
		selectFile.add(storageFilePath);
		JButton storageBrowse = new JButton ("Browse");
		storageBrowse.setBounds(340, 40, 100, 20);
		selectFile.add(storageBrowse);
		
		JLabel outputInstructions = new JLabel ("Please Enter the File path of where the .csv output file is located:");
		outputInstructions.setBounds(20, 100, 500, 15);
		selectFile.add(outputInstructions);
		JTextField outputFilePath = new JTextField ("C:/Users/LiveStream/Desktop/output.csv");
		outputFilePath.setBounds(20, 120, 300, 20);
		selectFile.add(outputFilePath);
		JButton outputBrowse = new JButton ("Browse");
		outputBrowse.setBounds(340, 120, 100, 20);
		selectFile.add(outputBrowse);
		
		JButton enter = new JButton ("Go to Stat Controller");
		enter.setBounds(125, 200, 250, 20);
		selectFile.add(enter);
		JFileChooser chooser = new JFileChooser();
		selectFile.setVisible(true);
		
		//Create a JFrame for the controller
	    JFrame frame = new JFrame();
		frame.setTitle("Stat Controller");
		frame.setSize(500,550);
		frame.setLayout(null);
		frame.setIconImage(logo);
		frame.setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JList list = new JList(names.toArray()); //data has type Object[]
	    list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    list.setLayoutOrientation(JList.VERTICAL);
	    list.setVisibleRowCount(-1);
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewportView(list);
	    list.setLayoutOrientation(JList.VERTICAL);
	    scrollPane.setBounds(10,10,250,400);
	    frame.add(scrollPane);
	    
	    JLabel totalPoints = new JLabel ("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    totalPoints.setBounds(280, 350, 200, 20);
	    frame.add(totalPoints);
	    
	    //Create Actions for the Components
		enter.addActionListener(new ActionListener(){  
	    	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e){
	    		/*File file = new File (storageFilePath.getText());
	    		try {
					file.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
	    		selectFile.setVisible(false);
	    		frame.setVisible(true);
	    		OutputCSVFilePath = outputFilePath.getText();
	    		StorageCSVFilePath = storageFilePath.getText();
	    		
	    		//Read And Load the CSV File
	    	    if (new File (StorageCSVFilePath).exists()) {
	    	    	BufferedReader reader;
	    			try {
	    				reader = new BufferedReader(new FileReader(
	    						StorageCSVFilePath));
	    				String line = reader.readLine();
	    				while (line != null) {
	    					String[] splitLine = line.split(",");
	    					names.add(splitLine[0]);
	    					number.add(splitLine[1]);
	    					team.add(splitLine[2]);
	    					make2.add(Integer.parseInt(splitLine[3]));
	    					total2.add(Integer.parseInt(splitLine[4]));
	    					make3.add(Integer.parseInt(splitLine[5]));
	    					total3.add(Integer.parseInt(splitLine[6]));
	    					makeFoul.add(Integer.parseInt(splitLine[7]));
	    					totalFoul.add(Integer.parseInt(splitLine[8]));
	    					numberName.add ("<html><font color=\"red\">#"+splitLine[1]+" - </font></font color=\"black\">"+splitLine[0]+"</font></html>");
	    					list.setListData(numberName.toArray());
	    					// read next line
	    					line = reader.readLine();
	    				}
	    				reader.close();
	    			} catch (IOException e1) {
	    				e1.printStackTrace();
	    			}
	    	    }
	    	    updateFile (StorageCSVFilePath);
	    	    totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    	    pushStats (OutputCSVFilePath, false, 0);
	    	}  
	    });
	    storageBrowse.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Seperated Values (.csv)", "csv");
	    	        chooser.setFileFilter(filter);
	    	    int returnVal = chooser.showOpenDialog(selectFile);
	    	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	       storageFilePath.setText(chooser.getSelectedFile().getAbsolutePath());
	    	    }
	    	}  
	    }); 
	    outputBrowse.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Comma Seperated Values (.csv)", "csv");
	    	        chooser.setFileFilter(filter);
	    	    int returnVal = chooser.showOpenDialog(selectFile);
	    	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	       outputFilePath.setText(chooser.getSelectedFile().getAbsolutePath());
	    	    }
	    	}  
	    }); 
	    
	    //Create Components for the Stat Controller Frame
	    JButton addPlayer = new JButton ("Add");
	    addPlayer.setBounds(10, 430, 100, 20);
	    frame.add(addPlayer);
	    
	    JButton removePlayer = new JButton ("Remove");
	    removePlayer.setBounds(120, 430, 100, 20);
	    frame.add(removePlayer);
	    
	    JButton updatePlayer = new JButton ("Update");
	    updatePlayer.setBounds(230, 430, 100, 20);
	    frame.add(updatePlayer);
	    
	    JTextField enterName = new JTextField("Name");
	    enterName.setBounds(10, 460, 200, 20);
	    frame.add(enterName);
	    
	    JLabel hashtag = new JLabel("#");
	    hashtag.setBounds(220, 460, 50, 20);
	    frame.add(hashtag);
	    
	    JTextField enterNumber = new JTextField("0");
	    enterNumber.setBounds(240, 460, 100, 20);
	    frame.add(enterNumber);
	    
	    JTextField enterTeam = new JTextField("Team");
	    enterTeam.setBounds(350, 460, 100, 20);
	    frame.add(enterTeam);
	    
	    
	    addPlayer.addActionListener(new ActionListener(){  
	    	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e){
	    	    numberName.add ("<html><font color=\"red\">#"+enterNumber.getText()+" - </font></font color=\"black\">"+enterName.getText()+"</font></html>");
	    		names.add(enterName.getText());
	    	    number.add(enterNumber.getText());
	    	    team.add(enterTeam.getText());
	    	    make2.add(0);
	    	    total2.add(0);
	    	    make3.add(0);
	    	    total3.add(0);
	    	    makeFoul.add(0);
	    	    totalFoul.add(0);
	    	    sort();
	    	    list.setListData(numberName.toArray());
	    	    totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    	    updateFile(StorageCSVFilePath);
	    	}  
	    }); 
	    
	    updatePlayer.addActionListener(new ActionListener(){  
	    	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e){
	    		if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {
	    			numberName.set(list.getSelectedIndex(),"<html><font color=\"red\">#"+enterNumber.getText()+" - </font></font color=\"black\">"+enterName.getText()+"</font></html>");
	    			names.set(list.getSelectedIndex(),enterName.getText());
		    	    number.set(list.getSelectedIndex(),enterNumber.getText());
		    	    team.set(list.getSelectedIndex(),enterTeam.getText());
		    	    sort();
					updateFile(StorageCSVFilePath);
		    	    list.setListData(numberName.toArray());
	    		}
	    	}  
	    }); 

	    removePlayer.addActionListener(new ActionListener(){  
	    	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e){
	    		numberName.remove(list.getSelectedIndex());
	    		names.remove(list.getSelectedIndex());
	    	    number.remove(list.getSelectedIndex());
	    	    team.remove(list.getSelectedIndex());
	    	    make2.remove(list.getSelectedIndex());
	    	    total2.remove(list.getSelectedIndex());
	    	    make3.remove(list.getSelectedIndex());
	    	    total3.remove(list.getSelectedIndex());
	    	    makeFoul.remove(list.getSelectedIndex());
	    	    totalFoul.remove(list.getSelectedIndex());
				list.clearSelection();
	    	    list.setListData(numberName.toArray());
	    	    totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    	    updateFile(StorageCSVFilePath);
	    	}  
	    });
	    

        //Set up the logic for the output of the controller
        JToggleButton program = new JToggleButton ("PROGRAM");
        program.setBounds(330, 10, 100, 35);
        program.setBackground(Color.RED);
        program.setForeground(Color.WHITE);

        frame.add(program);
        
        JLabel percentage2 = new JLabel ("-");
        percentage2.setBounds(400, 95, 100, 20);
        frame.add(percentage2);
        
        JLabel label2 = new JLabel ("2 Point Shots:");
        label2.setBounds(280, 65, 200, 20);
        frame.add(label2);
        
        JTextField enterMake2 = new JTextField("-");
        enterMake2.setBounds(280, 95, 25, 20);
        frame.add(enterMake2);
        
        JLabel slash2 = new JLabel ("/");
        slash2.setBounds(315, 95, 25, 20);
        frame.add(slash2);
        
        JTextField enterTotal2 = new JTextField("-");
        enterTotal2.setBounds(330, 95, 25, 20);
        frame.add(enterTotal2);
        
        JButton addMake2 = new JButton("Make");
        addMake2.setBounds(280, 125, 70, 20);
        frame.add(addMake2);
        
        JButton addTotal2 = new JButton("Miss");
        addTotal2.setBounds(360, 125, 70, 20);
        frame.add(addTotal2);
        
        JLabel percentage3 = new JLabel ("-");
        percentage3.setBounds(400, 175, 100, 20);
        frame.add(percentage3);
        
        JLabel label3 = new JLabel ("3 Point Shots:");
        label3.setBounds(280, 145, 200, 20);
        frame.add(label3);
        
        JTextField enterMake3 = new JTextField("-");
        enterMake3.setBounds(280, 175, 25, 20);
        frame.add(enterMake3);
        
        JLabel slash3 = new JLabel ("/");
        slash3.setBounds(315, 175, 25, 20);
        frame.add(slash3);
        
        JTextField enterTotal3 = new JTextField("-");
        enterTotal3.setBounds(330, 175, 25, 20);
        frame.add(enterTotal3);
        
        JButton addMake3 = new JButton("Make");
        addMake3.setBounds(280, 205, 70, 20);
        frame.add(addMake3);
        
        JButton addTotal3 = new JButton("Miss");
        addTotal3.setBounds(360, 205, 70, 20);
        frame.add(addTotal3);
        
        JLabel percentageFoul = new JLabel ("-");
        percentageFoul.setBounds(400, 265, 100, 20);
        frame.add(percentageFoul);
        
        JLabel labelFoul = new JLabel ("Foul Shots:");
        labelFoul.setBounds(280, 235, 200, 20);
        frame.add(labelFoul);
        
        JTextField enterMakeFoul = new JTextField("-");
        enterMakeFoul.setBounds(280, 265, 25, 20);
        frame.add(enterMakeFoul);
        
        JLabel slashFoul = new JLabel ("/");
        slashFoul.setBounds(315, 265, 25, 20);
        frame.add(slashFoul);
        
        JTextField enterTotalFoul = new JTextField("-");
        enterTotalFoul.setBounds(330, 265, 25, 20);
        frame.add(enterTotalFoul);
        
        JButton addMakeFoul = new JButton("Make");
        addMakeFoul.setBounds(280, 295, 70, 20);
        frame.add(addMakeFoul);
        
        JButton addTotalFoul = new JButton("Miss");
        addTotalFoul.setBounds(360, 295, 70, 20);
        frame.add(addTotalFoul);
        
        
        list.addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedIndex() != -1){
					enterName.setText(names.get(list.getSelectedIndex()));
					enterNumber.setText(number.get(list.getSelectedIndex()));
					enterTeam.setText(team.get(list.getSelectedIndex()));
					enterMake2.setText(""+make2.get(list.getSelectedIndex()));
					enterTotal2.setText(""+total2.get(list.getSelectedIndex()));
					enterMake3.setText(""+make3.get(list.getSelectedIndex()));
					enterTotal3.setText(""+total3.get(list.getSelectedIndex()));
					enterMakeFoul.setText(""+makeFoul.get(list.getSelectedIndex()));
					enterTotalFoul.setText(""+totalFoul.get(list.getSelectedIndex()));
					percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
		        	percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
		        	percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
					if (program.isSelected()){
						pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
					}
				} else {
					enterName.setText("Name");
					enterNumber.setText("0");
					enterTeam.setText("Team");
					enterMake2.setText("-");
					enterTotal2.setText("-");
					enterMake3.setText("-");
					enterTotal3.setText("-");
					enterMakeFoul.setText("-");
					enterTotalFoul.setText("-");
					percentage2.setText("-");
		        	percentage3.setText("-");
		        	percentageFoul.setText("-");
					if (program.isSelected()){
						program.setSelected(false);;
					}
				}
			}
        });
        
        enterMake2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {
            		make2.set(list.getSelectedIndex(), Integer.parseInt(enterMake2.getText()));
            		totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
            		updateFile(StorageCSVFilePath);
            		pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
            		percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
                    percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
                    percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
            }
        });
        
        enterTotal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
            		total2.set(list.getSelectedIndex(), Integer.parseInt(enterTotal2.getText()));
            		totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
            		updateFile(StorageCSVFilePath);
            		pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
            		percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
                    percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
                    percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
            }
        });
        
	    addMake2.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {
	    			make2.set(list.getSelectedIndex(), make2.get(list.getSelectedIndex())+1);
	    			total2.set(list.getSelectedIndex(), total2.get(list.getSelectedIndex())+1);
	    			enterMake2.setText(""+make2.get(list.getSelectedIndex()));
	    			enterTotal2.setText(""+total2.get(list.getSelectedIndex()));
	    			totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    			updateFile(StorageCSVFilePath);
	    			pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
	    			percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
	    	        percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
	    	        percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
	    	}  
	    }); 
	    addTotal2.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
	    			total2.set(list.getSelectedIndex(), total2.get(list.getSelectedIndex())+1);
	    	    	enterTotal2.setText(""+total2.get(list.getSelectedIndex()));
	    	    	totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    	    	updateFile(StorageCSVFilePath);
	    	    	pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
	    	    	percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
	    	        percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
	    	        percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
	    	}  
	    }); 
        enterMake3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
            		make3.set(list.getSelectedIndex(), Integer.parseInt(enterMake3.getText()));
            		totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
            		updateFile(StorageCSVFilePath);
            		pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
            		percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
                    percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
                    percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
            }
        });
        
        enterTotal3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
            		total3.set(list.getSelectedIndex(), Integer.parseInt(enterTotal3.getText()));
            		totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
            		updateFile(StorageCSVFilePath);
            		pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
            		percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
                    percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
                    percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}            
            }
        });
        
	    addMake3.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
	    			make3.set(list.getSelectedIndex(), make3.get(list.getSelectedIndex())+1);
	    	    	total3.set(list.getSelectedIndex(), total3.get(list.getSelectedIndex())+1);
	    	    	enterMake3.setText(""+make3.get(list.getSelectedIndex()));
	    	    	enterTotal3.setText(""+total3.get(list.getSelectedIndex()));
	    	    	totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    	    	updateFile(StorageCSVFilePath);
	    	    	pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
	    	    	percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
	    	        percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
	    	        percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
	    	}  
	    }); 
	    addTotal3.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
	    			total3.set(list.getSelectedIndex(), total3.get(list.getSelectedIndex())+1);
	    	    	enterTotal3.setText(""+total3.get(list.getSelectedIndex()));
	    	    	totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    	    	updateFile(StorageCSVFilePath);
	    	    	pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
	    	    	percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
	    	        percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
	    	        percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
	    	}  
	    }); 
	    
        enterMakeFoul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
            		makeFoul.set(list.getSelectedIndex(), Integer.parseInt(enterMakeFoul.getText()));
            		totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
            		updateFile(StorageCSVFilePath);
            		pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
            		percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
                    percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
                    percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
            }
        });
        
        enterTotalFoul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
            	if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
            		totalFoul.set(list.getSelectedIndex(), Integer.parseInt(enterTotalFoul.getText()));
            		totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
            		updateFile(StorageCSVFilePath);
            		pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
            		percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
                    percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
                    percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
            }
        });
        
	    addMakeFoul.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
	    			makeFoul.set(list.getSelectedIndex(), makeFoul.get(list.getSelectedIndex())+1);
	    	    	totalFoul.set(list.getSelectedIndex(), totalFoul.get(list.getSelectedIndex())+1);
	    	    	enterMakeFoul.setText(""+makeFoul.get(list.getSelectedIndex()));
	    	    	enterTotalFoul.setText(""+totalFoul.get(list.getSelectedIndex()));
	    	    	totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    	    	updateFile(StorageCSVFilePath);
	    	    	pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
	    	    	percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
	    	        percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
	    	        percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
	    	}  
	    }); 
	    addTotalFoul.addActionListener(new ActionListener(){  
	    	public void actionPerformed(ActionEvent e){
	    		if (list.getSelectedValue() == null) {
            		JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
            	} else {	
	    			totalFoul.set(list.getSelectedIndex(), totalFoul.get(list.getSelectedIndex())+1);
	    	    	enterTotalFoul.setText(""+totalFoul.get(list.getSelectedIndex()));
	    	    	totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
	    	    	updateFile(StorageCSVFilePath);
	    	    	pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
	    	    	percentage2.setText((int)(((double)make2.get(list.getSelectedIndex()))/(total2.get(list.getSelectedIndex()))*100) + "%");
	    	        percentage3.setText((int)(((double)make3.get(list.getSelectedIndex()))/(total3.get(list.getSelectedIndex()))*100) + "%");
	    	        percentageFoul.setText((int)(((double)makeFoul.get(list.getSelectedIndex()))/(totalFoul.get(list.getSelectedIndex()))*100) + "%");
            	}
	    	}  
	    }); 
		program.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (list.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(new JFrame(), "Please Select a Player to Edit");
					program.setSelected(false);
				} else {
					pushStats(OutputCSVFilePath, program.isSelected(), list.getSelectedIndex());
				}
				if (program.isSelected()) {
					program.setForeground(Color.BLACK);
				} else {
					program.setForeground(Color.WHITE);
				}
			}
		});
	    
		JButton reset = new JButton("Reset");
		reset.setBounds(280, 400, 100, 20);
		reset.setBackground(Color.RED);
		reset.setForeground(Color.WHITE);
		frame.add(reset);
		
	    reset.addActionListener(new ActionListener(){  
	    	@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e){
	    		for (int i = numberName.size()-1; i>=0; i--) {
		    		numberName.remove(i);
		    		names.remove(i);
		    	    number.remove(i);
		    	    team.remove(i);
		    	    make2.remove(i);
		    	    total2.remove(i);
		    	    make3.remove(i);
		    	    total3.remove(i);
		    	    makeFoul.remove(i);
		    	    totalFoul.remove(i);
		    	    list.setListData(numberName.toArray());
		    	    totalPoints.setText("Total: "+ ((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
		    	    updateFile(StorageCSVFilePath);
	    		}
	    	}  
	    });	
		JButton clear = new JButton("Clear Selection");
		clear.setBounds(280, 370, 200, 20);
		clear.setBackground(Color.RED);
		clear.setForeground(Color.WHITE);
		frame.add(clear);

		clear.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
	    		list.clearSelection();
	    	}  
	    });	
	}

	//Update the files for storage and output
	public static void updateFile(String filePath) {
		try {
			String writing = "";
			FileWriter Fwriter1 = new FileWriter(new File (filePath));
			BufferedWriter writer1 = new BufferedWriter(Fwriter1);
			for (int i = 0; i< names.size(); i++) {
				writing += (names.get(i) + "," + number.get(i) + "," + team.get(i) + "," + make2.get(i) + "," + total2.get(i) + "," + make3.get(i) + "," + total3.get(i) + "," + makeFoul.get(i) + "," + totalFoul.get(i) + "\n");
			}
			writer1.write(writing);
			writer1.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	//Output the stats for a particular player
	public static void pushStats(String filePath, boolean program, int selected) {
		FileWriter Fwriter2;
		try {
			Fwriter2 = new FileWriter(new File(filePath));
			BufferedWriter writer2 = new BufferedWriter(Fwriter2);
		if (program) {
			writer2.write("Name, Hashtag, Number, Team, 2-Point Ratio , 2-Point Percentage, 3-Point Ratio, 3-Point Percentage, Foul Shots Ratio, Foul Shots Percentage, Total Ratio, Total Percentage, Points\n" + names.get(selected) + "," + "#" + "," + number.get(selected) + "," + team.get(selected) + "," + make2.get(selected) + "/" + total2.get(selected) + "," + Math.round(((double)make2.get(selected)/total2.get(selected))*100) + "%," + make3.get(selected) + "/" + total3.get(selected) + "," + Math.round(((double)make3.get(selected)/total3.get(selected))*100) + "%," + makeFoul.get(selected) + "/" + totalFoul.get(selected) + "," + Math.round(((double)makeFoul.get(selected)/totalFoul.get(selected))*100) + "%," + (make2.get(selected)+make3.get(selected)+makeFoul.get(selected)) + "/" + (total2.get(selected)+total3.get(selected)+totalFoul.get(selected)) + "," + Math.round(((double)(make2.get(selected)+make3.get(selected)+makeFoul.get(selected))/(total2.get(selected)+total3.get(selected)+totalFoul.get(selected)))*100) + "%, " +((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
		} else {
			writer2.write("Name, Hashtag, Number, Team, 2-Point Ratio , 2-Point Percentage, 3-Point Ratio, 3-Point Percentage, Foul Shots Ratio, Foul Shots Percentage, Total Ratio, Total Percentage, Points\n" + "Team Stats" + "," + " " + "," + " " + "," + "Team" + "," + sum(make2) + "/" + sum(total2) + "," + Math.round(((double)sum(make2)/sum(total2))*100) + "%," + sum(make3) + "/" + sum(total3) + "," + Math.round(((double)sum(make3)/sum(total3))*100) + "%," + sum(makeFoul) + "/" + sum(totalFoul) + "," + Math.round(((double)sum(makeFoul)/sum(totalFoul))*100) + "%," + (sum(make2)+sum(make3)+sum(makeFoul)) + "/" + (sum(total2)+sum(total3)+sum(totalFoul)) + "," + Math.round((double)(sum(make2)+sum(make3)+sum(makeFoul))/(sum(total2)+sum(total3)+sum(totalFoul))*100) + "%, " +((sum(make2)*2)+(sum(make3)*3)+sum(makeFoul)));
		}
		writer2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//Get the sum of a list
	public static int sum (ArrayList <Integer> list) {
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i);
		}
		return sum;
		
	}

	//Sort a list by number
	public static void sort () {
		boolean sorted = false;
		String tempNameNumber;
		String tempName;
		String tempNumber;
		String tempTeam;
		int tempMake2;
		int tempTotal2;
		int tempMake3;
		int tempTotal3;
		int tempMakeFoul;
		int tempTotalFoul;
		while (!(sorted)) {
			sorted = true;
			for (int i = 0; i < number.size() - 1; i++) {
				if (Integer.parseInt(number.get(i)) > Integer.parseInt(number.get(i+1))) {
					tempNameNumber = numberName.get(i);
					numberName.set(i, numberName.get(i+1));
					numberName.set(i+1, tempNameNumber);
					tempName = names.get(i);
					names.set(i, names.get(i+1));
					names.set(i+1, tempName);
					tempNumber = number.get(i);
					number.set(i, number.get(i+1));
					number.set(i+1, tempNumber);
					tempTeam = team.get(i);
					team.set(i, team.get(i+1));
					team.set(i+1, tempTeam);
					tempMake2 = make2.get(i);
					make2.set(i, make2.get(i+1));
					make2.set(i+1, tempMake2);
					tempTotal2 = total2.get(i);
					total2.set(i, total2.get(i+1));
					total2.set(i+1, tempTotal2);
					tempMake3 = make3.get(i);
					make3.set(i, make3.get(i+1));
					make3.set(i+1, tempMake3);
					tempTotal3 = total3.get(i);
					total3.set(i, total3.get(i+1));
					total3.set(i+1, tempTotal3);
					tempMakeFoul = makeFoul.get(i);
					makeFoul.set(i, makeFoul.get(i+1));
					makeFoul.set(i+1, tempMakeFoul);
					tempTotalFoul = totalFoul.get(i);
					totalFoul.set(i, totalFoul.get(i+1));
					totalFoul.set(i+1, tempTotalFoul);
					sorted = false;
				}
			}
		}
	}
}
