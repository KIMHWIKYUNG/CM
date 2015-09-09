
class FruitSeller {
	int numOfApple;
	int myMoney;
	final int APPLE_PRICE;
	
	public FruitSeller(int money, int appleNum, int price) {
		myMoney = money;
		numOfApple = appleNum;
		APPLE_PRICE = price;
	}
	
	public void saleApple(FruitBuyer buyer, int money) {
		numOfApple -= buyer.buyApple(money);
		myMoney+=money;
	}
	
	public void showSaleResult() {
		System.out.println("남은 사과 : "+numOfApple);
		System.out.println("판매 수 : "+myMoney);		
	}	
}

class FruitBuyer {
	int myMoney;
	int numOfApple;
	final int APPLE_PRICE;
	
	public FruitBuyer(int money, int price) {
		myMoney = money ;
		APPLE_PRICE = price;
		numOfApple=0;
	}
	public int buyApple(int money) {
		myMoney-=money;
		int num= money/APPLE_PRICE;
		numOfApple+=num;
		return num;
}
	
	public void showBuyerResult() {
		System.out.println("남은 사과 : "+numOfApple);
		System.out.println("판매 수 : "+myMoney);		
	}	
}	
	
public class Apple {
	public static void main(String[] args) {
		FruitBuyer buyer1 = new FruitBuyer(15000,100);
		FruitBuyer buyer2 = new FruitBuyer(10000,100);
		
		FruitSeller seller = new FruitSeller(0, 30, 100);
		seller.saleApple(buyer1, 500);
		seller.saleApple(buyer2, 200);
		
		System.out.println("과일 판매자의 현재 상황");
		seller.showSaleResult();
		
		System.out.println("과일 구매자1의 현재 상황");
		buyer1.showBuyerResult();
		
		System.out.println("과일 구매자2의 현재 상황");
		buyer2.showBuyerResult();
	}
}








