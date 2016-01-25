package myatm;

public interface Account {
       
    public double getBalance(); //Возвращает баланс на счете
      
    public double withdrow(double amount);  //Снимает указанную сумму со счета
                                            //Возвращает сумму, которая была снята
}
