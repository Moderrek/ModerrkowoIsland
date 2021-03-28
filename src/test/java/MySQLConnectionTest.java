import pl.moderr.moderrkowo.core.api.mysql.MySQL;
import pl.moderr.moderrkowo.core.api.mysql.type.IDType;

public class MySQLConnectionTest {

    public static void main(String[] args) {
        MySQL mySQL = new MySQL("serwer2031888.home.pl", 3306, "32857308_moderrblock", "32857308_moderrblock", "moderrblock");
        /*Statement statement = null;
        long start = System.currentTimeMillis();
        try {
            System.out.println("Creating query");
            statement = mySQL.getConnection().createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.execute("SELECT `IS_ADMIN` FROM `panel_data` WHERE `UUID`='b24e41ab-8e0d-4c2f-843a-c6cf762be630'");
                System.out.println("Executed query " + (System.currentTimeMillis() - start) + " ms");
            }else{
                System.out.println("Statement is null");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        try {
            //MCID2
            //b24e41ab-8e0d-4c2f-843a-c6cf762be630
            System.out.println(mySQL.getQueries().getMinecraftUserMoney("testname", IDType.Username));
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}
