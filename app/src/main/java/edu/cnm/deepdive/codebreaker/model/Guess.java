package edu.cnm.deepdive.codebreaker.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.time.Instant;
import java.util.Date;

public class Guess {

  @Expose(serialize = false, deserialize = true)
  private final String id;

  @SerializedName("text")
  @Expose
  private final String content;

  @SerializedName("exactMatches")
  @Expose(serialize = false, deserialize = true)
  private final int correct;

  @SerializedName("nearMatches")
  @Expose(serialize = false, deserialize = true)
  private final int close;

  @SerializedName("created")
  @Expose(serialize = false, deserialize = true)
  private final Date timestamp;

  Guess(String content) {
    this.content = content;
    id = null;
    correct = 0;
    close = 0;
    timestamp = null;
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public int getCorrect() {
    return correct;
  }

  public int getClose() {
    return close;
  }

  public Date getTimestamp() {
    return timestamp;
  }
}