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
		throw new SAXException(df.format(new Date())+" ：在打开第 " + arg0.getLineNumber() + "行，第 "
				+ arg0.getColumnNumber() + "列发生普通错误，原因是：" + arg0.getMessage()
				+ "\n" + "错误文档位置：" + arg0.getSystemId() + "\n");
	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException
	{
		throw new SAXException(df.format(new Date())+" ：在第 " + arg0.getLineNumber() + "行，第 "
				+ arg0.getColumnNumber() + "列发生致命错误，原因是："
				+ arg0.getMessage() + "\n" + "错误文档位置：" + arg0.getSystemId()
				+ "\n");
	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException
	{
		throw new SAXException(df.format(new Date())+" ：在第 " + arg0.getLineNumber() + "行，第 "
				+ arg0.getColumnNumber() + "列出现警告，原因是：" + arg0.getMessage()
				+ "\n" + "错误文档位置：" + arg0.getSystemId() + "\n");
	}

}
