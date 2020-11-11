package wurmod.mod;

import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

public class ModDescriptionFile {

	private static final Yaml YAML = new Yaml(new SafeConstructor());
	
	private String name = null;
	private String version = null;
	private String main = null;
	
	public ModDescriptionFile(InputStream is) throws ModException {
		Map<String, Object> map = YAML.load(is);
		
		try {
			this.name = (String)map.get("name");
			this.version = (String)map.get("version");
			this.main = (String)map.get("main");
		} catch(ClassCastException e) {
			throw new ModException(e);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getVersion() {
		return version;
	}
	
	public String getMain() {
		return main;
	}
}
