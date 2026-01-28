package br.com.detran.crud_veiculo_proprietario.util;

public final class CpfCnpjValidator {

    private CpfCnpjValidator() {
    }

    public static boolean isValidCpfOrCnpj(String value) {
        String digits = onlyDigits(value);
        if (digits.length() == 11) return isValidCpfDigits(digits);
        if (digits.length() == 14) return isValidCnpjDigits(digits);
        return false;
    }

    public static boolean isValidCpf(String value) {
        return isValidCpfDigits(onlyDigits(value));
    }

    public static boolean isValidCnpj(String value) {
        return isValidCnpjDigits(onlyDigits(value));
    }

    private static String onlyDigits(String value) {
        if (value == null) return "";
        return value.replaceAll("\\D+", "");
    }

    private static boolean isValidCpfDigits(String cpf) {
        if (cpf.length() != 11) return false;
        if (allSameChar(cpf)) return false;

        int sum1 = 0;
        for (int i = 0; i < 9; i++) {
            sum1 += (cpf.charAt(i) - '0') * (10 - i);
        }
        int dv1 = 11 - (sum1 % 11);
        if (dv1 >= 10) dv1 = 0;
        if (dv1 != (cpf.charAt(9) - '0')) return false;

        int sum2 = 0;
        for (int i = 0; i < 10; i++) {
            sum2 += (cpf.charAt(i) - '0') * (11 - i);
        }
        int dv2 = 11 - (sum2 % 11);
        if (dv2 >= 10) dv2 = 0;

        return dv2 == (cpf.charAt(10) - '0');
    }

    private static boolean isValidCnpjDigits(String cnpj) {
        if (cnpj.length() != 14) return false;
        if (allSameChar(cnpj)) return false;

        int[] w1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] w2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum1 = 0;
        for (int i = 0; i < 12; i++) {
            sum1 += (cnpj.charAt(i) - '0') * w1[i];
        }
        int dv1 = sum1 % 11;
        dv1 = (dv1 < 2) ? 0 : (11 - dv1);
        if (dv1 != (cnpj.charAt(12) - '0')) return false;

        int sum2 = 0;
        for (int i = 0; i < 13; i++) {
            sum2 += (cnpj.charAt(i) - '0') * w2[i];
        }
        int dv2 = sum2 % 11;
        dv2 = (dv2 < 2) ? 0 : (11 - dv2);

        return dv2 == (cnpj.charAt(13) - '0');
    }

    private static boolean allSameChar(String s) {
        char c = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != c) return false;
        }
        return true;
    }
}
