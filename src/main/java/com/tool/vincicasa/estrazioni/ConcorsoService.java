package com.tool.vincicasa.estrazioni;

import com.workshop.winh.service.rest.api.client.ConcorsoApi;
import com.workshop.winh.service.rest.api.client.dto.ConcorsiDto;
import com.workshop.winh.service.rest.api.client.dto.DettaglioConcorsoDto;

public class ConcorsoService {
	
	private ConcorsoApi concorsoAPI;
	private ConcorsoRepository concorsoRepository;
	private ConcorsoMapper concorsoMapper;

	public ConcorsoService(ConcorsoApi estrazioniAPI, ConcorsoRepository concorsoRepository,
			ConcorsoMapper concorsoMapper) {
		super();
		this.concorsoAPI = estrazioniAPI;
		this.concorsoRepository = concorsoRepository;
		this.concorsoMapper = concorsoMapper;
	}

	// Concoroso
	public void getEstrazione(int anno, int mese, String idPartner) {
		if (anno == 0 && mese == 0) {
			DettaglioConcorsoDto concorso = concorsoAPI.getEstrazione(idPartner);
			concorsoRepository.save(concorsoMapper.toConcorso(concorso));
		} else {
			ConcorsiDto archivioEstrazione = concorsoAPI.getArchivioEstrazione(anno, mese, idPartner);
			archivioEstrazione.getConcorsi().forEach(concorso -> {
				concorsoRepository.save(concorsoMapper.toConcorso(concorso));
			});
		}
	}

}
