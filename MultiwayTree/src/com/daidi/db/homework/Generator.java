package com.daidi.db.homework;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import org.abego.treelayout.demo.TextInBox;
import org.abego.treelayout.demo.TextInBoxNodeExtentProvider;
import org.abego.treelayout.demo.swing.TextInBoxTreePane;

public class Generator extends JPanel
{
	private TextInBoxTreePane xmltree;
	public JTextArea logs;
	private Document doc;
	JScrollPane jLogs;
	JScrollPane treePane;
	JScrollBar bar;
	final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public DefaultTreeForTreeLayout<TextInBox> tree;
	FontMetrics fm = this.getFontMetrics(getFont());

	public Generator()
	{
		GridBagLayout layout = new GridBagLayout();
		// ʵ���������鲼�ֵ�֧����
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);
		// ˮƽ����
		gbc.weightx = 0;
		// ��ֱ����
		gbc.weighty = 0;
		// ���
		gbc.fill = GridBagConstraints.BOTH;
		// ����
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(8, 3, 3, 3);

		logs = new JTextArea(
				"************************************************************\n���ݿ����ҵ\n���ߣ����E\nѧ�ţ�13S003116\n��ӭʹ��XML����������XML�ĵ�������ѡ���ļ���-����XML�ĵ������������ⶨ���Լ�����˵���ɵ�����������鿴��\n************************************************************\n");
		logs.setLineWrap(true);
		logs.setEditable(false);

		super.setBackground(Color.white);
		int w = fm.stringWidth("��ʼ����ϣ��ȴ�����XML�ṹ");
		TextInBox root = new TextInBox("��ʼ����ϣ��ȴ�����XML�ṹ", w+60, 20);
		tree = new DefaultTreeForTreeLayout<TextInBox>(root);
		// setup the tree layout configuration
		double gapBetweenLevels = 50;
		double gapBetweenNodes = 10;
		DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(
				gapBetweenLevels, gapBetweenNodes);
		// create the NodeExtentProvider for TextInBox nodes
		TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
		// create the layout
		TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree,
				nodeExtentProvider, configuration);
		// Create a panel that draws the nodes and edges and show the panel
		xmltree = new TextInBoxTreePane(treeLayout);
		treePane= new JScrollPane(xmltree,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		treePane.setPreferredSize(new Dimension(1150, 600));
		treePane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("���νṹ"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		treePane.setBackground(Color.white);
		jLogs = new JScrollPane(logs,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jLogs.setPreferredSize(new Dimension(1150, 100));
		jLogs.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("���м�¼"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		jLogs.setBackground(Color.white);	
		this.addComponent(0, 0, 7, 10, gbc, treePane);
		this.addComponent(0, 600, 7, 10, gbc, jLogs);
		bar=jLogs.getVerticalScrollBar();
		bar.setValue(bar.getMaximum());
	}

	/**
	 * ��֤xml
	 * 
	 * @param xmlFileName
	 * @throws Exception
	 */
	public void validateWithDTD(File xmlFile) throws Exception
	{
		/*
		 * if (xmlFileName.length() != 1) { System.out.println("��������!");
		 * System.exit(1); }
		 */
		// ȡ�ù������һ����ʵ��
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		// ͨ����̬��������DocumentBuilder ʵ��
		// ׼������Document ����
		DocumentBuilder db = dbf.newDocumentBuilder();
		db.setErrorHandler(new DTDErrorHandler());
		// ����XML �ĵ�����
		Document doc = db.parse(xmlFile);
		// ��ʽ���ĵ�
		doc.getDocumentElement().normalize();
	}

	/*
	 * ���ݴ����XML�ļ���������JTree
	 */
	public void LoadTree(File xmlFile)
	{
		Generator.this.remove(xmltree);

		int w = fm.stringWidth("xml:" +xmlFile.getName());
		TextInBox root = new TextInBox("xml:" + xmlFile.getName(), w+60, 20);
		tree = new DefaultTreeForTreeLayout<TextInBox>(root);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try
		{
			db = dbf.newDocumentBuilder();
			doc = db.parse(xmlFile);
		}
		catch (SAXException e1)
		{
		}
		catch (IOException e3)
		{
		}
		catch (ParserConfigurationException e2)
		{
		}
		Element tempRoot = doc.getDocumentElement();
		if (tempRoot != null)
		{
			buildTree(tempRoot, root);// root�����tempRoot
		}

		// setup the tree layout configuration
		double gapBetweenLevels = 60;
		double gapBetweenNodes = 5;
		DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(
				gapBetweenLevels, gapBetweenNodes);
		// create the NodeExtentProvider for TextInBox nodes
		TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
		// create the layout
		TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree,
				nodeExtentProvider, configuration);
		// Create a panel that draws the nodes and edges and show the panel
		Generator.this.remove(treePane);
		xmltree = new TextInBoxTreePane(treeLayout);
		treePane= new JScrollPane(xmltree,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		treePane.setPreferredSize(new Dimension(1150, 600));
		treePane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("���νṹ"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		treePane.setBackground(Color.white);
		Generator.this.add(treePane);
		Generator.this.updateUI();


	}

	/*
	 * �ݹ鷽�������е�Ԫ�����ӵ�Tree��
	 */
	public void buildTree(Element rootXML, TextInBox myNode)// myNode�����rootXML
	{

		if (rootXML.getNodeType() == Node.ELEMENT_NODE)
		{
			int w = fm.stringWidth(rootXML.getNodeName());
			TextInBox t = new TextInBox(rootXML.getNodeName(), w+60, 20);
			tree.addChild(myNode, t);
			NodeList nl = rootXML.getChildNodes();// ȡ�øýڵ���ӽڵ��б�
			for (int i = 0; i < nl.getLength(); i++)
			{
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
					Element curElement = (Element) nl.item(i);
					buildTree(curElement, t);
				}
				else
				// ����ǽڵ�ֵ
				{
					String data = nl.item(i).getNodeValue();
					if (data != null)
					{
						data = data.trim();
						if (!data.equals("\n") && !data.equals("\r\n")
								&& data.length() > 0)
						{
							w = fm.stringWidth(nl.item(i).getNodeValue());
							tree.addChild(t, new TextInBox(nl.item(i)
									.getNodeValue(), w+60, 20));
						}
					}
				}
			}
		}
	}

	// ������
	public void addComponent(int x, int y, int w, int h, GridBagConstraints g,
			Component c)
	{
		g.gridx = x;
		g.gridy = y;
		g.gridwidth = w;
		g.gridheight = h;
		add(c, g);
	}
}
