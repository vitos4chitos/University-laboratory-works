package hashMap;

import java.util.Arrays;

public class HashMap {
    private int FREE = Integer.MIN_VALUE;
    private int size;
    private int[] keys;
    private int[] values;

    /* Конструктор */
    public HashMap(int size) {
        this.size = size;
        keys = new int [this.size];
        values = new int [this.size];
        Arrays.fill(keys, FREE);
    }

    /* Добавляет пару в множество */
    public void put(int x, int y) {
          int sum = 0;
          for (int i = index(hash(x)); ; i++) {
                sum++;
                if (i == size) i = 0;
                if (keys[i] == FREE)
                    keys[i] = x;
                if (keys[i] == x) {
                    values[i] = y;
                    return;
               }
               if(sum == size){
                   return;
               }
          }
    }
    /* Извлекает значение */
    public int get(int x) {
        int sum = 0;
        for (int i = index(hash(x)); ; i++) {
            sum++;
            if (i == size) i = 0;
            if (keys[i] == FREE) throw new RuntimeException("No such key!");
            if (keys[i] == x) return values[i];
            if(sum == size){
                throw new RuntimeException("No such key!");
            }
        }
    }

    /* Проверяет наличие пары с ключем x */
    public boolean containsKey(int x) {
        int sum = 0;
        for (int i = index(hash(x)); ; i++) {
            sum++;
            if (i == size) i = 0;
            if (keys[i] == FREE) return false;
            if (keys[i] == x) return true;
            if(sum == size){
                return false;
            }
        }
    }

    public int getSize(){
        return size;
    }

    public int[] getKeys(){
        return keys;
    }

    public  int[] getValues(){
        return values;
    }
    /* хэш-функция */
    public int hash(int x) {
           return (x >> 15) ^ x;
    }

    /* возвращает номер головы по значению хэш-функции */
    public int index(int hash) {
        return Math.abs(hash) % size;
    }
}
