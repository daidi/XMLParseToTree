package com.daidi.db.homework;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Generator extends JPanel
{
	private JTree xmltree;
	public JTextArea logs;
	private Document doc;
	private DefaultMutableTreeNode rootNode;
	JScrollPane scroll;
	JScrollPane scroll2;
	final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
				"************************************************************\nXML���ӻ���������\n���ߣ����E\n���䣺daidi88@sina.com\n��ӭʹ��XML����������XML�ĵ�������ѡ���ļ���-����XML�ĵ������������ⶨ���Լ�����˵���ɵ�����������鿴��\n************************************************************\n");
		logs.setLineWrap(true);

		logs.setEditable(false);

		rootNode = new DefaultMutableTreeNode("xmlRoot");

		super.setBackground(Color.white);
		xmltree = new JTree(rootNode);
		xmltree.setModel(new DefaultTreeModel(rootNode));
		xmltree.setRowHeight(20);

		xmltree.setEnabled(false);
		scroll = new JScrollPane(xmltree,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(350, 450));
		scroll.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("���νṹ"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		scroll.setBackground(Color.white);
		scroll2 = new JScrollPane(logs,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll2.setPreferredSize(new Dimension(350, 450));
		scroll2.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("���м�¼"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		scroll2.setBackground(Color.white);

		this.addComponent(0, 0, 3, 10, gbc, scroll);
		this.addComponent(4, 0, 7, 10, gbc, scroll2);

		// add(tip,BorderLayout.SOUTH);
		xmltree.addTreeSelectionListener(new TreeSelectionListener()
		{

			public void valueChanged(TreeSelectionEvent e)
			{
				// DefaultMutableTreeNode
				// selectNode=(DefaultMutableTreeNode)xmltree.get;
				// if(selectNode!=null)
				JTree tree = (JTree) e.getSource();
				DefaultMutableTreeNode selectNode = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				String str = selectNode.toString();
				logs.append(df.format(new Date()) + " ����ѡ���˽ڵ�" + str + "\n");

			}
		});

	}

	public String toXpath(DefaultMutableTreeNode myNode)
	{
		String result = "";

		DefaultMutableTreeNode parent = (DefaultMutableTreeNode) myNode
				.getParent();

		if (parent.isRoot())
		{
			result = "/" + myNode.toString();
		}
		else
		{
			// int x=parent.getIndex(aChild);

			int cnt = parent.getChildCount();
			int j = 0;
			for (int i = 0; i < cnt; i++)
			{
				if (parent.getChildAt(i).toString().equals(myNode.toString()))
				{
					j++;
					if (parent.getChildAt(i).equals(myNode))
						break;
				}
			}
			// if(parent.get)
			result += toXpath(parent) + "/" + myNode.toString() + "[" + j + "]";
		}
		// DefaultMutableTreeNode
		// parent=(DefaultMutableTreeNode)path.getParentPath().getLastPathComponent();

		return result;

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

		// this.rootNode=new DefaultMutableTreeNode("xml:"+xmlFile.getName());
		DefaultTreeModel myModel = (DefaultTreeModel) xmltree.getModel();
		this.rootNode = new DefaultMutableTreeNode("xml:" + xmlFile.getName());
		myModel.setRoot(rootNode);
		myModel.reload();
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
		Element root = doc.getDocumentElement();
		if (root != null)
		{
			buildTree(root, rootNode);
		}
		if (!xmltree.isEnabled())
			xmltree.setEnabled(true);
		// xmltree.expandRow(2);
		expandAll(xmltree, true);

	}

	/*
	 * �ݹ鷽�������е�Ԫ�����ӵ�JTree��
	 */
	public void buildTree(Element rootXML, DefaultMutableTreeNode myNode)
	{

		if (rootXML.getNodeType() == Node.ELEMENT_NODE)
		{
			DefaultMutableTreeNode t = new DefaultMutableTreeNode(
					rootXML.getNodeName());
			myNode.add(t);
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
							t.add(new DefaultMutableTreeNode(nl.item(i)
									.getNodeValue()));
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

	/*
	 * չ��������JTree�Ľڵ�
	 */

	public void expandAll(JTree tree, boolean expand)
	{
		TreeNode root = (TreeNode) tree.getModel().getRoot();

		expandAll(tree, new TreePath(root), expand);
	}

	private void expandAll(JTree tree, TreePath parent, boolean expand)
	{
		// Traverse children
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0)
		{
			for (Enumeration e = node.children(); e.hasMoreElements();)
			{
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		// Expansion or collapse must be done bottom-up
		if (expand)
		{
			tree.expandPath(parent);
		}
		else
		{
			tree.collapsePath(parent);
		}
	}
}
