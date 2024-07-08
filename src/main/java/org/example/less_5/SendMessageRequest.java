package org.example.less_5;

/**
 * {
 *   "type": "sendMessage",
 *   "recipient: "nagibator",
 *   "message": "text to nagibator"
 * }
 */
public class SendMessageRequest extends AbstractRequest {

  public static final String TYPE = "sendMessage";

  private String recipient;
  private String message;

  public SendMessageRequest() {
    setType(TYPE);
  }

  public String getRecipient() {
    return recipient;
  }

  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
