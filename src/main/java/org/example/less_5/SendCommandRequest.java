package org.example.less_5;


/**
 * {
 *   "type": "sendCommand",
 *   "recipient: "nagibator",
 *   "command": "command from nagibator" // /help, /all, ...
 * }
 */
public class SendCommandRequest extends AbstractRequest {

    public static final String TYPE = "sendCommand";

    private String recipient;
    private String command;

    public SendCommandRequest() {
        setType(TYPE);
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String message) {
        this.command = command;
    }
}

