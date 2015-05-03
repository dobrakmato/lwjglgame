package eu.matejkormuth.game.client.content.fileformat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShaderProgram implements ToDataStreamSerializable {

    private String vertex;
    private String geometry;
    private String fragment;

    public String getVertex() {
        return vertex;
    }

    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    @Override
    public void serialize(DataOutputStream out) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void deserialize(DataInputStream in) throws IOException {
        // TODO Auto-generated method stub

    }

}
