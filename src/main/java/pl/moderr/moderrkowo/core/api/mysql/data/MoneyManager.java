package pl.moderr.moderrkowo.core.api.mysql.data;

import org.jetbrains.annotations.Contract;
import pl.moderr.moderrkowo.core.api.exceptions.UserDontHaveMoney;
import pl.moderr.moderrkowo.core.api.mysql.struct.LocalUser;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyManager{

    @Contract(pure = true)
    public static boolean isUserHasMoney(double userMoney, double requiredMoney){
        return userMoney >= requiredMoney;
    }
    public static void subtractMoneyFromUser(LocalUser user, double money) throws UserDontHaveMoney {
        double userMoney = user.getMoney();
        if(isUserHasMoney(userMoney, money)){
            user.setMoney(userMoney-money);
        }else{
            throw new UserDontHaveMoney();
        }
    }
    public static void setMoneyToUser(LocalUser user, double money){
        user.setMoney(money);
    }
    public static void addMoneyToUser(LocalUser user, double money){
        user.setMoney(user.getMoney()+money);
    }
    public static String formatMoney(double money, String suffix) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pl-PL"));
        nf.setMaximumFractionDigits(2);
        return nf.format(money) + " " + suffix;
    }
    public static String formatMoney(double money) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pl-PL"));
        nf.setMaximumFractionDigits(2);
        return nf.format(money) + " zł";
    }

}
