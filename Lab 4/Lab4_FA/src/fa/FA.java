package fa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FA {
    private List<String> states;
    private List<String> alphabet;
    private List<Transition> transitions;
    private String initialState;
    private List<String> finalStates;
    private String fileName;
    private boolean isDeterministic;

    public FA(String filename) {
        this.fileName = filename;
        this.states = new ArrayList<>();
        this.alphabet = new ArrayList<>();
        this.transitions = new ArrayList<>();
        this.finalStates = new ArrayList<>();
        this.initialState = "";
        init();
    }

    private void init() {
        try {
            for(String line: Files.readAllLines(Paths.get(fileName))) {
                parseLine(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading FA from file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Invalid FA format: " + e.getMessage());
        }
        this.isDeterministic = checkIfDeterministic();
    }

    private boolean checkIfDeterministic() {
        Map<String, Set<String>> stateSymbolMap = new HashMap<>();

        for (Transition transition : transitions) {
            String state = transition.getFrom();
            String symbol = transition.getLabel();

            // If this state already has transitions for this symbol, FA is non-deterministic
            if (stateSymbolMap.containsKey(state) && stateSymbolMap.get(state).contains(symbol)) {
                return false;
            }

            stateSymbolMap.computeIfAbsent(state, k -> new HashSet<>()).add(symbol);
        }

        return true;
    }

    public boolean isDeterministic() {
        return isDeterministic;
    }

    private void parseLine(String line) throws Exception {
        if (line.startsWith("states=")) {
            states = parseList(line);
        } else if (line.startsWith("alphabet=")) {
            alphabet = parseList(line);
        } else if (line.startsWith("transitions=")) {
            transitions = parseTransitions(line);
        } else if (line.startsWith("initial state=")) {
            initialState = line.split("=")[1].trim();
        } else if (line.startsWith("final states=")) {
            finalStates = parseList(line);
        } else {
            throw new Exception("Not known line: " + line);
        }
    }

    private List<String> parseList(String line) {
        String listContent = line.split("=")[1].trim();
        return Arrays.asList(listContent.substring(1, listContent.length() - 1).split(","));
    }

    private List<Transition> parseTransitions(String line) {
        String transitionContent = line.split("=")[1].trim();
        String[] transitions = transitionContent.substring(1, transitionContent.length() - 1).split("; *");
        List<Transition> transitionList = new ArrayList<>();
        for (String transition : transitions) {
            String[] transitionParts = transition.substring(1, transition.length() - 1).split(", *");
            transitionList.add(new Transition(transitionParts[0], transitionParts[1], transitionParts[2]));
        }
        return transitionList;
    }

    private void printListOfString(String listname, List<String> list) {
        System.out.print(listname + " = {");
        for(int i = 0; i < list.size(); i++) {
            if(i != list.size() - 1) {
                System.out.print(list.get(i) + ", ");
            } else {
                System.out.print(list.get(i));
            }
        }
        System.out.println("}");
    }

    public void printStates() {
        printListOfString("states", states);
    }

    public void printAlphabet() {
        printListOfString("alphabet", alphabet);
    }

    public void printTransitions() {
        System.out.print("transitions = {");
        for(int i = 0; i < transitions.size(); i++) {
            if(i != transitions.size() - 1) {
                System.out.print("(" + transitions.get(i).getFrom() + ", " + transitions.get(i).getTo() + ", " + transitions.get(i).getLabel() + "); ");
            } else {
                System.out.print("(" + transitions.get(i).getFrom() + ", " + transitions.get(i).getTo() + ", " + transitions.get(i).getLabel() + ")");
            }
        }
        System.out.println("}");
    }

    public void printFinalStates() {
        printListOfString("final states", finalStates);
    }

    public void printInitialState() {
        System.out.println("initial state = {" + initialState + "}");
    }

    public boolean isAcceptedByFA(String sequence) {
        if(isDeterministic) {
            String currentState = initialState;
            for(char symbol: sequence.toCharArray()) {
                String symbolValue = String.valueOf(symbol);
                boolean found = false;
                for(String alphabetSymbol: alphabet) {
                    if(symbolValue.equals(alphabetSymbol.trim())) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    System.out.println("Invalid symbol in sequence: " + symbol);
                    return false;
                }
                currentState = getNextState(currentState, symbolValue);
                if(currentState == null) {
                    return false;
                }
            }
            return finalStates.contains(currentState);
        } else {
            System.out.println("The FA is not deterministic!");
            return false;
        }
    }

    private String getNextState(String currentState, String symbol) {
        for(Transition transition: transitions) {
            if(transition.getFrom().equals(currentState) && transition.getLabel().equals(symbol)) {
                return transition.getTo();
            }
        }
        return null;
    }
}