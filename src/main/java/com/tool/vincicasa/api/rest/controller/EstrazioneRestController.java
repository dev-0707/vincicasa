package com.tool.vincicasa.api.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tool.vincicasa.estrazioni.ConcorsoService;

@RestController
public class EstrazioneRestController {

	private ConcorsoService concorsoService;

	public EstrazioneRestController(ConcorsoService concorsoService) {
		super();
		this.concorsoService = concorsoService;
	}

	@GetMapping("/api/estrazioni")
	public void getEstrazioni(@RequestParam(required = false) int anno,
			@RequestHeader(name = "partner", required = true) String idPartner,
			@RequestParam(required = false) int mese) {
		concorsoService.getEstrazione(anno, mese, idPartner);
	}
}
