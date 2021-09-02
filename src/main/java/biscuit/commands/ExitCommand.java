package biscuit.commands;

import biscuit.storage.Storage;
import biscuit.task.TaskList;
import biscuit.ui.Ui;

/**
 * Exit command to exit Biscuit.
 */
public class ExitCommand extends Command {

    /**
     * Constructs ExitCommand class.
     *
     * @param userInputs User input array with this structure: [command, details].
     */
    public ExitCommand(String[] userInputs) {
        super(CommandType.EXIT, userInputs);
    }

    /**
     * Exits from Biscuit.
     *
     * @param taskList Task list.
     * @param ui       Ui to display.
     * @param storage  Storage to save to.
     * @return Response to user input.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return "Bye! Hope to see you again soon! 8==8";
    }
}
