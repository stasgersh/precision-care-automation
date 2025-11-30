package com.ge.onchron.verif.model.clinicalConfiguration;

import lombok.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.ge.onchron.verif.utils.Delimiter.COMMA;
import static com.ge.onchron.verif.utils.Delimiter.PIPE;


@Builder
@Getter
@ToString
public class ExpectedCSVContainer {

    private List<ExpectedCriteria> expectedCriteriaList;

    public static ExpectedCSVContainer parseExpectedFile(final String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            List<ExpectedCriteria> criteriaList = lines.stream()
                    .skip(1)
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(ExpectedCSVContainer::splitRespectingQuotes)
                    .filter(ExpectedCSVContainer::isValidLine)
                    .map(ExpectedCSVContainer::toExpectedCriteria)
                    .collect(Collectors.toList());

            return new ExpectedCSVContainer(criteriaList);

        } catch (IOException e) {
            throw new RuntimeException(STR."Failed to read file: \{filePath}", e);
        }
    }

    private static boolean isValidLine(List<String> columns) {
        if (columns.size() < 4) {
            System.out.println(STR."Skipping malformed line: \{String.join(PIPE, columns)}");
            return false;
        }
        return true;
    }

    private static ExpectedCriteria toExpectedCriteria(List<String> columns) {
        String eligibilityKey = columns.get(1).trim();

        List<String> expectedKeywords = Arrays.stream(columns.get(2).split(COMMA))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return new ExpectedCriteria(eligibilityKey, expectedKeywords);
    }

    private static List<String> splitRespectingQuotes(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == '|' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
                continue;
            }
            current.append(c);
        }
        result.add(current.toString());
        return result;
    }
}