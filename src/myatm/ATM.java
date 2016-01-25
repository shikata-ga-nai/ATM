package myatm;

public class ATM {
    public double moneyInATM;
    public boolean checkCard;
    Card card;
    Account acc;
    
    //Можно задавать количество денег в банкомате 
    ATM(double moneyInATM){
        if (moneyInATM < 0 ) throw new IllegalArgumentException("IllegalMoneyInATM!!!");
        else {
           this.moneyInATM = moneyInATM;
           this.checkCard = false;
           this.acc = null; 
        }
    }
    
    public double getMoneyInATM() {
        return moneyInATM;
    }
       
    //С вызова данного метода начинается работа с картой
    //Метод принимает карту и пин-код, проверяет пин-код карты и не заблокирована ли она
    //Если неправильный пин-код или карточка заблокирована, возвращаем false. 
    //!!!!!При этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
    
    public boolean validateCard(Card card, int pinCode)throws Exception{
        checkCard = false;
        if (card == null){
            throw new NoCardInserted();
        }
        else{
            if (card.isBlocked() != true && card.checkPin(pinCode) == true){
                acc = card.getAccount();
                checkCard = true;
            }
            else throw new NoCardInserted();
        }
        return checkCard;   
    }
        
    public double checkBalance()throws Exception{   //Возвращает сколько денег есть на счету
        if (checkCard == true) {
            return acc.getBalance();  
        }
        else throw new NoCardInserted();
        
    }
    
    /*
     *  Метод для снятия указанной суммы
     *  Метод возвращает сумму, которая у клиента осталась на счету после снятия
     *  Кроме проверки счета, метод так же должен проверять достаточно ли денег в самом банкомате
     *  Если недостаточно денег на счете, то должно генерироваться исключение NotEnoughMoneyInAccount 
     *  Если недостаточно денег в банкомате, то должно генерироваться исключение NotEnoughMoneyInATM 
     *  При успешном снятии денег, указанная сумма должна списываться со счета, 
     *  и в банкомате должно уменьшаться количество денег  
     */
    
    public double getCash(double amount) throws Exception {
        double moneyInAccount = checkBalance();
        
        if (checkCard == true){
            if (moneyInAccount >= amount){
                if (moneyInATM >= amount){
                    moneyInATM -= amount;
                    return acc.withdrow(amount);
                }
                else throw new NotEnoughMoneyInATM();
            }
            else throw new NotEnoughMoneyInAccount();
        }
        else throw new NoCardInserted();
    }
}