
import java.util.Scanner;
import java.io.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;

//p.867 열거형
interface INIT_MENU {
	int INPUT=1, EXIT=2;
}
//p.867 열거형
interface INPUT_SELECT {
	int NORMAL=1, UNIV=2, COMPANY=3;
}

//p.490 예외처리 
class MenuChoiceException extends Exception {
	int wrongChoice;
	
	//생성자:p.379 상속과 생성자 예제
	//생성자상속: p.381 생성자 상속
	public MenuChoiceException(int choice) {
		super("잘못된 선택이 이뤄졌습니다.");
		wrongChoice = choice;
	}
	
	public void showWrongChoice() {
		System.out.println(wrongChoice+"에 해당하는 선택은 존재하지 않습니다.");
	}	
}

// http://lng1982.tistory.com/150 직렬화 시리얼라이저블 //p527
// abstract, interface p.444 ~ p.481
class PhoneInfo implements Serializable {
	String name;
	String phoneNumber;
	
	public PhoneInfo(String name, String num){
		this.name=name;
		phoneNumber=num;
	}
	
	public void showPhoneInfo() {
		System.out.println("name : "+name);
		System.out.println("phone : "+phoneNumber);
	}
	
	public String toString() {
		return "name : " +name+ '\n'+ "phone :" +phoneNumber + '\n'; 
	}
	
	
	//http://docs.oracle.com/javase/8/docs/api/ 스트링의 해쉬코드 함수
	public int hashCode() {
		return name.hashCode();
	}
	
	//p.432 모든 클래스가 상속하는 Object 클래스
	public boolean equals(Object obj) {
		PhoneInfo cmp=(PhoneInfo)obj;
		if(name.compareTo(cmp.name)==0)
			return true;
		else
			return false;
	}
}

//p.376 ~p.394 상속 일반
class PhoneUnivInfo extends PhoneInfo {
	String major;
	int year;
	
	//생성자 p.379
	public PhoneUnivInfo(String name, String num, String major, int year) {
		super(name, num);
		this.major=major;
		this.year=year;
	}
	
	
	public void showPhoneInfo() {
		super.showPhoneInfo();
		System.out.println("major : " + major);
		System.out.println("year : "+ year);
	}
	
	public String toString() {
		return super.toString() + "major :"+ major+'\n' + "year : "+year+'\n';
	}
}


class PhoneCompanyInfo extends PhoneInfo {
	String company;
	
	public PhoneCompanyInfo(String name, String num, String company) {
		super(name, num);
		this.company=company;
	}
	
	public void showPhoneInfo() {
		super.showPhoneInfo();
		System.out.println("company : " + company);
	}
	
	public String toString() {
		return super.toString() + "company :" + company + '\n';
	}
}


class PhoneBookManager {
	//p.756 file클래스에 관한 설명
	private final File dataFile = new File("PhoneBook.dat");
	//p.628~635
	HashSet<PhoneInfo> infoStorage = new HashSet<PhoneInfo>();
	
	static PhoneBookManager inst = null;
	
	public static PhoneBookManager createManagerInst() {
		if(inst==null) 
			inst=new PhoneBookManager();
		
		return inst;
	}
	
	//infoStorage에 모든 데이터를 집어넣음
	private PhoneBookManager() {
		readFromFile();
	}
	
	//p.326 ~ 332 Scanner사용법
	private PhoneInfo readFriendInfo() {
		System.out.print("이름 : ");
		String name =MenuViewer.keyboard.nextLine();
		System.out.print("전화번호 : "); 
		String phone =MenuViewer.keyboard.nextLine();
		return new PhoneInfo(name, phone);
	}
	
	//p.326 ~ 332 Scanner사용법
	private PhoneInfo readUnivFriendInfo() {
		System.out.print("이름 : ");
		String name =MenuViewer.keyboard.nextLine();
		
		System.out.print("전화번호 : ");
		String phone =MenuViewer.keyboard.nextLine();
		
		System.out.print("전공 : ");
		String major =MenuViewer.keyboard.nextLine();
		
		System.out.print("학년 : ");
		int year =MenuViewer.keyboard.nextInt();
		
		MenuViewer.keyboard.nextLine();
		return new PhoneUnivInfo(name, phone, major, year);
		
	}
	
	//p.326 ~ 332 Scanner사용법
	private PhoneInfo readCompanyFriendInfo() {
		System.out.print("이름 : ");
		String name =MenuViewer.keyboard.nextLine();
		
		System.out.print("전화번호 : ");
		String phone =MenuViewer.keyboard.nextLine();
		
		System.out.print("회사 : ");
		String company =MenuViewer.keyboard.nextLine();
		
		MenuViewer.keyboard.nextLine();
		return new PhoneCompanyInfo(name, phone, company);
	}

	public void inputData() throws MenuChoiceException {
		System.out.println("데이터 입력을 시작합니다..");
		System.out.println("1. 일반, 2. 대학, 3. 회사");
		System.out.print("선택 >> ");
		int choice = MenuViewer.keyboard.nextInt();
		MenuViewer.keyboard.nextLine();
		PhoneInfo info =null;
		
		//이상한 숫자 입력시 예외처리 해줌, 사용자 정의 예외 p.504
		if(choice<INPUT_SELECT.NORMAL || choice>INPUT_SELECT.COMPANY)
			throw new MenuChoiceException(choice);
		
		switch(choice) {
		case INPUT_SELECT.NORMAL :
			info=readFriendInfo();
			break;
		case INPUT_SELECT.UNIV :
			info=readUnivFriendInfo();
			break;
		case INPUT_SELECT.COMPANY :
			info=readCompanyFriendInfo();
			break;
		}
		
		boolean isAdded = infoStorage.add(info);
		if(isAdded==true)
			System.out.println("데이터 입력이 완료되었습니다. \n");
		else
			System.out.println("이미 저장된 데이터입니다. \n");
	}
	
	public String searchData(String name) {
		PhoneInfo info = search(name);
		if(info==null)
			return null;
		else 
			return info.toString();
	}

	public boolean deleteData(String name) {
		Iterator<PhoneInfo> itr=infoStorage.iterator();
		
		while(itr.hasNext()) {
			PhoneInfo curInfo=itr.next();
			if(name.compareTo(curInfo.name)==0) {
				itr.remove();
				return true;
			}
		}
		return true;
	}		


private PhoneInfo search(String name) {
	Iterator<PhoneInfo> itr =infoStorage.iterator();
	while(itr.hasNext()) {
		PhoneInfo curInfo=itr.next();
		if(name.compareTo(curInfo.name)==0)
			return curInfo;
	}
	return null;
}

public void storeToFile() {
	try {
		FileOutputStream file=new FileOutputStream(dataFile);
		ObjectOutputStream out =new ObjectOutputStream(file);
		
		Iterator<PhoneInfo> itr =infoStorage.iterator();
		while(itr.hasNext())
			out.writeObject(itr.next());
		
			out.close();
	}
	catch(IOException e) {
		e.printStackTrace();
	}
}

public void readFromFile() {
	if(dataFile.exists()==false)
		return;
	
	try {
		//p.713 스트림
		//FileInputStream file= ;
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
		
		while(true) {
			PhoneInfo info =(PhoneInfo)in.readObject();
			if(info==null)
				break;
			infoStorage.add(info);
		}
		in.close();
	}
	catch(IOException e) {
		return;
	}
	catch(ClassNotFoundException e) {
		return;
	}
}

}

//p.326 ~ 332 Scanner사용법
class MenuViewer {
	public static Scanner keyboard = new Scanner(System.in);
	
	public static void showMenu() {
		System.out.println("선택하세요...");
		System.out.println("1. 데이터 입력");
		System.out.println("2. 프로그램 종료");
		System.out.println("선택 : ");
	}
}

class CM {
	public static void main(String[] args) {
		PhoneBookManager manager = PhoneBookManager.createManagerInst();
		
		int choice;
		
		while(true) {
			try {
				MenuViewer.showMenu();
				choice=MenuViewer.keyboard.nextInt();
				MenuViewer.keyboard.nextLine();
				
				if(choice<INIT_MENU.INPUT || choice>INIT_MENU.EXIT)
					throw new MenuChoiceException(choice);
				
				switch(choice) {
					case INIT_MENU.INPUT :
						manager.inputData();
						break;
					case INIT_MENU.EXIT :
						manager.storeToFile();
						System.out.println("프로그램을 종료합니다.");
						return;
				}
			}
			catch(MenuChoiceException e) {
				e.showWrongChoice();
				System.out.println("메뉴 선택을 처음부터 다시 진행합니다. \n");
			}
		}
	}	
}







