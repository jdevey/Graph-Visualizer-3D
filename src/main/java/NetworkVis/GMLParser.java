package NetworkVis;

import java.io.*;
import java.util.ArrayList;

public class GMLParser extends Parser {
    @Override
    public GraphState parse(String filePath) throws Exception {
        InputStream inputStream = new FileInputStream(filePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = bufferedReader.readLine();

        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        while(line != null){
            if(line.contains("node")) {
                // skip two unnecessary lines
                bufferedReader.readLine();
                bufferedReader.readLine();

                // find the label for the node
                line = bufferedReader.readLine();
                line = line.replace("label", "");
                line = line.trim();

                // create a new node and its adjacency list
                Node newNode = new Node(
                        Constants.SCREEN_WIDTH * Math.random(),
                        Constants.SCREEN_WIDTH * Math.random(),
                        Constants.SCREEN_DEPTH * Math.random(),
                        line);

                adj.add(new ArrayList<>());
                nodes.add(newNode);
            } else if(line.contains("edge")) {
                bufferedReader.readLine();
                line = bufferedReader.readLine();

                line = line.replace("source", "");
                line = line.trim();
                int source = Integer.parseInt(line);

                line = bufferedReader.readLine();
                line = line.replace("target", "");
                line = line.trim();
                Integer target = Integer.parseInt(line);

                adj.get(source).add(target);
            }
            line = bufferedReader.readLine();
        }

        return new GraphState(nodes, adj);
    }
}
