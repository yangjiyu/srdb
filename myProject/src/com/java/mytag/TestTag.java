package com.java.mytag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class TestTag extends TagSupport{
	private static int count=0;
	
	
	
	@Override
	public int doAfterBody() throws JspException {
		// TODO Auto-generated method stub
		JspWriter out=this.pageContext.getOut();
		if(count>=0){
			try {
				out.write("<a href='http://www.baidu.com'>测试"+count+"</a><br>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count--;
			return TagSupport.EVAL_BODY_AGAIN;
		}
		
		return TagSupport.SKIP_BODY;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		ServletRequest request=this.pageContext.getRequest();
		String param=request.getParameter("param");
		count=Integer.valueOf(request.getParameter("count"));
		JspWriter out=this.pageContext.getOut();
		try {
			out.write(param+":"+"<br>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TagSupport.EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return TagSupport.SKIP_PAGE;
	}
	
	public static  Map<String,String> getChange(Map<String,String> map){
		map.put("key","234");
		return map;
	}
	public static void main(String[] args) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("key","1");
		getChange(map);
		System.out.println(map.get("key"));		
	}

}
