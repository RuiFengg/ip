package duke.task;

import duke.exceptions.DukeException;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Represents a list that stores all tasks.
 */
public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Overloaded constructor that sets the user's task list as the task list from the database.
     *
     * @param taskList the list received from the database
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public void addTask(Task task) {
        this.taskList.add(task);
    }

    public Task deleteTask(int index) throws DukeException {
        if (isIndexInRange(index)) {
            return this.taskList.remove(index - 1);
        }

        throw new DukeException("Oh no! Task number does not exist in task list.");
    }

    public int size() {
        return this.taskList.size();
    }

    public Task getTask(int index) throws DukeException {
        if (isIndexInRange(index)) {
            return this.taskList.get(index - 1);
        }

        throw new DukeException("Oh no! Task number does not exist in task list.");
    }

    public void forEach(Consumer<Task> consumer) {
        this.taskList.forEach(consumer);
    }

    public boolean isIndexInRange(int index) {
        return index <= this.taskList.size() && index > 0;
    }

}
