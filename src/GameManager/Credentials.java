package GameManager;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Credentials {
    private SystemGame s;

    public Credentials(SystemGame s) {
        this.s = s;
    }

    public String isValid(String userName, String password) {
        if (userName.length() < 3 || password.length() < 3 ) {
            return "-1";
        }
        Player ultimo = null;
        LinkedHashMap<String, Player> userList = s.getUserList();
        if (userList.isEmpty()) {
            return "A00AA";
        }
        for (Player user : s.getUserList().values()) {
            if (user.getUserName().equals(userName)) {
                return "-1";
            }
            ultimo = user;
        }
        String lastCode = ultimo.getRegistrationNumber();
        return createCode(lastCode);
    }

    private String createCode(String code) {
        char[] codeArray = code.toCharArray();
        char letra1 = codeArray[0];
        char letra2 = codeArray[3];
        char letra3 = codeArray[4];
        int numero1 = Character.getNumericValue(codeArray[1]);
        int numero2 = Character.getNumericValue(codeArray[2]);
        int num = numero1 * 10 + numero2;
        if (num + 1 < 100) {
            num++;
            if (num < 10) {
                return letra1 + "0" + num + letra2 + letra3;
            } else {
                return "" + letra1 + num + letra2 + letra3;
            }
        } else {
            if (letra1 == letra2) {
                letra1 = (char) (letra1 + 1);
            } else if (letra2 == letra3) {
                letra2 = (char) (letra2 + 1);
            } else {
                letra3 = (char) (letra3 + 1);
            }
            return letra1 + "00" + letra2 + letra3;
        }
    }

    private boolean validPassword(String password) {
        int numeros = 0;
        int count = 0;
        boolean tieneMayuscula = false;
        boolean tieneSimbolo = false;
        for (char c : password.toCharArray()) {
            count++;
            if (Character.isDigit(c)) {
                numeros++;
            } else if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            } else if (!Character.isLetterOrDigit(c)) {
                tieneSimbolo = true;
            }
        }
        return numeros >= 4 && tieneMayuscula && tieneSimbolo && count >= 9;
    }
}
