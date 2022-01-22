package com.tool.vincicasa.estrazioni;

import com.workshop.winh.service.rest.api.client.dto.ArchivioConcorsoDto;
import com.workshop.winh.service.rest.api.client.dto.CombinazioneVincenteDto;
import com.workshop.winh.service.rest.api.client.dto.DettaglioConcorsoDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface ConcorsoMapper {

	@Mapping(target = "dataEstrazione", expression = "java(new java.util.Date(concorsoDto.getDettaglioConcorso().getDataEstrazione()))")
	@Mapping(target = "combinazioneVincente", expression = "java(java.lang.String.join(\" \", concorsoDto.getDettaglioConcorso().getCombinazioneVincente().getEstratti()))")
	@Mapping(target = "numero", source="dettaglioConcorso.concorso.numero")
	@Mapping(target = "anno", source="dettaglioConcorso.concorso.anno")
	Concorso toConcorso(DettaglioConcorsoDto concorsoDto);

	@Mapping(target = "dataEstrazione", expression = "java(new java.util.Date(concorsoDto.getDataEstrazione()))")
	@Mapping(target = "dettaglioDisponibile", ignore = true, expression = "java(com.telepass.service.proximity.channel.eb.Util.capitalize(cliente.getCognome()))")
	@Mapping(target = "combinazioneVincente", expression = "java(java.lang.String.join(\" \", concorsoDto.getCombinazioneVincente().getEstratti()))")
	@Mapping(target = "numero", source="concorso.numero")
	@Mapping(target = "anno", source="concorso.anno")
	Concorso toConcorso(ArchivioConcorsoDto concorsoDto);


	CombinazioneVincente toCombinazioneVincente(CombinazioneVincenteDto combinazioneVincenteDto);

}
