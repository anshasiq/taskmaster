//package com.example.taskmaster.Model;
//import androidx.annotation.NonNull;
//public enum StateOfTask {
//    ASSIGNED("Assigned"),
//    IN_PROGRESS("In Progress"),
//    COMPLETED("Completed");
//
//
//
//    private final String State;
//
//    StateOfTask(String State) {
//        this.State = State;
//    }
//
//    public String getState() {
//        return State;
//    }
//
//    public static StateOfTask fromString(String possibleProductText){
//        for(StateOfTask product : StateOfTask.values()){
//            if (product.State.equals(possibleProductText)){
//                return product;
//            }
//        }
//
//        return null;
//    }
//
//    @NonNull
//    @Override
//    public String toString(){
//        if(State == null){
//            return "";
//        }
//        return State;
//    }
//}
