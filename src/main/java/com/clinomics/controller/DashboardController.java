package com.clinomics.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.clinomics.service.CalendarExcelService;
import com.clinomics.service.CalendarService;
import com.clinomics.service.ChartService;
import com.clinomics.service.setting.BundleService;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/")
@Controller
public class DashboardController {

	@Autowired
	CalendarService calendarService;

	@Autowired
	CalendarExcelService calendarExcelService;

	@Autowired
	ChartService chartService;

	@Autowired
	BundleService bundleService;

	@GetMapping("/calendar/get/statistics")
	@ResponseBody
	public Map<String, Object> calendar(@RequestParam Map<String, String> params) {
		return calendarService.selectCountByMonthly(params);
	}

	@GetMapping("/popup/registered")
	@ResponseBody
	public Map<String, Object> registered(@RequestParam Map<String, String> params) {
		return calendarService.selectRegistered(params);
	}

	@GetMapping("/popup/analysis")
	@ResponseBody
	public Map<String, Object> analysis(@RequestParam Map<String, String> params) {
		return calendarService.selectAnalysis(params);
	}

	@GetMapping("/popup/completed")
	@ResponseBody
	public Map<String, Object> completed(@RequestParam Map<String, String> params) {
		return calendarService.selectCompleted(params);
	}

	@GetMapping("/popup/reported")
	@ResponseBody
	public Map<String, Object> reported(@RequestParam Map<String, String> params) {
		return calendarService.selectReported(params);
	}

	@GetMapping("/chart/get/statistics")
	@ResponseBody
	public Map<String, Object> chart(@RequestParam Map<String, String> params) {
		return chartService.selectCountByMonthly(params);
	}

	@GetMapping("/human/excel/form")
	public void exportExcelForm(@RequestParam Map<String, String> params, HttpServletResponse response) {
		XSSFWorkbook xlsx = calendarExcelService.exportHumanExcelForm(params);
		requestExcel(xlsx, "인체유래물등 관리대장", response);
	}

	// ############################ private
	private void requestExcel(XSSFWorkbook xlsx, String fileName, HttpServletResponse response) {
		if (fileName == null || fileName.trim().length() < 1) {
			fileName = "sample";
		}
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		// 엑셀파일명 한글깨짐 조치
		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		try {
			ServletOutputStream out = response.getOutputStream();
			
			xlsx.write(out);
			xlsx.close();
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}