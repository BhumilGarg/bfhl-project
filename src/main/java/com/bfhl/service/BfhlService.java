package com.bfhl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BfhlService {

    @Value("${bfhl.official_email}")
    private String officialEmail;

    @Value("${google.gemini.api.key}")
    private String geminiApiKey;

    @Autowired
    private RestTemplate restTemplate;

    public String getOfficialEmail() {
        return officialEmail;
    }

    public List<Integer> getFibonacci(int n) {
        List<Integer> series = new ArrayList<>();
        if (n <= 0)
            return series;
        int a = 0, b = 1;
        series.add(a);
        if (n == 1)
            return series;
        series.add(b);
        for (int i = 2; i < n; i++) {
            int next = a + b;
            series.add(next);
            a = b;
            b = next;
        }
        return series;
    }

    public List<Integer> getPrimes(List<Integer> numbers) {
        return numbers.stream()
                .filter(this::isPrime)
                .collect(Collectors.toList());
    }

    private boolean isPrime(int n) {
        if (n <= 1)
            return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public int getLcm(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty())
            return 0;
        int result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, numbers.get(i));
        }
        return result;
    }

    private int lcm(int a, int b) {
        if (a == 0 || b == 0)
            return 0;
        return Math.abs(a * b) / hcf(a, b);
    }

    public int getHcf(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty())
            return 0;
        int result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            result = hcf(result, numbers.get(i));
        }
        return result;
    }

    private int hcf(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public String getAiResponse(String question) {
    try {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                + geminiApiKey;

        Map<String, Object> part = new HashMap<>();
        part.put("text", "Give a single-word answer to: " + question);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", Collections.singletonList(part));

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", Collections.singletonList(content));

        Map<String, Object> response =
                restTemplate.postForObject(url, requestBody, Map.class);

        if (response != null && response.containsKey("candidates")) {
            List<Map<String, Object>> candidates =
                    (List<Map<String, Object>>) response.get("candidates");

            if (!candidates.isEmpty()) {
                Map<String, Object> firstCandidate = candidates.get(0);
                Map<String, Object> contentMap =
                        (Map<String, Object>) firstCandidate.get("content");

                List<Map<String, Object>> parts =
                        (List<Map<String, Object>>) contentMap.get("parts");

                if (!parts.isEmpty()) {
                    String fullResponse = (String) parts.get(0).get("text");
                    return fullResponse.trim().replaceAll("[^a-zA-Z]", "");
                }
            }
        }
        return "No AI answer";

    } catch (Exception e) {
    e.printStackTrace();
    return "Error calling AI";
}

    }
}

