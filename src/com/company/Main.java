package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    int result;

    public static void main(String[] args) throws FileNotFoundException {

        long a = System.currentTimeMillis();

        System.setIn(new FileInputStream("src/com/company/Input"));
        Scanner s = new Scanner(System.in);
        int NUMBER_OF_CASES = s.nextInt();

        ListFIFO LIST_OF_CASES = new ListFIFO();
        ListFIFO LIST_OF_PHASES_IN_CASES = new ListFIFO();
        for (int i = 0; i < NUMBER_OF_CASES; i++) {
            int NUMBER_OF_PHASES = s.nextInt();
            LIST_OF_PHASES_IN_CASES.push_end(NUMBER_OF_PHASES);
            int[][] MATRIX_OF_PARAMETERS = new int[2][NUMBER_OF_PHASES];
            for (int k = 0; k < NUMBER_OF_PHASES; k++) {
                MATRIX_OF_PARAMETERS[0][k] = s.nextInt();
                MATRIX_OF_PARAMETERS[1][k] = s.nextInt();
            }
            LIST_OF_CASES.push_end(MATRIX_OF_PARAMETERS);
        }

        System.out.println(LIST_OF_PHASES_IN_CASES.toString());
        System.out.println(LIST_OF_CASES.toString());
        Main main = new Main();

        for (int g = 0; g < NUMBER_OF_CASES; g++) {

            main.result = 1000000;

            main.solve((int) LIST_OF_PHASES_IN_CASES.getObjectByIndex(g), (int[][]) LIST_OF_CASES.getObjectByIndex(g));

            System.out.println(main.result);

            long b = System.currentTimeMillis();
            System.out.println("runtime was : " + (b - a) + " ms");

        }
    }

    void solve(int NumberOfPhases, int[][] MatrixOrcsToll) {

        //NumberOfPhases
        // MatrixOrcsToll - first NoOfOrcsInPhase / second TollToPass
        int currentPhase = 0;
        int spentSoFar = 0;
        int[] orcsInHand = {0,0,0};

        goNextPhase(NumberOfPhases, MatrixOrcsToll, currentPhase, spentSoFar, orcsInHand);
    }

    void goNextPhase(int NumberOfPhases, int[][] MatrixOrcsToll, int currentPhase, int spentSoFar, int[] orcsInHand) {
    if (currentPhase == NumberOfPhases) {
            if (spentSoFar < result) result = spentSoFar;
        } else {

                    int cashWhilePayToGo = spentSoFar + MatrixOrcsToll[1][currentPhase];
                    goNextPhase(NumberOfPhases, MatrixOrcsToll, currentPhase + 1, cashWhilePayToGo, orcsInHand);

                    int cashWhileBuying = spentSoFar + 2 * MatrixOrcsToll[1][currentPhase];
                    int [] orcsWhileAquiringOrcs = {orcsInHand[0],orcsInHand[1],orcsInHand[2] + MatrixOrcsToll[0][currentPhase]};
                    goNextPhase(NumberOfPhases, MatrixOrcsToll, currentPhase + 1, cashWhileBuying, orcsWhileAquiringOrcs);


                    int orcsToFightInCurrentPhase = MatrixOrcsToll[0][currentPhase];
                    if (orcsInHand[0] + orcsInHand[1] + orcsInHand[2] >= orcsToFightInCurrentPhase) {
                        int [] orcsAfterFight = new int [3];

                        if (orcsInHand[0] >= orcsToFightInCurrentPhase) {
                            orcsAfterFight[0] = orcsInHand[1];
                            orcsAfterFight[1] = orcsInHand[2];
                            orcsAfterFight[2] = 0;
                        }
                        else if (orcsInHand[0] + orcsInHand[1] >= orcsToFightInCurrentPhase) {
                            orcsAfterFight[0] = orcsInHand[0] + orcsInHand[1] - orcsToFightInCurrentPhase;
                            orcsAfterFight[1] = orcsInHand[2];
                            orcsAfterFight[2] = 0;
                        }
                        else if(orcsInHand[0] + orcsInHand[1] + orcsInHand[2]  >= orcsToFightInCurrentPhase) {
                            orcsAfterFight[1] = orcsInHand[0] + orcsInHand[1] + orcsInHand[2] - orcsToFightInCurrentPhase;
                            orcsAfterFight[0] = 0;
                            orcsAfterFight[2] = 0;
                        }

                        goNextPhase(NumberOfPhases, MatrixOrcsToll, currentPhase + 1, spentSoFar, orcsAfterFight);
                    }

            }
        }


        static class ListFIFO {
            int SIZE = 1;
            Object[] list = new Object[SIZE];
            int INDEX_OF_FIRST_EL = 0;
            int INDEX_OF_NEW_EL = 0;

            int length() {
                return INDEX_OF_NEW_EL - INDEX_OF_FIRST_EL;
            }

            void push_end(Object t) {
                if (INDEX_OF_NEW_EL == SIZE)
                    makeBigger();
                list[INDEX_OF_NEW_EL] = t;
                INDEX_OF_NEW_EL++;
            }

            Object getObjectByIndex(int x) {
                return list[x];
            }

            Object pop_front() {
                if (INDEX_OF_FIRST_EL < INDEX_OF_NEW_EL) {
                    INDEX_OF_FIRST_EL++;
                    return getObjectByIndex(INDEX_OF_FIRST_EL - 1);
                } else return null;
            }


            void makeBigger() {
                SIZE *= 2;
                Object[] biggerObjectList = new Object[SIZE];
                for (int a = 0; a < list.length; a++) biggerObjectList[a] = list[a];
                list = biggerObjectList;
            }

            @Override
            public String toString() {
                return "ListFIFO{" +
                        "SIZE=" + SIZE +
                        ", list=" + Arrays.deepToString(list) +
                        ", INDEX_OF_FIRST_EL=" + INDEX_OF_FIRST_EL +
                        ", INDEX_OF_NEW_EL=" + INDEX_OF_NEW_EL +
                        '}';
            }
        }
    }

