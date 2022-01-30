package douluolevel.douluolevel.tools;

public class Tools {

    public static boolean isNumberLegal(String amount) {
        try {
            int i = Integer.parseInt(amount);
            return i >= 0;
        } catch (Exception e) {
            return false;
        }
    }
}
