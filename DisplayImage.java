/*****************************************
javac DisplayImage.java
java DisplayImage
******************************************/
import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.*; 
public class DisplayImage implements ActionListener 
{
    String filepath;
    JFrame frame;
    JFrame inter,nnm;
    JPanel panel;
    JPanel intermediate,nnmp;
    JLabel result,result1,classification;
    JButton b,as,asc,exit,clear,exit1,nnmodel,exi;
    JLabel cc1,cc2,ed,ori,edge,asl1,asl2;
    JLabel label,head,lnnm;
    JLabel original,edges,colorcontrast1,colorcontrast2;
    String[] arr=new String[10];
    Color col=new Color(235,222,240);
    JTextField cc1t,cc2t,ic1,ic2,ic3,tc,h,e,c,edget,ast1,ast2;
    JLabel lcc1,lcc2,lic1,lic2,lic3,ltc,lh,le,lc,heading;
    
    //CONSTRUCTOR 
    public DisplayImage()
    {
        frame = new JFrame("Aesthetic Classification");
        nnm=new JFrame("NN Model");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //nnm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(null); 
	nnm.setLayout(null);
	nnmp=(JPanel)nnm.getContentPane();
	nnm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = (JPanel)frame.getContentPane();
        
        inter=new JFrame("Intermediate Results");
        inter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inter.setLayout(null); 
 	intermediate=(JPanel)inter.getContentPane();
 	
 	
	cc1t=new JTextField();
	cc2t=new JTextField();
	ast1=new JTextField();
	ast2=new JTextField();
	ic1=new JTextField();
	ic2=new JTextField();
	ic3=new JTextField();
	tc=new JTextField();
	edget=new JTextField();
	c=new JTextField();
	e=new JTextField();
	h=new JTextField();	
	//tas=new JTextField(); 
	
	asl1=new JLabel("Aesthetic Score(1-7)");
	asl2=new JLabel("Aesthetic Score(1-10)");
	edge=new JLabel("Gradient of Edges");
	heading=new JLabel("Aesthetic Classification");
	head=new JLabel("Aesthetic Classification");
	lcc1=new JLabel("Color Contrast(RGB)");
	lcc2=new JLabel("Color Contrast(HSV)");
	lic1=new JLabel("Intensity Contrast");
	lic2=new JLabel("Foreground Intensity");
	lic3=new JLabel("Background Intensity");
	ltc=new JLabel("Texture Contrast");
	lh=new JLabel("Texture Homogeneity");
	le=new JLabel("Texture Energy");
	lc=new JLabel("Texture Corelation");
	cc1=new JLabel("Extracted Image 1");
	cc2=new JLabel("Extracted Image 2");
	ed=new JLabel("Edge Detection");
	ori=new JLabel("Original Image");
	
	heading.setFont(new Font("Serif",Font.PLAIN,25));
	head.setFont(new Font("Serif",Font.PLAIN,25));
	
	heading.setBounds(250,10,500,100);
	head.setBounds(600,10,500,100);
	lcc1.setBounds(650,90,175,30);
	lcc2.setBounds(650,180,175,30);
	lic1.setBounds(650,270,175,30);
	lic2.setBounds(650,360,175,30);
	lic3.setBounds(650,450,175,30);
	
	ltc.setBounds(970,90,175,30);
	le.setBounds(970,180,175,30);
	lh.setBounds(970,270,175,30);
	lc.setBounds(970,360,175,30);
	edge.setBounds(970,450,175,30);
	 
 	cc1t.setBounds(820,90,100,30);
 	cc2t.setBounds(820,180,100,30);
 	ic1.setBounds(820,270,100,30);
 	ic2.setBounds(820,360,100,30);
 	ic3.setBounds(820,450,100,30);
 	
 	tc.setBounds(1140,90,100,30);
 	e.setBounds(1140,180,100,30);
 	h.setBounds(1140,270,100,30);
 	c.setBounds(1140,360,100,30);
 	edget.setBounds(1140,450,100,30);
 
 	b=new JButton("Insert Image");
 	b.setBounds(100,600,150,25);
 
 	
 
 	as=new JButton("Feature Extractor");
 	as.setBounds(300,600,175,25);
 	asc=new JButton("Aesthetic Score ");
 	asc.setBounds(525,600,150,25);
 	
 	exi=new JButton("Exit");
 	exi.setBounds(400,650,100,25);
 	
 	exit1=new JButton("Exit");
 	exit1.setBounds(1000,600,150,25);
 	
 	nnmodel=new JButton("View NN Model");
 	nnmodel.setBounds(775,600,150,25);
 	
 	clear=new JButton("Clear Fields");
 	clear.setBounds(200,650,150,25);
 	
 	exit=new JButton("Exit");
 	exit.setBounds(375,650,150,25);
 	
        label = new JLabel();
        label.setOpaque(true);
        label.setBounds(100,100,500,400);
        
        lnnm = new JLabel();
        lnnm.setOpaque(true);
        lnnm.setBounds(100,100,700,500);
        
        original = new JLabel();
        original.setOpaque(true);
        original.setBounds(100,100,250,250);
        ori.setBounds(175,334,150,50);
        
        edges = new JLabel();
        edges.setOpaque(true);
        edges.setBounds(375,100,250,250);
	ed.setBounds(450,334,150,50);

	colorcontrast1 = new JLabel();
        colorcontrast1.setOpaque(true);
        colorcontrast1.setBounds(100,375,250,250);
        cc1.setBounds(175,609,150,50);

	colorcontrast2 = new JLabel();
        colorcontrast2.setOpaque(true);
        colorcontrast2.setBounds(375,375,250,250);
        cc2.setBounds(450,609,150,50);
        
        
        
	label.setBackground(Color.WHITE);       
	//if want to scale the image to be displayed.
        panel.add(label);
        intermediate.add(original);
        intermediate.add(ed);
        intermediate.add(cc1);
        intermediate.add(cc2);
        intermediate.add(edges);
        intermediate.add(colorcontrast1);
        intermediate.add(colorcontrast2);
        intermediate.add(ori);
        intermediate.add(nnmodel);
       
	cc1t.setEditable(false);
	cc2t.setEditable(false);
	ic1.setEditable(false);
	ic2.setEditable(false);
	ic3.setEditable(false);
	tc.setEditable(false);
	e.setEditable(false);
	c.setEditable(false);
	ast1.setEditable(false);
	ast2.setEditable(false);
	h.setEditable(false);
	edget.setEditable(false);
	//tas.setEditable(false);
        
        
 	panel.setBounds(0,0,900,640);
 	intermediate.setBounds(0,0,900,640);
 	nnmp.setBounds(0,0,900,640);
 	intermediate.setBackground(col);
 	panel.setBackground(col);
	b.addActionListener(this);
	clear.addActionListener(this);
	exit.addActionListener(this);
	as.addActionListener(this);
	asc.addActionListener(this); 	
	exit1.addActionListener(this);
        frame.setLocationRelativeTo(null);
        nnm.setLocationRelativeTo(null);
        nnmodel.addActionListener(this);
        
        
        
        frame.add(heading);
	frame.add(clear);       
	frame.add(exit);
	frame.add(edge);
	frame.add(edget);
        frame.add(b);
        frame.add(as);
        frame.add(lcc1);
        frame.add(lcc2);
        frame.add(lic1);
        frame.add(lic3);
        frame.add(ltc);
        frame.add(le);
        frame.add(tc);
        frame.add(lic2);
        frame.add(lh);
        frame.add(cc1t);
        frame.add(cc2t);
        frame.add(tc);
        frame.add(lc);
        frame.add(ic1);
        frame.add(ic2);
        frame.add(ic3);
        frame.add(h);
        frame.add(c);
        frame.add(e);
        frame.add(asc);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	frame.setUndecorated(true);
        frame.setVisible(true);
        
        inter.setLocationRelativeTo(null);
        inter.add(head);
        inter.add(exit1);
        inter.add(original);
        inter.add(edges);
        inter.add(colorcontrast1);
        inter.add(colorcontrast2);
	inter.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	inter.setUndecorated(true);
	
	nnm.add(lnnm);
	inter.setLocationRelativeTo(null);
	nnm.add(exi);
	exi.addActionListener(this);
	nnm.setUndecorated(true);
	nnm.setExtendedState(JFrame.MAXIMIZED_BOTH);
	
    }
    //END OF CONSTRUCTOR


   //ACTION PERFORMED
   public void actionPerformed(ActionEvent ae)
   {
   	//EXIT BUTTON
   	if(ae.getSource()==exit1)
   	{
		ast1.setText("");
		ast2.setText("");
		classification.setText("");
		inter.setVisible(false);
   	}
	if(ae.getSource()==exit)
        {
        	System.exit(0);
        }
        if(ae.getSource()==exi)
        {
        	nnm.setVisible(false);
        }        
        //END OF EXIT BUTTON
   	
   	
   	//VIEW NN MODEL
   	if(ae.getSource()==nnmodel)
   	{
   		nnm.setVisible(true);
   		
   		filepath="rplot.jpg";    
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage(filepath);

		Dimension imgsize=new Dimension(img.getWidth(null),img.getHeight(null));
 		Dimension labelsize=new Dimension(700,500);
	
       		Dimension imgdim=getScaledDimension(imgsize,labelsize);
       		img = img.getScaledInstance(700,500, Image.SCALE_SMOOTH);
	  	lnnm.setBackground(col);
	  	lnnm.setIcon(new ImageIcon(img));
   	}
   	//END VIEW IN MODEL
   	
   	//CLEAR BUTTON	
   	if(ae.getSource()==clear)
   	{
   		cc1t.setText("");
   		cc2t.setText("");
   		ic1.setText("");
   		ic2.setText("");
   		ic3.setText("");
   		tc.setText("");
   		h.setText("");
   		e.setText("");
   		c.setText("");
   		edget.setText("");
   		//tas.setText("");
   		label.setIcon(null);
   		label.setBackground(Color.WHITE);
   	}
   	//END OF CLEAR BUTTON
   	
   	//FEATURE EXTRACTOR BUTTON
   	if(ae.getSource()==as)
   	{
		try
   		{
	   		String[] command={"./fes", filepath};
			ProcessBuilder pb=new ProcessBuilder(command);
			Process p=pb.start();
			InputStream is=p.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(is),1);
			String line;
	
			int i=0;
			while((line=br.readLine())!=null)
			{
				arr[i]=line;
				System.out.println(line);
				i++;
			}
			cc1t.setText(arr[0]);
			cc2t.setText(arr[1]);
			ic1.setText(arr[2]);
			ic2.setText(arr[3]);
			ic3.setText(arr[4]);
			tc.setText(arr[5]);
			e.setText(arr[6]);
			h.setText(arr[7]);
			c.setText(arr[8]);
			edget.setText(arr[9]);
			writingData();
			is.close();
			br.close();	
		}
		catch(Exception e)
		{
			System.out.println("Exception : "+e);
		}		
   	}
   	//END OF FEATURE EXTRACTOR BUTTON	
   	
   	
   	//AESTHETIC SCORE CALCULATOR BUTTON
   	if(ae.getSource()==asc)
   	{
   		try
   		{
   			//String c = "python tt1.py "+arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]+" "+arr[4]+" "+arr[5]+" "+arr[6]+" "+arr[7]+" "+arr[8]+" "+arr[9];
 			
 			String c = "Rscript traintestR.r";
 			
			Process p = Runtime.getRuntime().exec(c);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line="";
			String[] linearr=new String[100000];
			int i=0;
			while((line=in.readLine())!=null)
			{
					System.out.println(line);
					linearr[i++]=line;
			}
			//tas.setText(linearr[i-1]);
			inter.setVisible(true);
			inter_result(linearr[i-1],linearr[i-4]);
		}
		catch(Exception e){}
	}	
   	//END OF AESTHETIC SCORE CALCULATOR BUTTON	
   	
   	
   		
   	//CHOOSE IMAGE BUTTON
   	if(ae.getSource()==b)
   	{
   		//System.out.println("Hello");
   		JFileChooser fc=new JFileChooser();    
        	int i=fc.showOpenDialog(frame);    
        	if(i==JFileChooser.APPROVE_OPTION)
        	{    
		    	File f=fc.getSelectedFile(); 
		    	if(!f.exists())
		    	{
		    		JOptionPane.showMessageDialog(null, "File entered does not exist...", "Error", JOptionPane.ERROR_MESSAGE); 
		    		label.setIcon(null);
		   		label.setBackground(Color.WHITE);
		    	}   
		    	else
		    	{
			    	filepath=f.getPath();    
			  	Toolkit kit = Toolkit.getDefaultToolkit();
		      		Image img = kit.getImage(filepath);

				Dimension imgsize=new Dimension(img.getWidth(null),img.getHeight(null));
		 		Dimension labelsize=new Dimension(500,400);
		
		       		Dimension imgdim=getScaledDimension(imgsize,labelsize);
		       		img = img.getScaledInstance(500,400, Image.SCALE_SMOOTH);
			  	label.setBackground(col);
			  	label.setIcon(new ImageIcon(img));
		  	}
	        }
   	}
   	//END OF CHOOSE IMAGE BUTTON
   }
   //END OF ACTION PERFORMED
   
   //PRINT DATA TO CSV FILE
   public void writingData()
   {
   	try
   	{
	   	PrintWriter pw = new PrintWriter(new File("data.csv"));
		StringBuilder sb = new StringBuilder();

		sb.append("colorcontrast(rgb)");
		sb.append(',');
		sb.append("colorcontrast(hsv)");
		sb.append(',');
		sb.append("intensityContrast");
		sb.append(',');
		sb.append("i1");
		sb.append(',');
		sb.append("i2");
		sb.append(',');
		sb.append("tcontrast");
		sb.append(',');
		sb.append("tenergy");
		sb.append(',');
		sb.append("thomogeneity");
		sb.append(',');
		sb.append("tcorrelation");
		sb.append(',');
		sb.append("edgesG");
		sb.append('\n');
		
		sb.append(arr[0]);
		sb.append(',');
		sb.append(arr[1]);
		sb.append(',');
		sb.append(arr[2]);
		sb.append(',');
		sb.append(arr[3]);
		sb.append(',');
		sb.append(arr[4]);
		sb.append(',');
		sb.append(arr[5]);
		sb.append(',');
		sb.append(arr[6]);
		sb.append(',');
		sb.append(arr[7]);
		sb.append(',');
		sb.append(arr[8]);
		sb.append(',');
		sb.append(arr[9]);
		sb.append('\n');
		
		pw.write(sb.toString());
		pw.close();
	}
	catch(FileNotFoundException f)
	{}
   }
   //END OF PRINT DATA TO CSV FILE
   
   
   
   //SCALED IMAGE
    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) 
    {
	    int original_width = imgSize.width;
	    int original_height = imgSize.height;
	    int bound_width = boundary.width;
	    int bound_height = boundary.height;
	    int new_width = original_width;
	    int new_height = original_height;

	    // first check if we need to scale width
	    if (original_width > bound_width) {
		//scale width to fit
		new_width = bound_width;
		//scale height to maintain aspect ratio
		new_height = (new_width * original_height) / original_width;
	    }

	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) {
		//scale height to fit instead
		new_height = bound_height;
		//scale width to maintain aspect ratio
		new_width = (new_height * original_width) / original_height;
	    }

	    return new Dimension(new_width, new_height);
    }
    //END OF SCALED IMAGE

    //DISPLAY INTERMEDIATE RESULTS
    void inter_result(String res,String res1)
    {
    	//original image
    	Toolkit kit = Toolkit.getDefaultToolkit();
	Image img = kit.getImage("inter_results/original.jpg");
	Dimension imgsize=new Dimension(img.getWidth(null),img.getHeight(null));
	Dimension labelsize=new Dimension(250,250);
	
	Dimension imgdim=getScaledDimension(imgsize,labelsize);
	img = img.getScaledInstance(250,250, Image.SCALE_SMOOTH);
	original.setBackground(col);
	original.setIcon(new ImageIcon(img));
	
	//edges image
	kit = Toolkit.getDefaultToolkit();
	img = kit.getImage("inter_results/edge1.jpg");
	imgsize=new Dimension(img.getWidth(null),img.getHeight(null));
	labelsize=new Dimension(250,250);
	
	imgdim=getScaledDimension(imgsize,labelsize);
	img = img.getScaledInstance(250,250, Image.SCALE_SMOOTH);
	edges.setBackground(col);
	edges.setIcon(new ImageIcon(img));
	
	//color contrast1
	kit = Toolkit.getDefaultToolkit();
	img = kit.getImage("inter_results/colorcontrast1.jpg");
	imgsize=new Dimension(img.getWidth(null),img.getHeight(null));
	labelsize=new Dimension(250,250);
	
	imgdim=getScaledDimension(imgsize,labelsize);
	img = img.getScaledInstance(250,250, Image.SCALE_SMOOTH);
	colorcontrast1.setBackground(col);
	colorcontrast1.setIcon(new ImageIcon(img));
	
	//color contrast1
	kit = Toolkit.getDefaultToolkit();
	img = kit.getImage("inter_results/colorcontrast2.jpg");
	imgsize=new Dimension(img.getWidth(null),img.getHeight(null));
	labelsize=new Dimension(250,250);
	
	imgdim=getScaledDimension(imgsize,labelsize);
	img = img.getScaledInstance(250,250, Image.SCALE_SMOOTH);
	colorcontrast2.setBackground(col);
	colorcontrast2.setIcon(new ImageIcon(img));	
	
	
	//DISPLAY RESULT
	
	asl1.setFont(new Font("Serif",Font.PLAIN,18));
	asl1.setBounds(675,250,200,50);
	intermediate.add(asl1);
	

	String newres1="",newres2="";
	for(int i=5,j=0;i<res.length();i++,j++)
	{
		newres1+=res.charAt(i);
	}
	
	for(int i=5,j=0;i<res1.length();i++,j++)
	{
		newres2+=res1.charAt(i);
	}
	ast1.setText(newres1);
	asl2.setFont(new Font("Serif",Font.PLAIN,18));
	ast1.setBounds(900,250,120,50);
	intermediate.add(ast1);
	
	
	//DISPLAY RESULT
	
	asl2.setFont(new Font("Serif",Font.PLAIN,18));
	asl2.setBounds(675,350,200,50);
	intermediate.add(asl2);
	
	float nr=0;
	nr=Float.parseFloat(newres2);
	nr*=10;
	newres2=Float.toString(nr);
	ast2.setText(newres2);
	asl2.setFont(new Font("Serif",Font.PLAIN,18));
	ast2.setBounds(900,350,120,50);
	intermediate.add(ast2);
	
	
	if(nr>=6.000000000)
	{
		classification=new JLabel("Image is aesthetically appealing.");
		classification.setFont(new Font("Serif",Font.PLAIN,18));
		classification.setBounds(675,450,500,50);
		intermediate.add(classification);
	}
	else if(nr<6.000000000)
	{
		classification=new JLabel("Image is aesthetically unappealing.");
		classification.setFont(new Font("Serif",Font.PLAIN,18));
		classification.setBounds(675,450,500,50);
		intermediate.add(classification);
	}
    }
    //END OF DISPLAY INTERMEDIATE RESULTS

    //MAIN 
    public static void main (String args[])
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new DisplayImage();
            }
        });
    }
    //END OF MAIN
}
