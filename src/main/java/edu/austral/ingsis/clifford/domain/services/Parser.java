package edu.austral.ingsis.clifford.domain.services;

import java.util.Arrays;
import java.util.List;

public class Parser {
    public static List<String> parse(String command) {
        return Arrays.stream(command.split(" ")).toList();
    }
}
