package com.daidi.db.homework;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DTDErrorHandler implements ErrorHandler
{
	final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	public void error(SAXParseException arg0) throws SAXException
	{
		throw new SAXException(df.format(new Date())+" ���ڴ򿪵� " + arg0.getLineNumber() + "�У��� "
				+ arg0.getColumnNumber() + "�з�����ͨ����ԭ���ǣ�" + arg0.getMessage()
				+ "\n" + "�����ĵ�λ�ã�" + arg0.getSystemId() + "\n");
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException
	{
		throw new SAXException(df.format(new Date())+" ���ڵ� " + arg0.getLineNumber() + "�У��� "
				+ arg0.getColumnNumber() + "�з�����������ԭ���ǣ�"
				+ arg0.getMessage() + "\n" + "�����ĵ�λ�ã�" + arg0.getSystemId()
				+ "\n");
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException
	{
		throw new SAXException(df.format(new Date())+" ���ڵ� " + arg0.getLineNumber() + "�У��� "
				+ arg0.getColumnNumber() + "�г��־��棬ԭ���ǣ�" + arg0.getMessage()
				+ "\n" + "�����ĵ�λ�ã�" + arg0.getSystemId() + "\n");
	}

}
