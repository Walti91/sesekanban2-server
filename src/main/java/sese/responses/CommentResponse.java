package sese.responses;

import sese.entities.Comment;

public class CommentResponse {

    private Long id;
    private String text;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
