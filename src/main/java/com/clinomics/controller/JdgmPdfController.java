package com.clinomics.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clinomics.service.setting.BundleService;
import com.clinomics.service.JdgmPdfService;
import com.clinomics.service.SampleDbService;
import com.clinomics.enums.StatusCode;
import com.clinomics.service.InputExcelService;
import com.clinomics.service.SampleItemService;

@Controller
public class JdgmPdfController {

	@Autowired
	JdgmPdfService jdgmPdfService;
	
	@Autowired
	SampleItemService sampleItemService;

	@Autowired
	InputExcelService inputExcelService;

	@Autowired
	SampleDbService sampleDbService;
	
	@Autowired
	BundleService bundleService;
	
	@GetMapping("/jdgm/list")
	@ResponseBody
	public Map<String, Object> rvc(@RequestParam Map<String, String> params) {
		return sampleDbService.find(params, 460);
	}
	
	@PostMapping("/jdgm/approve")
	@ResponseBody
	public Map<String, String> approve(@RequestBody List<Integer> ids) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return jdgmPdfService.jdgmApprove(ids, userDetails.getUsername());
	}
	
	@GetMapping("/output/list")
	@ResponseBody
	public Map<String, Object> outputList(@RequestParam Map<String, String> params) {
		return sampleDbService.find(params, 700);
	}
	
	@PostMapping("/output/approve")
	@ResponseBody
	public Map<String, String> outputApprove(@RequestBody List<Integer> ids) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return jdgmPdfService.outputApprove(ids, userDetails.getUsername());
	}
}