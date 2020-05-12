package org.imperfect.autumn.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PersistenceProperties extends Properties {
	
	public void loadFromYML(InputStream source) {
		@SuppressWarnings("unchecked")
		Map<String, Object> root = new Yaml().loadAs(source, Map.class);
		loadFromNode(root, "");
	}
	
	@SuppressWarnings("unchecked")
	private void loadFromNode(Map<String, Object> parentNode, String prefix) {
		parentNode.forEach((name, value) -> {
			String qualifiedName = "".equals(prefix)? name
					: String.join(".", prefix, name);
			if(value == null) {
				setProperty(qualifiedName, "");
			} else if(Map.class.isAssignableFrom(value.getClass())) {
				loadFromNode((Map<String, Object>) value, qualifiedName);
			} else {
				setProperty(qualifiedName, value.toString());
			}
		});
	}
	
}
