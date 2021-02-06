
public class ReportMember {

	public static void main(String[] args) {
		String[][] members = {
				{"이수훈", "안용현", "김경찬", "백수진", "유동기"},	
				{"김형석", "김도영", "김우성", "이수정"},	
				{"최동준", "김보연", "김건호", "송하섭"},	
				{"이예슬", "김선중", "박소영", "김강영"},	
				{"황시연", "이연승", "김수연", "유승종"},	
				{"오제현", "김준혁", "이건영", "전영헌"}	
		};
		
		String[] reporters = new String[members.length];
		
		for(int i=0; i<members.length; i++) {
			int index = (int)(Math.random() * members[i].length);
			reporters[i] = members[i][index];
		}
		
		System.out.println("... 발 표 자 ...");
		for(int i=0; i<reporters.length; i++) {
			System.out.println((i+1) + "조 : " + reporters[i]);
		}

	}

}
