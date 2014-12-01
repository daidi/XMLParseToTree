package com.daidi.db.homework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

class XMLFrame extends JFrame
{
	private JMenuBar mb;
	private JMenu file, about;
	private File xmlFile;
	private Generator gen;
	final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public XMLFrame()
	{
//		double width = Toolkit.getDefaultToolkit().getScreenSize().width; //得到当前屏幕分辨率的高
//		  double height = Toolkit.getDefaultToolkit().getScreenSize().height;//得到当前屏幕分辨率的宽
//		  this.setSize((int)width,(int)height);//设置大小
//		  this.setLocation(0,0); //设置窗体居中显示
//		  this.setResizable(false);//禁用最大化按钮
		this.setBounds(30, 90, 1200, 800);
		this.setTitle("XML解析器");

		mb = new JMenuBar();
		file = new JMenu("文件");
		about = new JMenu("帮助");
		JMenuItem openXML = new JMenuItem("打开XML文档(O)...");
		openXML.setMnemonic('O');
		JMenuItem exit = new JMenuItem("退出");
		file.add(openXML);
		file.add(exit);
		JMenuItem problem = new JMenuItem("作业要求");
		JMenuItem guide = new JMenuItem("操作说明");
		about.add(problem);
		about.add(guide);
		mb.add(file);
		mb.add(about);
		this.setJMenuBar(mb);
		gen = new Generator();
		add(gen);
		//开始定义方法
		openXML.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				String curPath = System.getProperty("user.dir");
				JFileChooser chooser = new JFileChooser(curPath);
				// chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				chooser.setFileFilter(new FileFilter()
				{

					public boolean accept(File f)
					{
						// TODO Auto-generated method stub
						if (f.isFile())
						{
							String filename = f.getName();
							return filename.endsWith(".xml");
						}
						if (f.isDirectory())
							return true;

						return false;
					}

					public String getDescription()
					{
						// TODO Auto-generated method stub
						return "XML文档(*.xml)";
					}

				});
				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					gen.logs.append(df.format(new Date())+" ：正在打开文档……\n" );		
					gen.logs.setCaretPosition(gen.logs.getText().length());
					xmlFile = chooser.getSelectedFile();
					try
					{
						gen.logs.append(df.format(new Date())+" ：正在解析文档……\n" );		
						gen.validateWithDTD(xmlFile);
						gen.logs.append(df.format(new Date())+" ：文档解析成功！提供的XML与DTD是匹配的！\n" );	
						gen.logs.append(df.format(new Date())+" ：开始生成树形结构……\n" );		
						gen.LoadTree(xmlFile);
						gen.logs.append(df.format(new Date())+" ：树形结构生成成功！\n" );	
						gen.logs.setCaretPosition(gen.logs.getText().length());
					}
					catch (Exception e1)
					{
						gen.logs.append(df.format(new Date())+" ："+e1.toString());
					}
				}
				else {
					gen.logs.append(df.format(new Date())+" ：文档打开失败！\n" );	
				}
			}

		});
		exit.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);

			}

		});
		
		problem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final JFrame helpframe=new JFrame("作业要求");
	        	JScrollPane scrollpane;
	        	JTextArea helpArea=new JTextArea(4,27);   //创建文本输入区
	        	helpArea.setLineWrap(true);//自动换行
	        	helpArea.setText("         自己定义一个DTD，并给出与这个DTD相对应的两个XML文档。程序要求输入这个XML文档，检测是否符合DTD的规范，如果符合则以树形结构表示出来。整个程序要求是图形界面程序。");
	        	helpArea.setEditable(false);
	        	JButton return1=new JButton("知道了！");
	        	return1.addActionListener(new ActionListener()
	            {
				     public void actionPerformed(ActionEvent e)
				     {
				     	helpframe.dispose();
				     }
				});
	        	JPanel helppanel=new JPanel();
	        	helppanel.add(scrollpane=new JScrollPane(helpArea));  //把文本输入区添加到可滚动窗格
	        	helppanel.add(return1);
	            helpframe.getContentPane().add(helppanel);	 	
		 	    helpframe.setSize(new Dimension(310,150));
		 	    helpframe.setLocation(320,200);
		 	    helpframe.setVisible(true);
	            helpframe.setResizable(false);
				
			}
		});
		
		guide.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final JFrame helpframe=new JFrame("操作说明");
	        	JScrollPane scrollpane;
	        	JTextArea helpArea=new JTextArea(10,28);   //创建文本输入区
	        	helpArea.setLineWrap(true);//自动换行
	        	helpArea.setText("1.DTD文件并没有读入，因为可以在XML文件中通过如下语句绑定DTD。\n<!DOCTYPE sample SYSTEM \"sample.dtd\"> \n\n2.balabala");
	        	helpArea.setEditable(false);
	        	JButton return1=new JButton("知道了！");
	        	return1.addActionListener(new ActionListener()
	            {
				     public void actionPerformed(ActionEvent e)
				     {
				     	helpframe.dispose();
				     }
				});
	        	JPanel helppanel=new JPanel();
	        	helppanel.add(scrollpane=new JScrollPane(helpArea));  //把文本输入区添加到可滚动窗格
	        	helppanel.add(return1);
	            helpframe.getContentPane().add(helppanel);	 	
		 	    helpframe.setSize(new Dimension(310,250));
		 	    helpframe.setLocation(320,200);
		 	    helpframe.setVisible(true);
	            helpframe.setResizable(false);
				
			}
		});
		
	}
}

public class MainFrame
{
	public static void main(String[] args)
	{
		XMLFrame f = new XMLFrame();
		f.getContentPane().setBackground(Color.white);
		f.setDefaultCloseOperation(XMLFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}
}
