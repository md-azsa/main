package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VETTECH_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddVetTechToAppointmentCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemoveVetTechFromAppointmentCommand;
import seedu.address.logic.commands.RescheduleCommand;
import seedu.address.logic.commands.RescheduleCommand.RescheduleAppointmentDescriptor;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.AppointmentUtil;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.RescheduleAppointmentDescriptorBuilder;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().buildWithRoleClient();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    //@@author Godxin-test
    @Test
    public void parseCommand_schedule() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(
                                    AppointmentUtil.getScheduleCommand(appointment));
        assertEquals(new ScheduleCommand(appointment), command);
    }

    //@@author Godxin-test
    @Test
    public void parseCommand_reschedule() throws Exception {
        Appointment appointment = new AppointmentBuilder().build();
        RescheduleAppointmentDescriptor descriptor = new RescheduleAppointmentDescriptorBuilder(appointment).build();
        RescheduleCommand command = (RescheduleCommand) parser.parseCommand(RescheduleCommand.COMMAND_WORD + " "
                + INDEX_FIRST_APPT.getOneBased() + " "
                + AppointmentUtil.getAppointmentDetails(appointment));
        assertEquals(new RescheduleCommand(INDEX_FIRST_APPT, descriptor), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().buildWithRoleClient();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_addVetTechToAppointment() throws Exception {
        AddVetTechToAppointmentCommand command = (AddVetTechToAppointmentCommand) parser
                .parseCommand(AddVetTechToAppointmentCommand.COMMAND_WORD + " " + PREFIX_VETTECH_INDEX
                + INDEX_FIRST.getOneBased() + " " + PREFIX_APPOINTMENT_INDEX
                + INDEX_FIRST.getOneBased());
        assertEquals(new AddVetTechToAppointmentCommand(INDEX_FIRST, INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_removeVetTechFromAppointment() throws Exception {
        RemoveVetTechFromAppointmentCommand command = (RemoveVetTechFromAppointmentCommand) parser.parseCommand(
                RemoveVetTechFromAppointmentCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new RemoveVetTechFromAppointmentCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " client") instanceof ListCommand);
        assertTrue(parser.parseCommand("list client") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " client") instanceof ListCommand);
        assertTrue(parser.parseCommand("ls client") instanceof ListCommand);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("rd 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("ud 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
