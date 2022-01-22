package com.tool.vincicasa.estrazioni;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CombinazioneVincente
 */
public class CombinazioneVincente {
	private List<String> estratti;

	public CombinazioneVincente estratti(List<String> estratti) {

		this.estratti = estratti;
		return this;
	}

	public CombinazioneVincente addEstrattiItem(String estrattiItem) {
		if (this.estratti == null) {
			this.estratti = new ArrayList<>();
		}
		this.estratti.add(estrattiItem);
		return this;
	}

	/**
	 * Get estratti
	 * 
	 * @return estratti
	 **/
	public List<String> getEstratti() {
		return estratti;
	}

	public void setEstratti(List<String> estratti) {
		this.estratti = estratti;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CombinazioneVincente combinazioneVincente = (CombinazioneVincente) o;
		return Objects.equals(this.estratti, combinazioneVincente.estratti);
	}

	@Override
	public int hashCode() {
		return Objects.hash(estratti);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class CombinazioneVincente {\n");
		sb.append("    estratti: ").append(toIndentedString(estratti)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
