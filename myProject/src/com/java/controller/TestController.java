package com.java.controller;

import java.awt.List;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import javax.management.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.ConsoleAppender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/test")
public class TestController {

	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/change",method = RequestMethod.POST)
	public String change(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		InputStream  in=request.getInputStream();
		InputStreamReader tr=new  InputStreamReader(in);
		BufferedReader  br=new BufferedReader(tr);
		String s="";
		String result="";
		while((s=br.readLine())!=null){
			result+=s;
		}
		System.out.println(result);
		return "succss";
	}

	@RequestMapping(value = "/jsonn", method = RequestMethod.POST)
	public void jsonn(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		System.out.println("进入方法jsonn");
		String flag = request.getHeader("X-Requested-With");
		if (flag != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", "1");
			response.getWriter().write(JSONObject.fromObject(map).toString());
		} else {
			response.sendRedirect("http://www.baidu.com");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/json", method = RequestMethod.POST)
	public String json(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		System.out.println("进入方法json");
		try {
			request.getRequestDispatcher("jsonn").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "1";
	}


	public static void attack() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpgets = new HttpPost(
				"http://192.168.1.181:8886/myProject/test/change");
		
		HttpResponse response;

		StringEntity s = null;
		try {
			s = new StringEntity("123");
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		httpgets.setEntity(s);

		
		try {
			response = httpclient.execute(httpgets);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String str = convertStreamToString(instreams);
				System.out.println("Do something");
				System.out.println(str);
				// Do not need the rest
				httpgets.abort();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		final BlockingQueue<String> q=new ArrayBlockingQueue<String>(3);
		q.offer("2222");
		q.offer("3333");
		q.offer("4444");
		Timer t=new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				String s=q.poll();
				System.out.println("拿出元素："+s);
			}
		}, 5000);
		try {
			boolean flag=q.offer("111", 10, TimeUnit.MINUTES);
			if(!flag){
				System.out.println("队列已满");
			}else{
				System.out.println("已入队");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<String> list=new ArrayList<String>();
		q.drainTo(list,2);
		for(String s:list){
			System.out.println(s);
		}
	}
}
