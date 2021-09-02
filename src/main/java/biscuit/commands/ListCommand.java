package biscuit.commands;

import biscuit.storage.Storage;
import biscuit.task.TaskList;
import biscuit.ui.Ui;

/**
 * List command to list tasks.
 */
public class ListCommand extends Command {

    /**
     * Constructs ListCommand class.
     *
     * @param userInputs User input array with this structure: [command, details].
     */
    public ListCommand(String[] userInputs) {
        super(CommandType.LIST, userInputs);
    }

    /**
     * Lists tasks.
     *
     * @param taskList Task list to add.
     * @param ui       Ui to display.
     * @param storage  Storage to save to.
     * @return Response to user input.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        StringBuilder message = new StringBuilder();
        if (taskList.isEmpty()) {
            message = new StringBuilder("List is currently empty.");
        } else {
            for (int i = 0; i < taskList.size(); i++) {
                message.append(i).append(". ").append(taskList.getTask(i)).append("\n");
            }
        }
        return message.toString();
    }
}
