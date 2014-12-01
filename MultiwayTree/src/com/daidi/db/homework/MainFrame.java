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
//		double width = Toolkit.getDefaultToolkit().getScreenSize().width; //�õ���ǰ��Ļ�ֱ��ʵĸ�
//		  double height = Toolkit.getDefaultToolkit().getScreenSize().height;//�õ���ǰ��Ļ�ֱ��ʵĿ�
//		  this.setSize((int)width,(int)height);//���ô�С
//		  this.setLocation(0,0); //���ô��������ʾ
//		  this.setResizable(false);//������󻯰�ť
		this.setBounds(30, 90, 1200, 800);
		this.setTitle("XML������");

		mb = new JMenuBar();
		file = new JMenu("�ļ�");
		about = new JMenu("����");
		JMenuItem openXML = new JMenuItem("��XML�ĵ�(O)...");
		openXML.setMnemonic('O');
		JMenuItem exit = new JMenuItem("�˳�");
		file.add(openXML);
		file.add(exit);
		JMenuItem problem = new JMenuItem("��ҵҪ��");
		JMenuItem guide = new JMenuItem("����˵��");
		about.add(problem);
		about.add(guide);
		mb.add(file);
		mb.add(about);
		this.setJMenuBar(mb);
		gen = new Generator();
		add(gen);
		//��ʼ���巽��
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
						return "XML�ĵ�(*.xml)";
					}

				});
				int result = chooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
					gen.logs.append(df.format(new Date())+" �����ڴ��ĵ�����\n" );		
					gen.logs.setCaretPosition(gen.logs.getText().length());
					xmlFile = chooser.getSelectedFile();
					try
					{
						gen.logs.append(df.format(new Date())+" �����ڽ����ĵ�����\n" );		
						gen.validateWithDTD(xmlFile);
						gen.logs.append(df.format(new Date())+" ���ĵ������ɹ����ṩ��XML��DTD��ƥ��ģ�\n" );	
						gen.logs.append(df.format(new Date())+" ����ʼ�������νṹ����\n" );		
						gen.LoadTree(xmlFile);
						gen.logs.append(df.format(new Date())+" �����νṹ���ɳɹ���\n" );	
						gen.logs.setCaretPosition(gen.logs.getText().length());
					}
					catch (Exception e1)
					{
						gen.logs.append(df.format(new Date())+" ��"+e1.toString());
					}
				}
				else {
					gen.logs.append(df.format(new Date())+" ���ĵ���ʧ�ܣ�\n" );	
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
				final JFrame helpframe=new JFrame("��ҵҪ��");
	        	JScrollPane scrollpane;
	        	JTextArea helpArea=new JTextArea(4,27);   //�����ı�������
	        	helpArea.setLineWrap(true);//�Զ�����
	        	helpArea.setText("         �Լ�����һ��DTD�������������DTD���Ӧ������XML�ĵ�������Ҫ���������XML�ĵ�������Ƿ����DTD�Ĺ淶����������������νṹ��ʾ��������������Ҫ����ͼ�ν������");
	        	helpArea.setEditable(false);
	        	JButton return1=new JButton("֪���ˣ�");
	        	return1.addActionListener(new ActionListener()
	            {
				     public void actionPerformed(ActionEvent e)
				     {
				     	helpframe.dispose();
				     }
				});
	        	JPanel helppanel=new JPanel();
	        	helppanel.add(scrollpane=new JScrollPane(helpArea));  //���ı���������ӵ��ɹ�������
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
				final JFrame helpframe=new JFrame("����˵��");
	        	JScrollPane scrollpane;
	        	JTextArea helpArea=new JTextArea(10,28);   //�����ı�������
	        	helpArea.setLineWrap(true);//�Զ�����
	        	helpArea.setText("1.DTD�ļ���û�ж��룬��Ϊ������XML�ļ���ͨ����������DTD��\n<!DOCTYPE sample SYSTEM \"sample.dtd\"> \n\n2.balabala");
	        	helpArea.setEditable(false);
	        	JButton return1=new JButton("֪���ˣ�");
	        	return1.addActionListener(new ActionListener()
	            {
				     public void actionPerformed(ActionEvent e)
				     {
				     	helpframe.dispose();
				     }
				});
	        	JPanel helppanel=new JPanel();
	        	helppanel.add(scrollpane=new JScrollPane(helpArea));  //���ı���������ӵ��ɹ�������
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
