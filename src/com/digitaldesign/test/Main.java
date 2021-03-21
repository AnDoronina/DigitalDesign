package com.digitaldesign.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    int i = 0;
    String input;

    public Main(String input) {
        this.input = input;
    }

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Main parser;
        try {
            parser = new Main(reader.readLine());
            System.out.println(parser.parseBlock(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Boolean isLetter (char c) {
        return c >= 'a' && c <= 'z';
    }

    public Boolean isDigit (char c) {
        return c >= '0' && c <= '9';
    }

    public Boolean isEol () {
        return i >= input.length();
    }

    public void failed() throws Exception {
        throw new Exception("Unexpected symbol at pos " + i);
    }

    public char currentChar() throws Exception {
        if (isEol()) {
            failed();
        }
        return input.charAt(i);
    }

    public String parseBlock(int depth) throws Exception {
        StringBuilder result = new StringBuilder();
        while (true) {
            if (depth == 0 && isEol()) {
                return result.toString();
            }

            char c = currentChar();
            if (isLetter(c)) {
                result.append(c);
                i++;
            }
            else if (isDigit(c)) {
                result.append(parseSeq(depth));
            }
            else {
                if (depth == 0 || c != ']') {
                    failed();
                }
                i++;
                return result.toString();
            }
        }
    }
    public String parseSeq(int depth) throws Exception {
        StringBuilder multiplier = new StringBuilder();
        while (true) {
            char c = currentChar();
            if (isDigit(c)) {
                multiplier.append(c);
                i++;
            }
            else {
                break;
            }
        }
        if (multiplier.length() < 1) {
            failed();
        }
        if (currentChar() != '[') {
            failed();
        }
        i++;
        String content = parseBlock(depth + 1);
        return content.repeat(Integer.parseInt(multiplier.toString()));
    }
 }
