package io.dummyapi.models.errortype.bodynovalid;

public class BodyNoValid {
    private String error;
    private Data data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
