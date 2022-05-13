import java.util.ArrayList;
import java.util.List;

public class Epic extends AbstractTask {
    List<Integer> subTaskId = new ArrayList<>();


    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public void setSubTaskId(List<Integer> subTaskId) {
        this.subTaskId = subTaskId;
    }


    public List<Integer> getSubTaskId() {
        return subTaskId;
    }
}
