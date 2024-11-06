package lwz.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class LWZ {

    // Método para comprimir una cadena
    public static List<Integer> compress1(String input) {
        Map<String, Integer> dictionary = new HashMap<>();

        for (int i = 0; i < 256; i++) {
            dictionary.put(String.valueOf((char) i), i);
        }

        List<Integer> output = new ArrayList<>();
        int dictSize = 256;
        String currentString = "";

        for (char c : input.toCharArray()) {
            String newString = currentString + c;
            if (dictionary.containsKey(newString)) {
                currentString = newString;
            } else {
                output.add(dictionary.get(currentString));
                dictionary.put(newString, dictSize++);
                currentString = String.valueOf(c);
            }
        }

        if (!currentString.isEmpty()) {
            output.add(dictionary.get(currentString));
        }

        return output;
    }

    public static int compress(String pe, String se, Hashtable<String, Integer> diccionario) {
        int output;  // Almacena el código de salida de `pe`
        int dictSize = diccionario.size();  // Mantiene el tamaño actual del diccionario

        String ps = pe + se;

        // Si `ps` está en el diccionario, actualizamos `pe` a `ps`
        if (diccionario.containsKey(ps)) {
            pe = ps;
            output = diccionario.get(pe);  // Asignamos `output` al código de `pe`
        } else {
            output = diccionario.get(pe);  // Tomamos el código de `pe` como salida
            diccionario.put(ps, dictSize); // Agregamos `ps` al diccionario con un nuevo código
        }

        return output;  // Retornamos el código de `pe` en cada caso
    }

    // Método para descomprimir una lista de códigos
    public static String decompress(List<Integer> input) {
        Map<Integer, String> dictionary = new HashMap<>();

        for (int i = 0; i < 256; i++) {
            dictionary.put(i, String.valueOf((char) i));
        }

        StringBuilder result = new StringBuilder();
        int dictSize = 256;
        String currentString = String.valueOf((char) (int) input.remove(0));
        result.append(currentString);

        for (int code : input) {
            String entry;
            if (dictionary.containsKey(code)) {
                entry = dictionary.get(code);
            } else if (code == dictSize) {
                entry = currentString + currentString.charAt(0);
            } else {
                throw new IllegalArgumentException("Código inválido: " + code);
            }

            result.append(entry);
            dictionary.put(dictSize++, currentString + entry.charAt(0));
            currentString = entry;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String text = "wabba-wabba-wabba-wabba-woo-woo-woo";

        // Crear un ArrayList y agregar cada carácter como un elemento separado
        ArrayList<String> list = new ArrayList<>();
        for (char c : text.toCharArray()) {
            list.add(String.valueOf(c));
        }

        // Imprimir el ArrayList
        System.out.println(list);

        // Inicializar el diccionario una vez
        Hashtable<String, Integer> diccionario = new Hashtable<>();
        for (int i = 0; i < 256; i++) {
            diccionario.put("" + (char) i, i);
        }

        // Bucle para recorrer pares de caracteres
        for (int i = 0; i < list.size() - 1; i++) {
            String pe = list.get(i);       // Primer elemento del par actual
            String se = list.get(i + 1);   // Segundo elemento del par actual

            int compressed = compress(pe, se, diccionario);
            System.out.println("Par (" + pe + ", " + se + ") - Salida: " + compressed);
        }
    }

}
