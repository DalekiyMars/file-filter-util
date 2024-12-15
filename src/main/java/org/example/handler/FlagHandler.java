package org.example.handler;
import java.util.*;

public interface FlagHandler {
    /**
     * Обрабатывает определенный флаг из аргументов командной строки.
     *
     * @param args    Список аргументов
     * @param index   Номер аргумента в списке
     * @param context Мапа для хранения обработанных данных флагов.
     */
    void handle(List<String> args, int index, Map<String, Object> context);
}
