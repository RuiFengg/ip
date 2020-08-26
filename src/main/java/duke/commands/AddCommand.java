package duke.commands;

import duke.storage.Storage;
import duke.ui.Ui;
import duke.exceptions.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.TaskList;
import duke.task.Todo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {
    private Type type;

    public enum Type {
        TODO, DEADLINE, EVENT
    }

    public AddCommand(Type type, String command) {
        super(command);
        this.type = type;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        super.init(taskList, ui, storage);
        if (type == Type.TODO)
            addTodo();
        if (type == Type.DEADLINE)
            addDeadline();
        if (type == Type.EVENT)
            addEvent();
    }

    private void addTodo() {
        try {
            Todo todo = new Todo(super.command);
            super.taskList.addTask(todo);
            super.ui.printAddTaskMessage(super.taskList, todo);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addDeadline() {
        try {
            String detail = formatTimingInput("/by", super.command)[0];
            String timing = formatTimingInput("/by", super.command)[1].trim();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(timing, formatter);

            Deadline deadline = new Deadline(detail, dateTime);
            super.taskList.addTask(deadline);

            super.ui.printAddTaskMessage(super.taskList, deadline);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Please enter timing in '/by 02-30-2020 23:59' format");
        }
    }

    private void addEvent() {

        try {
            String detail = formatTimingInput("/at", super.command)[0];
            String timing = formatTimingInput("/at", super.command)[1].trim();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(timing, formatter);

            Event event = new Event(detail, dateTime);
            super.taskList.addTask(event);

            super.ui.printAddTaskMessage(super.taskList, event);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Please enter timing in '/at 02-30-2020 23:59' format");
        }
    }



    public String[] formatTimingInput(String format, String input) throws DukeException {
        if (!input.contains(format)) {
            String message = "Don't forget to add a timing in '"
                    + format + " 12-12-2020 23:59' format";
            throw new DukeException(message);
        }
        return input.trim().split(format);
    }
}

