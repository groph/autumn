package org.imperfect.autumn.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class Properties extends java.util.Properties {
	
	public void loadFromYML(InputStream source) throws IOException {
		Map<String, Object> root = new Yaml().loadAs(source, Map.class);
		loadFromNode(root, "");
	}
	
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