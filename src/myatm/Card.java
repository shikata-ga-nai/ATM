package myatm;

public interface Card {
    public boolean isBlocked();  //Заблокирована ли карта или нет
    public Account getAccount(); //Возвращает счет связанный с данной картой
    public boolean checkPin(int pinCode);  //Проверяет корректность пин-кода  
}
  
