package ch09_11;
class NBAteam {
	public static int counter=0;// 初始化也可在這裡，但只能給值
//	public static int counter; // 所有物件共享此份資料
	public int id; // 人員id
	public String name; // 人員姓名
//	static { // static的初始區塊
//		counter = 1;
//		System.out.println("初始化couter="+counter);
//	}
	public NBAteam() {
		id = ++counter; // 同時設定id和人數總計
		//counter=counter+1
		//id=counter
		
		
	}
	public void output() {
		System.out.println("id:" + id + "  Name: " + name);
		System.out.println("共有 " + counter + " 名成員");
	}
}
public class ch09_11 {
	public static void main(String[] args) {
		//在程式執行時，static區塊就會執行一次
		System.out.println(NBAteam.counter);
		
		NBAteam t1 = new NBAteam();
		t1.name = "Durant";
		t1.output();
		NBAteam t2 = new NBAteam();
		t2.name = "Curry";
		t2.output();
	}
}



