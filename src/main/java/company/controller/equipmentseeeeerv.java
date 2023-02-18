package company.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import company.model.EquipmentVO;
import company.service.EquipmentService;

@WebServlet("/equipment")
public class equipmentseeeeerv extends HttpServlet {

	private static final String EquipmentName = null;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		EquipmentService equipmentService = new EquipmentService();	
		PrintWriter out = res.getWriter();
		
	Gson gson = new Gson();
	out.println(gson.toJson(equipmentService.getAll()));
	}
	

}