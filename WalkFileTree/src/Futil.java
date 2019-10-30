
// java io, charset
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;//zakladamy ze juz jest stworzony?

public class Futil {
	public static void processDir(String dirName, String resultFileName) {
		//===============Tworzenie file==============
		File file = new File(resultFileName);
		try {
			if(file.exists()) file.delete();
			file.createNewFile();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		//===========================================
		try {
			Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {//clasa generyczna
				FileChannel fileChannel;
				FileChannel resultChannel;
				@Override
		         public FileVisitResult visitFile(Path filepath, BasicFileAttributes attrs){
					try {
					Path path=null;
					Path respath = Paths.get(resultFileName);
					resultChannel = FileChannel.open(respath,StandardOpenOption.WRITE);
					 if(filepath.toString().endsWith(".txt")) {
							 path = Paths.get(filepath.toString());
						     fileChannel = FileChannel.open(path);
						 	 ByteBuffer buffer = ByteBuffer.allocate((int)fileChannel.size());
							 fileChannel.read(buffer);
					         Charset decode = Charset.forName("UTF-8");
					         Charset encode = Charset.forName("UTF-8");
					         buffer.flip();
					         CharBuffer cb = decode.decode(buffer);
					         ByteBuffer inbuffer = encode.encode(cb);
					         //inbuffer.flip();
					         while(inbuffer.hasRemaining()) {
					        	// System.out.println("Sad");
					        	 resultChannel.write(inbuffer,resultChannel.size());
					        }
						 }
					 fileChannel.close();
					 resultChannel.close();
					}catch(Exception e) {
						System.out.println(e.toString());
					}
		             return FileVisitResult.CONTINUE;
		         }
				@Override
		         public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs){
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
