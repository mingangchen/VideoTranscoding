import java.io.IOException;

//Java 调用外部*.exe可执行文件
public class ExeFile {
	public static void main(String[] args){
		String path = "c:\\windows\\system32\\notepad.exe";	
		System.out.println("Executing a exe file!");	
		exeNotepad(path);
		System.out.println("Executing a NotePad Application!");
	}
	
	private static void exeNotepad(String path){
		try {
			Process process = null;
			Runtime rt = Runtime.getRuntime();
			process = rt.exec(path);
			//System.out.println(rt.freeMemory());
			//System.out.println(rt.totalMemory());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Executing Error!");
		}					
	}
}
/****
 * Process类是一个抽象类，封装了一个进程（即一个可执行程序）
 * Runtime.getRuntime().exec("") 创建一个本机进程
 * 每个Java Application都有一个Runtime类实例，使Java应用程序能够与其运行环境相连接。
 ***/
