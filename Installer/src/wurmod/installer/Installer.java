package wurmod.installer;

import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class Installer {

	public static void main(String[] args) throws Exception {
		Files.write(Paths.get("test.txt"), "Hello world".getBytes());
		
		Path path = Paths.get("client.jar");
		Map<String, String> env = new HashMap<String, String>();
		try (FileSystem fs = FileSystems.newFileSystem(URI.create("jar:" + path.toUri()), env)) {
			Path nf = fs.getPath("new.txt");
			try(Writer writer = Files.newBufferedWriter(nf, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
				writer.write("new!");
			}
		}
	}
}
