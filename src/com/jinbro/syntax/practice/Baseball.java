package com.jinbro.syntax.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
/*
    [야구게임]
    - 1~9까지 서로 다른 수로 이뤄진 3자리 숫자를 맞추는 게임
    - 같은 자리에 있으면 스트라이크, 다른 자리 같은 숫자면 볼, 전혀 없으면 포볼 또는 낫싱
    - 횟수가 적게 맞추는 사람이 이김 : 기록(카운트) 표시까지

    123 -> 246
    - (2배를 한 결과가 같은지 봤을 때 같으면 3스트라이크 : 다 안봐도 됨, 둘을 덧셈한 결과와 정답의 두배가 같은지)
    => 근데 이 방법은 세개가 다 같을 때는 효율적이지만 각 자리를 다시 맞춰봐야할 때는 결국 다시 처음부터해야해서 1회 낭비가 될 수도

    123 -> 849
    - 쏘팅?하고 난뒤에 체크를 할까..?
    => 쏘팅해버리면 원래 입력값을 모르기때문에 안됨 : 결국 루프만 돌려야할 것 같긴한데..

    123 -> 321

    [알게된 것]
    1. 정답 세팅
    - 리스트에 9까지 채우고 셔플 호출 -> 길이만큼 잘라버림
    - 랜덤으로 값을 뽑는데 set 사용해서 중복값 저장못하도록, 사이즈가 길이만큼 되면 끝
 */



public class Baseball {
    public static void main(String[] args) {
        Baseball.play();
    }

    private static final int LENGTH = 3;

    private static final int CASE = 2; //STRIKE(0), BALL(1)
    private static final int STRIKE = 0;
    private static final int BALL = 1;

    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void play(){
        char[] answer, userAnswer = null;
        answer = setAnswer(LENGTH);
        int[] result = new int[CASE];
        while(result[STRIKE] != LENGTH) {
            try { userAnswer = getUserAnswer(LENGTH); }
            catch (IOException e) { e.printStackTrace(); }
            result = compareAnswer(answer, userAnswer);
            printResult(result);
        }
    }

    private static char[] setAnswer(int length){
        char[] result = new char[length];
        ArrayList<Character> suffleTarget = new ArrayList<>();
        for(int i=49; i<58; i++){ // 아스키코드(1~9) : 49 ~ 58
            suffleTarget.add((char)i);
        }
        Collections.shuffle(suffleTarget);
        for(int i=0; i<length; i++){
            result[i] = suffleTarget.get(i);
        }
        return result;
    }

    private static char[] getUserAnswer(int length) throws IOException {
        String inputData;
        while(true){
            System.out.println("숫자를 입력해주세요 ex)123");
            inputData = bufferedReader.readLine();
            /* 중복제거하는 메서드 들어가면 좋음 */
            if(inputData.length() == length){
                break;
            }
        }
        return inputData.toCharArray();
    }

    private static int[] compareAnswer(char[] answer, char[] userAnswer){
        int[] result = new int[CASE]; // 0: strike, 1: ball
        int length = userAnswer.length;
        int userAnswerIdx = 0, answerIdx = 0;
        while(userAnswerIdx < length){
            recordResult(result, userAnswerIdx, userAnswer, answerIdx, answer);
            if(answerIdx == length-1){
                userAnswerIdx++;
                answerIdx = 0;
            }
            answerIdx++;
        }
        return result;
    }

    private static void recordResult(int[] result, int userAnswerIdx, char[] userAnswer, int answerIdx, char[] answer){
        if(userAnswer[userAnswerIdx] != answer[answerIdx]){
            return;
        }

        if(userAnswerIdx == answerIdx){
            result[STRIKE]++;
        } else{
            result[BALL]++;
        }
    }


    private static void printResult(int[] result){ // result[n] -> 0: strike, 1: ball
        if(checkNothing(result)){
            System.out.println("낫씽");
            return;
        }
        System.out.println(buildResultMessage(result));
    }

    private static boolean checkNothing(int[] result){
        boolean isNothing = true; // 0이라면 아무것도 기록 안된 것이라 낫씽
        for(int i : result){
            if(i > 0){
                isNothing = false;
                break;
            }
        }
        return isNothing;
    }

    private static String buildResultMessage(int[] result){
        String[] msg = {"스트라이크", "볼"};
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<result.length; i++){
            if(result[i] != 0){
                builder.append(result[i]).append(msg[i]).append(" ");
            }
        }
        return builder.toString();
    }

}

