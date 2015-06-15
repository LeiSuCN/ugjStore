package o2o;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/*******************************************
 * @CLASS:Test
 * @DESCRIPTION:	
 * @VERSION:v1.0
 * @DATE:2015年4月10日 下午12:20:05
 *******************************************/
public class Test {

	@org.junit.Test
	public void test() throws IOException {
		File src = new File("D:\\zfile\\store\\44");
		File desc = new File("D:\\zfile\\store\\4403010001");
		
		FileUtils.moveDirectory(src, desc);
	}

}

