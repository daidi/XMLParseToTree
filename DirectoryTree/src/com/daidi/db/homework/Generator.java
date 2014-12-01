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
		// 实例化网格组布局的支持类
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(layout);
		// 水平缩放
		gbc.weightx = 0;
		// 垂直缩放
		gbc.weighty = 0;
		// 填充
		gbc.fill = GridBagConstraints.BOTH;
		// 对齐
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(8, 3, 3, 3);

		logs = new JTextArea(
				"************************************************************\nXML可视化解析工具\n作者：戴頔\n邮箱：daidi88@sina.com\n欢迎使用XML解析器，打开XML文档请依次选择“文件”-“打开XML文档”，关于问题定义以及操作说明可点击“帮助”查看。\n************************************************************\n");
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
				BorderFactory.createTitledBorder("树形结构"),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		scroll.setBackground(Color.white);
		scroll2 = new JScrollPane(logs,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll2.setPreferredSize(new Dimension(350, 450));
		scroll2.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("运行记录"),
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
				logs.append(df.format(new Date()) + " ：您选中了节点" + str + "\n");

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
	 * 验证xml
	 * 
	 * @param xmlFileName
	 * @throws Exception
	 */
	public void validateWithDTD(File xmlFile) throws Exception
	{
		/*
		 * if (xmlFileName.length() != 1) { System.out.println("参数错误!");
		 * System.exit(1); }
		 */
		// 取得工厂类的一个新实例
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(true);
		// 通过静态方法创建DocumentBuilder 实例
		// 准备建立Document 对象
		DocumentBuilder db = dbf.newDocumentBuilder();
		db.setErrorHandler(new DTDErrorHandler());
		// 建立XML 文档对象
		Document doc = db.parse(xmlFile);
		// 格式化文档
		doc.getDocumentElement().normalize();
	}

	/*
	 * 根据传入的XML文件重新设置JTree
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
	 * 递归方法将所有的元素增加到JTree上
	 */
	public void buildTree(Element rootXML, DefaultMutableTreeNode myNode)
	{

		if (rootXML.getNodeType() == Node.ELEMENT_NODE)
		{
			DefaultMutableTreeNode t = new DefaultMutableTreeNode(
					rootXML.getNodeName());
			myNode.add(t);
			NodeList nl = rootXML.getChildNodes();// 取得该节点的子节点列表
			for (int i = 0; i < nl.getLength(); i++)
			{
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE)
				{
					Element curElement = (Element) nl.item(i);
					buildTree(curElement, t);
				}
				else
				// 如果是节点值
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

	// 添加组件
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
	 * 展开或收缩JTree的节点
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
