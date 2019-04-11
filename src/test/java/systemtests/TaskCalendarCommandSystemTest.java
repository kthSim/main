package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_CALENDAR_SHOWN;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import guitests.guihandles.CalendarWindowHandle;

import org.junit.Test;

import seedu.address.logic.commands.TaskCalendarCommand;
import seedu.address.model.Model;
import seedu.address.model.datetime.DateCustom;

public class TaskCalendarCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void taskcal() {

        Model expectedModel = getModel();

        //Checks case where taskcal executed without any parameters, should show today's date and no tasks for the date
        assertFalse(CalendarWindowHandle.isWindowPresent());
        assertCommandSuccess(TaskCalendarCommand.COMMAND_WORD, expectedModel, " ");
        ModelHelper.setFilteredTaskList(expectedModel);
        assertCalendarShowing();
        assertEquals(expectedModel.getFilteredTaskList().size(), 0);

        //Checks case where taskcal is executed again
        getMainWindowHandle().focus();
        assertFalse(CalendarWindowHandle.focused());
        assertCommandSuccess(TaskCalendarCommand.COMMAND_WORD + " 15-02-2019",
                expectedModel, "15-02-2019");
        assertCalendarShowing();
        assertTrue(CalendarWindowHandle.focused());


    }

    private void assertCommandSuccess(String command, Model expectedModel, String arg) {
        String expectedResultMessage = "";
        if (CalendarWindowHandle.isWindowPresent()) {
            expectedResultMessage = MESSAGE_CALENDAR_SHOWN;
        } else if (DateCustom.isValidDate(arg)) {
            expectedResultMessage = String.format(TaskCalendarCommand.MESSAGE_DISPLAY_CALENDAR_SUCCESS, arg);
        } else {
            expectedResultMessage = String.format(String.format(TaskCalendarCommand.MESSAGE_USING_CURRENT_DATE
                    + TaskCalendarCommand.MESSAGE_DISPLAY_CALENDAR_SUCCESS, DateCustom.getToday()));
        }
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    private void assertCalendarShowing() {
        assertTrue(CalendarWindowHandle.isWindowPresent());
    }
}
