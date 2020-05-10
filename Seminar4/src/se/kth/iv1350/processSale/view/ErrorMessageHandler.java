package se.kth.iv1350.processSale.view;

/**
 * This class is responsible for showing error messages to the user.
 */
class ErrorMessageHandler {
    
    /**
     * Displays the specified error message.
     * 
     * @param msg The error message.
     */
    void showErrorMsg(String msg) {
        StringBuilder errorMsgBuilder = new StringBuilder();
        errorMsgBuilder.append("(---User Interface---)");
        errorMsgBuilder.append("\n");
        errorMsgBuilder.append("ERROR: ");
        errorMsgBuilder.append(msg);
        errorMsgBuilder.append("\n");
        System.out.println(errorMsgBuilder);
    }
}