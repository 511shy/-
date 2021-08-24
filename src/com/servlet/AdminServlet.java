package com.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.AdminInfo;
import com.beans.PageInfo;
import com.beans.RoleInfo;
import com.dao.AdminDao;
import com.utils.PageUtil;


@WebServlet("/AdminServlet.do")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String flag = request.getParameter("flag");
		if("add".equals(flag)) {
			addadmin(request, response);
		}
		else if("ajax".equals(flag)) {
			selectbyname(request,response);
		}
		else if("manage".equals(flag)) {
			manage(request,response);
		}
		else if("lockon".equals(flag)) {
			lockon(request,response);
		}
		else if("del".equals(flag)) {
			deladmin(request,response);
		}
		else if("lockoff".equals(flag)) {
			lockoff(request,response);
		}
		else if("select".equals(flag)) {
			selectbyid(request,response);
		}
		else if("res".equals(flag)) {
			AdminRes(request,response);
		}
		else if("delMore".equals(flag)) {
			AdminDelMore(request,response);
		}
		else if("logOut".equals(flag)) {
			logOut(request,response);
		}
		if("date".equals(flag)) {
			date(request,response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	private void date(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd EEEE HH:mm:ss");//�������ڸ�ʽ
		String date=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		response.getWriter().print(date);
	}
	protected void addadmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AdminDao _adminDao=new AdminDao();
		//�õ�����Ա��ص���Ϣ(����˵Ҫ��һ���������֤,��֤�û��˺Ų����ظ�)
				String adminName=request.getParameter("adminName");
				String password=request.getParameter("password");
				String note=request.getParameter("note");
			    
				AdminInfo admin=new AdminInfo();
				admin.setAdminName(adminName);
				admin.setPassword(password);
				admin.setNote(note);
				admin.setState("1");  //�û�Ĭ�ϵ�״̬�ǿ���
			//admin.setEditDate(editDate);  �������������������ݿ�����ʱ���,���Բ��ô�ֵ
			//admin.setRoleId(0);  //Ĭ�������û�н�ɫ
				
				int result=_adminDao.addAdmin(admin);
				if(result==1) {
					request.setAttribute("msg", "�û���ӳɹ�");
					request.getRequestDispatcher("/admin/admin_add.jsp").forward(request, response);
				}else {
					request.setAttribute("msg", "�û����ʧ��");
					request.getRequestDispatcher("/admin/admin_add.jsp").forward(request, response);
				}
	}
	private void selectbyname(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AdminDao admindao=new AdminDao();
		String adminName = request.getParameter("adminName");
		AdminInfo admin = admindao.getAdminByName(adminName);
		if(admin!=null) {
			response.getWriter().print("�û����Ѵ��ڣ�");
		}
		else {
			String regex="^\\w{4,15}$"; 
			if(adminName.matches(regex)) {
				response.getWriter().print("��");
			}
			else {
				response.getWriter().print("�û�����ʽ�Ƿ���");
			}
		}
	}
	private void manage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDao admindao=new AdminDao();
		int pageSize = 5;
		int rowCount = admindao.getAdminCount();
		int pageIndex = 1;
		String indexStr = request.getParameter("pageIndex");
		if(indexStr != null) {
			pageIndex = Integer.valueOf(indexStr);
		}
		PageInfo page = PageUtil.getPageInfo(pageSize, rowCount, pageIndex);
		List<AdminInfo> list = admindao.getAdminInfos(page);
		
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/admin/admin_manage.jsp").forward(request, response);
	}
	private void lockon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDao admindao=new AdminDao();
		int id = Integer.valueOf(request.getParameter("id"));
		AdminInfo now_admin = (AdminInfo)request.getSession().getAttribute("session_admin");
		if(id==now_admin.getId()) {
			request.setAttribute("msg", "����������ǰ��¼���û�");
			manage(request,response);
		}
		else {
			admindao.adminResState("2", id);
			manage(request,response);
		}	
	}
	private void lockoff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDao admindao=new AdminDao();
		int id = Integer.valueOf(request.getParameter("id"));
		admindao.adminResState("1", id);
		manage(request,response);
	}
	private void deladmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDao admindao=new AdminDao();
		int id = Integer.valueOf(request.getParameter("id"));
		AdminInfo now_admin = (AdminInfo)request.getSession().getAttribute("session_admin");
		if(id==now_admin.getId()) {
			request.setAttribute("msg", "����ɾ����ǰ��¼���û�");
			manage(request,response);
		}
		else {
			admindao.adminResState("0", id);
			manage(request,response);
		}
	}
	private void selectbyid(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		AdminDao admindao=new AdminDao();
		int id = Integer.valueOf(request.getParameter("id"));
		AdminInfo admin = admindao.getAdminById(id);
		AdminInfo now_admin = (AdminInfo)request.getSession().getAttribute("session_admin");
		
		if(admin.getId()==now_admin.getId()) {
			System.out.println(now_admin);
			request.setAttribute("msg", "�����޸ĵ�ǰ��¼���û�");
			manage(request,response);
		}
		else {
			List<RoleInfo> roles = admindao.getAllRoleInfo();
			request.setAttribute("admin", admin);
			request.setAttribute("roles", roles);
			request.getRequestDispatcher("/admin/admin_res.jsp").forward(request, response);
		
		}
	}
	private void AdminRes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminDao admindao=new AdminDao();
		int id = Integer.valueOf(request.getParameter("id"));
		String adminName = request.getParameter("adminName");
		String password = request.getParameter("password");
		String note = request.getParameter("note");
		int roleId = Integer.valueOf(request.getParameter("roleId"));
		System.out.println(roleId);
		AdminInfo admin = new AdminInfo();
		admin.setId(id);
		admin.setAdminName(adminName);
		admin.setPassword(password);
		admin.setNote(note);
		admin.setRoleId(roleId);
		int result = admindao.adminRes(admin);
		request.setAttribute("admin", admin);
		List<RoleInfo> roles = admindao.getAllRoleInfo();
		request.setAttribute("roles", roles);
		if(result == 1) {
			request.setAttribute("msg", "�޸ĳɹ�");
			request.getRequestDispatcher("/admin/admin_res.jsp").forward(request, response);
		}
		else {
			request.setAttribute("msg", "�޸�ʧ��");
			request.getRequestDispatcher("/admin/admin_res.jsp").forward(request, response);
		}		
	}
	private void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		response.sendRedirect("login.jsp");
	}

	private void AdminDelMore(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AdminDao admindao=new AdminDao();
		String[] ids = request.getParameterValues("ids");
		for(String id:ids) {
			int aid = Integer.valueOf(id);
			admindao.adminResState("0", aid);
		}
		response.getWriter().print("ɾ���ɹ�");
		
	}
}
